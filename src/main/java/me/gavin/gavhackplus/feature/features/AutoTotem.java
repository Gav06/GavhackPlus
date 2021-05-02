package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class AutoTotem extends Feature {

    private final NumberSetting health = new NumberSetting("Health", this, 15f, 2f, 36f, 0.5f);

    public AutoTotem() {
        super("AutoTotem", "Automatically use totem", Category.Combat);
        addSettings(health);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        int totemSlot = -1;
        for (int i = 0; i < 36; i++) {
            Item slotItem = mc.player.inventory.getStackInSlot(i).getItem();
            if (slotItem == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                totemSlot = i;
                break;
            }
        }

        if (totemSlot != -1 && mc.player.getHealth() < health.getValue()) {
            // putting totem in hand
            int slotToPutTotemIn = 45;

            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemSlot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slotToPutTotemIn, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemSlot, 0, ClickType.PICKUP, mc.player);
        }
    }
}
