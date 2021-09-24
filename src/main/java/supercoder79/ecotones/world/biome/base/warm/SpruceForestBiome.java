package supercoder79.ecotones.world.biome.base.warm;

import net.minecraft.block.Blocks;
import net.minecraft.structure.TaigaVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.state.DeferredBlockStateProvider;
import supercoder79.ecotones.util.compat.FloralisiaCompat;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.*;
import supercoder79.ecotones.world.structure.EcotonesConfiguredStructures;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class SpruceForestBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome CLEARING;
    public static Biome GRAVELLY;
    public static Biome LAKE;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest"), new SpruceForestBiome(0.4f, 0.125f, 3.2, 0.88).build());
        CLEARING = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest_clearing"), new SpruceForestBiome(0.5f, 0.125f, 2.2, 0.95, 1.25, SurfaceBuilder.DEFAULT).build());
        GRAVELLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest_gravelly"), new SpruceForestBiome(0.4f, 0.125f, 2.8, 0.88, 7.5, EcotonesSurfaces.GRAVEL_BANDS).build());
        LAKE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest_lake"), new SpruceForestBiome(-0.25f, 0.075f, 1.2, 0.97).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest_hilly"), new SpruceForestBiome(1f, 0.5f, 4.2, 0.83).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "spruce_forest_mountainous"), new SpruceForestBiome(1.75f, 0.8f, 7, 0.75).build());

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 4);
        BiomeRegistries.registerBiomeVariants(INSTANCE, LAKE, CLEARING, GRAVELLY);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);

        Climate.WARM_HUMID.add(INSTANCE, 1);
    }

    protected SpruceForestBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 7.5, SurfaceBuilder.DEFAULT);
    }

    protected SpruceForestBiome(float depth, float scale, double hilliness, double volatility, double treeCount, SurfaceBuilder<TernarySurfaceConfig> surfaceBuilder) {
        this.surfaceBuilder(surfaceBuilder, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.RAIN);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8F);
        this.downfall(0.6F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.category(Biome.Category.TAIGA);

        this.addStructureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
        this.addStructureFeature(ConfiguredStructureFeatures.MINESHAFT);
        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);
        this.addStructureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> TaigaVillageData.STRUCTURE_POOLS, 5)));
        this.addStructureFeature(EcotonesConfiguredStructures.CAMPFIRE_SPRUCE);
        this.addStructureFeature(EcotonesConfiguredStructures.COTTAGE);

        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 6, 8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.65))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_SPRUCE)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_SPRUCE.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.FLOWER.configure(FeatureConfigHolder.TAIGA_FLOWERS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.WIDE_FERNS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(1));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.LAVENDER)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .repeat(1));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MUSHROOMS)
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(6, 14), true, UniformIntProvider.create(3, 5)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(4, 12), false, UniformIntProvider.create(2, 4)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(6));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(16, 64), false, UniformIntProvider.create(8, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(72));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(64, 96), UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_PAD.getDefaultState()),
                        SimpleBlockPlacer.INSTANCE).tries(10).build())
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.PODZOL.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeCount / 7.0))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.SPRUCE_TREE_CONFIG)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeCount))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BLUEBERRY_BUSH.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.BLUEBERRY_BUSH.configure(new ShrubDecoratorConfig(0.1))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(1, 4)))
                        .spreadHorizontally()
                        .repeat(3)
                        .applyChance(72));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(96));

        if (FloralisiaCompat.isEnabled()) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new DeferredBlockStateProvider(FloralisiaCompat.gladiolus()),
                            SimpleBlockPlacer.INSTANCE).tries(16).build())
                            .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                            .spreadHorizontally()
                            .applyChance(3));
        }

        BiomeHelper.addTemperateSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
