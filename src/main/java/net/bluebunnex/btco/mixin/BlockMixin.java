package net.bluebunnex.btco.mixin;

import net.bluebunnex.btco.BetterThanCoalOre;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Block.class)
public class BlockMixin {

    @Final
    @Shadow
    public int id;

    @Inject(method = "getDroppedItemCount", at = @At("HEAD"), cancellable = true)
    public void getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {

        if (id == Block.GRASS_BLOCK.id || id == Block.DIRT.id || id == Block.SAND.id || id == Block.GRAVEL.id) {

            cir.setReturnValue(random.nextInt(2, 4)); // [2,4)
        }
    }

    @Inject(method = "getDroppedItemId", at = @At("HEAD"), cancellable = true)
    public void getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {

        if (id == Block.DIRT.id || id == Block.SAND.id) {

            if (random.nextInt(16) == 0) {

                cir.setReturnValue(BetterThanCoalOre.ROCK.id);

            } else {

                cir.setReturnValue(id == Block.DIRT.id ? BetterThanCoalOre.DIRT_PILE.id : BetterThanCoalOre.SAND_PILE.id);
            }
        }
    }
}
