package net.bluebunnex.shiveringhills.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

    @Shadow
    private Random random;

    @Shadow
    private double[] heightMap;

    @Shadow
    private World world;

    @Shadow
    OctavePerlinNoiseSampler perlinNoise1;

    /**
     * @author BlueBunnex
     * @reason new terrain for Shivering Hills
     */
    @Overwrite
    private double[] generateHeightMap(double[] heightMap, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {

        double[] downfallMap = world.method_1781().downfallMap;

        if (perlinNoise1 == null) {

            perlinNoise1 = new OctavePerlinNoiseSampler(random, 8);
        }

        if (heightMap == null) {

            heightMap = new double[sizeX * sizeY * sizeZ];
        }

        int index = 0;

        for (int dx = 0; dx < sizeX; dx++) {

            for (int dz = 0; dz < sizeZ; dz++) {

                int var18 = dx * 16 / sizeX + 8 / sizeX;
                int var20 = dz * 16 / sizeX + 8 / sizeX;

                // lower downfall = flatter terrain, for now
                // noise samples seem to be between -128 and 128
                double downfall = downfallMap[var18 * 16 + var20];
                double sample = ((perlinNoise1.sample((x + dx) * 7, (z + dz) * 7) + 128) / 32 * downfall + 8);

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

    /**
     * @author BlueBunnex
     * @reason water bad :(
     */
    @Overwrite
    public void buildTerrain(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes, double[] temperatures) {

        heightMap = this.generateHeightMap(heightMap, chunkX * 4, 0, chunkZ * 4, 5, 17, 5);

        for(int var11 = 0; var11 < 4; ++var11) {
            for(int var12 = 0; var12 < 4; ++var12) {
                for(int var13 = 0; var13 < 16; ++var13) {
                    double var14 = 0.125;
                    double var16 = heightMap[((var11 + 0) * 5 + var12 + 0) * 17 + var13 + 0];
                    double var18 = heightMap[((var11 + 0) * 5 + var12 + 1) * 17 + var13 + 0];
                    double var20 = heightMap[((var11 + 1) * 5 + var12 + 0) * 17 + var13 + 0];
                    double var22 = heightMap[((var11 + 1) * 5 + var12 + 1) * 17 + var13 + 0];
                    double var24 = (heightMap[((var11 + 0) * 5 + var12 + 0) * 17 + var13 + 1] - var16) * var14;
                    double var26 = (heightMap[((var11 + 0) * 5 + var12 + 1) * 17 + var13 + 1] - var18) * var14;
                    double var28 = (heightMap[((var11 + 1) * 5 + var12 + 0) * 17 + var13 + 1] - var20) * var14;
                    double var30 = (heightMap[((var11 + 1) * 5 + var12 + 1) * 17 + var13 + 1] - var22) * var14;

                    for(int var32 = 0; var32 < 8; ++var32) {
                        double var33 = 0.25;
                        double var35 = var16;
                        double var37 = var18;
                        double var39 = (var20 - var16) * var33;
                        double var41 = (var22 - var18) * var33;

                        for(int var43 = 0; var43 < 4; ++var43) {
                            int var44 = var43 + var11 * 4 << 11 | var12 * 4 << 7 | var13 * 8 + var32;
                            short var45 = 128;
                            double var46 = 0.25;
                            double var48 = var35;
                            double var50 = (var37 - var35) * var46;

                            for(int var52 = 0; var52 < 4; ++var52) {

                                int var55 = 0;

                                if (var13 * 8 + var32 < 64) {
                                    var55 = Block.ICE.id;
                                }

                                if (var48 > 0.0) {
                                    var55 = Block.STONE.id;
                                }

                                blocks[var44] = (byte)var55;
                                var44 += var45;
                                var48 += var50;
                            }

                            var35 += var39;
                            var37 += var41;
                        }

                        var16 += var24;
                        var18 += var26;
                        var20 += var28;
                        var22 += var30;
                    }
                }
            }
        }
    }

    /*
        This probably'll be replaced later once we want to change ore generation, but whatever
     */
    @Inject(method = "decorate", at = @At("HEAD"))
    private void moreTrees(ChunkSource source, int x, int z, CallbackInfo ci) {

        int chunkX = x * 16;
        int chunkZ = z * 16;

        Biome biome = world.method_1781().getBiome(chunkX + 16, chunkZ + 16);

        if (biome != Biome.TAIGA) {
            return;
        }

        int featureX, featureZ;

        for (int i = 0; i < 30; i++) {
            featureX = chunkX + random.nextInt(16) + 8;
            featureZ = chunkZ + random.nextInt(16) + 8;
            Feature var18 = biome.getRandomTreeFeature(random);
            var18.prepare(1.0, 1.0, 1.0);
            var18.generate(world, random, featureX, world.getTopY(featureX, featureZ), featureZ);
        }
    }
}
