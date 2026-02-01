package net.comet.core.module.setting;

import java.util.function.Supplier;

/**
 * 모듈의 설정값을 나타내는 기본 추상 클래스입니다.
 * 모든 구체적인 설정(Boolean, Number 등)은 이 클래스를 상속받아야 합니다.
 *
 * @author ReVersing
 */
public abstract class Setting<T> {
    private final String name;
    private final Supplier<Boolean> visibility;

    public Setting(String name) {
        this(name, () -> true);
    }

    public Setting(String name, Supplier<Boolean> visibility) {
        this.name = name;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visibility.get();
    }

    /**
     * 현재 설정값을 반환합니다.
     */
    public abstract T getValue();

    /**
     * 설정값을 변경합니다.
     */
    public abstract void setValue(T value);
}
