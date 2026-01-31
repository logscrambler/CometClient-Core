package net.comet.core.module;

import net.minecraft.client.gui.DrawContext;

/**
 * 활성화된 모든 HUD 모듈들을 화면에 렌더링하는 역할을 담당하는 클래스입니다.
 * 싱글톤 패턴으로 구현되어, 게임 내에서 단 하나의 인스턴스만 존재합니다.
 *
 * @author ReVersing
 */
public class HudManager {
    // HudManager의 유일한 인스턴스입니다.
    private static final HudManager INSTANCE = new HudManager();

    /**
     * 외부에서 직접 인스턴스화하는 것을 막기 위한 private 생성자입니다.
     */
    private HudManager() {}

    /**
     * HudManager의 유일한 인스턴스를 반환합니다.
     *
     * @return HudManager의 싱글톤 인스턴스
     */
    public static HudManager getInstance() {
        return INSTANCE;
    }

    /**
     * 활성화된 모든 HUD 모듈의 render 메서드를 호출하여 화면에 그립니다.
     * 이 메서드는 InGameHudMixin에 의해 매 프레임 호출됩니다.
     *
     * @param context   렌더링을 위한 DrawContext 객체
     * @param tickDelta 게임 틱 간의 시간 차이 (애니메이션 등에 사용)
     */
    public void render(DrawContext context, float tickDelta) {
        // ModuleManager로부터 모든 HUD 모듈 목록을 가져옵니다.
        for (IHudModule module : ModuleManager.getInstance().getHudModules()) {
            // 모듈이 활성화된 경우에만 렌더링을 수행합니다.
            if (module.isEnabled()) {
                module.render(context, tickDelta);
            }
        }
    }
}
