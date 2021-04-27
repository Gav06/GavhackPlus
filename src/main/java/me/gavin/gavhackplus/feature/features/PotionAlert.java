package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.util.MessageUtil;
import me.gavin.gavhackplus.events.TickEvent;
import net.minecraft.init.MobEffects;

public class PotionAlert extends Feature {

    public PotionAlert() {
        super("PotionAlert", "Says in chat when you get hit by a arrow", Category.Chat);
    }

    private boolean hasAnnounced = false;

    public BooleanSetting weakness = new BooleanSetting("Weakness", this, true);
    public BooleanSetting slowness = new BooleanSetting("Slowness", this, true);

    public void onTick(TickEvent event) {
        if(weakness.getValue()) {
            if(mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
                hasAnnounced = true;
                MessageUtil.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "PotionAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Hey" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " unlucky move mate" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you now have " + ChatFormatting.RED + "weakness");
            }
            if(!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
                hasAnnounced = false;
                MessageUtil.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "PotionAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Phew" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " that was close" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you no longer have " + ChatFormatting.RED + "weakness");
            }
        }
        if(slowness.getValue()) {
            if(mc.player.isPotionActive(MobEffects.SLOWNESS) && !hasAnnounced) {
                hasAnnounced = true;
                MessageUtil.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "PotionAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Hey" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " unlucky move mate" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you now have " + ChatFormatting.RED + "slowness");
            }
            if(!mc.player.isPotionActive(MobEffects.SLOWNESS) && hasAnnounced) {
                hasAnnounced = false;
                MessageUtil.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "PotionAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Phew" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " that was close" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you no longer have " + ChatFormatting.RED + "slowness");
            }
        }
    }
}
