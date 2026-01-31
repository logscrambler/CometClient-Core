package net.comet.core.module;

import net.minecraft.text.Text;

/**
 * Comet Client의 모든 모듈이 구현해야 하는 기본 인터페이스입니다.
 * 모듈이 가져야 할 가장 기본적인 기능들을 정의합니다.
 *
 * @author ReVersing
 */
public interface IModule {
    /**
     * 모듈의 이름을 반환합니다.
     * 이 이름은 설정 화면의 버튼에 표시됩니다.
     *
     * @return 모듈의 이름 (Text 객체)
     */
    Text getName();

    /**
     * 모듈에 대한 간단한 설명을 반환합니다.
     * 나중에 설정 화면에서 툴팁 등으로 활용될 수 있습니다.
     *
     * @return 모듈의 설명 (Text 객체)
     */
    Text getDescription();

    /**
     * 모듈이 현재 활성화되어 있는지 여부를 반환합니다.
     *
     * @return 활성화 상태이면 true, 아니면 false
     */
    boolean isEnabled();

    /**
     * 모듈의 활성화 상태를 설정합니다.
     *
     * @param enabled 새로운 활성화 상태
     */
    void setEnabled(boolean enabled);

    /**
     * 모듈의 활성화 상태를 반전시킵니다 (ON -> OFF, OFF -> ON).
     * 설정 화면의 버튼 클릭 시 주로 사용됩니다.
     */
    void toggle();
}
