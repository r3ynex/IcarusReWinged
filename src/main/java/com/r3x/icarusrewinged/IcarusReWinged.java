package com.r3x.icarusrewinged;

import com.r3x.icarusrewinged.registry.IcarusReItems;
import com.r3x.icarusrewinged.registry.IcarusReTabs;
import net.fabricmc.api.ModInitializer;

public class IcarusReWinged implements ModInitializer {

    @Override
    public void onInitialize() {
        IcarusReItems.register();
        IcarusReTabs.register();
    }
}