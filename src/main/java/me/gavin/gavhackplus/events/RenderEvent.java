package me.gavin.gavhackplus.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class RenderEvent extends EventCancellable {

	private float partialTicks;
	
	private RenderEvent(float partialTicks) {
		this.partialTicks = partialTicks;
	}
	
	public float getPartialTicks() {
		return partialTicks;
	}
	
	public static class Screen extends RenderEvent {
		
		public Screen(float partialTicks) {
			super(partialTicks);
		}
	}
	
	public static class World extends RenderEvent {
		
		public World(float partialTicks) {
			super(partialTicks);
		}
	}
}
