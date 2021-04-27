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
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AutoEnderCrystalAura extends Feature {

    private final NumberSetting attackDistance = new NumberSetting("AttackRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
    private final NumberSetting placeDistance = new NumberSetting("PlaceRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
    private final NumberSetting minDmg = new NumberSetting("MinDmg", this, 4.0f, 0.1f, 10.0f, 0.1f);
    private final NumberSetting maxSelfDmg = new NumberSetting("MaxSelfDmg", this, 15.0f, 1.0f, 30.0f, 0.1f);
    private final NumberSetting breakDelay = new NumberSetting("BreakDelay", this, 2.0f, 1.0f, 20.0f, 1.0f);
    private final NumberSetting placeDelay = new NumberSetting("PlaceDelay", this, 2.0f, 1.0f, 20.0f, 1.0f);
    private final BooleanSetting setDead = new BooleanSetting("SetDead", this, true);
    private final BooleanSetting fastPlace = new BooleanSetting("FastPlace", this, true);


    private final TickTimer breakTickTimer;
    private final TickTimer placeTickTimer;

    private boolean isPlacing;
    private boolean doneBreaking;

    public AutoEnderCrystalAura() {
        super("AutoEnderCrystalAura", ":)", Category.Combat);

        addSettings(breakDelay, placeDelay, attackDistance, placeDistance, setDead, minDmg, maxSelfDmg, fastPlace);
        breakTickTimer = new TickTimer();
        placeTickTimer = new TickTimer();
    }

    private ArrayList<BlockPos> placedCrystals = new ArrayList<>();

    private EntityEnderCrystal targetCrystal;

    @EventTarget
    public void onTick(TickEvent event) {
        if (fastPlace.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                ((IMinecraft) mc).setDelayTimer(0);
            }
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
        doneBreaking = false;
        targetCrystal = mc.world.loadedEntityList.stream()
                .filter(e -> e.getDistance(mc.player) <= attackDistance.getValue())
                .filter(e -> e instanceof EntityEnderCrystal)
                .map(e -> (EntityEnderCrystal) e)
                .filter(this::canAttackCrystal)
                .findFirst().orElse(null);

        if (canAttackCrystal(targetCrystal) && breakTickTimer.hasTicksPassed((int) breakDelay.getValue(), false) && !isPlacing) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketUseEntity(targetCrystal));
            breakTickTimer.reset();
        }
        doneBreaking = true;
    }

    EntityPlayer targetPlayer;

    private void doPlaceLogic() {
        // finding target based on highest damage
        isPlacing = true;
        List<BlockPos> crystalBlocks =
                getBlocksAroundPlayer(placeDistance.getValue())
                        .stream()
                        .filter(this::canPlaceCrystal)
                        .sorted(Comparator.comparing(blockPos -> mc.player.getDistanceSq(blockPos)))
                        .collect(Collectors.toList());

        BlockPos crystalPosition = null;

        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer && e.getDistance(mc.player) <= placeDistance.getValue()) {
                if (e.equals(mc.player))
                    continue;

                targetPlayer = (EntityPlayer) e;

                double bestDamage = Double.MIN_VALUE;
                BlockPos bestPos = null;

                for (BlockPos pos : crystalBlocks) {
                    // adding 0.5 to x for crystal width
                    // adding 1.0 to y because crystal will be placed above
                    double targetDamage = calculateDamage(pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, targetPlayer);
                    double selfDmg = calculateDamage(pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, mc.player);

                    if (targetDamage >= minDmg.getValue() && selfDmg <= maxSelfDmg.getValue()) {
                        if (targetDamage > bestDamage) {
                            bestDamage = targetDamage;
                            bestPos = pos;
                        }
                    }
                }

                if (bestPos != null) {
                    crystalPosition = bestPos;
                }
            }
        }
        if (crystalPosition != null) {
            assert crystalPosition != null;
            if (placeTickTimer.hasTicksPassed((int) placeDelay.getValue(), false)) {

                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(crystalPosition, EnumFacing.UP, EnumHand.MAIN_HAND, 0f, 0f, 0f));
                mc.player.swingArm(EnumHand.MAIN_HAND);
                placeTickTimer.reset();
            }
        }
        isPlacing = false;
    }

    private boolean canPlaceCrystal(BlockPos pos) {
        // checking if blocks above are air
        if (getBlock(pos.add(0, 1, 0)) == Blocks.AIR && getBlock(pos.add(0, 2, 0)) == Blocks.AIR) {

            // seeing if it is obsidian or bedrock
            if (getBlock(pos) == Blocks.OBSIDIAN || getBlock(pos) == Blocks.BEDROCK) {
                // checking if nobody is standing directly above the block
                BlockPos air1 = pos.add(0, 1, 0);
                BlockPos air2 = pos.add(0, 2, 0);

                return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(air1)).isEmpty()
                        && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(air2)).isEmpty();

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Block getBlock(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
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