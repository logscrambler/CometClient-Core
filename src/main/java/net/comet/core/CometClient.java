package net.comet.core;

import net.comet.core.config.ConfigManager;
import net.comet.core.module.ModuleManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comet Client의 메인 진입점입니다.
 * Fabric 로더가 이 클래스를 찾아 모드를 초기화합니다.
 *
 * @author ReVersing
 */
public class CometClient implements ModInitializer {

    // 모드의 고유 ID입니다. 다른 모드와 충돌하지 않도록 유니크하게 설정합니다.
    public static final String MOD_ID = "comet-core";
    // 로그를 기록하기 위한 로거 인스턴스입니다. 개발 중 디버깅이나 정보 출력을 위해 사용됩니다.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * 모드가 초기화될 때 호출되는 메서드입니다.
     * 여기서 Comet Client의 핵심 기능들을 설정하고 모듈을 로드합니다.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Comet Client Core가 깨어났습니다! (Initialized)");

        // 1. 모듈 로드
        // Fabric 로더를 통해 'comet_module' 엔트리포인트를 구현한 모든 모듈을 찾습니다.
        FabricLoader.getInstance().getEntrypoints("comet_module", CometModule.class).forEach(entrypoint -> {
            entrypoint.registerModules(ModuleManager.getInstance()::register);
        });

        LOGGER.info("총 {}개의 모듈이 Comet Client에 등록되었습니다.", ModuleManager.getInstance().getModules().size());
        
        // 2. 설정 로드
        // 등록된 모듈들의 저장된 설정을 불러옵니다.
        ConfigManager.load();
        
        // 3. 종료 시 저장 훅 등록
        // 게임이 종료될 때 설정을 자동으로 저장합니다.
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::save));
    }
}
