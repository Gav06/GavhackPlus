package me.gavin.gavhackplus.util;

import me.gavin.gavhackplus.mixin.accessor.ICPacketPlayer;
import me.gavin.gavhackplus.mixin.accessor.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class AccessHelper {

    public static class CPPlayer {
        public static void setPitch(CPacketPlayer packet, float pitch) {
            ((ICPacketPlayer)packet).setPacketPitch(pitch);
        }

        public static void setYaw(CPacketPlayer packet, float yaw) {
            ((ICPacketPlayer)packet).setPacketYaw(yaw);
        }

        public static void setX(CPacketPlayer packet, double x) {
            ((ICPacketPlayer)packet).setPacketX(x);
        }

        public static void setY(CPacketPlayer packet, double y) {
            ((ICPacketPlayer)packet).setPacketY(y);
        }

        public static void setZ(CPacketPlayer packet, double z) {
            ((ICPacketPlayer)packet).setPacketZ(z);
        }

        public static double getX(CPacketPlayer packet) {
            return ((ICPacketPlayer)packet).getPacketX();
        }

        public static double getY(CPacketPlayer packet) {
            return ((ICPacketPlayer)packet).getPacketY();
        }

        public static double getZ(CPacketPlayer packet) {
            return ((ICPacketPlayer)packet).getPacketZ();
        }
    }

    public static class MinecraftClient {
        public static void setRightClickDelay(int delay) {
            ((IMinecraft) Minecraft.getMinecraft()).setDelayTimer(0);
        }
    }
}
