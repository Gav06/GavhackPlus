package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.ICPacketUseEntity;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;

public class AntiBot extends Feature {

	private BooleanSetting cancelPacket = new BooleanSetting("CancelPackets", this, true);
	public static BooleanSetting filterAura;
	public static NumberSetting ticks;

	public AntiBot() {
		super("AntiBot", "Prevents attacking AntiCheat bots", Category.Combat);
		filterAura = new BooleanSetting("FilterAura", this, true);
		ticks = new NumberSetting("TicksExisted", this, 80f, 20f, 200f, 5f);
		addSettings(ticks, cancelPacket, filterAura);
	}

	@EventTarget
	public void onPacket(PacketEvent.Send event) {
		if (!cancelPacket.getValue())
			return;
		
		if (event.getPacket() instanceof CPacketUseEntity) {
			CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
			
			if (packet.getAction() == Action.ATTACK) {
				int id = ((ICPacketUseEntity)(packet)).getEntityId();
				if (mc.world.getEntityByID(id).ticksExisted < ticks.getValue())
					event.setCancelled(true);
			}
		}
	}
}
