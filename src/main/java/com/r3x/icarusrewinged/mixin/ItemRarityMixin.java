package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemRarityMixin {

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void changeIcarusWingsRarity(ItemStack stack, CallbackInfoReturnable<Rarity> cir) {
        Item item = (Item) (Object) this;

        if (item instanceof WingItem wingItem) {
            if (!(wingItem instanceof CustomTextureWingItem)) {

                ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item);

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