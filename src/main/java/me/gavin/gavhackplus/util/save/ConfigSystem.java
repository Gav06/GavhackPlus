package me.gavin.gavhackplus.util.save;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.feature.FeatureManager;
import me.gavin.gavhackplus.setting.Setting;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.KeybindSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigSystem {

    private final File saveDir;
    private final File moduleSaveDir;

    private Yaml yaml;

    public ConfigSystem() {
        this.saveDir = new File(Minecraft.getMinecraft().gameDir, "gavhackplus");
        this.moduleSaveDir = new File(saveDir, "features");
        if (!saveDir.exists())
            saveDir.mkdir();

        if (!moduleSaveDir.exists())
            moduleSaveDir.mkdir();
        this.yaml = new Yaml();
    }

    public void saveConfigs() {
        System.out.println("starting save");
        for (Feature feature : Gavhack.featureManager.getFeatures()) {
            HashMap<String, Object> valueMap = new HashMap<>();

            valueMap.put("enabled", feature.isEnabled());
            valueMap.put("toggleBind", feature.getKey());

            for (Setting s : feature.settings) {
                if (s instanceof ModeSetting) {
                    valueMap.put(s.getName(), ((ModeSetting) s).getMode());
                } else if (s instanceof KeybindSetting) {
                    valueMap.put(s.getName(), ((KeybindSetting) s).getKey());
                } else if (s instanceof BooleanSetting) {
                    valueMap.put(s.getName(), ((BooleanSetting) s).getValue());
                } else if (s instanceof NumberSetting) {
                    valueMap.put(s.getName(), ((NumberSetting) s).getValue());
                }
            }

            try {
                writeMapToYamlFile(valueMap, new File(moduleSaveDir, feature.getName() + ".yaml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfigs() {
        for (Feature feature : Gavhack.featureManager.getFeatures()) {
            try {
                InputStream istream = new FileInputStream(new File(moduleSaveDir, feature.getName() + ".yaml"));
                Map<String, Object> detectedMap = (Map<String, Object>) yaml.load(istream);

                if ((boolean) detectedMap.get("enabled")) {
                    feature.toggle();
                }

                feature.setKey((int) detectedMap.get("toggleBind"));

                for (Setting s : feature.settings) {
                    if (s instanceof BooleanSetting) {
                        ((BooleanSetting) s).setValue((boolean) detectedMap.get(s.getName()));
                    } else if (s instanceof KeybindSetting) {
                        ((KeybindSetting) s).setKey((int) detectedMap.get(s.getName()));
                    } else if (s instanceof ModeSetting) {
                        ((ModeSetting) s).setMode((String) detectedMap.get(s.getName()));
                    } else if (s instanceof NumberSetting) {
                        ((NumberSetting) s).setValueClamped(((Double) detectedMap.get(s.getName())).floatValue());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMapToYamlFile(HashMap<String, Object> hashMap, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        yaml.dump(hashMap, writer);
    }
}