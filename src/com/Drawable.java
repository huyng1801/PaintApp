package com;

import javafx.scene.canvas.GraphicsContext;

/**
 * The Drawable interface represents an object that can be drawn onto a graphical context.
 * Classes that implement this interface should provide an implementation for the 'draw'
 * method to render themselves on the given GraphicsContext.
 */
public interface Drawable {

    /**
     * Draws the drawable object onto the specified GraphicsContext.
     * Implementing classes should define how the object is rendered.
     *
     * @param g The GraphicsContext to draw the object onto.
     */
    void draw(GraphicsContext g);
}
