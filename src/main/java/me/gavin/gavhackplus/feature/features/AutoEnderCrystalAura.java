package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.IMinecraft;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.TickTimer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;

public class AutoEnderCrystalAura extends Feature {

    private final NumberSetting attackDistance = new NumberSetting("AttackRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
    private final NumberSetting breakDelay = new NumberSetting("BreakDelay", this, 2.0f, 1.0f, 20.0f, 1.0f);
    private final BooleanSetting setDead = new BooleanSetting("SetDead", this, true);

    private final TickTimer tickTimer;

    public AutoEnderCrystalAura() {
        super("AutoEnderCrystalAura", ":)", Category.Combat);
        BooleanSetting fastPlace = new BooleanSetting("FastPlace", this, true);
        addSettings(breakDelay, attackDistance, setDead, fastPlace);
        tickTimer = new TickTimer();
    }

    private ArrayList<BlockPos> placedCrystals = new ArrayList<>();

    private EntityEnderCrystal targetCrystal;

    @EventTarget
    public void onTick(TickEvent event) {
        if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            ((IMinecraft)mc).setDelayTimer(0);
        }

        doPlaceLogic();
        doBreakLogic();
    }

    @EventTarget
    public void onPacket(PacketEvent.Receive event) {
        if (!setDead.getValue())
            return;

        if (event.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect sound = (SPacketSoundEffect) event.getPacket();
            if (sound.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                if (canAttackCrystal(targetCrystal))
                    targetCrystal.setDead();
            }
        }
    }

    private void doBreakLogic() {

        targetCrystal = mc.world.loadedEntityList.stream()
                .filter(e -> e.getDistance(mc.player) <= attackDistance.getValue())
                .filter(e -> e instanceof EntityEnderCrystal)
                .map(e -> (EntityEnderCrystal)e)
                .filter(this::canAttackCrystal)
                .findFirst().orElse(null);

        if (canAttackCrystal(targetCrystal) && tickTimer.hasTicksPassed((int)breakDelay.getValue(), false)) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketUseEntity(targetCrystal));
            tickTimer.reset();
        }
    }

    private void doPlaceLogic() {

    }

    private boolean canAttackCrystal(EntityEnderCrystal targetCrystal) {
        if (targetCrystal != null) {
            if (mc.player.getDistance(targetCrystal) <= attackDistance.getValue()) {
                return !targetCrystal.isDead;
            }
        }

        return false;
    }

}
