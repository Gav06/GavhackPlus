package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.IMinecraft;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.TickTimer;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AutoCrystal extends Feature {

    private final ModeSetting logic = new ModeSetting("Logic", this, "Place -> Break", "Place -> Break", "Break -> Place");
    private final BooleanSetting breakBool = new BooleanSetting("Break", this, true);
    private final BooleanSetting placeBool = new BooleanSetting("Place", this, true);
    private final NumberSetting attackDistance = new NumberSetting("AttackRange", this, 4.0f, 1.0f, 6.0f, 0.1f);
    private final NumberSetting breakDistance = new NumberSetting("BreakDistance", this, 4f, 1f, 6f, 0.1f);
    private final NumberSetting wallsDistance = new NumberSetting("WallsDistance", this, 2.5f, 0f, 5f, 0.1f);
    private final NumberSetting placeDistance = new NumberSetting("PlaceDistance", this, 4f, 1f, 6f, 0.1f);
    private final NumberSetting breakDelay = new NumberSetting("BreakDelay", this, 2f, 0f, 20f, 1f);
    private final NumberSetting placeDelay = new NumberSetting("PlaceDelay", this, 3f, 0f, 20f, 1f);
    private final BooleanSetting offhand = new BooleanSetting("Offhand", this, false);
    private final BooleanSetting fastPlace = new BooleanSetting("FastPlace", this, true);
    private final BooleanSetting rotations = new BooleanSetting("Rotations", this, true);
    private final NumberSetting minTargetDmg = new NumberSetting("MinTargetDmg", this, 4f, 0.1f, 20f, 0.1f);
    private final NumberSetting maxSelfDmg = new NumberSetting("MaxSelfDmg", this, 10f, 0f, 50f, 0.1f);
    private final BooleanSetting debugRenders = new BooleanSetting("DebugRenders", this, false);

    public AutoCrystal() {
        super("AutoCrystal", ":)", Category.Combat);
        addSettings(
                logic,
                breakBool,
                placeBool,
                attackDistance,
                breakDistance,
                wallsDistance,
                placeDistance,
                breakDelay,
                placeDelay,
                offhand,
                fastPlace,
                minTargetDmg,
                maxSelfDmg,
                debugRenders);
    }

    @Override
    public void onEnable() {
        active = false;
        if (mc.player != null) {
            rotatePitch = mc.player.rotationPitch;
            rotateYaw = mc.player.rotationYaw;
        }
    }

    @Override
    public void onDisable() {
        active = false;
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (logic.getMode().equals("Place -> Break")) {
            doPlaceLogic();
            doBreakLogic();
        } else {
            doBreakLogic();
            doPlaceLogic();
        }
    }

    @EventTarget
    public void onRender(RenderEvent.World event) {
        if (renderBlock != null) {
            RenderUtil.prepareGL(1.0f);
            double[] rpos = Util.getRenderPos();
            Color c = ColorMod.globalColor;
            RenderGlobal.renderFilledBox(renderBlock.offset(-rpos[0], -rpos[1], -rpos[2]), c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.4f);
            RenderUtil.releaseGL();
        }

        if (!debugRenders.getValue())
            return;

        if (targetCrystal != null) {

        }
    }

    @EventTarget
    public void onPacket(PacketEvent.Send event) {
        if (rotations.getValue()) {
            if (event.getPacket() instanceof CPacketPlayer) {
                CPacketPlayer packet = (CPacketPlayer) event.getPacket();

                packet.yaw = rotateYaw;
                packet.pitch = rotatePitch;
            }
        }
    }

    private final TickTimer breakTimer = new TickTimer();
    private final TickTimer placeTimer = new TickTimer();

    private EntityPlayer targetPlayer;
    private EntityEnderCrystal targetCrystal;
    private AxisAlignedBB renderBlock;

    private float rotateYaw = 0f;
    private float rotatePitch = 0f;

    private boolean active = false;

    private void doBreakLogic() {
        if (breakTimer.hasTicksPassed((long) breakDelay.getValue()) && breakBool.getValue()) {
            active = true;
            for (Entity e : mc.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    if (canAttackCrystal((EntityEnderCrystal) e)) {
                        targetCrystal = (EntityEnderCrystal) e;
                        break;
                    }
                }
            }

            if (canAttackCrystal(targetCrystal)) {
                if (mc.player.canEntityBeSeen(targetCrystal)) {
                    breakCrystal(targetCrystal);
                } else {
                    if (mc.player.getPosition().getDistance(
                            targetCrystal.getPosition().getX(),
                            targetCrystal.getPosition().getY(),
                            targetCrystal.getPosition().getZ()) <= wallsDistance.getValue()) {

                        breakCrystal(targetCrystal);

                    }
                }
            }

            breakTimer.reset();
            active = true;
        } else {
            active = false;
        }
    }

    private void doPlaceLogic() {

        if (placeTimer.hasTicksPassed((long) placeDelay.getValue()) && placeBool.getValue()) {
            switchToCrystal();

            if (!offhand.getValue()) {
                if (mc.player.inventory.getCurrentItem().getItem() != Items.END_CRYSTAL) {
                    renderBlock = null;
                    return;
                }
            } else {
                if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    renderBlock = null;
                    return;
                }
            }

            if (fastPlace.getValue())
                ((IMinecraft)mc).setDelayTimer(0);

            // getting target
            targetPlayer = mc.world.playerEntities.stream()
                    .filter(e -> !e.equals(mc.player))
                    .min(Comparator.comparing(e -> mc.player.getDistance(e)))
                    .orElse(null);

            // attacking target
            if (targetPlayer != null) {
                BlockPos bestBlockPos = null;
                // finding best spot
                if (targetPlayer.getDistance(mc.player) <= attackDistance.getValue()) {

                    double highestTargetDamage = Double.MIN_VALUE;

                    for (BlockPos blockPos : getBlocksAroundPlayer(placeDistance.getValue())) {
                        if (!canPlaceCrystal(blockPos))
                            continue;

                        double targetDamage = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, targetPlayer);
                        double selfDamage = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, mc.player);

                        if (targetDamage >= highestTargetDamage && targetDamage >= minTargetDmg.getValue()) {
                            if (selfDamage <= maxSelfDmg.getValue()) {
                                bestBlockPos = blockPos;
                                renderBlock = new AxisAlignedBB(blockPos);
                            }
                        }
                    }
                }

                // attacking the best spot

                if (bestBlockPos != null && !active) {
                    if (rotations.getValue()) {
                        double[] rotate = Util.calculateLookAt(bestBlockPos.getX(), bestBlockPos.getY(), bestBlockPos.getZ(), mc.player);
                        rotateYaw = (float) rotate[0];
                        rotatePitch = (float) rotate[1];
                    }
                    place(bestBlockPos);
                    placeTimer.reset();
                    return;
                }
            }

            renderBlock = null;
        }
    }

    private boolean canPlaceCrystal(BlockPos pos) {

        if (pos.getDistance(mc.player.getPosition().getX(), mc.player.getPosition().getY(), mc.player.getPosition().getZ()) > placeDistance.getValue())
            return false;

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
            if (mc.player.getDistance(targetCrystal) <= breakDistance.getValue()) {
                return !targetCrystal.isDead;
            }
        }

        return false;
    }

    private ArrayList<BlockPos> getBlocksAroundPlayer(float range) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        // loop for x axis
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

    private void place(BlockPos pos) {
        EnumHand hand = offhand.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        swingArm();
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, hand, 0, 0, 0));
    }

    private void breakCrystal(EntityEnderCrystal crystal) {
        swingArm();
        mc.player.connection.sendPacket(new CPacketUseEntity(crystal));
    }

    private void swingArm() {
        if (offhand.getValue()) {
            mc.player.swingArm(EnumHand.OFF_HAND);
        } else {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    private void switchToCrystal() {
        if (!offhand.getValue()) {
            if (mc.player.inventory.getCurrentItem().getItem() != Items.END_CRYSTAL) {
                for (int i = 0; i < 9; i++) {
                    if (mc.player.inventory.mainInventory.get(i).getItem() == Items.END_CRYSTAL) {
                        mc.player.inventory.currentItem = i;
                        mc.getConnection().sendPacket(new CPacketHeldItemChange(i));
                    }
                }
            }
        }
    }

    private boolean isCrystalAtPos(BlockPos pos) {
        return mc.world.getEntitiesWithinAABB(EntityEnderCrystal.class, new AxisAlignedBB(pos)).size() == 0;
    }
}
