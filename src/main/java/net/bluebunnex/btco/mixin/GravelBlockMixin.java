package net.bluebunnex.btco.mixin;

import net.bluebunnex.btco.BetterThanCoalOre;
import net.minecraft.block.GravelBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(GravelBlock.class)
public class GravelBlockMixin {

    /**
     * @author BlueBunnex
     * @reason gotta drop gravel piles (and flint), not block
     */
    @Overwrite
    public int getDroppedItemId(int blockMeta, Random random) {

        return random.nextInt(16) == 0 ? Item.FLINT.id : BetterThanCoalOre.GRAVEL_PILE.id;
    }
}
