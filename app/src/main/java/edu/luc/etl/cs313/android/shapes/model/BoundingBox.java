package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return new Location(0,0,f);
    }

    @Override
    public Location onGroup(final Group g) {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        var shapes = g.getShapes();
        for(int i = 0; i < shapes.size(); i++){
           Location boundingBox = shapes.get(i).accept(this);

           int left = boundingBox.getX();
           int top = boundingBox.getY();
           Rectangle rect = (Rectangle) boundingBox.getShape();
           int right = left + rect.getWidth();
           int bottom = top + rect.getHeight();

           minX = Math.min(minX, left);
           minY = Math.min(minY, top);
           maxX = Math.max(maxX, right);
           maxY = Math.max(maxY, bottom);
        }
        int width = maxX- minX;
        int height = maxY - minY;
        return new Location(minX,minY,new Rectangle(width,height));
    }

    @Override
    public Location onLocation(final Location l) {
        Location innerBox = l.accept(this);
        int innerX = innerBox.getX();
        int innerY = innerBox.getY();
        Rectangle rect = (Rectangle) innerBox.getShape();
        int newX = l.getX() + innerX;
        int newY = l.getY() + innerY;
        return new Location(newX,newY,rect);
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        r.getHeight();
        r.getWidth();
        return new Location(r,0,r);
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        Shape shapeOfC = c.getShape();
        return new Location(0,0,shapeOfC);
    }

    @Override
    public Location onOutline(final Outline o) {
        Shape shapeOfO = o.getShape();
        return new Location(0,0,shapeOfO);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        Shape shapeOfS = (Shape) s.getPoints();
        return new Location(0,0,shapeOfS);
    }
}
