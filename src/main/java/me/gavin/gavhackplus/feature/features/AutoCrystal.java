package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.TickTimer;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AutoCrystal extends Feature {

    private final NumberSetting targetRange = new NumberSetting("TargetRange", this, 6f, 2f, 12f, 0.1f);

    private final BooleanSetting swingArm = new BooleanSetting("SwingArm", this, true);

    private final BooleanSetting offhand = new BooleanSetting("Offhand", this, false);

    private final NumberSetting placeDelay = new NumberSetting("PlaceDelay", this, 2f, 1f, 20f, 1f);
    private final NumberSetting breakDelay = new NumberSetting("BreakDelay", this, 2f, 1f, 20f, 1f);

    private final NumberSetting maxSelfDmg = new NumberSetting("MaxSelfDmg", this, 8f, 0.1f, 50f, 0.1f);
    private final NumberSetting minTargetDmg = new NumberSetting("MinTargetDmg", this, 5f, 1f, 50f, 0.1f);

    private final NumberSetting placeRange = new NumberSetting("PlaceRange", this, 4f, 1f, 6f, 0.1f);
    private final NumberSetting breakRange = new NumberSetting("BreakRange", this, 4f, 1f, 6f, 0.1f);

    private final NumberSetting wallsRange = new NumberSetting("WallsRange", this, 3f, 0f, 5f, 0.1f);


    public AutoCrystal() {
        super("AutoCrystal", ":^)", Category.Combat);
        addSettings(
                targetRange,
                swingArm,
                placeDelay, breakDelay,
                maxSelfDmg, minTargetDmg,
                placeRange, breakRange, wallsRange);
    }

    private List<Integer> idList = new ArrayList<>();

    private EntityPlayer targetPlayer = null;
    private EntityEnderCrystal targetCrystal = null;

    private final TickTimer placeTimer = new TickTimer();
    private final TickTimer breakTimer = new TickTimer();

    @EventTarget
    public void onTick(TickEvent event) {
        // target


        placeTimer.setDelay((int) placeDelay.getValue());
        breakTimer.setDelay((int) breakDelay.getValue());

        // break logic
        if (breakTimer.isPassed()) {

        }

        // place logic
        if (placeTimer.isPassed()) {
            targetPlayer = getTargetPlayer();
            if (targetPlayer != null) {

            }
        }

        placeTimer.resetDelay();
        breakTimer.setPaused(false);
        placeTimer.setPaused(true);
    }

    private EntityPlayer getTargetPlayer() {
       return mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityPlayer)
                .map(e -> (EntityPlayer) e)
                .filter(player -> !player.equals(mc.player))
                .filter(player -> player.getDistance(mc.player) <= targetRange.getValue())
                .min(Comparator.comparing(player -> player.getDistance(mc.player)))
                .orElse(null);
    }

    private EntityEnderCrystal getTargetCrystal() {
        return mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityEnderCrystal)
                .map(e -> (EntityEnderCrystal) e)
                .filter(crystal -> crystal.getDistance(mc.player) <= breakRange.getValue())
                .min(Comparator.comparing(crystal -> crystal.getDistance(mc.player)))
                .orElse(null);
    }

    private boolean canPlaceCrystal(BlockPos pos) {
        if (blockAt(pos) == Blocks.BEDROCK || blockAt(pos) == Blocks.OBSIDIAN) {
            if (blockAt(pos.add(0, 1, 0)) == Blocks.AIR && blockAt(pos.add(0, 2, 0)) == Blocks.AIR) {
                return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos)).size() == 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Block blockAt(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
    }

    private ArrayList<BlockPos> getBlocksAroundPlayer(float range) {
        ArrayList<BlockPos> posList = new ArrayList<>();

        for (float x = -range; x < range; x++) {
            for (float y = range + 1; y > -range; y--) {
                for (float z = -range; z < range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    posList.add(pos.add(mc.player.getPosition()));
                }
            }
        }

        return posList;
    }

    // credit to srgantmoomoo and postman for these methods
    // i was too lazy to write these 3 by myself
    public float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(MobEffects.ABSORPTION)) {
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

    private void attack(EntityEnderCrystal crystal) {
        EnumHand hand = offhand.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        swingArm(hand);
        mc.player.connection.sendPacket(new CPacketUseEntity(crystal, hand));
    }

    private void swingArm(EnumHand hand) {
        if (swingArm.getValue()) {
            mc.player.swingArm(hand);
        }
    }
}
