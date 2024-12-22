package net.bluebunnex.shiveringhills.mixin.terrain;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public interface OverworldChunkGeneratorAccessorMixin {

    @Accessor("random")
    Random getRandom();

    @Accessor("heightMap")
    double[] getHeightMap();

    @Accessor("heightMap")
    void setHeightMap(double[] value);

    @Accessor("world")
    World getWorld();

    @Accessor("perlinNoise1")
    OctavePerlinNoiseSampler getNoise1();

    @Accessor("perlinNoise1")
    void setNoise1(OctavePerlinNoiseSampler value);

//    @Accessor("perlinNoise2")
//    OctavePerlinNoiseSampler getNoise2();
//
//    @Accessor("perlinNoise3")
//    OctavePerlinNoiseSampler getNoise3();
}