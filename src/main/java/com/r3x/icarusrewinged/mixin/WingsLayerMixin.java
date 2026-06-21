package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.client.IcarusReModels;
import com.r3x.icarusrewinged.client.models.*;
import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import dev.cammiescorner.icarus.client.models.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WingsLayer.class)
public abstract class WingsLayerMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Unique private FixedFeatheredWingsModel<T> fixedFeatheredWings;
    @Unique private FixedDiscordsWingsModel<T> fixedDiscordsWings;
    @Unique private FixedFlandresWingsModel<T> fixedFlandresWings;
    @Unique private FixedLeatherWingsModel<T> fixedLeatherWings;
    @Unique private FixedLightWingsModel<T> fixedLightWings;
    @Unique private FixedZanzasWingsModel<T> fixedZanzasWings;

    // ФИКС: Меняем тип на родительский класс оригинального Икаруса, чтобы он принимал ЛЮБЫЕ модели крыльев
    @Unique private WingEntityModel<T> currentRenderModel;


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(RenderLayerParent<T, M> context, EntityModelSet loader, CallbackInfo ci) {
        this.fixedFeatheredWings = new FixedFeatheredWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FEATHERED));
        this.fixedDiscordsWings = new FixedDiscordsWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_DISCORD));
        this.fixedFlandresWings = new FixedFlandresWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FLANDRES));
        this.fixedLeatherWings = new FixedLeatherWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LEATHER));
        this.fixedLightWings = new FixedLightWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LIGHT));
        this.fixedZanzasWings = new FixedZanzasWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_ZANZAS));
    }

    // =========================================================================
    // ТОЧКА 1: СМЕЩАЕМ ВСЮ МАТРИЦУ РЕНДЕРА ИКАРА В САМОМ НАЧАЛЕ (До рендера модели)
    // =========================================================================
    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            remap = false
    )
    private void onRenderHead(com.mojang.blaze3d.vertex.PoseStack matrices,
                              net.minecraft.client.renderer.MultiBufferSource vertexConsumers,
                              int light, T entity, float limbAngle, float limbDistance,
                              float tickDelta, float animationProgress, float headYaw, float headPitch,
                              CallbackInfo ci) {

        ItemStack stack = dev.cammiescorner.icarus.api.client.IcarusAPIClient.getWingsForRendering(entity);

        // Если игрок надел наши кастомные крылья, сдвигаем весь PoseStack по Y на лету!
        if (stack.getItem() instanceof CustomTextureWingItem customWings) {
            matrices.translate(0.0D, customWings.getModelOffsetY(), customWings.getModelOffsetZ());
        }
    }

    // Select Model
    @ModifyVariable(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "STORE"),
            ordinal = 0,
            remap = false
    )
    private WingEntityModel<T> swapWingModel(WingEntityModel<T> original,
                                             com.mojang.blaze3d.vertex.PoseStack matrices,
                                             net.minecraft.client.renderer.MultiBufferSource vertexConsumers,
                                             int light, T entity) {

        this.currentRenderModel = null;
        if (original == null) return null;

        WingEntityModel<T> swapped = original;
        ItemStack stack = dev.cammiescorner.icarus.api.client.IcarusAPIClient.getWingsForRendering(entity);

        if (stack.getItem() instanceof CustomTextureWingItem customWings) {
            String modelType = customWings.getCustomModelType();

            if ("discord".equals(modelType)) {
                swapped = this.fixedDiscordsWings;
            }
            else if ("flandres".equals(modelType)) {
                swapped = this.fixedFlandresWings;
            }
            else if ("zanzas".equals(modelType)) {
                swapped = this.fixedZanzasWings;
            }
            else if ("leather".equals(modelType) || "dragon".equals(modelType)) {
                swapped = this.fixedLeatherWings;
            }
            else if ("light".equals(modelType)) {
                swapped = this.fixedLightWings;
            }
            else {
                swapped = this.fixedFeatheredWings;
            }
        } else {
            if (original instanceof FeatheredWingsModel) swapped = this.fixedFeatheredWings;
            else if (original instanceof LeatherWingsModel)   swapped = this.fixedLeatherWings;
            else if (original instanceof LightWingsModel)     swapped = this.fixedLightWings;
        }

        // ФИКС: Безопасно сохраняем любую из наших моделей для рендера кастомных слоев текстур
        if (swapped != null) {
            this.currentRenderModel = swapped;
        }

        return swapped;
    }

    // Draw Separate and Custom Layers (ОЧИЩЕННЫЙ И ИСПРАВЛЕННЫЙ)
    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = {
                    @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"),
                    @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;m_85849_()V")
            },
            remap = false
    )
    private void onRenderSeparateWings(com.mojang.blaze3d.vertex.PoseStack matrices,
                                       net.minecraft.client.renderer.MultiBufferSource vertexConsumers,
                                       int light, T entity, float limbAngle, float limbDistance,
                                       float tickDelta, float animationProgress, float headYaw, float headPitch,
                                       CallbackInfo ci) {

        ItemStack stack = dev.cammiescorner.icarus.api.client.IcarusAPIClient.getWingsForRendering(entity);

        if (this.currentRenderModel != null && stack.getItem() instanceof CustomTextureWingItem customWings) {
            int overlay = OverlayTexture.NO_OVERLAY;
            String modelType = customWings.getCustomModelType();

            ModelPart leftPart = this.currentRenderModel.leftWing;
            ModelPart rightPart = this.currentRenderModel.rightWing;

            RenderType baseRenderType = customWings.isTranslucent() ?
                    RenderType.entityTranslucentCull(customWings.getCustomLayer1()) :
                    RenderType.entityCutoutNoCull(customWings.getCustomLayer1());

            // ==========================================================
            // 1. РАЗДЕЛЬНЫЕ КРЫЛЬЯ (isSeparate = true)
            // ==========================================================
            if (customWings.isSeparate()) {
                // Проверяем флаг полупрозрачности для левой и правой стороны отдельно
                RenderType leftType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1L());
                leftPart.render(matrices, vertexConsumers.getBuffer(leftType1), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

                RenderType rightType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1R());
                rightPart.render(matrices, vertexConsumers.getBuffer(rightType1), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

                if (customWings.hasSecondLayer()) {
                    int lightLevel = customWings.isSecondLayerEmissive() ? 15728880 : light;

                    // Для второго слоя: если он полупрозрачный — даем translucent, если светящийся — eyes, иначе cutout
                    RenderType leftType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2L()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2L()));

                    RenderType rightType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2R()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2R()));

                    leftPart.render(matrices, vertexConsumers.getBuffer(leftType2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    rightPart.render(matrices, vertexConsumers.getBuffer(rightType2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            // ==========================================================
            // 2. ОДИНАКОВЫЕ КРЫЛЬЯ (isSeparate = false)
            // ==========================================================
            else {
                leftPart.render(matrices, vertexConsumers.getBuffer(baseRenderType), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                rightPart.render(matrices, vertexConsumers.getBuffer(baseRenderType), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

                if (customWings.hasSecondLayer()) {
                    int lightLevel = customWings.isSecondLayerEmissive() ? 15728880 : light;

                    RenderType type2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2()));

                    leftPart.render(matrices, vertexConsumers.getBuffer(type2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    rightPart.render(matrices, vertexConsumers.getBuffer(type2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}