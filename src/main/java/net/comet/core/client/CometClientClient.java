package net.comet.core.client;

import net.comet.core.client.gui.CometScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Comet Client의 클라이언트 사이드 초기화 로직을 담당합니다.
 * 게임 클라이언트가 시작될 때 호출됩니다.
 *
 * @author ReVersing
 */
public class CometClientClient implements ClientModInitializer {

    // Comet Client 메뉴를 열기 위한 키 바인딩입니다.
    private static KeyBinding openCometMenuKey;
    // 키 바인딩 카테고리를 정의합니다. 마인크래프트 설정 화면에 표시됩니다.
    private static final KeyBinding.Category COMET_CATEGORY = KeyBinding.Category.create(Identifier.of("comet", "client"));

    /**
     * 클라이언트 모드가 초기화될 때 호출되는 메서드입니다.
     * 여기서 키 바인딩을 등록하고, 게임 메뉴에 버튼을 추가하는 등의 클라이언트 전용 설정을 합니다.
     */
    @Override
    public void onInitializeClient() {
        // 1. Comet Client 메뉴 열기 키 바인딩 등록
        // 키: 오른쪽 Shift, 카테고리: "Comet Client"
        openCometMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.comet.open_menu", // 키 바인딩의 고유 ID (번역 파일에서 사용될 수 있습니다)
                InputUtil.Type.KEYSYM, // 키보드 키를 사용합니다.
                GLFW.GLFW_KEY_RIGHT_SHIFT, // 오른쪽 Shift 키를 기본값으로 설정합니다.
                COMET_CATEGORY // 위에서 정의한 Comet Client 카테고리에 속합니다.
        ));

        // 2. 키 바인딩 이벤트 리스너 등록
        // 게임 틱(tick)의 끝마다 이벤트를 확인하여 키가 눌렸는지 감지합니다.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 키가 눌렸다면 CometScreen을 엽니다.
            // client.currentScreen은 현재 열려있는 화면을 의미하며, CometScreen에서 '뒤로가기' 시 이 화면으로 돌아옵니다.
            while (openCometMenuKey.wasPressed()) {
                client.setScreen(new CometScreen(client.currentScreen));
            }
        });

        // 3. 게임 메뉴 화면에 Comet 버튼 추가 (Fabric Screen Events API 사용)
        // Mixin 대신 Fabric API를 사용하여 안전하게 버튼을 추가합니다.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            // 현재 화면이 GameMenuScreen (ESC 눌렀을 때 나오는 게임 메뉴)인지 확인합니다.
            if (screen instanceof GameMenuScreen) {
                // GameMenuScreen의 버튼 목록에 새로운 버튼을 추가합니다.
                Screens.getButtons(screen).add(ButtonWidget.builder(Text.of("Comet"), (button) -> {
                    // 버튼 클릭 시 CometScreen을 엽니다.
                    client.setScreen(new CometScreen(screen));
                }).dimensions(10, 10, 90, 20).build()); // 버튼의 위치와 크기를 설정합니다.
            }
        });
    }
}
