package net.bluebunnex.shiveringhills.mixin.player;

import net.bluebunnex.shiveringhills.src.IPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayer {

    // TODO add these values to NBT
    @Unique
    int statusDamageTimer;

    @Unique
    int hunger = 1000;

    @Unique
    int warmth = 1000;

    @Override
    public String getStatusText() {

        return "H=" + hunger + "; C=" + warmth;
//        return "Starving + Freezing";
    }

    @Override
    public boolean feed(int vanillaHealthRestored) {

        hunger += vanillaHealthRestored * 500;

        return true;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickMixin(CallbackInfo ci) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        boolean hasSkyLight = player.world.hasSkyLight(MathHelper.floor(player.x), MathHelper.floor(player.y), MathHelper.floor(player.z));

        if (hunger > 0)
            hunger--;

        if (hasSkyLight) {

            if (warmth > 300)
                warmth--;

        } else {

            if (warmth > 0)
                warmth--;
        }

        // heal when warmth is high
        //player.heal(1);

        // damage when statuses are low
        boolean hungerDamaging = hunger <= 0;
        boolean warmthDamaging = warmth <= 200;

        if (hungerDamaging || warmthDamaging) {

            statusDamageTimer--;

            if (statusDamageTimer <= 0) {

                player.damage(null, 1);

                statusDamageTimer = hungerDamaging && warmthDamaging ? 50 : 100;
            }
        }
    }

//    @Inject(method = "onKilledBy", at = @At("TAIL"))
//    public void onKilledByMixin(Entity adversary, CallbackInfo ci) {
//
//    }
}
