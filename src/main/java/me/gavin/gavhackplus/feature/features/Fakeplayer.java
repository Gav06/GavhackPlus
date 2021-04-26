package me.gavin.gavhackplus.feature.features;

import com.mojang.authlib.GameProfile;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Fakeplayer extends Feature {

    public Fakeplayer() {
        super("FakePlayer", "Used for debugging and testing configs", Category.Misc);
    }

    EntityOtherPlayerMP fakePlayer;

    @Override
    public void onEnable() {
        if (mc.world == null)
            disable();

        fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), "Gav06"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.inventory.copyInventory(mc.player.inventory);
        mc.world.addEntityToWorld(-6969, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (fakePlayer != null)
            mc.world.removeEntity(fakePlayer);
    }
}
