package com.r3x.icarusrewinged.registry;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import java.util.function.UnaryOperator;

public class IcarusReEnumParams {
    public static final EnumProxy<Rarity> LEGENDARY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "icarusrewinged:legendary",
            (UnaryOperator<Style>) style -> style.withColor(TextColor.fromRgb(0xFFAA00))
    );

    public static final EnumProxy<Rarity> MYTHIC_PROXY = new EnumProxy<>(
            Rarity.class, -1, "icarusrewinged:mythic",
            (UnaryOperator<Style>) style -> style.withColor(TextColor.fromRgb(0xD90033))
    );
}