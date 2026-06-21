package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.registry.IcarusReTabs;
import com.r3x.icarusrewinged.mixin_interface.MenuExtension;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class CreativeMenuMixin implements MenuExtension {

    @Shadow protected abstract int getRowIndexForScroll(float f);

    // Обычное, не статическое приватное поле — полностью безопасно
    @Unique
    private int icarus$currentRow = 0;

    @Inject(method = "scrollTo", at = @At("HEAD"))
    private void icarus$scrollTo(final float f, final CallbackInfo ci) {
        if (getSelectedTab() != null && getSelectedTab() == IcarusReTabs.REWINGED_TAB.get()) {
            this.icarus$currentRow = this.getRowIndexForScroll(f);
        }
    }

    // Реализуем метод из нашего интерфейса-расширения
    @Override
    public int icarus$getCurrentRow() {
        return this.icarus$currentRow;
    }

    @Unique
    private static net.minecraft.world.item.CreativeModeTab getSelectedTab() {
        try {
            java.lang.reflect.Field field = CreativeModeInventoryScreen.class.getDeclaredField("selectedTab");
            field.setAccessible(true);
            return (net.minecraft.world.item.CreativeModeTab) field.get(null);
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = CreativeModeInventoryScreen.class.getDeclaredField("f_98528_");
                field.setAccessible(true);
                return (net.minecraft.world.item.CreativeModeTab) field.get(null);
            } catch (Exception ignored) {}
        }
        return null;
    }
}