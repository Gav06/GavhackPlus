package me.gavin.gavhackplus.feature.features;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.InventoryUtil;
import me.gavin.gavhackplus.util.TimerUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
<<<<<<< HEAD
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketUseEntity;
=======
import net.minecraft.network.play.client.CPacketPlayer;
>>>>>>> adba50685dfffc4eebf9ea345bdf4e01745703eb
import net.minecraft.util.EnumHand;

public class KillAura extends Feature {
	
	private NumberSetting attackRange = new NumberSetting("AttackRange", this, 3.5f, 0.1f, 6.0f, 0.1f);
<<<<<<< HEAD
	private BooleanSetting rotate = new BooleanSetting("Rotations", this, true);
	private BooleanSetting autoSwitch = new BooleanSetting("SwitchToSword", this, false);

	private BooleanSetting players = new BooleanSetting("Players", this, true);
	private BooleanSetting monsters = new BooleanSetting("Monsters", this, true);
	private BooleanSetting animals = new BooleanSetting("Animals", this, true);
	
	public KillAura() {
		super("KillAura", "Attacks stuff for you", Category.Combat);
		addSettings(
				attackRange,
				rotate,
				autoSwitch,
				players,
				monsters,
				animals);
	}


	// TODO re-add CPS to kill aura instead of just normal cooldown timer

	@EventTarget
	public void onTick(TickEvent event) {
		List<Entity> targets = mc.world.loadedEntityList.stream()
				.filter(Objects::nonNull)
				.filter(entity -> entity != mc.player)
				.filter(entity -> entity.getDistance(mc.player) <= attackRange.getValue())
				.filter(entity -> !entity.isDead)
				.filter(entity -> entity instanceof EntityLivingBase)
				.map(entity -> (EntityLivingBase) entity)
				.filter(entityLivingBase -> entityLivingBase.getHealth() > 0)
				.filter(this::attackCheck)
				.sorted(Comparator.comparing(entityLivingBase -> mc.player.getDistance(entityLivingBase)))
				.collect(Collectors.toList());

		if (targets.size() == 0)
			return;

		targets.forEach(this::attack);

	}

	private void attack(Entity e) {
		double[] rotations = Util.calculateLookAt(e.posX, e.posY + (e.height / 2), e.posZ, mc.player);
		mc.player.rotationYaw = (float) rotations[0];
		mc.player.rotationPitch = (float) rotations[1];

		if (mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
			mc.playerController.attackEntity(mc.player, e);
			mc.player.swingArm(EnumHand.MAIN_HAND);
		}
	}

	private boolean attackCheck(Entity entity) {
		if (entity instanceof EntityPlayer && players.getValue()) {
			return true;
		} else if (entity instanceof EntityMob && monsters.getValue()) {
			return true;
		} else return entity instanceof EntityAnimal && animals.getValue();
	}

	/*
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
				if (!rotationMode.getMode().equals("None")) {
					double[] rotations = Util.calculateLookAt(e.posX, e.posY + (e.height / 2), e.posZ, mc.player);
					if (rotationMode.getMode().equals("Force")) {
						mc.player.rotationPitch = (float) rotations[1];
						mc.player.rotationYaw = (float) rotations[0];
					} else {
						mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)rotations[0], (float)rotations[1], false));
					}
				}

				// switching to diamond sword
				if (autoSwitch.getValue())
					InventoryUtil.switchToHotbarItem(Items.DIAMOND_SWORD);
				
				// attacking entity + cooldown check
				if (attackType.getMode().equals("Cooldown")) {
					if (mc.player.getCooledAttackStrength(0.0f) >= 1.0f)
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
		mc.getConnection().sendPacket(new CPacketUseEntity(e));
		mc.player.swingArm(EnumHand.MAIN_HAND);
		mc.player.resetCooldown();
	}*/
}
