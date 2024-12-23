package net.bluebunnex.shiveringhills.mixin.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.awt.*;

@Mixin(Biome.class)
public abstract class BiomeMixin {

    /**
     * @author BlueBunnex
     * @reason Shivering Hills only has snowy biomes. also temperature is always 0
     */
    @Overwrite
    public static Biome locateBiome(float temperature, float downfall) {

        if (downfall > 0.5) {
            return Biome.TAIGA;
        }

        return Biome.TUNDRA;
    }

    /**
     * @author BlueBunnex
     * @reason sky dark :D aesthetic
     */
    @Overwrite
    @Environment(EnvType.CLIENT)
    public int getSkyColor(float f) {

        return Color.getHSBColor(0, 0, 0).getRGB();
    }
}
