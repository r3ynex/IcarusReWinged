package com.r3x.icarusrewinged.datagen;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "icarusrewinged", existingFileHelper);
    }

    @Override
    protected void registerModels() {

        IcarusReItems.ITEMS.getEntries().forEach(registryObject -> {
            String itemName = registryObject.getId().getPath();

            withExistingParent(itemName, "item/generated")
                    .texture("layer0", modLoc("item/" + itemName));
        });
    }
}