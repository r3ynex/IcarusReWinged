package com.r3x.icarusrewinged.item;

import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class CustomTextureWingItem extends WingItem {

    private static final long FRAME_DURATION = 200L;

    private final String textureName;
    private final String modelType;
    private final boolean hasSecondLayer;
    private final int frames;
    private final Rarity rarity;
    private final boolean isSeparate;
    private final boolean isEmissive;
    private final boolean isTranslucent;
    private final float modelOffsetY;
    private final float modelOffsetZ;
    private final boolean pingPong;

    // 1. Главный конструктор (Теперь принимает float modelOffsetY в конце)
    public CustomTextureWingItem(String textureName, String modelType, boolean hasSecondLayer, int frames, boolean pingPong, Rarity rarity, boolean isSeparate, boolean isEmissive, boolean isTranslucent, float modelOffsetY, float modelOffsetZ) {
        super(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.FEATHERED);
        this.textureName = textureName;
        this.modelType = modelType.toLowerCase();
        this.hasSecondLayer = hasSecondLayer;
        this.frames = frames;
        this.pingPong = pingPong;
        this.rarity = rarity;
        this.isSeparate = isSeparate;
        this.isEmissive = isEmissive;
        this.isTranslucent = isTranslucent;
        this.modelOffsetY = modelOffsetY;
        this.modelOffsetZ = modelOffsetZ;
    }

    // 2. Конструктор без анимации (Передаем modelOffsetY)
    public CustomTextureWingItem(String textureName, String modelType, boolean hasSecondLayer, Rarity rarity, boolean isSeparate, boolean isEmissive, boolean isTranslucent, float modelOffsetY, float modelOffsetZ) {
        this(textureName, modelType, hasSecondLayer, 1, false, rarity, isSeparate, isEmissive, isTranslucent, modelOffsetY, modelOffsetZ);
    }

    // 3. Дефолтный конструктор (Смещение 0.0F по умолчанию)
    // 3. Дефолтный конструктор (Смещение 0.0F по умолчанию)
    public CustomTextureWingItem(String textureName) {
        this(textureName, "feathered", false, 1, false, Rarity.COMMON, false, false, false, 0.0F, 0.0F);
    }

    public String getCustomModelType() { return this.modelType; }
    public float getModelOffsetY() { return this.modelOffsetY; }
    public float getModelOffsetZ() { return this.modelOffsetZ; }
    public boolean isSeparate() { return this.isSeparate; }
    public boolean hasSecondLayer() { return this.hasSecondLayer; }
    public boolean isSecondLayerEmissive() { return this.isEmissive; }
    public boolean isTranslucent() { return this.isTranslucent; }

    @Override
    public Rarity getRarity(ItemStack stack) { return this.rarity; }

    private int getCurrentFrame() {
        if (frames <= 1) return 0;

        if (this.pingPong) {
            int cycleLength = 2 * (frames - 1);
            int currentStep = (int) ((System.currentTimeMillis() / FRAME_DURATION) % cycleLength);
            return (currentStep < frames) ? currentStep : cycleLength - currentStep;
        }

        else {
            return (int) ((System.currentTimeMillis() / FRAME_DURATION) % frames);
        }
    }

    // Утилитарный метод, который автоматически определяет подпапку для первого слоя
    private String getLayer1Folder() {
        return (frames > 1) ? "entity/animated/" : "entity/";
    }

    // Утилитарный метод, который автоматически определяет подпапку для второго (светящегося/дополнительного) слоя
    private String getLayer2Folder() {
        if (isEmissive) {
            return (frames > 1) ? "entity/emission/animated/" : "entity/emission/";
        }
        return (frames > 1) ? "entity/animated/" : "entity/";
    }

    // ==========================================
    // ТЕКСТУРНЫЕ СЛОИ (С АВТО-ПОДПАПКАМИ)
    // ==========================================
    public ResourceLocation getCustomLayer1() {
        if (isSeparate) return new ResourceLocation("icarusrewinged", "textures/entity/empty_wings.png");
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + ".png");
    }

    public ResourceLocation getCustomLayer2() {
        if (isSeparate || !hasSecondLayer) return new ResourceLocation("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + ".png");
    }

    public ResourceLocation getCustomLayer1L() {
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_l_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_l.png");
    }

    public ResourceLocation getCustomLayer1R() {
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_r_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_r.png");
    }

    public ResourceLocation getCustomLayer2L() {
        if (!hasSecondLayer) return new ResourceLocation("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_l_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_l.png");
    }

    public ResourceLocation getCustomLayer2R() {
        if (!hasSecondLayer) return new ResourceLocation("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_r_" + getCurrentFrame() + ".png");
        return new ResourceLocation("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_r.png");
    }
}