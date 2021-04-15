package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Nuker extends Feature {
    NumberSetting rad = new NumberSetting("Radius", this, 4.0f, 0.0f, 6.0f, 1.0f);
    ModeSetting breakMode = new ModeSetting("BreakMode", this, "Packet", "Packet", "Creative");

    public Nuker() {
        super("Nuker", "Automatically mines blocks around you", Category.World);
        addSettings(rad, breakMode);
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

                    if (block != Blocks.AIR && block.getBlockHardness(block.getBlockState().getBaseState(), mc.world, blockPos) != -1) {

                        if (breakMode.getMode().equals("Packet")) {
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        } else {
                            // only works in creative
                            mc.playerController.clickBlock(blockPos, EnumFacing.NORTH);
                            mc.playerController.updateController();
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }
}