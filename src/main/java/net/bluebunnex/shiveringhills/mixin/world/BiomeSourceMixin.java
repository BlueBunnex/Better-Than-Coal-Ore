package net.bluebunnex.shiveringhills.mixin.world;

import net.minecraft.world.biome.source.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BiomeSource.class)
public class BiomeSourceMixin {

    /**
     * @author BlueBunnex
     * @reason Shivering Hills needs the temperature to be 0
     */
    @Overwrite
    public double[] create(double[] map, int x, int z, int width, int depth) {

        if (map == null || map.length < width * depth) {
            map = new double[width * depth];
        }

        return map;
    }

//    /**
//     * @author BlueBunnex
//     * @reason Shivering Hills needs the temperature to be 0
//     */
//    @Overwrite
//    @Environment(EnvType.CLIENT)
//    public double getTemperature(int x, int z) {
//
//        return 0;
//    }
//
//    /**
//        this is assigning the value this.temperatureMap
//     */
//    @Redirect(method = "getBiomesInArea([Lnet/minecraft/world/biome/Biome;IIII)[Lnet/minecraft/world/biome/Biome;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/noise/OctaveSimplexNoiseSampler;sample([DDDIIDDD)[D"))
//    private double[] mixin(OctaveSimplexNoiseSampler instance, double[] map, double x, double y, int width, int height, double d, double e, double f) {
//
//        return new double[width * height];
//    }
}
