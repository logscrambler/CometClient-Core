package net.comet.core.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CometScreen extends Screen {

    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 250;
    private static final int BACKGROUND_COLOR = 0xAA050505;
    private static final int PANEL_COLOR = 0xEE0A0A10;
    private static final int BORDER_COLOR = 0xFF333344;
    private static final int TITLE_COLOR = 0xFFCCCCFF;

    private final Screen parent;

    public CometScreen(Screen parent) {
        super(Text.of("Comet Client"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        // Add a "Back" button
        ButtonWidget backButton = ButtonWidget.builder(Text.of("Back"), (button) -> this.close())
                .dimensions(panelX + 10, panelY + PANEL_HEIGHT - 30, 80, 20)
                .build();
        this.addDrawableChild(backButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render a semi-transparent dark background (Space theme base)
        context.fill(0, 0, this.width, this.height, BACKGROUND_COLOR);

        // Calculate panel position
        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        // Draw Panel Background
        context.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, PANEL_COLOR);
        
        // Draw Panel Border
        drawBorder(context, panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT, BORDER_COLOR);

        // Draw the title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, panelY + 10, TITLE_COLOR);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int borderSize = 1;
        // Top
        context.fill(x, y, x + width, y + borderSize, color);
        // Bottom
        context.fill(x, y + height - borderSize, x + width, y + height, color);
        // Left
        context.fill(x, y, x + borderSize, y + height, color);
        // Right
        context.fill(x + width - borderSize, y, x + width, y + height, color);
    }
    
    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
