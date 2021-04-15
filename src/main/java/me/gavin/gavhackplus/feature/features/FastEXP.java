package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.IMinecraft;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class FastEXP extends Feature {

    private BooleanSetting throwAtFeet = new BooleanSetting("ThrowAtFeet", this, false);

    public FastEXP() {
        super("FastEXP", "Tweak how you use xp bottles", Category.Combat);
        addSettings(throwAtFeet);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE)
            ((IMinecraft)mc).setDelayTimer(0);
    }

    @EventTarget
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem) {
            CPacketPlayerTryUseItem packet = (CPacketPlayerTryUseItem) event.getPacket();

            if (mc.player.getHeldItem(packet.getHand()).getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.getConnection().sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90f, false));
            }
        }
    }
}
