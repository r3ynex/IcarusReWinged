package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.registry.IcarusReTabs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeScreenMixin {

    @Shadow private static CreativeModeTab selectedTab;
    @Shadow private float scrollOffs;

    private static final ResourceLocation MYTHIC_BANNER = new ResourceLocation("icarusrewinged", "textures/gui/mythic_banner.png");
    private static final ResourceLocation LEGENDARY_BANNER = new ResourceLocation("icarusrewinged", "textures/gui/legendary_banner.png");
    private static final ResourceLocation EPIC_BANNER = new ResourceLocation("icarusrewinged", "textures/gui/epic_banner.png");
    private static final ResourceLocation RARE_BANNER = new ResourceLocation("icarusrewinged", "textures/gui/rare_banner.png");

    @Inject(method = "mouseScrolled(DDD)Z", at = @At("HEAD"), cancellable = true)
    private void handleMouseScroll(double mouseX, double mouseY, double delta, CallbackInfoReturnable<Boolean> cir) {
        if (selectedTab != null && selectedTab == IcarusReTabs.REWINGED_TAB.get()) {
            this.scrollOffs = (float) ((double) this.scrollOffs - delta * 0.2D);
            if (this.scrollOffs < 0.0F) this.scrollOffs = 0.0F;
            if (this.scrollOffs > 1.0F) this.scrollOffs = 1.0F;

            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
            screen.getMenu().scrollTo(this.scrollOffs);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mouseDragged(DDIDD)Z", at = @At("HEAD"), cancellable = true)
    private void handleMouseDrag(double mouseX, double mouseY, int button, double dragX, double dragY, CallbackInfoReturnable<Boolean> cir) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        if (selectedTab != null && selectedTab == IcarusReTabs.REWINGED_TAB.get()) {
            int i = screen.getGuiLeft() + 175;
            int j = screen.getGuiTop() + 18;
            int k = i + 14;
            int l = j + 112;

            if (mouseX >= (double) i && mouseX < (double) k && mouseY >= (double) j && mouseY < (double) l) {
                this.scrollOffs = ((float) mouseY - (float) j - 7.5F) / ((float) (l - j) - 15.0F);
                if (this.scrollOffs < 0.0F) this.scrollOffs = 0.0F;
                if (this.scrollOffs > 1.0F) this.scrollOffs = 1.0F;

                screen.getMenu().scrollTo(this.scrollOffs);
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At("TAIL"))
    private void drawRealBanners(GuiGraphics gui, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        // Сортируем позиции физических слотов на экране
        for (Slot slot : screen.getMenu().slots) {
            if (slot.index < 45) {
                if (selectedTab != null && selectedTab == IcarusReTabs.REWINGED_TAB.get() &&
                        slot.hasItem() && slot.getItem().is(com.r3x.icarusrewinged.registry.IcarusReItems.SECTION_BANNER.get())) {
                    // Прячем маркеры баннеров, чтобы они не рендерились как предметы и не кликались
                    setSlotPosition(slot, -2000, -2000);
                } else {
                    int originalX = left + 9 + (slot.index % 9) * 18;
                    int originalY = top + 18 + (slot.index / 9) * 18;
                    setSlotPosition(slot, originalX - left, originalY - top);
                }
            }
        }

        if (selectedTab != null && selectedTab == IcarusReTabs.REWINGED_TAB.get()) {
            // Отрисовываем текстуры плашек[cite: 7]
            for (int displayRow = 0; displayRow < 5; displayRow++) {
                int firstSlotIndex = displayRow * 9;
                Slot firstSlot = screen.getMenu().slots.get(firstSlotIndex);

                if (firstSlot.hasItem() && firstSlot.getItem().is(com.r3x.icarusrewinged.registry.IcarusReItems.SECTION_BANNER.get())) {
                    ItemStack bannerItem = firstSlot.getItem();
                    String bannerType = bannerItem.getOrCreateTag().getString("BannerType");

                    if ("empty".equals(bannerType)) {
                        continue;
                    }

                    int bannerX = left + 9;
                    int bannerY = top + 18 + (displayRow * 18);

                    if (bannerY >= top + 15 && bannerY <= top + 108) {
                        if ("mythic".equals(bannerType)) {
                            gui.blit(MYTHIC_BANNER, bannerX, bannerY, 0, 0, 160, 16, 160, 16);
                        } else if ("legendary".equals(bannerType)) {
                            gui.blit(LEGENDARY_BANNER, bannerX, bannerY, 0, 0, 160, 16, 160, 16);
                        } else if ("epic".equals(bannerType)) {
                            gui.blit(EPIC_BANNER, bannerX, bannerY, 0, 0, 160, 16, 160, 16);
                        } else if ("rare".equals(bannerType)) {
                            gui.blit(RARE_BANNER, bannerX, bannerY, 0, 0, 160, 16, 160, 16);
                        }
                    }
                }
            }
        }
    }

    private void setSlotPosition(Slot slot, int x, int y) {
        try {
            java.lang.reflect.Field xField = Slot.class.getDeclaredField("f_40220_");
            java.lang.reflect.Field yField = Slot.class.getDeclaredField("f_40221_");
            xField.setAccessible(true);
            yField.setAccessible(true);
            xField.setInt(slot, x);
            yField.setInt(slot, y);
        } catch (Exception e) {
            try {
                java.lang.reflect.Field xField = Slot.class.getDeclaredField("x");
                java.lang.reflect.Field yField = Slot.class.getDeclaredField("y");
                xField.setAccessible(true);
                yField.setAccessible(true);
                xField.setInt(slot, x);
                xField.setInt(slot, y);
            } catch (Exception ignored) {}
        }
    }
}