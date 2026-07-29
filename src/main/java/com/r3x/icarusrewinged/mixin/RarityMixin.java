package com.r3x.icarusrewinged.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.UnaryOperator;

@Mixin(Rarity.class)
enum RarityMixin {

    ICARUSREWINGED_LEGENDARY(4, "icarusrewinged_legendary", ChatFormatting.GOLD),
    ICARUSREWINGED_MYTHIC(5, "icarusrewinged_mythic", ChatFormatting.RED);

    @Shadow
    private RarityMixin(int j, String string2, ChatFormatting chatFormatting) {
    }
}