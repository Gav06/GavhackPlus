package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.DiscordUtil;

public class RPC extends Feature {
    public RPC() {
        super("RPC", "rpigc", Category.Misc);
    }

    @Override
    protected void onEnable() {
        DiscordUtil.INSTANCE.enable();
    }

    @Override
    protected void onDisable() {
        DiscordUtil.INSTANCE.disable();
    }
}
