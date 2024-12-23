package net.bluebunnex.shiveringhills.mixin.gui;

import net.bluebunnex.shiveringhills.src.IPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void addPlayerStatuses(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {

        ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
        int x = scaler.getScaledWidth() / 2 - 91;
        int y = scaler.getScaledHeight() - 42;

        if (this.minecraft.player.isInFluid(Material.WATER)) {
            y -= 9;
        }

        TextRenderer textRenderer = this.minecraft.textRenderer;

        textRenderer.drawWithShadow(((IPlayer) this.minecraft.player).getStatusText(), x, y, -1);
    }
}
