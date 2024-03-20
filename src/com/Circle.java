package com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Circle class represents a circle geometric shape that can be drawn onto a graphical context.
 * It extends the GeometricObject class and provides the necessary implementation to render itself.
 */
public class Circle extends GeometricObject {

    private double radius;

    /**
     * Constructs a circle with the specified radius, position, and fill color.
     *
     * @param radius    The radius of the circle.
     * @param x         The x-coordinate of the circle's center.
     * @param y         The y-coordinate of the circle's center.
     * @param fillColor The fill color of the circle.
     */
    public Circle(double radius, double x, double y, Color fillColor) {
        super(x, y, fillColor);
        this.radius = radius;
    }

    /**
     * Draws the circle onto the specified GraphicsContext.
     *
     * @param g The GraphicsContext to draw the circle onto.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.setFill(getFillColor());
        g.fillOval(getX() - radius, getY() - radius, 2 * radius, 2 * radius);
    }
}
