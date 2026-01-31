package net.comet.core.module;

import net.comet.core.CometClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Comet Client의 모든 모듈을 관리하는 중앙 관리자 클래스입니다.
 * 싱글톤 패턴으로 구현되어, 게임 내에서 단 하나의 인스턴스만 존재합니다.
 *
 * @author ReVersing
 */
public class ModuleManager {
    // ModuleManager의 유일한 인스턴스입니다.
    private static final ModuleManager INSTANCE = new ModuleManager();
    // 등록된 모든 모듈들을 저장하는 리스트입니다.
    private final List<IModule> modules = new ArrayList<>();

    /**
     * 외부에서 직접 인스턴스화하는 것을 막기 위한 private 생성자입니다.
     */
    private ModuleManager() {}

    /**
     * ModuleManager의 유일한 인스턴스를 반환합니다.
     *
     * @return ModuleManager의 싱글톤 인스턴스
     */
    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    /**
     * 새로운 모듈을 ModuleManager에 등록합니다.
     * 이 메서드는 주로 CometModule 엔트리포인트에서 호출됩니다.
     *
     * @param module 등록할 {@link IModule} 인스턴스
     */
    public void register(IModule module) {
        modules.add(module);
        CometClient.LOGGER.info("모듈 등록됨: {}", module.getName().getString());
    }

    /**
     * 등록된 모든 모듈들의 변경 불가능한 리스트를 반환합니다.
     *
     * @return 등록된 모든 {@link IModule}의 리스트
     */
    public List<IModule> getModules() {
        return Collections.unmodifiableList(modules);
    }

    /**
     * 등록된 모듈 중 {@link IHudModule} 인터페이스를 구현하는 모든 모듈의 리스트를 반환합니다.
     *
     * @return 등록된 모든 {@link IHudModule}의 리스트
     */
    public List<IHudModule> getHudModules() {
        List<IHudModule> hudModules = new ArrayList<>();
        for (IModule module : modules) {
            if (module instanceof IHudModule) {
                hudModules.add((IHudModule) module);
            }
        }
        return Collections.unmodifiableList(hudModules);
    }
}
