package com.r3x.icarusrewinged.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.r3x.icarusrewinged.client.IcarusReModels;
import com.r3x.icarusrewinged.client.models.*;
import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.api.client.IcarusAPIClient;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import dev.cammiescorner.icarus.client.models.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
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

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(RenderLayerParent<T, M> context, EntityModelSet loader, CallbackInfo ci) {
        this.fixedFeatheredWings = new FixedFeatheredWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FEATHERED));
        this.fixedDiscordsWings = new FixedDiscordsWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_DISCORD));
        this.fixedFlandresWings = new FixedFlandresWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FLANDRES));
        this.fixedLeatherWings = new FixedLeatherWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LEATHER));
        this.fixedLightWings = new FixedLightWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LIGHT));
        this.fixedZanzasWings = new FixedZanzasWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_ZANZAS));
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )

    private void onRenderOverride(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity,
                                  float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                                  float headYaw, float headPitch, CallbackInfo ci) {

        ItemStack stack = IcarusAPIClient.getWingsForRendering(entity);

        // if Icarus Wings
        if (!(stack.getItem() instanceof CustomTextureWingItem customWings)) {
            return;
        }

        // if IRW wings
        ci.cancel();

        String modelType = customWings.getCustomModelType();
        WingEntityModel<T> wingModel;
        if ("discord".equals(modelType)) wingModel = this.fixedDiscordsWings;
        else if ("flandres".equals(modelType)) wingModel = this.fixedFlandresWings;
        else if ("zanzas".equals(modelType)) wingModel = this.fixedZanzasWings;
        else if ("leather".equals(modelType) || "dragon".equals(modelType)) wingModel = this.fixedLeatherWings;
        else if ("light".equals(modelType)) wingModel = this.fixedLightWings;
        else wingModel = this.fixedFeatheredWings;

        matrices.pushPose();
        matrices.translate(0.0F, customWings.getModelOffsetY(), customWings.getModelOffsetZ() + 0.125F);

        WingsLayer<T, M> thiz = (WingsLayer<T, M>) (Object) this;
        thiz.getParentModel().copyPropertiesTo(wingModel);
        wingModel.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        int overlay = OverlayTexture.NO_OVERLAY;
        ModelPart leftPart = wingModel.leftWing;
        ModelPart rightPart = wingModel.rightWing;

        if (customWings.isSeparate()) {
            RenderType leftType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1L());
            leftPart.render(matrices, vertexConsumers.getBuffer(leftType1), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

            RenderType rightType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1R());
            rightPart.render(matrices, vertexConsumers.getBuffer(rightType1), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

            if (customWings.hasSecondLayer()) {
                int lightLevel = customWings.isSecondLayerEmissive() ? 15728880 : light;

                RenderType leftType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2L()) :
                        (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2L()));
                RenderType rightType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2R()) :
                        (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2R()));

                leftPart.render(matrices, vertexConsumers.getBuffer(leftType2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                rightPart.render(matrices, vertexConsumers.getBuffer(rightType2), lightLevel, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        } else {
            RenderType baseRenderType = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1());
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

        matrices.popPose();
    }
}