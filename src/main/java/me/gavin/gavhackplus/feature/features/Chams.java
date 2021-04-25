package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEntityEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.entity.player.EntityPlayer;

public class Chams extends Feature {

    private BooleanSetting players = new BooleanSetting("Players", this, true);
    private BooleanSetting crystals = new BooleanSetting("Crystals", this, true);

    public Chams() {
        super("Chams", "See entities through walls", Category.Render);
        addSettings(players, crystals);
    }

    @EventTarget
    public void onRenderPre(RenderEntityEvent.Pre event) {
        if (event.getEntity() instanceof EntityPlayer && players.getValue()) {

        }
    }

    @EventTarget
    public void onRenderPost(RenderEntityEvent.Post event) {

    }

    private void doChamPre() {

    }

    private void doChamPost() {
        
    }

}
