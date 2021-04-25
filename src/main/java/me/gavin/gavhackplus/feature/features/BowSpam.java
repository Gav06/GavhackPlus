package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.feature.Category;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class BowSpam extends Feature {
    public BowSpam() {
        super("BowSpam", "spam with bow", Category.Combat);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive()) {
                if (mc.player.getItemInUseMaxCount() > 3) {

                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));

                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                    mc.player.stopActiveHand();
                }
            }
        }
    }
}
