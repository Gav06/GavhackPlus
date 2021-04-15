package me.gavin.gavhackplus.feature.features;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.TimerUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

public class KillAura extends Feature {
	
	private NumberSetting attackRange = new NumberSetting("AttackRange", this, 3.5f, 0.1f, 6.0f, 0.1f);
	private ModeSetting attackType = new ModeSetting("AttackMode", this, "Cooldown", "Cooldown", "CPS");
	private NumberSetting aps = new NumberSetting("CPS", this, 8.0f, 1.0f, 20.0f, 1.0f);
	private NumberSetting apsRandomness = new NumberSetting("Variability", this, 2.0f, 0.0f, 5.0f, 1.0f);
	private BooleanSetting rotate = new BooleanSetting("Rotations", this, true);
	
	
	public KillAura() {
		super("KillAura", "Attacks stuff for you", Category.Combat);
		addSettings(attackRange, attackType, aps , apsRandomness, rotate);
	}
	
	TimerUtil timer = new TimerUtil();
	
	@EventTarget
	public void onTick(TickEvent event) {
		int variability = (int)(Math.random() * apsRandomness.getValue());
		if (Math.random() > 0.5)
			variability *= -1;
		
		// list sorted by entity distance to player
		List<Entity> list = mc.world.loadedEntityList
				.stream()
				.sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
				.collect(Collectors.toList());
		
		for (Entity e : list) {
			if (e instanceof EntityLivingBase) {
				// self check
				if (e.equals(mc.player))
					continue;
				
				// distance check
				if (e.getDistance(mc.player) > attackRange.getValue())
					continue;
				
				// attack check
				if (!shouldAttack((EntityLivingBase)e))
					continue;
				
				// antibot check
				if (AntiBot.filterAura.getValue() && Gavhack.featureManager.isFeatureEnabled(AntiBot.class)) {
					if (e.ticksExisted < AntiBot.ticks.getValue())
						continue;
				}
				
				long var1 = (long)(aps.getValue() + variability);
				if (var1 <= 1)
					var1 = 1L;
				
				// rotations
				if (rotate.getValue()) {
					double[] rotations = Util.calculateLookAt(e.posX, e.posY + (e.height / 2), e.posZ, mc.player);
					mc.player.rotationPitch = (float) rotations[1];
					mc.player.rotationYaw = (float) rotations[0];
				}
				
				// attacking entity + cooldown check
				if (attackType.getMode().equals("Cooldown")) {
					if (mc.player.getCooledAttackStrength(0) >= 1)
						attack(e);
				} else {
					if (timer.hasTimeElapsed(1000L / var1, true))
						attack(e);
				}
			}
		}
	}
	
	private boolean shouldAttack(EntityLivingBase e) {
		if (e.getHealth() > 0 && !e.isDead) {
			return true;
		} else {
			return false;
		}
	}
	
	private void attack(Entity e) {
		mc.player.swingArm(EnumHand.MAIN_HAND);
		mc.playerController.attackEntity(mc.player, e);
	}
}
