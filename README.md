# ☄️ Comet Client

**Created by ReVersing**

---

## 1. 프로젝트 개요 (Overview)

**Comet Client**는 Minecraft Java Edition (Fabric 기반)을 위한 경량 클라이언트 사이드 모드 프레임워크입니다. Lunar Client나 Badlion Client와 같이 세련된 UI와 다양한 기능을 제공하는 것을 목표로 하지만, 핵심적인 차이점은 **모든 기능을 외부 모듈(Module)로 분리**하여 극대화된 확장성과 안정성을 추구한다는 점입니다.

Comet Core 자체는 강력한 UI 프레임워크와 모듈 로딩 시스템만을 제공하며, 실제 기능(FPS, CPS, Keystrokes 등)은 모두 독립적인 모듈로 개발되어 추가됩니다.

- **타겟 플랫폼**: Minecraft Java Edition
- **모드 로더**: Fabric
- **개발 버전**: 1.21.x

---

## 2. 주요 특징 (Features)

- **모듈 기반 아키텍처**: 모든 기능이 독립적인 모듈로 분리되어 있어, 필요한 기능만 골라 사용하거나 직접 개발하여 추가할 수 있습니다.
- **세련된 UI**: 기본 마인크래프트 UI를 완전히 대체하는 어둡고 미니멀한 Comet 테마를 적용했습니다.
- **안전한 UI 커스터마이징**: Mixin을 최소화하고 Fabric API와 커스텀 렌더링을 활용하여 다른 모드와의 충돌 가능성을 최소화했습니다.
- **자동 모듈 탐색**: `mods` 폴더에 Comet 모듈을 추가하기만 하면, 클라이언트가 자동으로 인식하고 설정 화면에 추가합니다.
- **인게임 설정**: 오른쪽 Shift 키로 언제든지 설정 화면을 열어 모듈을 켜고 끌 수 있습니다.

---

## 3. 설치 및 사용법 (Installation & Usage)

### 사용자 (For Users)

1. [Fabric Loader](https://fabricmc.net/use/installer/)를 마인크래프트에 설치합니다.
2. [Fabric API](https://modrinth.com/mod/fabric-api)를 다운로드하여 `mods` 폴더에 넣습니다.
3. `comet-core-x.x.x.jar` 파일을 다운로드하여 `mods` 폴더에 넣습니다.
4. 원하는 Comet 모듈(예: `comet-fps-x.x.x.jar`)을 `mods` 폴더에 넣습니다.
5. 게임을 실행하고, 인게임에서 **오른쪽 Shift** 키를 눌러 Comet 설정 화면을 엽니다.

### 개발자 (For Developers)

1. 이 저장소를 클론(Clone)합니다.
   ```bash
   git clone https://github.com/your-repo/CometClient.git
   ```
2. 프로젝트를 IDE(IntelliJ IDEA 또는 Visual Studio Code)로 엽니다.
3. Gradle 프로젝트를 빌드합니다.
4. `runClient` Gradle 태스크를 실행하여 개발 환경에서 클라이언트를 테스트합니다.

---

## 4. 모듈 개발 가이드 (Module Development)

Comet Client를 위한 자신만의 모듈을 만드는 것은 매우 간단합니다.

### Step 1: 모듈 클래스 생성

`IHudModule` (화면에 표시되는 모듈) 또는 `IModule` (백그라운드 기능 모듈) 인터페이스를 구현하는 클래스를 만듭니다.

```java
// src/main/java/com/yourname/yourmod/modules/MyAwesomeModule.java
package com.yourname.yourmod.modules;

import net.comet.core.module.IHudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MyAwesomeModule implements IHudModule {
    // 모듈의 상태와 로직을 여기에 구현합니다.
    private boolean enabled = true;

    @Override
    public Text getName() { return Text.of("My Awesome Module"); }

    @Override
    public Text getDescription() { return Text.of("This is my first Comet module!"); }

    @Override
    public boolean isEnabled() { return this.enabled; }

    @Override
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public void toggle() { this.enabled = !this.enabled; }

    @Override
    public void render(DrawContext context, float tickDelta) {
        if (!this.enabled) return;
        // 화면에 텍스트를 그리는 로직
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Hello, Comet!", getX(), getY(), 0xFFFFFF);
    }
    
    // X, Y, Width, Height 등 나머지 IHudModule 메서드 구현...
}
```

### Step 2: 모듈 등록 클래스 생성

`CometModule` 인터페이스를 구현하여, 위에서 만든 모듈을 Comet Core에 알려줍니다.

```java
// src/main/java/com/yourname/yourmod/YourModEntrypoint.java
package com.yourname.yourmod;

import net.comet.core.CometModule;
import net.comet.core.module.IModule;
import com.yourname.yourmod.modules.MyAwesomeModule;
import java.util.function.Consumer;

public class YourModEntrypoint implements CometModule {
    @Override
    public void registerModules(Consumer<IModule> registrar) {
        // 생성한 모듈의 인스턴스를 registrar에 전달하여 등록합니다.
        registrar.accept(new MyAwesomeModule());
    }
}
```

### Step 3: `fabric.mod.json` 설정

`fabric.mod.json` 파일에 `"comet_module"` 엔트리포인트를 추가하고, Comet Core에 대한 의존성을 명시합니다.

```json
{
  "schemaVersion": 1,
  "id": "your-mod-id",
  "version": "1.0.0",
  "entrypoints": {
    "comet_module": [
      "com.yourname.yourmod.YourModEntrypoint"
    ]
  },
  "depends": {
    "fabricloader": ">=0.15.0",
    "minecraft": "~1.21.0",
    "java": ">=21",
    "comet-core": ">=1.0.0" // Comet Core에 대한 의존성
  }
}
```

---

## 5. 라이센스 (License)

본 프로젝트는 [LICENSE](LICENSE) 파일에 명시된 조건에 따라 제공됩니다. 요약하자면, 개인적인 사용 및 수정은 허용되나 **상업적 이용 및 재배포는 엄격히 금지됩니다.**

**Copyright (c) 2024 ReVersing**
