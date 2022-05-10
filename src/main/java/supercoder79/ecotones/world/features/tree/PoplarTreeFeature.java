package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.Shapes;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.tree.trait.EcotonesTreeTraits;
import supercoder79.ecotones.world.tree.trait.poplar.PoplarTrait;
import supercoder79.ecotones.world.tree.trait.poplar.DefaultPoplarTrait;

import java.util.Random;

public class PoplarTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {

    public PoplarTreeFeature(Codec<SimpleTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return false;

        // Trait data
        PoplarTrait trait = DefaultPoplarTrait.INSTANCE;
        if (generator instanceof EcotonesChunkGenerator) {
            trait = EcotonesTreeTraits.POPLAR.get((EcotonesChunkGenerator) generator, pos);
        }

        double maxRadius = trait.maxRadius(random);
        int heightAddition = trait.extraHeight(random);
        int leafHeight = trait.leafHeight(random);
        double leafRadius = trait.leafRadius(leafHeight, random);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int y = 0; y < leafHeight; y++) {
            world.setBlockState(mutable, config.woodState, 0);

            //add branch blocks
            if (maxRadius * trait.model(y / leafRadius) > 2.3) {
                Direction.Axis axis = getAxis(random);
                world.setBlockState(mutable.offset(getDirection(axis, random)).up(heightAddition), config.woodState.with(Properties.AXIS, axis), 0);
            }

            mutable.move(Direction.UP);
        }

        if (config.leafState.isAir()) {
            return true;
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, heightAddition);

        for (int y = 0; y < leafHeight; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * trait.model(y / leafRadius), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                }
            });

            mutable.move(Direction.UP);
        }

        return true;
    }

    private Direction.Axis getAxis(Random random) {
        return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
    }

    private Direction getDirection(Direction.Axis axis, Random random) {
        if (axis == Direction.Axis.X) {
            return random.nextBoolean() ? Direction.EAST : Direction.WEST;
        } else {
            return random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
        }
    }
}
