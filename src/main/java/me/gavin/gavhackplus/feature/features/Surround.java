package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class Surround extends Feature {

    public Surround() {
        super("Surround", "Surrounds you with obsidian in your hotbar", Category.Combat);
    }

    @Override
    public void onEnable() {
        if (mc.player == null)
            return;

        int slot = getObbyHotbarSlot();

        if (slot != -1) {
            mc.player.inventory.currentItem = slot;
            mc.playerController.updateController();
        } else {
            Util.sendMsg("No obby found in hotbar, disabling");
            disable();
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        
    }

    private int getObbyHotbarSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) {
                continue;
            }

            if (((ItemBlock)stack.getItem()).getBlock() == Blocks.OBSIDIAN) {
                return i;
            }
        }

        return -1;
    }
}
