package net.comet.core.client.gui;

import net.comet.core.module.IModule;
import net.comet.core.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Comet Client의 메인 설정 화면입니다.
 * 모든 모듈 목록을 표시하고, 각 모듈을 활성화/비활성화할 수 있는 UI를 제공합니다.
 *
 * @author ReVersing
 */
public class CometScreen extends Screen {

    // 패널의 고정된 너비와 높이입니다.
    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 250;

    // UI 요소들의 색상 코드입니다. (ARGB 형식)
    private static final int BACKGROUND_COLOR = 0xAA050505; // 전체 화면 배경 (반투명 어두운 회색)
    private static final int PANEL_COLOR = 0xEE0A0A10;    // 중앙 패널 배경 (짙은 남색 계열)
    private static final int BORDER_COLOR = 0xFF333344;    // 패널 테두리 색상 (은은한 청회색)
    private static final int TITLE_COLOR = 0xFFCCCCFF;    // 화면 제목 색상 (연한 보라색)

    // 이 화면이 닫혔을 때 돌아갈 이전 화면입니다.
    private final Screen parent;

    /**
     * Comet Client 설정 화면의 생성자입니다.
     *
     * @param parent 이 화면이 닫혔을 때 돌아갈 이전 화면 (예: 게임 메뉴 화면)
     */
    public CometScreen(Screen parent) {
        // Screen 클래스의 생성자를 호출하여 화면의 제목을 설정합니다.
        super(Text.of("Comet Client Settings"));
        this.parent = parent;
    }

    /**
     * 화면이 초기화될 때 호출됩니다.
     * 여기에 버튼이나 다른 UI 요소들을 추가합니다.
     */
    @Override
    protected void init() {
        // 중앙 패널의 시작 X, Y 좌표를 계산합니다.
        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        // 1. "Back" (뒤로가기) 버튼 추가
        this.addDrawableChild(ButtonWidget.builder(Text.of("Back"), (button) -> this.close())
                .dimensions(panelX + 10, panelY + PANEL_HEIGHT - 30, 80, 20) // 패널 좌측 하단에 위치
                .build());

        // 2. 등록된 모든 모듈들을 버튼으로 표시
        int buttonY = panelY + 40; // 첫 번째 모듈 버튼의 Y 좌표 시작점
        for (IModule module : ModuleManager.getInstance().getModules()) {
            // 각 모듈의 이름으로 버튼을 생성합니다.
            this.addDrawableChild(ButtonWidget.builder(module.getName(), (button) -> {
                // 버튼 클릭 시 모듈의 활성화/비활성화 상태를 토글합니다.
                module.toggle();
                // 버튼의 텍스트를 모듈의 현재 상태에 맞게 업데이트할 수 있습니다. (예: "FPS: ON" / "FPS: OFF")
                // 현재는 모듈 이름만 표시되므로, 토글 후에도 이름은 동일합니다.
                button.setMessage(module.getName());
            }).dimensions(panelX + 10, buttonY, 120, 20).build()); // 패널 좌측에 모듈 버튼들을 나열
            buttonY += 24; // 다음 버튼을 위해 Y 좌표를 24픽셀 아래로 이동 (버튼 높이 20 + 여백 4)
        }
    }

    /**
     * 화면을 렌더링할 때마다 호출됩니다.
     * 배경, 패널, 제목 등 고정된 UI 요소들을 그립니다.
     *
     * @param context 렌더링을 위한 DrawContext 객체
     * @param mouseX  마우스 커서의 X 좌표
     * @param mouseY  마우스 커서의 Y 좌표
     * @param delta   게임 틱 간의 시간 차이 (애니메이션 등에 사용)
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. 전체 화면 배경을 Comet 테마 색상으로 채웁니다.
        context.fill(0, 0, this.width, this.height, BACKGROUND_COLOR);

        // 중앙 패널의 X, Y 좌표를 다시 계산합니다.
        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        // 2. 중앙 패널 배경을 그립니다.
        context.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, PANEL_COLOR);
        
        // 3. 중앙 패널의 테두리를 그립니다.
        drawBorder(context, panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT, BORDER_COLOR);

        // 4. 화면 제목을 중앙 상단에 그립니다.
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, panelY + 10, TITLE_COLOR);

        // 5. 등록된 모듈이 없을 경우 안내 메시지를 표시합니다.
        if (ModuleManager.getInstance().getModules().isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.of("아직 로드된 모듈이 없습니다..."), this.width / 2, this.height / 2, 0xFF888888);
        }

        // 부모 클래스(Screen)의 render 메서드를 호출하여 자식 위젯(버튼 등)들이 렌더링되도록 합니다.
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * 주어진 좌표와 크기로 테두리를 그리는 헬퍼 메서드입니다.
     *
     * @param context 렌더링을 위한 DrawContext 객체
     * @param x       테두리의 시작 X 좌표
     * @param y       테두리의 시작 Y 좌표
     * @param width   테두리의 너비
     * @param height  테두리의 높이
     * @param color   테두리의 색상
     */
    private void drawBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int borderSize = 1; // 테두리 두께
        // 상단 테두리
        context.fill(x, y, x + width, y + borderSize, color);
        // 하단 테두리
        context.fill(x, y + height - borderSize, x + width, y + height, color);
        // 좌측 테두리
        context.fill(x, y, x + borderSize, y + height, color);
        // 우측 테두리
        context.fill(x + width - borderSize, y, x + width, y + height, color);
    }
    
    /**
     * 현재 화면이 닫힐 때 호출됩니다.
     * 이전 화면으로 돌아가도록 설정합니다.
     */
    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
