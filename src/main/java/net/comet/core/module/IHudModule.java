package net.comet.core.module;

import net.minecraft.client.gui.DrawContext;

/**
 * 화면에 정보를 표시하는 HUD (Head-Up Display) 모듈이 구현해야 하는 인터페이스입니다.
 * {@link IModule}의 기능을 모두 포함하며, 추가적으로 렌더링 및 위치 관련 기능을 정의합니다.
 *
 * @author ReVersing
 */
public interface IHudModule extends IModule {
    /**
     * HUD 모듈을 화면에 렌더링합니다.
     * 이 메서드 안에서 DrawContext를 사용하여 원하는 정보를 그립니다.
     *
     * @param context   렌더링을 위한 DrawContext 객체
     * @param tickDelta 게임 틱 간의 시간 차이 (애니메이션 등에 사용)
     */
    void render(DrawContext context, float tickDelta);

    /**
     * HUD 모듈이 차지하는 너비를 반환합니다.
     * 모듈 위치 조정 시 충돌 방지나 레이아웃 계산에 사용될 수 있습니다.
     *
     * @return 모듈의 너비 (픽셀)
     */
    int getWidth();

    /**
     * HUD 모듈이 차지하는 높이를 반환합니다.
     *
     * @return 모듈의 높이 (픽셀)
     */
    int getHeight();

    /**
     * HUD 모듈의 현재 X 좌표를 반환합니다.
     *
     * @return X 좌표 (픽셀)
     */
    int getX();

    /**
     * HUD 모듈의 현재 Y 좌표를 반환합니다.
     *
     * @return Y 좌표 (픽셀)
     */
    int getY();

    /**
     * HUD 모듈의 X 좌표를 설정합니다.
     *
     * @param x 새로운 X 좌표
     */
    void setX(int x);

    /**
     * HUD 모듈의 Y 좌표를 설정합니다.
     *
     * @param y 새로운 Y 좌표
     */
    void setY(int y);
}
