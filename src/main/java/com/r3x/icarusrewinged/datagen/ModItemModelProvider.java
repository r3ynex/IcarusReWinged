package com.r3x.icarusrewinged.datagen;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class ModItemModelProvider extends FabricModelProvider {

    public ModItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModels) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModels) {
        for (Item item : IcarusReItems.getAllItems()) {
            itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }
    }
}