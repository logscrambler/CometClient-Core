package net.comet.core.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClickableWidget.class)
public abstract class ClickableWidgetMixin {

    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void onRenderWidget(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ClickableWidget self = (ClickableWidget)(Object)this;

        // IMPORTANT: Only apply this custom rendering to ButtonWidget instances
        if (!(self instanceof ButtonWidget)) {
            return; // Do not interfere with other widgets like sliders
        }

        // --- From this point, we are sure it's a ButtonWidget ---
        ci.cancel(); // Cancel the original rendering

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        
        boolean hovered = self.isHovered();
        
        int backgroundColor = hovered ? 0xCC222233 : 0xCC111111;
        int borderColor = hovered ? 0xFF666688 : 0xFF444444;
        int textColor = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;

        context.fill(self.getX(), self.getY(), self.getX() + self.getWidth(), self.getY() + self.getHeight(), backgroundColor);
        drawBorder(context, self.getX(), self.getY(), self.getWidth(), self.getHeight(), borderColor);

        int availableWidth = self.getWidth() - 8;
        Text message = self.getMessage();
        
        OrderedText orderedText;
        if (textRenderer.getWidth(message) > availableWidth && availableWidth > 0) {
            StringVisitable trimmed = textRenderer.trimToWidth(message, availableWidth);
            orderedText = Language.getInstance().reorder(trimmed);
        } else {
            orderedText = message.asOrderedText();
        }

        int textX = self.getX() + (self.getWidth() - textRenderer.getWidth(orderedText)) / 2;
        int textY = self.getY() + (self.getHeight() - 8) / 2;
        context.drawTextWithShadow(textRenderer, orderedText, textX, textY, textColor);
    }

    private void drawBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int borderSize = 1;
        context.fill(x, y, x + width, y + borderSize, color);
        context.fill(x, y + height - borderSize, x + width, y + height, color);
        context.fill(x, y, x + borderSize, y + height, color);
        context.fill(x + width - borderSize, y, x + width, y + height, color);
    }
}
