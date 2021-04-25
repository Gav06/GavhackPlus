package me.gavin.gavhackplus.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class RenderEntityEvent extends EventCancellable {

    private final Entity entity;

    public RenderEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Pre extends RenderEntityEvent {
        public Pre(Entity entity) {
            super(entity);
        }
    }

    public static class Post extends RenderEntityEvent {
        public Post(Entity entity) {
            super(entity);
        }
    }
}
