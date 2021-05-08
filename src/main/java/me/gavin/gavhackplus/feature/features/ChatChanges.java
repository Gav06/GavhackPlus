package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.ICPacketChatMessage;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Literally just a combination of chatTimestamps and chatSuffix into one module
 */

public class ChatChanges extends Feature {
    public ChatChanges() {
        super("ChatTweaks", "Tweaks for your chat.", Category.Chat);
        addSettings(
                chatSuffix,
                chatTimestamps,
                unicode
        );
    }

    public BooleanSetting chatSuffix = new BooleanSetting("ChatSuffix", this, true);
    public BooleanSetting chatTimestamps = new BooleanSetting("ChatTimestamps", this, true);
    public BooleanSetting unicode = new BooleanSetting("Unicode", this, true);

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onReceiveMessage(ClientChatReceivedEvent event) {
        if (event.getType() != ChatType.GAME_INFO) {
            String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
            ITextComponent dateComponent = new TextComponentString(ChatFormatting.GRAY + "[" + date + "] " + ChatFormatting.RESET);
            if(chatTimestamps.getValue()) {
                event.setMessage(dateComponent.appendSibling(event.getMessage()));
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String suffix = " | gavhack+";
            if (unicode.getValue())
                suffix = StringEscapeUtils.unescapeCsv(" \uff5c \u01e4\u0394\u1d20\u0126\u0394\u0106\u049c+");

            if(chatSuffix.getValue()) {
                if (!isCommand(((CPacketChatMessage) event.getPacket()).getMessage()))
                    ((ICPacketChatMessage)event.getPacket()).setMessageText(((CPacketChatMessage) event.getPacket()).getMessage() + suffix);
            }
        }
    }

    private boolean isCommand(String message) {
        return message.startsWith("/") || message.startsWith(".") || message.startsWith("*");
    }
}
