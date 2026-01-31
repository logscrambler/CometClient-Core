package net.comet.core;

import net.comet.core.module.IModule;
import java.util.function.Consumer;

/**
 * Comet Client 코어 모드의 엔트리포인트입니다.
 * 'comet_module' 엔트리포인트로 등록되어, 다른 모드들이 Comet Client에 모듈을 등록하는 방식과 동일하게 작동합니다.
 *
 * 현재는 코어 자체에 내장된 모듈이 없으므로 비어있지만,
 * 나중에 코어에 기본적으로 포함될 모듈이 생긴다면 여기에 등록할 수 있습니다.
 *
 * @author ReVersing
 */
public class CometCoreModule implements CometModule {
    /**
     * 이 메서드는 Fabric 로더에 의해 호출되며,
     * Comet Client의 ModuleManager에 모듈들을 등록하는 역할을 합니다.
     *
     * @param registrar 모듈을 ModuleManager에 등록하기 위한 Consumer 함수입니다.
     */
    @Override
    public void registerModules(Consumer<IModule> registrar) {
        // 현재 Comet Core 자체에는 내장된 모듈이 없습니다.
        // 만약 FPS 표시기나 좌표 표시기 같은 기본 모듈을 코어에 포함시키고 싶다면,
        // 여기에 해당 모듈의 인스턴스를 생성하여 registrar.accept(new YourModule()); 와 같이 등록할 수 있습니다.
        // 예시: registrar.accept(new FpsDisplayModule());
    }
}
