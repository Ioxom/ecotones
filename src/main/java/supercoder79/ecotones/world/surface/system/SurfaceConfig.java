package supercoder79.ecotones.world.surface.system;

import net.minecraft.block.BlockState;

public interface SurfaceConfig {
   BlockState getTopMaterial();

   BlockState getUnderMaterial();

   BlockState getUnderwaterMaterial();
}
