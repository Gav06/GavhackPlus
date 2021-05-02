package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.DiscordService;

public class RPC extends Feature {
    public RPC() {
        super("RPC", "Rpigc", Category.Client);
    }

    @Override
    protected void onEnable() {
        DiscordService.INSTANCE.enable();
    }

    @Override
    protected void onDisable() {
        DiscordService.INSTANCE.disable();
    }
}
