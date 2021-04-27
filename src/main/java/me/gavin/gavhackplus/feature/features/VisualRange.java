package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.network.play.server.SPacketSpawnPlayer;

public class VisualRange extends Feature {

    public VisualRange() {
        super("VisualRange", "Tells you when a player enters your visual range", Category.Chat);
    }

    @EventTarget
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnPlayer) {
            String name = mc.getConnection().getPlayerInfo(((SPacketSpawnPlayer) event.getPacket()).getUniqueId()).getGameProfile().getName();
            Util.sendMsg(ChatFormatting.GREEN + name + ChatFormatting.RESET + " has entered your visual range");
        }
    }
}
