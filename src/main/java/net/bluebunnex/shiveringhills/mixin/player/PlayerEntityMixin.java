package net.bluebunnex.shiveringhills.mixin.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // gotta look into FoodItem as well
//    @Unique
//    int hunger;
//
//    @Unique
//    int coldness;

//    @Shadow public abstract void sendMessage(String message);
//
//    @Inject(method = "tick", at = @At("TAIL"))
//    public void tickMixin(CallbackInfo ci) {
//
//    }
//
//    @Inject(method = "onKilledBy", at = @At("TAIL"))
//    public void onKilledByMixin(Entity adversary, CallbackInfo ci) {
//
//        ((PlayerEntityMixin) this).sendMessage("You died! So sad...");
//    }
}
