package supercoder79.ecotones.world.layers.util.unused;

import supercoder79.ecotones.world.layers.system.layer.type.ParentedLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

/**
 * Samples biomes around the ends of a 5x5 plus sign. No, I'm not calling it KnightTransformer, what gave you that idea?
 * This is probably very bad for performance, so use it sparingly.
 *
 * @author Juuz
 */
public interface AroundCrossEndsSamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
    int sample(LayerRandomnessSource random, int n1, int n2, int e1, int e2, int s1, int s2, int w1, int w2, int center);

    @Override
    default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        return sample(context,
                parent.sample(transformX(x - 1), transformZ(z - 2)),
                parent.sample(transformX(x + 1), transformZ(z - 2)),
                parent.sample(transformX(x + 2), transformZ(z - 1)),
                parent.sample(transformX(x + 2), transformZ(z + 1)),
                parent.sample(transformX(x - 1), transformZ(z + 2)),
                parent.sample(transformX(x + 1), transformZ(z + 2)),
                parent.sample(transformX(x - 2), transformZ(z - 1)),
                parent.sample(transformX(x - 2), transformZ(z + 1)),
                parent.sample(transformX(x), transformZ(z))
        );
    }
}