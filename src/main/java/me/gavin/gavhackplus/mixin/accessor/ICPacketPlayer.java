package me.gavin.gavhackplus.mixin.accessor;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayer.class)
public interface ICPacketPlayer {

    @Accessor("pitch")
    void setPacketPitch(float pitch);

    @Accessor("yaw")
    void setPacketYaw(float yaw);

    @Accessor("x")
    void setPacketX(double x);

    @Accessor("y")
    void setPacketY(double y);

    @Accessor("z")
    void setPacketZ(double z);

    @Accessor("x")
    double getPacketX();

    @Accessor("y")
    double getPacketY();

    @Accessor("z")
    double getPacketZ();
}
