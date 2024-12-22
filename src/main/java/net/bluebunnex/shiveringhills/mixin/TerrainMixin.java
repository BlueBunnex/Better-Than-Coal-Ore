package net.bluebunnex.shiveringhills.mixin;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public class TerrainMixin {

    private static OctavePerlinNoiseSampler noise;

    /**
     * @author BlueBunnex
     * @reason new terrain for Shivering Hills
     */
    @Overwrite
    private double[] generateHeightMap(double[] heightMap, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {

        Random random = ((TerrainAccessorMixin) this).getRandom();

        if (noise == null)
            noise = new OctavePerlinNoiseSampler(random, 8);

        if (heightMap == null) {
            heightMap = new double[sizeX * sizeY * sizeZ];
        }

        int index = 0;

        for (int dx = 0; dx < sizeX; dx++) {

            for (int dz = 0; dz < sizeZ; dz++) {

                for(int dy = 0; dy < sizeY; dy++) {

                    // negative is no block, positive is block
                    // 64 is water height
                    heightMap[index] = ((noise.sample(x + dx, z + dz) + 128) / 8) - (y + dy);

                    index++;
                }
            }
        }

        return heightMap;
    }
}
