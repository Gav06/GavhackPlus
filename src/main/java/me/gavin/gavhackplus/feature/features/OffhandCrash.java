package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/*
@author sn01
 */
public class OffhandCrash extends Feature {
    NumberSetting speed = new NumberSetting("Speed", this, 500.0f, 0.0f, 1000.0f, 1.0f);
    public OffhandCrash() {
        super("OffhandCrash", "crash kids using snowballs in ur offhand", Category.World);
        addSettings(speed);
    }
    @EventTarget
    void onTick(TickEvent e){
        for (int i = 0; i < this.speed.getValue(); ++i) {
            final BlockPos playerBlock = new BlockPos(mc.player.posX, mc.player.posY - 1.0, mc.player.posZ);
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, playerBlock, EnumFacing.UP));
        }
    }
}
