package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator indicating that a shape should be drawn as an outlined shape
 * instead of a filled one.
 */
public final class Outline implements Shape {

    private final Shape shape;

    public Outline(final Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        // Done your job
        return v.onOutline(this);
    }
}
