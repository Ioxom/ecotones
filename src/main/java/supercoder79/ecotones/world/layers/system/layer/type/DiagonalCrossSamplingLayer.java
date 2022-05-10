package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;
import supercoder79.ecotones.world.layers.system.layer.util.NorthWestCoordinateTransformer;

public interface DiagonalCrossSamplingLayer extends ParentedLayer, NorthWestCoordinateTransformer {
   int sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center);

   @Override
   default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      return this.sample(
         context,
         parent.sample(this.transformX(x + 0), this.transformZ(z + 2)),
         parent.sample(this.transformX(x + 2), this.transformZ(z + 2)),
         parent.sample(this.transformX(x + 2), this.transformZ(z + 0)),
         parent.sample(this.transformX(x + 0), this.transformZ(z + 0)),
         parent.sample(this.transformX(x + 1), this.transformZ(z + 1))
      );
   }
}
