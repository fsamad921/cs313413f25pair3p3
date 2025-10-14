package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying a shape's location.
 */
public non-sealed class Location implements Shape {

    protected final int x, y;

    protected final Shape shape;

    public Location(final int x, final int y, final Shape shape) {
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public Location(Rectangle r, int y, Rectangle r1) {
        x = 0;
        this.y = 0;
        shape = null;
    }

    public Shape getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        return v.onLocation(this);
    }
}
