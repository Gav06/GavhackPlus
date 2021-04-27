package me.gavin.gavhackplus.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessageUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static String prefix = ChatFormatting.RED + "[" + ChatFormatting.GRAY + "Gavhack+" + ChatFormatting.RED + "] " + TextFormatting.WHITE;
    private static final EntityPlayerSP player = mc.player;

    public static void sendMessagePrefix(String message) {
        player.sendMessage(new TextComponentString(message));
    }
}
