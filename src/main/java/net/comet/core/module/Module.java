package net.comet.core.module;

import net.comet.core.module.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Comet Client의 모든 기능성 모듈의 기반이 되는 추상 클래스입니다.
 * IModule 인터페이스를 구현하며, 설정 관리, 위치 관리, 렌더링 등의 공통 기능을 제공합니다.
 *
 * @author ReVersing
 */
public abstract class Module implements IModule {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    
    private final String name;
    private final String description;
    private boolean enabled;
    
    // 설정값 목록
    private final List<Setting<?>> settings = new ArrayList<>();
    
    // HUD 모듈을 위한 위치 및 크기 정보
    private int x, y;
    private int width, height;
    private boolean draggable = true; // 드래그 가능 여부

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Text getName() {
        return Text.of(name);
    }

    @Override
    public Text getDescription() {
        return Text.of(description);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    @Override
    public void toggle() {
        setEnabled(!enabled);
    }

    // 하위 클래스에서 오버라이드하여 사용
    public void onEnable() {}
    public void onDisable() {}
    
    /**
     * 화면에 무언가를 그릴 때 호출됩니다. (HUD 모듈용)
     * @param context 그리기 도구
     */
    public void onRender(DrawContext context) {}

    // 설정 관련 메서드
    protected void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }
    
    public List<Setting<?>> getSettings() {
        return settings;
    }

    // 위치 및 크기 관련 메서드 (HUD용)
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    
    public boolean isDraggable() { return draggable; }
    public void setDraggable(boolean draggable) { this.draggable = draggable; }
}
