package me.gavin.gavhackplus.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class KeyEvent extends EventCancellable {
	
	private final int key;
	
	public KeyEvent(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}

}
