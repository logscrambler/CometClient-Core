package net.comet.core.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 마인크래프트의 일반 텍스트 버튼(ButtonWidget.Text)에만 Comet 테마를 적용하기 위한 Mixin입니다.
 * 이 방법은 아이콘을 사용하는 특수 버튼(조합법 책 등)의 렌더링을 방해하지 않아 안전합니다.
 *
 * @author ReVersing
 */
@Mixin(ButtonWidget.Text.class)
public abstract class ButtonTextMixin extends ButtonWidget {

    // Mixin 대상 클래스의 생성자와 일치하는 생성자가 필요합니다.
    public ButtonTextMixin(int x, int y, int width, int height, net.minecraft.text.Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    /**
     * ButtonWidget.Text의 drawIcon 메서드에 코드를 주입합니다.
     * 이 메서드는 텍스트 버튼의 실제 렌더링 로직을 포함하고 있습니다.
     */
    @Inject(method = "drawIcon", at = @At("HEAD"), cancellable = true)
    private void onDrawIcon(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // 1. 원래의 버튼 렌더링 로직을 취소합니다.
        ci.cancel();

        // 2. 우리가 원하는 커스텀 버튼을 그립니다.
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        
        boolean hovered = this.isHovered(); // 마우스가 버튼 위에 있는지 확인
        
        // 마우스 호버 상태에 따라 다른 색상을 적용합니다.
        int backgroundColor = hovered ? 0xCC222233 : 0xCC111111; // 배경색
        int borderColor = hovered ? 0xFF666688 : 0xFF444444;     // 테두리색
        int textColor = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;     // 글자색

        // 버튼의 배경과 테두리를 그립니다.
        context.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), backgroundColor);
        drawBorder(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), borderColor);

        // 버튼의 텍스트를 중앙에 그립니다.
        int availableWidth = this.getWidth() - 8; // 텍스트가 표시될 수 있는 너비 (좌우 여백 고려)
        net.minecraft.text.Text message = this.getMessage();
        
        OrderedText orderedText;
        // 텍스트가 버튼 너비보다 길 경우, "..."을 붙여 자동으로 줄입니다.
        if (textRenderer.getWidth(message) > availableWidth && availableWidth > 0) {
            StringVisitable trimmed = textRenderer.trimToWidth(message, availableWidth);
            orderedText = Language.getInstance().reorder(trimmed);
        } else {
            orderedText = message.asOrderedText();
        }

        // 계산된 위치에 텍스트를 그립니다.
        int textX = this.getX() + (this.getWidth() - textRenderer.getWidth(orderedText)) / 2;
        int textY = this.getY() + (this.getHeight() - 8) / 2;
        context.drawTextWithShadow(textRenderer, orderedText, textX, textY, textColor);
    }

    /**
     * 테두리를 그리는 헬퍼 메서드입니다.
     */
    private void drawBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int borderSize = 1;
        context.fill(x, y, x + width, y + borderSize, color);
        context.fill(x, y + height - borderSize, x + width, y + height, color);
        context.fill(x, y, x + borderSize, y + height, color);
        context.fill(x + width - borderSize, y, x + width, y + height, color);
    }
}
