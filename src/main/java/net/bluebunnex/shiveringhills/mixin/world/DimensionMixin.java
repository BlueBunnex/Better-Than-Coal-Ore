package net.bluebunnex.shiveringhills.mixin.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Dimension.class)
@Environment(EnvType.CLIENT)
public class DimensionMixin {

    // gotta find out where that pesky OverworldDimension class is

//    /**
//     * @author BlueBunnex
//     * @reason Shivering Hills has black fog
//     */
//    @Overwrite
//    @Environment(EnvType.CLIENT)
//    public Vec3d getFogColor(float timeOfDay, float tickDelta) {
//
//        return Vec3d.create(0, 0, 0);
//    }
}
