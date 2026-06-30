package com.r3x.icarusrewinged.client;

import com.r3x.icarusrewinged.client.models.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = "icarusrewinged", value = Dist.CLIENT)
public class IcarusReModels {

    public static final ModelLayerLocation FIXED_FEATHERED = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_feathered"), "main");
    public static final ModelLayerLocation FIXED_DISCORD = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_discord"), "main");
    public static final ModelLayerLocation FIXED_FLANDRES = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_flandres"), "main");
    public static final ModelLayerLocation FIXED_LEATHER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_leather"), "main");
    public static final ModelLayerLocation FIXED_LIGHT = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_light"), "main");
    public static final ModelLayerLocation FIXED_ZANZAS = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "fixed_zanzas"), "main");

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FIXED_FEATHERED, FixedFeatheredWingsModel::getLayerDefinition);
        event.registerLayerDefinition(FIXED_DISCORD, FixedDiscordsWingsModel::getLayerDefinition);
        event.registerLayerDefinition(FIXED_FLANDRES, FixedFlandresWingsModel::getLayerDefinition);
        event.registerLayerDefinition(FIXED_LEATHER, FixedLeatherWingsModel::getLayerDefinition);
        event.registerLayerDefinition(FIXED_LIGHT, FixedLightWingsModel::getLayerDefinition);
        event.registerLayerDefinition(FIXED_ZANZAS, FixedZanzasWingsModel::getLayerDefinition);

    }
}