package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.world.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class BlessedSpringsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig GEYSER_CONFIG = new TernarySurfaceConfig(EcotonesBlocks.GEYSER.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());
    private static final TernarySurfaceConfig COBBLESTONE_CONFIG = new TernarySurfaceConfig(Blocks.COBBLESTONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());
    private static final TernarySurfaceConfig STONE_CONFIG = new TernarySurfaceConfig(Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());
    private static final TernarySurfaceConfig REGULAR_CONFIG = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());

    public BlessedSpringsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        int rand = random.nextInt(96);
        if (rand <= 2) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, GEYSER_CONFIG);
        }  else if (rand <= 10) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, STONE_CONFIG);
        } else if (rand <= 20) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, COBBLESTONE_CONFIG);
        } else if (rand <= 35) {
            boolean n = chunk.getBlockState(new BlockPos(x-1, height-1, z)).isAir();
            boolean s = chunk.getBlockState(new BlockPos(x+1, height-1, z)).isAir();
            boolean e = chunk.getBlockState(new BlockPos(x, height-1, z+1)).isAir();
            boolean w = chunk.getBlockState(new BlockPos(x, height-1, z-1)).isAir();
            if (!n && !s && !e && !w) {
                chunk.setBlockState(new BlockPos(x, height-1, z), Blocks.WATER.getDefaultState(), false);
            }
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, REGULAR_CONFIG);
        }
    }
}
