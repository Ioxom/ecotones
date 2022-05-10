package supercoder79.ecotones.world.layers.util.unused;

import supercoder79.ecotones.world.layers.system.layer.type.ParentedLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

/**
 * Samples randomly in a 2x2 square. This is good for.... something? I don't even know at this point, have fun
 *
 * @author SuperCoder79
 */
public interface FuzzySamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
    int sample(LayerRandomnessSource random, int value);
    @Override
    default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int i = context.nextInt(4);
        switch (i) {
            case 0:
                return sample(context, parent.sample(x + 1, z));
            case 1:
                return sample(context, parent.sample(x - 1, z));
            case 2:
                return sample(context, parent.sample(x, z + 1));
            case 3:
                return sample(context, parent.sample(x, z - 1));
        }

        return sample(context, parent.sample(x, z));
    }
}
