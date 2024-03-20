package com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Square class represents a square geometric shape that can be drawn onto a graphical context.
 * It extends the GeometricObject class and provides the necessary implementation to render itself.
 */
public class Square extends GeometricObject {

    private double sideLength;

    /**
     * Constructs a square with the specified side length, position, and fill color.
     *
     * @param sideLength The length of the square's sides.
     * @param x          The x-coordinate of the square's center.
     * @param y          The y-coordinate of the square's center.
     * @param fillColor  The fill color of the square.
     */
    public Square(double sideLength, double x, double y, Color fillColor) {
        super(x, y, fillColor);
        this.sideLength = sideLength;
    }

    /**
     * Draws the square onto the specified GraphicsContext.
     *
     * @param g The GraphicsContext to draw the square onto.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.setFill(getFillColor());
        g.fillRect(getX() - sideLength / 2, getY() - sideLength / 2, sideLength, sideLength);
    }
}
