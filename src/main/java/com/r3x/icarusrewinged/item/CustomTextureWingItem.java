package com.r3x.icarusrewinged.item;

import dev.cammiescorner.icarus.IcarusConfig;
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

    public CustomTextureWingItem(String textureName, String modelType, boolean hasSecondLayer, int frames, boolean pingPong, Rarity rarity, boolean isSeparate, boolean isEmissive, boolean isTranslucent, float modelOffsetY, float modelOffsetZ) {
        super(WingItem.WingType.FEATHERED);
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

    // // // // Just for this fkn durability to make it working
    @Override
    public boolean onFlightTick(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.item.ItemStack wings, int ticks) {
        if (!entity.level().isClientSide() && entity instanceof net.minecraft.world.entity.player.Player player) {
            if (!player.isCreative() && dev.cammiescorner.icarus.IcarusConfig.wingsDurability > 0) {
                if (player.tickCount % 20 == 0) {
                    wings.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.CHEST);
                }
            }
        }
        return this.isUsable(entity, wings);
    }

    @Override
    public boolean isUsable(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        if (dev.cammiescorner.icarus.IcarusConfig.wingsDurability <= 0) {
            return true;
        }
        int maxDurability = dev.cammiescorner.icarus.IcarusConfig.wingsDurability;
        return stack.getDamageValue() < maxDurability - 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        int realDurability = dev.cammiescorner.icarus.IcarusConfig.wingsDurability;

        if (realDurability > 1) {
            Integer currentMax = stack.get(net.minecraft.core.component.DataComponents.MAX_DAMAGE);
            if (currentMax == null || currentMax != realDurability) {
                stack.set(net.minecraft.core.component.DataComponents.MAX_DAMAGE, realDurability);
            }
        }
    }

    // // // //

    // 2. Конструктор без анимации
    public CustomTextureWingItem(String textureName, String modelType, boolean hasSecondLayer, Rarity rarity, boolean isSeparate, boolean isEmissive, boolean isTranslucent, float modelOffsetY, float modelOffsetZ) {
        this(textureName, modelType, hasSecondLayer, 1, false, rarity, isSeparate, isEmissive, isTranslucent, modelOffsetY, modelOffsetZ);
    }

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

    public Rarity getCustomRarity() {
        return this.rarity;
    }

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

    private String getLayer1Folder() {
        return (frames > 1) ? "entity/animated/" : "entity/";
    }

    private String getLayer2Folder() {
        if (isEmissive) {
            return (frames > 1) ? "entity/emission/animated/" : "entity/emission/";
        }
        return (frames > 1) ? "entity/animated/" : "entity/";
    }

    public ResourceLocation getCustomLayer1() {
        if (isSeparate) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/entity/empty_wings.png");
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + ".png");
    }

    public ResourceLocation getCustomLayer2() {
        if (isSeparate || !hasSecondLayer) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + ".png");
    }

    public ResourceLocation getCustomLayer1L() {
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_l_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_l.png");
    }

    public ResourceLocation getCustomLayer1R() {
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_r_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer1Folder() + textureName + "_r.png");
    }

    public ResourceLocation getCustomLayer2L() {
        if (!hasSecondLayer) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_l_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_l.png");
    }

    public ResourceLocation getCustomLayer2R() {
        if (!hasSecondLayer) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/entity/empty_wings.png");
        String suffix = isEmissive ? "_e" : "_2";
        if (frames > 1) return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_r_" + getCurrentFrame() + ".png");
        return ResourceLocation.fromNamespaceAndPath("icarusrewinged", "textures/" + getLayer2Folder() + textureName + suffix + "_r.png");
    }
}