package supercoder79.ecotones.world.biome.unused;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import supercoder79.ecotones.world.decorator.*;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class HotSpringsBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "hot_springs"), new HotSpringsBiome().build());
        BiomeRegistries.registerSpecialBiome(INSTANCE, id -> true);
    }

    protected HotSpringsBiome() {
        this.surfaceBuilder(EcotonesSurfaces.HOT_SPRINGS_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(0.25F);
        this.scale(0.0125F);
        this.temperature(1.6F);
        this.downfall(0.4F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.waterColor(4445678);
        this.waterFogColor(329011);

//         this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .repeat(2)
                        .spreadHorizontally()
                        .decorate(new Spread32Decorator()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 6, 8)));

//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                Feature.TREE.configure(DefaultBiomeFeatures.PINE_TREE_CONFIG)
//                        .decorate(EcotonesDecorators.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.66f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, (FeatureConfigHolder.SPRUCE_TREE_CONFIG))
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.33f, 1)))
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.7))));

//        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.LAVA.getDefaultState()))
//                        .decorate(Decorator.LAVA_LAKE.configure(new ChanceDecoratorConfig(30))));

//        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()))
//                        .decorate(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(1))));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
