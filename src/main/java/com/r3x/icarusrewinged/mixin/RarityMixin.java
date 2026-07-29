package com.r3x.icarusrewinged.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Rarity.class)
enum RarityMixin {
    ICARUSREWINGED_LEGENDARY(ChatFormatting.GOLD),
    ICARUSREWINGED_MYTHIC(ChatFormatting.DARK_RED);

    @Shadow
    RarityMixin(ChatFormatting color) {}
}