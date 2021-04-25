package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class ESP extends Feature {

	private BooleanSetting players = new BooleanSetting("Players", this, true);
	private BooleanSetting crystals = new BooleanSetting("Crystals", this, true);
	private BooleanSetting monsters = new BooleanSetting("Monsters", this, true);
	private BooleanSetting animals = new BooleanSetting("Animals", this, true);

	public ESP() {
		super("ESP", "Highlights entities", Category.Render);
		addSettings(players, crystals, monsters, animals);
	}

	@EventTarget
	public void onTick(TickEvent event) {
		for (Entity e : mc.world.loadedEntityList) {

			e.setGlowing(e instanceof EntityPlayer && players.getValue());

			e.setGlowing(e instanceof EntityEnderCrystal && crystals.getValue());

			e.setGlowing(e instanceof EntityMob && monsters.getValue());

			e.setGlowing(e instanceof EntityAnimal && animals.getValue());
		}
	}

	@Override
	public void onDisable() {
		for (Entity e : mc.world.loadedEntityList)
			if (e instanceof EntityLivingBase) {
				EntityLivingBase ent = (EntityLivingBase) e;
				if (!ent.isPotionActive(Potion.getPotionById(24)))
					e.setGlowing(false);
			}
	}
}
