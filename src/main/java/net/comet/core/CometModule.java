package net.comet.core;

import net.comet.core.module.IModule;
import java.util.function.Consumer;

/**
 * 다른 모드들이 Comet Client에 자신의 모듈을 등록하기 위해 구현해야 하는 인터페이스입니다.
 * 이 인터페이스를 구현하고 fabric.mod.json의 'comet_module' 엔트리포인트에 등록하면,
 * Comet Client가 시작될 때 자동으로 이 모드의 모듈들을 인식하고 로드합니다.
 *
 * 마치 앱 개발자가 앱 스토어에 자신의 앱을 등록하는 것과 같은 역할을 합니다.
 *
 * @author ReVersing
 */
public interface CometModule {
    /**
     * Comet Client가 모드를 초기화할 때 이 메서드를 호출하여 모듈 등록을 요청합니다.
     * 이 메서드 안에서 자신이 만든 모듈들을 registrar를 통해 등록해야 합니다.
     *
     * @param registrar 모듈을 ModuleManager에 등록하기 위한 Consumer 함수입니다.
     *                  `registrar.accept(new MyAwesomeModule());` 와 같이 사용합니다.
     */
    void registerModules(Consumer<IModule> registrar);
}
