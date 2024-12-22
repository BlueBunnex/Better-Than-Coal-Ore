package net.bluebunnex.shiveringhills.mixin;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OverworldChunkGenerator.class)
public class TerrainMixin {

    /**
     * @author BlueBunnex
     * @reason new terrain for Shivering Hills
     */
    @Overwrite
    private double[] generateHeightMap(double[] heightMap, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {

        TerrainAccessorMixin access = (TerrainAccessorMixin) this;

        OctavePerlinNoiseSampler noise = access.getNoise1();

        if (noise == null) {

            noise = new OctavePerlinNoiseSampler(access.getRandom(), 8);
            access.setNoise1(noise);
        }

        if (heightMap == null) {

            heightMap = new double[sizeX * sizeY * sizeZ];
        }

        int index = 0;

        for (int dx = 0; dx < sizeX; dx++) {

            for (int dz = 0; dz < sizeZ; dz++) {

                double sample = (noise.sample((x + dx) * 12, (z + dz) * 12) / 16 + 8);

                for(int dy = 0; dy < sizeY; dy++) {

                    // negative is no block, positive is block
                    // 64 is water height
                    heightMap[index] = sample - (y + dy);

                    index++;
                }
            }
        }

        return heightMap;
    }
}
