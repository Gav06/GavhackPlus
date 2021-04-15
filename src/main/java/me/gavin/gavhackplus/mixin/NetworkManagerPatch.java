package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import io.netty.channel.ChannelHandlerContext;
import me.gavin.gavhackplus.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerPatch {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void sendPacketPatch(Packet<?> packetIn, CallbackInfo ci) {
        PacketEvent.Send event = new PacketEvent.Send(packetIn);
        EventManager.call(event);
        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0Patch(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci) {
        PacketEvent.Receive event = new PacketEvent.Receive(p_channelRead0_2_);
        EventManager.call(event);
        if (event.isCancelled())
            ci.cancel();
    }
}
