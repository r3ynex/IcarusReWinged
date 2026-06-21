package com.r3x.icarusrewinged.mixin;

import com.r3x.icarusrewinged.registry.IcarusReTabs;
import com.r3x.icarusrewinged.registry.IcarusReItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Mixin(CreativeModeTab.class)
public class CreativeTabMixin {

    @Shadow private Collection<ItemStack> displayItems;

    @Inject(method = "buildContents", at = @At("TAIL"))
    private void icarus$buildContents(CreativeModeTab.ItemDisplayParameters parameters, CallbackInfo ci) {
        CreativeModeTab self = (CreativeModeTab) (Object) this;

        if (self == IcarusReTabs.REWINGED_TAB.get()) {
            List<ItemStack> originalItems = new ArrayList<>();

            // Фильтруем от случайных дубликатов маркеров
            for (ItemStack stack : this.displayItems) {
                if (!stack.is(IcarusReItems.SECTION_BANNER.get()) && !stack.is(IcarusReItems.MOD_LOGO.get())) {
                    originalItems.add(stack);
                }
            }

            // ЖЕСТКАЯ СОРТИРОВКА: Группируем по редкости от высшей к низшей (Mythic -> Legendary -> Epic -> Rare)
            originalItems.sort((a, b) -> Integer.compare(b.getRarity().ordinal(), a.getRarity().ordinal()));

            List<ItemStack> structuredItems = new ArrayList<>();
            Rarity lastRarity = null;
            int slotsInCurrentRow = 0;

            for (ItemStack stack : originalItems) {
                Rarity currentRarity = stack.getRarity();

                if (currentRarity != lastRarity) {
                    lastRarity = currentRarity;

                    // Закрываем предыдущий ряд пустышками
                    if (slotsInCurrentRow > 0) {
                        int rowsOccupied = (slotsInCurrentRow + 8) / 9;
                        int totalSkippedSlots = (rowsOccupied * 9) - slotsInCurrentRow;
                        for (int i = 0; i < totalSkippedSlots; i++) {
                            ItemStack dummy = new ItemStack(IcarusReItems.SECTION_BANNER.get());
                            dummy.getOrCreateTag().putString("BannerType", "empty");
                            structuredItems.add(dummy);
                        }
                        slotsInCurrentRow = 0;
                    }

                    // Создаем ровно 9 слотов-маркеров под баннер новой редкости
                    for (int i = 0; i < 9; i++) {
                        ItemStack bannerMarker = new ItemStack(IcarusReItems.SECTION_BANNER.get());
                        bannerMarker.getOrCreateTag().putString("BannerType", currentRarity.name().toLowerCase());
                        structuredItems.add(bannerMarker);
                    }
                }

                structuredItems.add(stack);
                slotsInCurrentRow++;
            }

            this.displayItems.clear();
            this.displayItems.addAll(structuredItems);
            icarus$tryUpdateSearchTab(self, structuredItems);
        }
    }

    @Unique
    private void icarus$tryUpdateSearchTab(CreativeModeTab tab, List<ItemStack> items) {
        String[] possibleNames = {"displayItemsSearchTab", "searchTabDisplayItems", "f_244510_"};
        for (String fieldName : possibleNames) {
            try {
                java.lang.reflect.Field field = CreativeModeTab.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Collection<ItemStack> searchList = (Collection<ItemStack>) field.get(tab);
                if (searchList != null) {
                    searchList.clear();
                    searchList.addAll(items);
                    break;
                }
            } catch (Exception ignored) {}
        }
    }
}