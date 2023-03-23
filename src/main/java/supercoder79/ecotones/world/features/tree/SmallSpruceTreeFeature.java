package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.util.Shapes;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.tree.trait.EcotonesTreeTraits;
import supercoder79.ecotones.world.tree.trait.smallspruce.SmallSpruceTrait;
import supercoder79.ecotones.world.tree.trait.smallspruce.DefaultSmallSpruceTrait;
import supercoder79.ecotones.world.treedecorator.LeafPileTreeDecorator;
import supercoder79.ecotones.world.treedecorator.LichenTreeDecorator;
import supercoder79.ecotones.world.treedecorator.PineconeTreeDecorator;

import java.util.*;
import java.util.function.BiConsumer;

public class SmallSpruceTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    private static final PineconeTreeDecorator PINECONES = new PineconeTreeDecorator(2);
    private static final LichenTreeDecorator LICHEN = new LichenTreeDecorator(3);
    private static final LeafPileTreeDecorator LEAF_PILES = new LeafPileTreeDecorator(EcotonesBlocks.SPRUCE_LEAF_PILE.getDefaultState(), 8, 3);

    public SmallSpruceTreeFeature() {
        super(SimpleTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        // Trait data
        SmallSpruceTrait trait = DefaultSmallSpruceTrait.INSTANCE;
        if (generator instanceof EcotonesChunkGenerator) {
            trait = EcotonesTreeTraits.SMALL_SPRUCE.get((EcotonesChunkGenerator) generator, pos);
        }

        int heightAddition = trait.extraHeight(random);

        double maxRadius = trait.maxRadius(random);

        BlockPos.Mutable mutable = pos.mutableCopy();

        List<BlockPos> logs = new ArrayList<>();
        for (int y = 0; y < 8 + heightAddition; y++) {
            world.setBlockState(mutable, config.woodState, 0);
            logs.add(mutable.toImmutable());
            mutable.move(Direction.UP);
        }

        mutable = pos.mutableCopy();
        mutable.move(Direction.UP, 1 + heightAddition);

        List<BlockPos> leaves = new ArrayList<>();
        for (int y = 0; y < 9; y++) {
            Shapes.circle(mutable.mutableCopy(), maxRadius * trait.model(y / 10.f), leafPos -> {
                if (AbstractTreeFeature.isAirOrLeaves(world, leafPos)) {
                    world.setBlockState(leafPos, config.leafState, 0);
                    leaves.add(leafPos.toImmutable());
                }
            });
            mutable.move(Direction.UP);
        }

        BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

        TreeDecorator.Generator decorator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), new HashSet<>(logs), new HashSet<>(leaves), Set.of());

        // Generate pinecones
        PINECONES.generate(decorator);

        // Generate lichen
        LICHEN.generate(decorator);

        // Generate leaf piles
        LEAF_PILES.generate(decorator);

        return false;
    }
}
