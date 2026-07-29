package com.r3x.icarusrewinged.registry;

import com.r3x.icarusrewinged.item.CustomTextureWingItem;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;

import static com.r3x.icarusrewinged.IcarusReRarity.LEGENDARY;
import static com.r3x.icarusrewinged.IcarusReRarity.MYTHIC;

public class IcarusReItems {

    private static final List<Item> ALL_ITEMS = new ArrayList<>();

    private static <T extends Item> T register(String name, T item) {
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.tryBuild("icarusrewinged", name),
                item
        );
        ALL_ITEMS.add(item);
        return item;
    }

    public static List<Item> getAllItems() {
        return ALL_ITEMS;
    }

    // Mod Icon
    public static final Item MOD_LOGO = register("mod_logo",
            new CustomTextureWingItem("mod_logo", "feathered", false, Rarity.COMMON, false, false, false, 0.0F, 0.0F));

    // // // Wings Register

    // // LEGENDARY

    // FEATHERED
    public static final Item FEATHERED_SCULK_WINGS = register("feathered_sculk_wings",
            new CustomTextureWingItem("feathered_sculk", "feathered", true, 3, false, LEGENDARY, false, true, false, 0.0F, 0.0F));
    public static final Item FEATHERED_PIVO_WINGS = register("feathered_pivo_wings",
            new CustomTextureWingItem("feathered_pivo", "feathered", false, 3, false, LEGENDARY, false, false, true, 0.0F, 0.0F));

    // MEMBRANE
    public static final Item MEMBRANE_BONE_WINGS = register("membrane_bone_wings",
            new CustomTextureWingItem("membrane_bone", "leather", false, 4, false, LEGENDARY, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_THE_END_WINGS = register("membrane_the_end_wings",
            new CustomTextureWingItem("membrane_the_end", "leather", true, 3,false, LEGENDARY, false, true, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_UNDEAD_WINGS = register("membrane_undead_wings",
            new CustomTextureWingItem("membrane_undead", "leather", false, LEGENDARY, true, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_STEAMPUNK_WINGS = register("membrane_steampunk_wings",
            new CustomTextureWingItem("membrane_steampunk", "leather", true, 5, true, LEGENDARY, false, true, false, 0.0F, 0.0F));

    // ZANZAS
    public static final Item ZANZAS_BUTTERFLY_PURPLE_WINGS = register("zanzas_butterfly_purple_wings",
            new CustomTextureWingItem("zanzas_butterfly_purple", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_DARK_WINGS = register("zanzas_butterfly_dark_wings",
            new CustomTextureWingItem("zanzas_butterfly_dark", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_DARKPINK_WINGS = register("zanzas_butterfly_darkpink_wings",
            new CustomTextureWingItem("zanzas_butterfly_darkpink", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_PINK_WINGS = register("zanzas_butterfly_pink_wings",
            new CustomTextureWingItem("zanzas_butterfly_pink", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_GREEN_WINGS = register("zanzas_butterfly_green_wings",
            new CustomTextureWingItem("zanzas_butterfly_green", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_YELLOW_WINGS = register("zanzas_butterfly_yellow_wings",
            new CustomTextureWingItem("zanzas_butterfly_yellow", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));
    public static final Item ZANZAS_BUTTERFLY_BLUE_WINGS = register("zanzas_butterfly_blue_wings",
            new CustomTextureWingItem("zanzas_butterfly_blue", "zanzas", false, Rarity.RARE, false, false, false, 0.35F, 0.1F));

    // DISCORD
    public static final Item DISCORD_HIKARITOYAMI_WINGS = register("discord_hikaritoyami_wings",
            new CustomTextureWingItem("discord_hikaritoyami", "discord", false, 4, false, LEGENDARY, false, false, false, 0.0F, 0.0F));


    // // LEGENDARY END

    // // EPIC
    // FEATHERED
    public static final Item FEATHERED_CHERRY_WINGS = register("feathered_cherry_wings",
            new CustomTextureWingItem("feathered_cherry", "feathered", true, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_SAND_WINGS = register("feathered_sand_wings",
            new CustomTextureWingItem("feathered_sand", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_SUNFLOWER_WINGS = register("feathered_sunflower_wings",
            new CustomTextureWingItem("feathered_sunflower", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_SNOWY_WINGS = register("feathered_snowy_wings",
            new CustomTextureWingItem("feathered_snowy", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_FLAME_WINGS = register("feathered_flame_wings",
            new CustomTextureWingItem("feathered_flame", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_MIDNIGHT_WINGS = register("feathered_midnight_wings",
            new CustomTextureWingItem("feathered_midnight", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_CLEARSKY_WINGS = register("feathered_clearsky_wings",
            new CustomTextureWingItem("feathered_clearsky", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_SEAFOAM_WINGS = register("feathered_seafoam_wings",
            new CustomTextureWingItem("feathered_seafoam", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_DARKFOREST_WINGS = register("feathered_darkforest_wings",
            new CustomTextureWingItem("feathered_darkforest", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_SOULFLAME_WINGS = register("feathered_soulflame_wings",
            new CustomTextureWingItem("feathered_soulflame", "feathered", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));

    // MEMBRANE
    public static final Item MEMBRANE_COPPER_WINGS = register("membrane_copper_wings",
            new CustomTextureWingItem("membrane_copper", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_COPPER_GRID_WINGS = register("membrane_copper_grid_wings",
            new CustomTextureWingItem("membrane_copper_grid", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_DEMONIC_WINGS = register("membrane_demonic_wings",
            new CustomTextureWingItem("membrane_demonic", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_CHAIN_MAIL_WINGS = register("membrane_chain_mail_wings",
            new CustomTextureWingItem("membrane_chain_mail", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GEMS_WINGS = register("membrane_gems_wings",
            new CustomTextureWingItem("membrane_gems", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_SPIDER_WEB_WINGS = register("membrane_spider_web_wings",
            new CustomTextureWingItem("membrane_spider_web", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_SCALY_WINGS = register("membrane_scaly_wings",
            new CustomTextureWingItem("membrane_scaly", "leather", false, Rarity.EPIC, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GHOSTLY_WINGS = register("membrane_ghostly_wings",
            new CustomTextureWingItem("membrane_ghostly", "leather", false, Rarity.EPIC, false, false, true, 0.0F, 0.0F));
    public static final Item MEMBRANE_SLIME_WINGS = register("membrane_slime_wings",
            new CustomTextureWingItem("membrane_slime", "leather", false, Rarity.EPIC, false, false, true, 0.0F, 0.0F));
    public static final Item MEMBRANE_HONEY_WINGS = register("membrane_honey_wings",
            new CustomTextureWingItem("membrane_honey", "leather", false, Rarity.EPIC, false, false, true, 0.0F, 0.0F));
    // // EPIC END

    // // RARE
    // FEATHERED
    public static final Item FEATHERED_GRADIENT_WHITE_WINGS = register("feathered_gradient_white_wings",
            new CustomTextureWingItem("feathered_gradient_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_BLACK_WINGS = register("feathered_gradient_black_wings",
            new CustomTextureWingItem("feathered_gradient_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_BROWN_WINGS = register("feathered_gradient_brown_wings",
            new CustomTextureWingItem("feathered_gradient_brown", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_NEON_WINGS = register("feathered_gradient_neon_wings",
            new CustomTextureWingItem("feathered_gradient_neon", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_GREEN_WINGS = register("feathered_gradient_green_wings",
            new CustomTextureWingItem("feathered_gradient_green", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_PEACH_WINGS = register("feathered_gradient_peach_wings",
            new CustomTextureWingItem("feathered_gradient_peach", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_PURPLE_WINGS = register("feathered_gradient_purple_wings",
            new CustomTextureWingItem("feathered_gradient_purple", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_BLUE_WINGS = register("feathered_gradient_blue_wings",
            new CustomTextureWingItem("feathered_gradient_blue", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_PINK_WINGS = register("feathered_gradient_pink_wings",
            new CustomTextureWingItem("feathered_gradient_pink", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GRADIENT_RED_WINGS = register("feathered_gradient_red_wings",
            new CustomTextureWingItem("feathered_gradient_red", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));

    public static final Item FEATHERED_BLOODY_WHITE_WINGS = register("feathered_bloody_white_wings",
            new CustomTextureWingItem("feathered_bloody_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_BURNED_WHITE_WINGS = register("feathered_burned_white_wings",
            new CustomTextureWingItem("feathered_burned_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_BLOODY_BLACK_WINGS = register("feathered_bloody_black_wings",
            new CustomTextureWingItem("feathered_bloody_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_BURNED_BLACK_WINGS = register("feathered_burned_black_wings",
            new CustomTextureWingItem("feathered_burned_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GILDED_WHITE_WINGS = register("feathered_gilded_white_wings",
            new CustomTextureWingItem("feathered_gilded_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_FROSTBITTEN_WHITE_WINGS = register("feathered_frostbitten_white_wings",
            new CustomTextureWingItem("feathered_frostbitten_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_GILDED_BLACK_WINGS = register("feathered_gilded_black_wings",
            new CustomTextureWingItem("feathered_gilded_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_FROSTBITTEN_BLACK_WINGS = register("feathered_frostbitten_black_wings",
            new CustomTextureWingItem("feathered_frostbitten_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_MOSSY_WHITE_WINGS = register("feathered_mossy_white_wings",
            new CustomTextureWingItem("feathered_mossy_white", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item FEATHERED_MOSSY_BLACK_WINGS = register("feathered_mossy_black_wings",
            new CustomTextureWingItem("feathered_mossy_black", "feathered", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));

    // MEMBRANE
    public static final Item MEMBRANE_GRADIENT_PINK_WINGS = register("membrane_gradient_pink_wings",
            new CustomTextureWingItem("membrane_gradient_pink", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_PRISMARINE_WINGS = register("membrane_gradient_prismarine_wings",
            new CustomTextureWingItem("membrane_gradient_prismarine", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_PEACHYGREEN_WINGS = register("membrane_gradient_peachygreen_wings",
            new CustomTextureWingItem("membrane_gradient_peachygreen", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_BLUEYELLOW_WINGS = register("membrane_gradient_blueyellow_wings",
            new CustomTextureWingItem("membrane_gradient_blueyellow", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_WATERMELON_WINGS = register("membrane_gradient_watermelon_wings",
            new CustomTextureWingItem("membrane_gradient_watermelon", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_EGGPLANT_WINGS = register("membrane_gradient_eggplant_wings",
            new CustomTextureWingItem("membrane_gradient_eggplant", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_SWAMPY_WINGS = register("membrane_gradient_swampy_wings",
            new CustomTextureWingItem("membrane_gradient_swampy", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_PINKYWHITE_WINGS = register("membrane_gradient_pinkywhite_wings",
            new CustomTextureWingItem("membrane_gradient_pinkywhite", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_WHITE_WINGS = register("membrane_gradient_white_wings",
            new CustomTextureWingItem("membrane_gradient_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GRADIENT_BLACK_WINGS = register("membrane_gradient_black_wings",
            new CustomTextureWingItem("membrane_gradient_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));

    public static final Item MEMBRANE_BLOODY_WHITE_WINGS = register("membrane_bloody_white_wings",
            new CustomTextureWingItem("membrane_bloody_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_BLOODY_BLACK_WINGS = register("membrane_bloody_black_wings",
            new CustomTextureWingItem("membrane_bloody_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_BURNED_WHITE_WINGS = register("membrane_burned_white_wings",
            new CustomTextureWingItem("membrane_burned_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_BURNED_BLACK_WINGS = register("membrane_burned_black_wings",
            new CustomTextureWingItem("membrane_burned_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GILDED_WHITE_WINGS = register("membrane_gilded_white_wings",
            new CustomTextureWingItem("membrane_gilded_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_GILDED_BLACK_WINGS = register("membrane_gilded_black_wings",
            new CustomTextureWingItem("membrane_gilded_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_FROSTBITTEN_WHITE_WINGS = register("membrane_frostbitten_white_wings",
            new CustomTextureWingItem("membrane_frostbitten_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_FROSTBITTEN_BLACK_WINGS = register("membrane_frostbitten_black_wings",
            new CustomTextureWingItem("membrane_frostbitten_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_MOSSY_WHITE_WINGS = register("membrane_mossy_white_wings",
            new CustomTextureWingItem("membrane_mossy_white", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));
    public static final Item MEMBRANE_MOSSY_BLACK_WINGS = register("membrane_mossy_black_wings",
            new CustomTextureWingItem("membrane_mossy_black", "leather", false, Rarity.RARE, false, false, false, 0.0F, 0.0F));

    // // RARE END


    public static void register() {
    }
}
