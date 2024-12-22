package net.bluebunnex.shiveringhills.mixin.terrain;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.awt.*;

@Mixin(Biome.class)
public class BiomeMixin {

    /**
     * @author BlueBunnex
     * @reason Shivering Hills only has snowy biomes. also temperature is always 0
     */
    @Overwrite
    public static Biome locateBiome(float temperature, float downfall) {

        if (downfall > 0.5) {
            return Biome.TAIGA;
        } else {
            return Biome.TUNDRA;
        }
    }

    /**
     * @author BlueBunnex
     * @reason sky dark :D aesthetic
     */
    @Overwrite
    @Environment(EnvType.CLIENT)
    public int getSkyColor(float f) {

        return Color.getHSBColor(0, 0, 0).getRGB();

//        f /= 3.0F;
//        if (f < -1.0F) {
//            f = -1.0F;
//        }
//
//        if (f > 1.0F) {
//            f = 1.0F;
//        }
//
//        return Color.getHSBColor(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
    }
}
