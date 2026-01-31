package net.comet.core.mixin;

import net.comet.core.module.HudManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 마인크래프트의 인게임 HUD에 Comet Client의 HUD 모듈들을 렌더링하기 위한 Mixin입니다.
 * 이 Mixin은 안정성을 위해 현재 비활성화되어 있습니다.
 *
 * @author ReVersing
 */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    /**
     * InGameHud의 render 메서드가 끝나는 시점(RETURN)에 코드를 주입합니다.
     * 이렇게 하면 마인크래프트의 기본 HUD가 모두 그려진 후에 우리 모듈이 그려져, 덮어쓰기 문제를 방지할 수 있습니다.
     */
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        // HudManager를 통해 활성화된 모든 HUD 모듈을 렌더링합니다.
        // 현재는 모든 Mixin이 비활성화되어 있으므로, 이 코드는 실행되지 않습니다.
        // HudManager.getInstance().render(context, tickDelta);
    }
}
