package com.r3x.client;

import net.fabricmc.api.ClientModInitializer;

public class IcarusReWingedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IcarusReModels.registerLayerDefinitions();
    }
}