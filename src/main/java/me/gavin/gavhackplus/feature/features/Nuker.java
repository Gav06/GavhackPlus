package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Nuker extends Feature {
        NumberSetting rad = new NumberSetting("Radius", this, 4.0f, 0.0f, 6.0f, 1.0f);
        public Nuker() {
        super("Nuker", "Automatically mines blocks around you", Category.World);
        addSettings(rad);
    }

    @EventTarget
   public void onTick(TickEvent e) {
        int dist = (int) rad.getValue();
        for (int x = -dist; x < dist; x++) {
            for (int y = dist + 1; y > -dist + 1; y--) {
                for (int z = -dist; z < dist; z++) {

                    double xBlock = (mc.player.posX + x);
                    double yBlock = (mc.player.posY + y);
                    double zBlock = (mc.player.posZ + z);

                    BlockPos blockPos = new BlockPos(xBlock, yBlock, zBlock);
                    Block block = mc.world.getBlockState(blockPos).getBlock();




                    mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                }

            }
        }
    }
}