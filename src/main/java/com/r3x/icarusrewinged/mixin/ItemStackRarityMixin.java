package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.item.CustomTextureWingItem; //[cite: 1]
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackRarityMixin {

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void onGetRarityOverride(CallbackInfoReturnable<Rarity> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (stack.getItem() instanceof CustomTextureWingItem customWing) {
            Rarity customRarity = customWing.getCustomRarity();
            if (customRarity != null) {

                cir.setReturnValue(customRarity);
            }
        }
    }
}