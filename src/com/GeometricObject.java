package com;

import javafx.scene.paint.Color;

/**
 * The GeometricObject class represents a basic geometric object with position and fill color.
 * It serves as a foundation for creating specific geometric shapes that can be drawn.
 */
public abstract class GeometricObject implements Drawable {
    private double x;
    private double y;
    private Color fillColor;

    /**
     * Constructs a geometric object with the specified position and fill color.
     *
     * @param x         The x-coordinate of the object's position.
     * @param y         The y-coordinate of the object's position.
     * @param fillColor The fill color of the object.
     */
    public GeometricObject(double x, double y, Color fillColor) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
    }

    /**
     * Gets the x-coordinate of the object's position.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the object's position.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the fill color of the object.
     *
     * @return The fill color.
     */
    public Color getFillColor() {
        return fillColor;
    }
}
