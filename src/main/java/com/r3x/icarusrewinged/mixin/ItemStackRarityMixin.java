package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackRarityMixin {

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void changeIcarusWingsRarity(CallbackInfoReturnable<Rarity> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        Item item = stack.getItem();

        if (item instanceof WingItem wingItem) {
            if (!(wingItem instanceof CustomTextureWingItem)) {

                ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(item);

                if (registryName != null) {
                    String itemName = registryName.getPath();

                    if (itemName.equals("discords_wings") ||
                            itemName.equals("flandres_wings") ||
                            itemName.equals("zanzas_wings")) {

                        cir.setReturnValue(Rarity.EPIC);
                    }
                    else {
                        cir.setReturnValue(Rarity.UNCOMMON);
                    }
                }
            }
        }
    }
}