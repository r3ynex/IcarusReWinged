package com.r3x.icarusrewinged;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import com.r3x.icarusrewinged.registry.IcarusReTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("icarusrewinged")
public class IcarusReWinged {

    public IcarusReWinged(IEventBus modEventBus) {

        IcarusReItems.ITEMS.register(modEventBus);
        IcarusReTabs.register(modEventBus);
    }
}
