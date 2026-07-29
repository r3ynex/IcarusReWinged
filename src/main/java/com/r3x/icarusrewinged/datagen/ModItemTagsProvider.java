package com.r3x.icarusrewinged.datagen;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<Item> ICARUS_WINGS = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath("icarus", "wings"));
        TagKey<Item> ICARUS_MELTS = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath("icarus", "melts"));

        var wingsBuilder = getOrCreateTagBuilder(ICARUS_WINGS);
        var meltsBuilder = getOrCreateTagBuilder(ICARUS_MELTS);

        for (Item item : IcarusReItems.getAllItems()) {
            if (item != IcarusReItems.MOD_LOGO) {
                wingsBuilder.add(item);
                meltsBuilder.add(item);
            }
        }
    }
}