package View;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

    private double lastDraw;
    private double lastW, lastH;

    @Override
    public double prefWidth(double v) {
        return super.prefWidth(v);
    }

    public ResizableCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> lazyDraw());
        heightProperty().addListener(evt -> lazyDraw());
        lastW = getWidth();
        lastH = getHeight();
    }


    public void lazyDraw(){
        if ( Math.abs( getWidth()-lastW)>100|| Math.abs( getHeight()-lastH)>100){
            lastW  = getWidth();
            lastH = getHeight();
            draw();
        }
    }

    public void draw(){
        // nada
    }


    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxHeight(double width) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double maxWidth(double height) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double minWidth(double height) {
        return 1D;
    }

    @Override
    public double minHeight(double width) {
        return 1D;
    }

    @Override
    public void resize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }
}
