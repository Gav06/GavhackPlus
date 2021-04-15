package me.gavin.gavhackplus.gui.particle;

import net.minecraft.client.gui.ScaledResolution;

import java.util.Random;

public class Particle {

	public float x, y, radius, speed, ticks, opacity;
	
	public Particle(ScaledResolution sr, float r, float s) {
		Random rand = new Random();
		
		x = rand.nextFloat() * sr.getScaledWidth();
		y = rand.nextFloat() * sr.getScaledHeight();
		
		ticks = rand.nextFloat() * sr.getScaledHeight() / 2;
		
		radius = r;
		speed = s;
	}
}
