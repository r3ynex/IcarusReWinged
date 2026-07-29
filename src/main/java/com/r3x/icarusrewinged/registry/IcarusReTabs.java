package com.r3x.icarusrewinged.registry;

import com.r3x.icarusrewinged.IcarusReRarity;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class IcarusReTabs {

    public static final ResourceKey<CreativeModeTab> REWINGED_TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath("icarusrewinged", "rewinged_tab")
    );

    public static final CreativeModeTab REWINGED_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            REWINGED_TAB_KEY,
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(IcarusReItems.MOD_LOGO))
                    .title(Component.translatable("creativetab.icarus_rewinged_tab"))
                    .build()
    );

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(REWINGED_TAB_KEY).register(entries -> {
            Rarity[] raritiesOrder = {
                    IcarusReRarity.MYTHIC,
                    IcarusReRarity.LEGENDARY,
                    Rarity.EPIC,
                    Rarity.RARE,
                    Rarity.UNCOMMON,
                    Rarity.COMMON
            };

            for (Rarity currentRarity : raritiesOrder) {
                for (Item item : IcarusReItems.getAllItems()) {
                    if (item != IcarusReItems.MOD_LOGO && new ItemStack(item).getRarity() == currentRarity) {
                        entries.accept(item);
                    }
                }
            }
        });
    }
}