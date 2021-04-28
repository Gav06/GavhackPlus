package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;

public class AutoSuicide extends Feature {
    public AutoSuicide() {
        super("AutoSuicide", "You know what this does :)", Category.Chat);
    }

    private final BooleanSetting announceUsage = new BooleanSetting("AnnounceUsage", this, true);

    public void onEnable() {
        if(announceUsage.getValue()) {
            mc.player.sendChatMessage("I just used GavHack+ AutoSuicide I'm so good :)");
        }
        mc.player.sendChatMessage("/kill");
        toggle();
    }
}
