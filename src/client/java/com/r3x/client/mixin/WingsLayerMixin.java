package com.r3x.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.r3x.client.IcarusReModels;
import com.r3x.client.models.*;
import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.api.client.IcarusAPIClient;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import dev.cammiescorner.icarus.client.models.WingEntityModel;
import dev.cammiescorner.icarus.init.IcarusItems;
import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    private void onInit(net.minecraft.client.renderer.entity.RenderLayerParent<T, M> context, EntityModelSet loader, CallbackInfo ci) {
        this.fixedFeatheredWings = new FixedFeatheredWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FEATHERED));
        this.fixedDiscordsWings = new FixedDiscordsWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_DISCORD));
        this.fixedFlandresWings = new FixedFlandresWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_FLANDRES));
        this.fixedLeatherWings = new FixedLeatherWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LEATHER));
        this.fixedLightWings = new FixedLightWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_LIGHT));
        this.fixedZanzasWings = new FixedZanzasWingsModel<>(loader.bakeLayer(IcarusReModels.FIXED_ZANZAS));
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderOverride(PoseStack pose, MultiBufferSource bufferSource, int light, T entity,
                                  float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                                  float headYaw, float headPitch, CallbackInfo ci) {

        ItemStack stack = IcarusAPIClient.getWingsForRendering(entity);
        if (!(stack.getItem() instanceof WingItem wingItem)) return;
        if (!IcarusClient.shouldRenderWings(entity)) return;

        ci.cancel();

        WingEntityModel<T> wingModel = null;

        if (wingItem instanceof CustomTextureWingItem customWings) {
            String modelType = customWings.getCustomModelType();
            if ("discord".equals(modelType)) wingModel = this.fixedDiscordsWings;
            else if ("flandres".equals(modelType)) wingModel = this.fixedFlandresWings;
            else if ("zanzas".equals(modelType)) wingModel = this.fixedZanzasWings;
            else if ("leather".equals(modelType) || "dragon".equals(modelType)) wingModel = this.fixedLeatherWings;
            else if ("light".equals(modelType)) wingModel = this.fixedLightWings;
            else wingModel = this.fixedFeatheredWings;
        } else {
            switch (wingItem.getWingType()) {
                case FEATHERED:
                case MECHANICAL_FEATHERED:
                    wingModel = this.fixedFeatheredWings;
                    break;
                case DRAGON:
                case MECHANICAL_LEATHER:
                    wingModel = this.fixedLeatherWings;
                    break;
                case LIGHT:
                    wingModel = this.fixedLightWings;
                    break;
                case UNIQUE:
                    if (stack.getItem() == IcarusItems.FLANDRES_WINGS.get()) wingModel = this.fixedFlandresWings;
                    else if (stack.getItem() == IcarusItems.DISCORDS_WINGS.get()) wingModel = this.fixedDiscordsWings;
                    else if (stack.getItem() == IcarusItems.ZANZAS_WINGS.get()) wingModel = this.fixedZanzasWings;
                    break;
            }
        }

        if (wingModel == null) return;

        pose.pushPose();


        if (wingItem instanceof CustomTextureWingItem customWings) {

            pose.translate(0.0F, customWings.getModelOffsetY(), customWings.getModelOffsetZ() + 0.125F);
        } else {
            pose.translate(0.0F, 0.0F, 0.125F);
        }

        WingsLayer<T, M> thiz = (WingsLayer<T, M>) (Object) this;
        thiz.getParentModel().copyPropertiesTo(wingModel);
        wingModel.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        if (wingItem instanceof CustomTextureWingItem customWings) {
            int overlay = OverlayTexture.NO_OVERLAY;
            net.minecraft.client.model.geom.ModelPart leftPart = wingModel.leftWing;
            net.minecraft.client.model.geom.ModelPart rightPart = wingModel.rightWing;

            if (customWings.isSeparate()) {
                RenderType leftType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1L());
                leftPart.render(pose, bufferSource.getBuffer(leftType1), light, overlay);

                RenderType rightType1 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1R());
                rightPart.render(pose, bufferSource.getBuffer(rightType1), light, overlay);

                if (customWings.hasSecondLayer()) {
                    int lightLevel = customWings.isSecondLayerEmissive() ? 15728880 : light;

                    RenderType leftType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2L()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2L()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2L()));

                    RenderType rightType2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2R()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2R()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2R()));

                    leftPart.render(pose, bufferSource.getBuffer(leftType2), lightLevel, overlay);
                    rightPart.render(pose, bufferSource.getBuffer(rightType2), lightLevel, overlay);
                }
            }

            else {
                RenderType baseRenderType = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer1()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer1());
                leftPart.render(pose, bufferSource.getBuffer(baseRenderType), light, overlay);
                rightPart.render(pose, bufferSource.getBuffer(baseRenderType), light, overlay);

                if (customWings.hasSecondLayer()) {
                    int lightLevel = customWings.isSecondLayerEmissive() ? 15728880 : light;

                    RenderType type2 = customWings.isTranslucent() ? RenderType.entityTranslucentCull(customWings.getCustomLayer2()) :
                            (customWings.isSecondLayerEmissive() ? RenderType.eyes(customWings.getCustomLayer2()) : RenderType.entityCutoutNoCull(customWings.getCustomLayer2()));

                    leftPart.render(pose, bufferSource.getBuffer(type2), lightLevel, overlay);
                    rightPart.render(pose, bufferSource.getBuffer(type2), lightLevel, overlay);
                }
            }
        } else {

            ResourceLocation baseId = BuiltInRegistries.ITEM.getKey(wingItem).withPrefix("textures/entity/icarus/wings/");
            ResourceLocation texture1 = baseId.withSuffix(".png");
            ResourceLocation texture2 = baseId.withSuffix("_2.png");

            thiz.renderWings(wingModel, pose, bufferSource, stack, RenderType.entityTranslucent(texture1), light, -1);
            thiz.renderWings(wingModel, pose, bufferSource, stack, RenderType.entityTranslucent(texture2), light, -1);
        }

        pose.popPose();
    }
}