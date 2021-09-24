package supercoder79.ecotones.world.features.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.Heightmap;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.ColumnPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.DoubleOrNormalPlacer;
import supercoder79.ecotones.util.state.SmallCactusBlockStateProvider;
import supercoder79.ecotones.world.features.foliage.PlusLeavesFoliagePlacer;
import supercoder79.ecotones.world.features.foliage.SmallPineFoliagePlacer;
import supercoder79.ecotones.world.treedecorator.LeafPileTreeDecorator;
import supercoder79.ecotones.world.treedecorator.LichenTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.Optional;
import java.util.OptionalInt;

public final class FeatureConfigHolder {
    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
    
    // grass
    public static final RandomPatchFeatureConfig REEDS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.REEDS.getDefaultState(), 1)
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 3)), new SimpleBlockPlacer()).tries(16).build();

    public static final RandomPatchFeatureConfig MOSTLY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 8)
                    .add(Blocks.GRASS.getDefaultState(), 1)), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 6)
                    .add(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 2)
                    .add(Blocks.GRASS.getDefaultState(), 1)), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig COOL_SCRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 4)
                    .add(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 5)
                    .add(Blocks.GRASS.getDefaultState(), 1)), new SimpleBlockPlacer()).tries(48).build();

    public static final RandomPatchFeatureConfig SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .add(Blocks.GRASS.getDefaultState(), 1)), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig RARELY_SHORT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 4)), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig PRAIRIE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 16)
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 2)), new SimpleBlockPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig GRASSLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 4)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 1)), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig BIRCH_GROVE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 8)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 2)), new DoubleOrNormalPlacer()).tries(32).build();
    
    public static final RandomPatchFeatureConfig SWITCHGRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SWITCHGRASS.getDefaultState(), 1)), new DoubleOrNormalPlacer())
                    .tries(72).build();

    public static final RandomPatchFeatureConfig LUSH_SHRUBLAND_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 2)
                    .add(EcotonesBlocks.MARIGOLD.getDefaultState(), 2)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 4)
                    .add(Blocks.GRASS.getDefaultState(), 8)), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig DRY_STEPPE_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 4)
                    .add(EcotonesBlocks.SMALL_SHRUB.getDefaultState(), 2)
                    .add(EcotonesBlocks.SANDY_GRASS.getDefaultState(), 4)
                    .add(Blocks.GRASS.getDefaultState(), 2)), new SimpleBlockPlacer()).tries(32).build();
    

    public static final RandomPatchFeatureConfig TAIGA_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 4)
                    .add(Blocks.FERN.getDefaultState(), 4)
                    .add(Blocks.LARGE_FERN.getDefaultState(), 1)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 1)), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SHORT_GRASS.getDefaultState(), 1)
                    .add(Blocks.GRASS.getDefaultState(), 6)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 3)), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig ONLY_TALL_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(Blocks.TALL_GRASS.getDefaultState(), 1)), new DoubleOrNormalPlacer())
                    .tries(96).build();

    public static final RandomPatchFeatureConfig BLUEBELL_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.BLUEBELL.getDefaultState(), 32)
                    .add(Blocks.GRASS.getDefaultState(), 4)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 2)), new DoubleOrNormalPlacer()).tries(32).build();

    public static final RandomPatchFeatureConfig LILY_OF_THE_VALLEY_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(Blocks.LILY_OF_THE_VALLEY.getDefaultState(), 32)
                    .add(Blocks.GRASS.getDefaultState(), 4)
                    .add(Blocks.TALL_GRASS.getDefaultState(), 2)), new DoubleOrNormalPlacer()).tries(48).build();

    public static final RandomPatchFeatureConfig DESERT_GRASS_CONFIG =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SANDY_GRASS.getDefaultState(), 1)), new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(24).build();

    public static final RandomPatchFeatureConfig SMALL_CACTUS =
            new RandomPatchFeatureConfig.Builder(SmallCactusBlockStateProvider.INSTANCE, new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(12).build();

    public static final RandomPatchFeatureConfig THORN_BUSH =
            new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(EcotonesBlocks.THORN_BUSH.getDefaultState()), new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(16).build();

    // flowers
    public static final RandomPatchFeatureConfig TAIGA_FLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(64).build();

    public static final RandomPatchFeatureConfig WIDE_FERNS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.WIDE_FERN.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(48).build();

    public static final RandomPatchFeatureConfig SMALL_LILAC =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SMALL_LILAC.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(12).build();

    public static final RandomPatchFeatureConfig LAVENDER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.LAVENDER.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(5)
                    .spreadZ(5)
                    .tries(12).build();

    public static final RandomPatchFeatureConfig WILDFLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(32).build();

    public static final RandomPatchFeatureConfig DANDELION_FIELD_FLOWERS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.POOFY_DANDELION.getDefaultState(), 1)
                    .add(Blocks.DANDELION.getDefaultState(), 3)
                    .add(EcotonesBlocks.WILDFLOWERS.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(5)
                    .spreadZ(5)
                    .tries(32).build();

    public static final RandomPatchFeatureConfig CYAN_ROSE =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.CYAN_ROSE.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(32).build();

    // misc
    public static final RandomPatchFeatureConfig SURFACE_ROCKS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.SURFACE_ROCK.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(4).build();

    public static final RandomPatchFeatureConfig CLOVER =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.CLOVER.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(12).build();

    public static final RandomPatchFeatureConfig MOSS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(EcotonesBlocks.MOSS.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(16).build();

    public static final RandomPatchFeatureConfig PUMPKIN =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(Blocks.PUMPKIN.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(64).build();

    public static final RandomPatchFeatureConfig COBWEB =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(Blocks.COBWEB.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .tries(12).build();

    public static final RandomPatchFeatureConfig LARGE_CACTUS_PATCH =
            new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.CACTUS.getDefaultState()),
                    new ColumnPlacer(UniformIntProvider.create(1, 3)))
                    .tries(32).cannotProject().build();

    public static final RandomPatchFeatureConfig MUSHROOMS =
            new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(pool()
                    .add(Blocks.BROWN_MUSHROOM.getDefaultState(), 1)
                    .add(Blocks.RED_MUSHROOM.getDefaultState(), 1)),
                    new SimpleBlockPlacer())
                    .spreadX(7)
                    .spreadZ(7)
                    .tries(64).build();

    public static final WaterFeatureConfig GRASS_WATER_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState()), Optional.empty(), 6);

    public static final WaterFeatureConfig PEAT_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), EcotonesBlocks.PEAT_BLOCK.getDefaultState()), Optional.of(new SimpleBlockStateProvider(EcotonesBlocks.PEAT_BLOCK.getDefaultState())), 2);

    public static final WaterFeatureConfig GRANITE_WATER_POOL_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRANITE.getDefaultState()), Optional.of(new SimpleBlockStateProvider(Blocks.GRANITE.getDefaultState())), 3);

    public static final WaterFeatureConfig GRANITE_WATER_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRANITE.getDefaultState()), Optional.empty(), 12);

    public static final WaterFeatureConfig DIORITE_WATER_POOL_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIORITE.getDefaultState()), Optional.of(new SimpleBlockStateProvider(Blocks.DIORITE.getDefaultState())), 4);

    public static final WaterFeatureConfig DIORITE_WATER_PATCH =
            new WaterFeatureConfig(ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIORITE.getDefaultState()), Optional.empty(), 12);

    public static final RockSpireFeatureConfig STONE_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(Blocks.STONE.getDefaultState()));

    public static final RockSpireFeatureConfig GRANITE_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(Blocks.GRANITE.getDefaultState()));

    public static final RockSpireFeatureConfig DIORITE_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(Blocks.DIORITE.getDefaultState()));

    public static final RockSpireFeatureConfig RED_ROCK_SPIRE =
            new RockSpireFeatureConfig(new SimpleBlockStateProvider(EcotonesBlocks.RED_ROCK.getDefaultState()));

    // trees
    public static final TreeFeatureConfig SPRUCE_TREE_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                    new StraightTrunkPlacer(6, 4, 3),
                    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.SPRUCE_SAPLING.getDefaultState()),
                    new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines().build().setTreeDecorators(ImmutableList.of(new PineconeTreeDecorator(6), new LichenTreeDecorator(12), new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 12, 4)));

    public static final TreeFeatureConfig SMALL_PINE_CONFIG = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new StraightTrunkPlacer(9, 6, 0),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_SAPLING.getDefaultState()),
            new SmallPineFoliagePlacer(UniformIntProvider.create(1, 1), UniformIntProvider.create(0, 1), UniformIntProvider.create(4, 6)),
            new TwoLayersFeatureSize(2, 0, 2))
            .ignoreVines().build()
            .setTreeDecorators(ImmutableList.of(new PineconeTreeDecorator(2), new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 6, 4)));

    public static final TreeFeatureConfig JUNGLE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.JUNGLE_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 8, 0),
            new SimpleBlockStateProvider(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.JUNGLE_SAPLING.getDefaultState()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(new CocoaBeansTreeDecorator(0.2F), TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).ignoreVines().build();

    public static final TreeFeatureConfig MEGA_JUNGLE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.JUNGLE_LOG.getDefaultState()),
            new MegaJungleTrunkPlacer(10, 2, 19),
            new SimpleBlockStateProvider(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.JUNGLE_SAPLING.getDefaultState()),
            new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeavesVineTreeDecorator.INSTANCE)).build();

    public static final TreeFeatureConfig DRY_STEPPE_TREE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
            new LargeOakTrunkPlacer(8, 6, 0),
            new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.OAK_SAPLING.getDefaultState()),
            new PlusLeavesFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .ignoreVines().build();
}
