package me.gavin.gavhackplus;

import me.gavin.gavhackplus.feature.FeatureManager;
import me.gavin.gavhackplus.gui.gavhack.impl.ClickGuiScreen;
import me.gavin.gavhackplus.gui.particle.ParticleEngine;
import me.gavin.gavhackplus.util.font.hal.CFontRenderer;
import me.gavin.gavhackplus.util.misc.EventTranslator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@Mod(name = "Gavhack+", modid = "gavhackplus", version = "0.2a")
public class Gavhack {
    public static final String nameVersion = "Gavhack+ 0.2a";

    public static Logger logger;
    public static FeatureManager featureManager;
    public static ParticleEngine particleEngine;
    public static CFontRenderer font;
    public static ClickGuiScreen clickGui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger = LogManager.getLogger("gavhack+");
        logger.info("--------------");
        logger.info("starting Gavhack+");
        logger.info("--------------");

        featureManager = new FeatureManager();
        logger.info("Feature manager initialized");

        font = new CFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
        logger.info("CFont renderer by hal initialized");

        //clickGui = new ClickGUISystem();
        clickGui = new ClickGuiScreen();
        logger.info("Click gui initialized");

        particleEngine = new ParticleEngine();
        logger.info("Particle engine initialized (courtesy of doctor swag) :O");

        new EventTranslator();
        logger.info("Forge event translator initialized");
    }
}
