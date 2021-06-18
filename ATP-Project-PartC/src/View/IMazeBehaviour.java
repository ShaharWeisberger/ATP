package View;

import javafx.scene.canvas.GraphicsContext;

public interface IMazeBehaviour {
    public void DrawPlayerIcon(GraphicsContext graphicsContext,double x, double y,
                               double cellWidth, double cellHeight);
}
