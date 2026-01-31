package net.comet.core.mixin;

import net.comet.core.client.gui.CometScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {

    @Invoker("addDrawableChild")
    protected abstract <T extends Element & Drawable & Selectable> T invokeAddDrawableChild(T drawableElement);

    @Inject(at = @At("RETURN"), method = "init")
    private void onInit(CallbackInfo ci) {
        GameMenuScreen self = (GameMenuScreen)(Object)this;
        
        this.invokeAddDrawableChild(ButtonWidget.builder(Text.of("Comet"), (button) -> {
            MinecraftClient.getInstance().setScreen(new CometScreen(self));
        }).dimensions(10, 10, 90, 20).build());
    }
}
