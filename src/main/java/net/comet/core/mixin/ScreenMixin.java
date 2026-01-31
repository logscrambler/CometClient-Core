package net.comet.core.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 마인크래프트의 모든 화면(Screen)에 Comet 테마를 적용하기 위한 Mixin입니다.
 *
 * @author ReVersing
 */
@Mixin(Screen.class)
public abstract class ScreenMixin {

    /**
     * Screen 클래스의 renderBackground 메서드가 호출되는 시점의 맨 앞(HEAD)에 코드를 주입합니다.
     * 이 메서드는 마인크래프트의 기본 배경(흙 블록 모양)을 그리는 역할을 합니다.
     *
     * @param ci 이 코드를 주입한 후 원래 메서드의 실행을 취소(cancel)하기 위해 필요합니다.
     */
    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void onRenderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // 1. 원래의 배경 렌더링 로직을 취소합니다.
        // 이렇게 하면 흙 블록 배경이 그려지지 않습니다.
        ci.cancel();

        // 2. 우리가 원하는 커스텀 배경을 그립니다.
        // CometScreen의 배경과 동일한 어둡고 반투명한 색상으로 화면 전체를 덮습니다.
        context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), 0xAA050505);
    }
}
