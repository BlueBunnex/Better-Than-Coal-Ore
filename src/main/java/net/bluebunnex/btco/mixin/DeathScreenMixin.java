package net.bluebunnex.btco.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
@Environment(EnvType.CLIENT)
public class DeathScreenMixin {

    /**
     * @author BlueBunnex
     * @reason remove respawn button
     */
    @Overwrite
    public void init() {

        List buttons = ((ScreenAccessorMixin) this).getButtons();

        int width  = ((DeathScreen) (Object) this).width;
        int height = ((DeathScreen) (Object) this).height;

        buttons.clear();
        buttons.add(new ButtonWidget(2, width / 2 - 100, height / 4 + 96, "Title menu"));
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(int mouseX, int mouseY, float delta, CallbackInfo ci) {

        int width  = ((DeathScreen) (Object) this).width;
        int height = ((DeathScreen) (Object) this).height;

        TextRenderer textRenderer = ((ScreenAccessorMixin) this).getTextRenderer();

        String text = "Can't respawn, this mod's hardcore!";

        textRenderer.drawWithShadow(text, width / 2 - textRenderer.getWidth(text) / 2, height / 4 + 80, -1);
    }
}
