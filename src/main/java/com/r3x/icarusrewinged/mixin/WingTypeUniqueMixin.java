package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.item.CustomTextureWingItem;
import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(
        value = WingItem.WingType.class,
        targets = {
                "dev.cammiescorner.icarus.item.WingItem$WingType$1",
                "dev.cammiescorner.icarus.item.WingItem$WingType$2",
                "dev.cammiescorner.icarus.item.WingItem$WingType$3",
                "dev.cammiescorner.icarus.item.WingItem$WingType$4",
                "dev.cammiescorner.icarus.item.WingItem$WingType$5",
                "dev.cammiescorner.icarus.item.WingItem$WingType$6",
                "dev.cammiescorner.icarus.item.WingItem$WingType$7",
                "dev.cammiescorner.icarus.item.WingItem$WingType$8"
        }
)
public class WingTypeUniqueMixin {

    @Inject(
            method = "getTextureLayer1",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onGetLayer1(ItemStack stack, CallbackInfoReturnable<ResourceLocation> cir) {
        if (stack.getItem() instanceof CustomTextureWingItem customWings) {
            cir.setReturnValue(customWings.getCustomLayer1());
        }
    }

    @Inject(
            method = "getTextureLayer2",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            remap = false
    )
    private void onGetLayer2(ItemStack stack, CallbackInfoReturnable<ResourceLocation> cir) {
        if (stack.getItem() instanceof CustomTextureWingItem customWings) {
            cir.setReturnValue(customWings.getCustomLayer2());
        }
    }
}