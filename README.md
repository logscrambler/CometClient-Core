# **CometClient**

**CometCore**는 마인크래프트 Fabric 모드 개발을 위한 강력하고 유연한 모듈형 클라이언트 프레임워크입니다.
이 코어 모드 자체는 기능을 제공하지 않으며, 다른 모드들이 CometCore를 기반으로 쉽게 기능을 확장할 수 있도록 돕습니다.

## 주요 기능

*   **모듈 시스템 (Module System)**: `IModule` 및 `Module` 클래스를 통해 손쉽게 기능을 추가할 수 있습니다.
*   **설정 관리 (Settings API)**: `Boolean`, `Number`, `Mode` 등 다양한 설정을 지원하며, 자동으로 저장 및 로드됩니다.
*   **자동 렌더링 (Auto Rendering)**: HUD 모듈을 위한 렌더링 파이프라인이 내장되어 있습니다.
*   **확장성 (Extensibility)**: `fabric.mod.json`의 `comet_module` 엔트리포인트를 통해 외부 모드에서 모듈을 주입할 수 있습니다.

## 개발자 가이드 (How to create a module)

### 1. 의존성 추가 (build.gradle)
```groovy
repositories {
    mavenLocal() // 또는 배포된 Maven 저장소
}

dependencies {
    modImplementation "org.mcmod:cometcore:1.0-SNAPSHOT"
}
```

### 2. 모듈 클래스 작성
`net.comet.core.module.Module`을 상속받아 구현합니다.

```java
public class FpsModule extends Module {
    private final BooleanSetting shadow = new BooleanSetting("Shadow", true);
    private final NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.1);

    public FpsModule() {
        super("FPS", "Shows current FPS");
        addSettings(shadow, scale);
    }

    @Override
    public void onRender(DrawContext context) {
        // 렌더링 로직 작성
        context.drawText(mc.textRenderer, "FPS: " + mc.getCurrentFps(), getX(), getY(), -1, shadow.getValue());
    }
}
```

### 3. 엔트리포인트 등록
`net.comet.core.CometModule`을 구현하여 모듈을 등록합니다.

```java
public class MyModEntrypoint implements CometModule {
    @Override
    public void registerModules(Consumer<IModule> registrar) {
        registrar.accept(new FpsModule());
    }
}
```

### 4. fabric.mod.json 설정
```json
"entrypoints": {
  "comet_module": [
    "com.example.mymod.MyModEntrypoint"
  ]
}
```

## 라이선스
MIT License
