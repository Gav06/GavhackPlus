package me.gavin.gavhackplus.feature.features;

import com.mojang.authlib.GameProfile;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Freecam extends Feature {
    public Freecam() {
        super("Freecam", "look around freely", Category.World);
    }

    EntityOtherPlayerMP cameraEntity = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), mc.player.getName()));

    @Override
    public void onEnable() {
        mc.world.addEntityToWorld(-99, cameraEntity);
        cameraEntity.copyLocationAndAnglesFrom(mc.player);
        mc.setRenderViewEntity(cameraEntity);
    }

    @Override
    public void onDisable() {
        mc.setRenderViewEntity(mc.player);
        mc.world.removeEntity(cameraEntity);
    }
}
