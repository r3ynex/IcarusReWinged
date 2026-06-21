package com.r3x.icarusrewinged.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = "icarusrewinged", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeClient(),
                new ModItemModelProvider(packOutput, existingFileHelper)
        );

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(packOutput, lookupProvider, "icarusrewinged", existingFileHelper) {
            @Override protected void addTags(HolderLookup.Provider pProvider) {}
        };

        generator.addProvider(event.includeServer(), blockTagsProvider);

        generator.addProvider(
                event.includeServer(),
                new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper)
        );
    }
}