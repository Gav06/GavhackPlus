package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AutoPorn extends Feature {
    public AutoPorn() {
        super("AutoPorn", "What are you doing step bro?", Category.Misc);
    }

    private final ModeSetting mode = new ModeSetting("PornMode", this, "Hentai", "Straight", "Gay", "Furry", "Milf", "Granny", "Tranny", "Femboy", "BBC", "Sounding");
    private final BooleanSetting announceUsage = new BooleanSetting("AnnounceUsage", this, true);

    public void onEnable() {
        if(announceUsage.getValue()) {
            mc.player.sendChatMessage("I just used CumHack AutoPorn I wonder what I just looked up?!");
        }
        if(mode.getMode().equals("Hentai")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=hentai%22"));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Straight")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=straight%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Gay")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=gay%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Furry")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=furry%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Milf")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=milf%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Granny")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=granny%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Tranny")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=tranny%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Femboy")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=femboy%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("BBC")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=BBC%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(mode.getMode().equals("Sounding")) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=sounding%22"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toggle();
    }
}
