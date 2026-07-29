package com.r3x.client;

import com.r3x.client.models.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class IcarusReModels {

    public static final ModelLayerLocation FIXED_FEATHERED = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_feathered"), "main");
    public static final ModelLayerLocation FIXED_DISCORD = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_discord"), "main");
    public static final ModelLayerLocation FIXED_FLANDRES = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_flandres"), "main");
    public static final ModelLayerLocation FIXED_LEATHER = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_leather"), "main");
    public static final ModelLayerLocation FIXED_LIGHT = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_light"), "main");
    public static final ModelLayerLocation FIXED_ZANZAS = new ModelLayerLocation(
            ResourceLocation.tryBuild("icarusrewinged", "fixed_zanzas"), "main");

    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(FIXED_FEATHERED, FixedFeatheredWingsModel::getLayerDefinition);
        EntityModelLayerRegistry.registerModelLayer(FIXED_DISCORD, FixedDiscordsWingsModel::getLayerDefinition);
        EntityModelLayerRegistry.registerModelLayer(FIXED_FLANDRES, FixedFlandresWingsModel::getLayerDefinition);
        EntityModelLayerRegistry.registerModelLayer(FIXED_LEATHER, FixedLeatherWingsModel::getLayerDefinition);
        EntityModelLayerRegistry.registerModelLayer(FIXED_LIGHT, FixedLightWingsModel::getLayerDefinition);
        EntityModelLayerRegistry.registerModelLayer(FIXED_ZANZAS, FixedZanzasWingsModel::getLayerDefinition);
    }
}