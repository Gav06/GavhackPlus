package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.tileentity.TileEntity;

public class StorageESP extends Feature {

    public StorageESP() {
        super("StorageESP", "Show storage containers", Category.Render);
    }

    @EventTarget
    public void onRender(RenderEvent.World event) {
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
        }
    }
}
