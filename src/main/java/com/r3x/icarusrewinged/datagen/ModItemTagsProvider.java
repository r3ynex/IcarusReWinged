package com.r3x.icarusrewinged.datagen;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, blockTags, "icarusrewinged", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        TagKey<Item> ICARUS_WINGS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("icarus", "wings"));

        var icarusBuilder = this.tag(ICARUS_WINGS);

        IcarusReItems.ITEMS.getEntries().forEach(registryObject -> {
            Item item = registryObject.get();

            if (item != IcarusReItems.MOD_LOGO.get()) {

                icarusBuilder.add(item);
            }
        });
    }
}