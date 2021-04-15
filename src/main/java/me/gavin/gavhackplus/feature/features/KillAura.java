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
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;

public class KillAura extends Feature {
	
	private NumberSetting attackRange = new NumberSetting("AttackRange", this, 3.5f, 0.1f, 6.0f, 0.1f);
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
}
