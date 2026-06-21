package com.r3x.icarusrewinged;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import com.r3x.icarusrewinged.registry.IcarusReTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod("icarusrewinged")
public class IcarusReWinged {
    public IcarusReWinged() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        IcarusReItems.ITEMS.register(modEventBus);

        IcarusReTabs.register(modEventBus);
    }

}
