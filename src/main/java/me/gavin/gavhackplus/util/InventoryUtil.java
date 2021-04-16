package me.gavin.gavhackplus.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;

public class InventoryUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void switchToHotbarItem(Item item) {
        int itemSlot = mc.player.getHeldItemMainhand().getItem() == item ? mc.player.inventory.currentItem : -1;
        if (itemSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (mc.player.inventory.getStackInSlot(l).getItem() == item) {
                    if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() != item) {
                        mc.player.inventory.currentItem = l;
                    }
                }
            }
        }
    }
}
