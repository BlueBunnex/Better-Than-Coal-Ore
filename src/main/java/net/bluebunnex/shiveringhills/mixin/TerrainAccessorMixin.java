package net.bluebunnex.shiveringhills.mixin;

import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public interface TerrainAccessorMixin {

    @Accessor("random")
    Random getRandom();
}