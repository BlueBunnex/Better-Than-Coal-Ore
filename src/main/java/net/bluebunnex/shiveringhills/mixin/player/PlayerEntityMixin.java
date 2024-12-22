package net.bluebunnex.shiveringhills.mixin.player;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // gotta look into FoodItem as well
//    @Unique
//    int hunger;
//
//    @Unique
//    int coldness;
}
