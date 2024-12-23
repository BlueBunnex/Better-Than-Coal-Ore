package net.bluebunnex.shiveringhills.mixin.player;

import net.bluebunnex.shiveringhills.src.IPlayer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayer {

    private static int MAX_HUNGER = 8000;
    private static int MAX_WARMTH = 1000;

    private static int DYING_TICKS = 100;
    private static int HEALING_TICKS = 60;

    // might add constants for warmth cutoffs

    @Unique
    int healthChangeTimer;

    @Unique
    int hunger = MAX_HUNGER;

    @Unique
    int warmth = MAX_WARMTH;

    @Override
    public String getStatusText() {

        return "H=" + hunger + "; C=" + warmth;
//        return "Starving + Freezing";
    }

    @Override
    public boolean feed(int vanillaHealthRestored) {

        hunger += vanillaHealthRestored * 400;

        return true;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickMixin(CallbackInfo ci) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        // hunger change
        if (hunger > 0)
            hunger--;

        // warmth change
        int targetWarmth = 0;

        boolean hasSkyLight = player.world.hasSkyLight(MathHelper.floor(player.x), MathHelper.floor(player.y), MathHelper.floor(player.z));
        boolean isLit = 4 < player.world.getLightLevel(MathHelper.floor(player.x), MathHelper.floor(player.y), MathHelper.floor(player.z));

        boolean isStronglyWarmed = hasNearbyHeatBlock(player.world, MathHelper.floor(player.x), MathHelper.floor(player.y) - 1, MathHelper.floor(player.z));
        boolean isWeaklyWarmed = hasSkyLight || isLit;

        System.out.println(isStronglyWarmed);

        if (isStronglyWarmed) {

            targetWarmth = 1000;

        } else if (isWeaklyWarmed) {

            targetWarmth = 300;
        }

        if (warmth > targetWarmth) {
            warmth--;
        } else {
            warmth++;
        }

        // being in water (foot block, not head block) makes you immediately frigid
        int stoodBlockId = player.world.getBlockId(MathHelper.floor(player.x), MathHelper.floor(player.y) - 1, MathHelper.floor(player.z));

        if (stoodBlockId == Block.WATER.id || stoodBlockId == Block.FLOWING_WATER.id) {
            warmth = 0;
        }

        // damage when statuses are low
        boolean hungerDamaging = hunger <= 0;
        boolean warmthDamaging = warmth <= 100;

        if (hungerDamaging || warmthDamaging) {

            healthChangeTimer--;

            if (healthChangeTimer <= 0) {

                player.damage(null, 1);

                healthChangeTimer = hungerDamaging && warmthDamaging ? DYING_TICKS / 2 : DYING_TICKS;
            }

        // heal when warmth is high (and not dying)
        } else if (warmth > 900) {

            healthChangeTimer--;

            if (healthChangeTimer <= 0) {

                player.heal(1);

                healthChangeTimer = HEALING_TICKS;
            }
        }
    }

    // check 5x3x5 for blocks like firepits or lava or whatever
    @Unique
    private boolean hasNearbyHeatBlock(World world, int x, int y, int z) {

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dy = -1; dy <= 1; dy++) {

                    int blockID = world.getBlockId(x + dx, y + dy, z + dz);

                    if (blockID == Block.LAVA.id)
                        return true;
                }
            }
        }

        return false;
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtCompound nbt, CallbackInfo ci) {

        this.healthChangeTimer = nbt.getInt("ShiveringHills_HpTimer");
        this.hunger = nbt.getInt("ShiveringHills_Hunger");
        this.warmth = nbt.getInt("ShiveringHills_Warmth");

        if (!nbt.contains("ShiveringHills_Hunger")) {
            this.hunger = MAX_HUNGER;
        }

        if (!nbt.contains("ShiveringHills_Warmth")) {
            this.warmth = MAX_WARMTH;
        }
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtCompound nbt, CallbackInfo ci) {

        nbt.putInt("ShiveringHills_HpTimer", this.healthChangeTimer);
        nbt.putInt("ShiveringHills_Hunger", this.hunger);
        nbt.putInt("ShiveringHills_Warmth", this.warmth);
    }

//    @Inject(method = "onKilledBy", at = @At("TAIL"))
//    public void onKilledByMixin(Entity adversary, CallbackInfo ci) {
//
//    }
}
