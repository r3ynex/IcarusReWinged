package com.r3x.icarusrewinged.registry;

import com.r3x.icarusrewinged.IcarusReRarity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IcarusReTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "icarusrewinged");

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REWINGED_TAB = CREATIVE_MODE_TABS.register("rewinged_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(IcarusReItems.MOD_LOGO.get()))
                    .title(Component.translatable("creativetab.icarus_rewinged_tab"))

                    // // Add items
                    .displayItems((parameters, output) -> {
                        Rarity[] raritiesOrder = {
                                IcarusReRarity.MYTHIC,
                                IcarusReRarity.LEGENDARY,
                                Rarity.EPIC,
                                Rarity.RARE,
                                Rarity.UNCOMMON,
                                Rarity.COMMON
                        };

                        for (Rarity currentRarity : raritiesOrder) {
                            IcarusReItems.ITEMS.getEntries().forEach(registryObject -> {
                                var item = registryObject.get();
                                if (item != IcarusReItems.MOD_LOGO.get()) {
                                    if (new ItemStack(item).getRarity() == currentRarity) {
                                        output.accept(item);
                                    }
                                }
                            });
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}