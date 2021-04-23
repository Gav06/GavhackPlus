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

public class ConfigSystem {

    private final File saveDir;
    private final File moduleSaveDir;
    private BufferedWriter fileWriter;
    private BufferedReader fileReader;
    private PrintWriter printWriter;

    private Yaml yaml;

    public ConfigSystem() {
        this.saveDir = new File(Minecraft.getMinecraft().gameDir, "gavhackplus");
        this.moduleSaveDir = new File(saveDir, "features");
        this.yaml = new Yaml();
    }

    public void saveConfigs() {
        System.out.println("starting save");
        for (Feature feature : Gavhack.featureManager.getFeatures()) {
            System.out.println(feature.getName());
            HashMap<String, Object> valueMap = new HashMap<>();

            valueMap.put("enabled", feature.isEnabled());


            for (Setting s : feature.settings) {
                System.out.println(s.getName());
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
                System.out.println("trying to save");
                writeMapToYamlFile(valueMap, new File(moduleSaveDir, feature.getName() + ".yaml"));
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void loadConfigs() {

    }

    private void writeMapToYamlFile(HashMap<String, Object> hashMap, File file) throws IOException {
        this.fileWriter = new BufferedWriter(new FileWriter(file));
        this.printWriter = new PrintWriter(fileWriter);
        yaml.dump(hashMap, printWriter);
        System.out.println("dumped yaml");
    }
}
