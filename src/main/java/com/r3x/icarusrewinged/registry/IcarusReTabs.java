package com.r3x.icarusrewinged.registry;

import com.r3x.icarusrewinged.IcarusReRarity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IcarusReTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "icarusrewinged");

    public static final RegistryObject<CreativeModeTab> REWINGED_TAB = CREATIVE_MODE_TABS.register("rewinged_tab",
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
                                    if (item.getRarity(new ItemStack(item)) == currentRarity) {
                                        output.accept(item); // Просто кладем предмет
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