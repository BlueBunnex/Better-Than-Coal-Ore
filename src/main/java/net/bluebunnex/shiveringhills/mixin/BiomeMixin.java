package net.bluebunnex.shiveringhills.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Biome.class)
public class BiomeMixin {

//    public static final Biome RAINFOREST = new TaigaBiome().setGrassColor (588342).setName("Rainforest").setFoliageColor(2094168);

    /**
     * @author BlueBunnex
     * @reason Shivering Hills only has snowy biomes
     */
    @Overwrite
    public static Biome locateBiome(float temperature, float downfall) {

        if (temperature > 0.5) {
            return Biome.TAIGA;
        } else {
            return Biome.TUNDRA;
        }
    }
}
