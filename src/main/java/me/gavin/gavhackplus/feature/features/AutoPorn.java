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
            mc.player.sendChatMessage("Me and the bois using GavHack+ AutoPorn what did I look up though?!");
        }
        switch (mode.getMode()) {
            case "Hentai":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=hentai%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Straight":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=straight%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Gay":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=gay%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Furry":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=furry%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Milf":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=milf%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Granny":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=granny%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Tranny":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=tranny%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Femboy":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=femboy%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "BBC":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=BBC%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Sounding":
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.pornhub.com/video/search?search=sounding%22"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        disable();
    }
}
