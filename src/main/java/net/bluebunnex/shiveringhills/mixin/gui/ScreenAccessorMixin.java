package net.bluebunnex.shiveringhills.mixin.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Screen.class)
public abstract interface ScreenAccessorMixin {

    @Accessor("buttons")
    abstract List getButtons();

    @Accessor("textRenderer")
    abstract TextRenderer getTextRenderer();
}