package net.bluebunnex.shiveringhills.mixin.item;

import net.bluebunnex.shiveringhills.src.IPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FoodItem.class)
public class FoodItemMixin {

    /**
     * @author BlueBunnex
     * @reason Shivering Hills has food replenish hunger, not health
     */
    @Overwrite
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {

        if (((IPlayer) user).feed(((FoodItem) (Object) this).getHealthRestored())) {
            stack.count--;
        }

        return stack;
    }
}
