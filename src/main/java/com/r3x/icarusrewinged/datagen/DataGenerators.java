package com.r3x.icarusrewinged.datagen;

import com.r3x.icarusrewinged.datagen.ModItemModelProvider;
import com.r3x.icarusrewinged.datagen.ModItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModItemModelProvider::new);
        pack.addProvider(ModItemTagsProvider::new);
    }
}