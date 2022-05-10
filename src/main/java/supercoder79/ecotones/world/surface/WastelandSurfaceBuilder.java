package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.world.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class WastelandSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig WASTELAND_GRASS = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), EcotonesBlocks.DRIED_DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());

    public WastelandSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig config) {
        // yes, this is all waaaaay overcomplicated, but it's cool
        double noiseAddition = noise * (random.nextDouble() / 2);

        double randomAddition = ((random.nextDouble() / 2) * (random.nextDouble() / 2)) + (random.nextDouble() * 2);
        double randomAfterAddition = (random.nextDouble() / 3) + (noiseAddition / 4) + (random.nextDouble() / 3);

        double coefficient = random.nextDouble() + randomAddition;
        double gradientNoise = noise + noiseAddition + (((random.nextDouble() + randomAfterAddition) * coefficient));

        if (gradientNoise > 1.0D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, WASTELAND_GRASS);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, config);
        }
    }
}
