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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

import java.util.ArrayList;

public class AutoEnderCrystalAura extends Feature {

    private final NumberSetting attackDistance = new NumberSetting("AttackRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
    private final NumberSetting placeDistance = new NumberSetting("PlaceRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
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
            ((IMinecraft) mc).setDelayTimer(0);
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
                .map(e -> (EntityEnderCrystal) e)
                .filter(this::canAttackCrystal)
                .findFirst().orElse(null);

        if (canAttackCrystal(targetCrystal) && tickTimer.hasTicksPassed((int) breakDelay.getValue(), false)) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketUseEntity(targetCrystal));
            tickTimer.reset();
        }
    }

    private void doPlaceLogic() {
        // finding target based on highest damage
        float highestDamage = Float.MIN_VALUE;
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer) {
                if (e.equals(mc.player))
                    continue;

                //if (calculateDamage())
            }
        }
    }

    private boolean canAttackCrystal(EntityEnderCrystal targetCrystal) {
        if (targetCrystal != null) {
            if (mc.player.getDistance(targetCrystal) <= attackDistance.getValue()) {
                return !targetCrystal.isDead;
            }
        }

        return false;
    }

    private ArrayList<BlockPos> getBlocksAroundPlayer(float range) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        for (float x = -range; x < range; x++) {
            // loop for y axis
            for (float y = range + 1; y > -range; y--) {
                // loop for z axis
                for (float z = -range; z < range; z++) {
                    posList.add(new BlockPos(x, y, z));
                }
            }
        }

        return posList;
    }

    public float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }
            damage = Math.max(damage, 0.0F);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1.0D;

        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

}