package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTimestamps extends Feature {

    public ChatTimestamps() {
        super("ChatTimestamps", "Add timestamps to chat", Category.Chat);
    }

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
            event.setMessage(dateComponent.appendSibling(event.getMessage()));
        }
    }
}
