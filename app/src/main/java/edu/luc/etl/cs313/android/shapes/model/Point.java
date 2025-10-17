package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

    // Done your job
    // HINT: use a circle with radius 0 as the shape!
    protected final int x;
    protected final int y;
    public Point(final int x, final int y) {
        super(x, x, null);
        assert x >= 0;
        assert y >= 0;
        this.x = x;
        this.y = y;
    }
    public int getX(){return x;}

    public int getY(){return y;}
}
