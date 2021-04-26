package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HoleESP extends Feature {

    private NumberSetting range = new NumberSetting("Range", this, 10f, 1f, 15f, 1f);

    private NumberSetting height = new NumberSetting("Height", this, 1.0f, 0.01f, 1.0f, 0.01f);

    private ModeSetting renderMode = new ModeSetting("RenderMode", this, "Filled", "Filled", "Outline", "Both");

    private NumberSetting bedrockR = new NumberSetting("Bedrock R", this, 0f, 0f, 255f, 1f);
    private NumberSetting bedrockG = new NumberSetting("Bedrock G", this, 255f, 0f, 255f, 1f);
    private NumberSetting bedrockB = new NumberSetting("Bedrock B", this, 0f, 0f, 255f, 1f);
    private NumberSetting bedrockA = new NumberSetting("Bedrock A", this, 127f, 0f, 255f, 1f);

    private NumberSetting obbyR = new NumberSetting("Obby R", this, 255f, 0f, 255f, 1f);
    private NumberSetting obbyG = new NumberSetting("Obby G", this, 0f, 0f, 255f, 1f);
    private NumberSetting obbyB = new NumberSetting("Obby B", this, 0f, 0f, 255f, 1f);
    private NumberSetting obbyA = new NumberSetting("Obby A", this, 127f, 0f, 255f, 1f);

    private final ExecutorService service = Executors.newFixedThreadPool(5);

    public HoleESP() {
        super("HoleESP", "Highlight safe spots for cpvp", Category.Render);
        addSettings(range, height, renderMode,
                bedrockR, bedrockG, bedrockB, bedrockA,
                obbyR, obbyG, obbyB, obbyA);
    }

    HoleCalculationCallable calcCallable;

    @Override
    public void onEnable() {
        calcCallable = new HoleCalculationCallable(mc, this);
    }

    public volatile ArrayList<EspHole> holes = new ArrayList<>();

    @EventTarget
    public void onRender3d(RenderEvent.World event) {
        if (holes != null) {
            if (holes.size() == 0)
                return;

            for (EspHole hole : holes) {
                BlockPos bpos = hole.getPos();

                float bHeight = 0f;

                if (height.getValue() > 0.01f)
                    bHeight = height.getValue();

                AxisAlignedBB box = new AxisAlignedBB(bpos.getX(), bpos.getY(), bpos.getZ(), bpos.getX() + 1, bpos.getY() + bHeight, bpos.getZ() + 1);
                double[] rpos = Util.getRenderPos();

                float r = 1f, g = 1f, b = 1f, a = 1f;

                if (hole.getType() == HoleType.BEDROCK) {
                    r = bedrockR.getValue() / 255f;
                    g = bedrockG.getValue() / 255f;
                    b = bedrockB.getValue() / 255f;
                    a = bedrockA.getValue() / 255f;
                } else if (hole.getType() == HoleType.OBBY) {
                    r = obbyR.getValue() / 255f;
                    g = obbyG.getValue() / 255f;
                    b = obbyB.getValue() / 255f;
                    a = obbyA.getValue() / 255f;
                }

                if (renderMode.getMode().equals("Filled") || renderMode.getMode().equals("Both")) {
                    RenderUtil.prepareGL(1.0f);
                    RenderGlobal.renderFilledBox(box.offset(-rpos[0], -rpos[1], -rpos[2]), r, g, b, a);
                    RenderUtil.releaseGL();
                }

                if (renderMode.getMode().equals("Outline") || renderMode.getMode().equals("Both")) {
                    RenderUtil.prepareGL(1.0f);
                    RenderGlobal.drawSelectionBoundingBox(box.offset(-rpos[0], -rpos[1], -rpos[2]), r, g, b, 1.0f);
                    RenderUtil.releaseGL();
                }

            }
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        calcCallable.setRange((int) range.getValue());
        service.submit(calcCallable);
    }

    private static class HoleCalculationCallable implements Callable {

        private final Minecraft mc;
        private int radialRange = 0;

        private final HoleESP parent;

        public HoleCalculationCallable(Minecraft minecraft, HoleESP module) {
            this.mc = minecraft;
            this.parent = module;
        }

        public synchronized void setRange(int range) {
            this.radialRange = range;
        }

        private boolean isHole(BlockPos blockPos) {
            return isBlock(Blocks.BEDROCK, blockPos.add(1.0, 0.0, 0.0))
                    // x - 1
                    && isBlock(Blocks.BEDROCK, blockPos.add(-1.0, 0.0, 0.0))
                    // z + 1
                    && isBlock(Blocks.BEDROCK, blockPos.add(0.0, 0.0, 1.0))
                    // z - 1
                    && isBlock(Blocks.BEDROCK, blockPos.add(0.0, 0.0, -1.0))
                    // y - 1
                    && isBlock(Blocks.BEDROCK, blockPos.add(0.0, -1.0, 0.0))
                    // y + 1
                    && isBlock(Blocks.AIR, blockPos.add(0.0, 1.0, 0.0));
        }

        private boolean isMixedHole(BlockPos blockPos) {
            return isEitherBlock(blockPos.add(1.0, 0.0, 0.0))
                    // x - 1
                    && isEitherBlock(blockPos.add(-1.0, 0.0, 0.0))
                    // z + 1
                    && isEitherBlock(blockPos.add(0.0, 0.0, 1.0))
                    // z - 1
                    && isEitherBlock(blockPos.add(0.0, 0.0, -1.0))
                    // y - 1
                    && isEitherBlock(blockPos.add(0.0, -1.0, 0.0))
                    // y + 1
                    && isBlock(Blocks.AIR, blockPos.add(0.0, 1.0, 0.0));
        }

        private boolean isBlock(Block block, BlockPos blockPos) {
            return mc.world.getBlockState(blockPos).getBlock() == block;
        }

        private boolean isEitherBlock(BlockPos blockPos) {
            return mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN;
        }


        // calculate hole list
        @Override
        public Object call() {
            ArrayList<EspHole> holeList = new ArrayList<>();
            // loop for x-axis
            for (int x = -radialRange; x < radialRange; x++) {
                // loop for y axis
                for (int y = radialRange + 1; y > -radialRange; y--) {
                    // loop for z axis
                    for (int z = -radialRange; z < radialRange; z++) {

                        double xBlock = (mc.player.posX + x);
                        double yBlock = (mc.player.posY + y);
                        double zBlock = (mc.player.posZ + z);

                        BlockPos blockPos = new BlockPos(xBlock, yBlock, zBlock);

                        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                            if (isHole(blockPos)) {
                                holeList.add(new EspHole(blockPos, HoleType.BEDROCK));
                            } else if (isMixedHole(blockPos)) {
                                holeList.add(new EspHole(blockPos, HoleType.OBBY));
                            }
                        }
                    }
                }
            }

            parent.holes = holeList;
            return null;
        }

        private boolean sameBlockPos(BlockPos bp1, BlockPos bp2) {
            return bp1.getX() == bp2.getX() && bp1.getY() == bp2.getY() && bp1.getZ() == bp2.getZ();
        }
    }

    private enum HoleType {
        BEDROCK,
        OBBY,
    }

    private static class EspHole {
        private final BlockPos pos;
        private final HoleType type;

        public EspHole(BlockPos pos, HoleType type) {
            this.pos = pos;
            this.type = type;
        }

        public BlockPos getPos() {
            return pos;
        }

        public HoleType getType() {
            return type;
        }
    }
}
