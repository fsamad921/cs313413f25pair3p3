package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // done entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {

        var shapes = g.getShapes();
        if (shapes.isEmpty()) {
            return new Location(0, 0, new Rectangle(0, 0));
        }

        Location firstBox = shapes.get(0).accept(this);
        int minX = firstBox.getX();
        int minY = firstBox.getY();
        Rectangle firstRect = (Rectangle) firstBox.getShape();
        int maxX = minX + firstRect.getWidth();
        int maxY = minY + firstRect.getHeight();
        //1 because we already processed shape 0
        for(int i = 1; i < shapes.size(); i++){
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
        Location innerBox = l.getShape().accept(this);
        int innerX = innerBox.getX();
        int innerY = innerBox.getY();
        Rectangle rect = (Rectangle) innerBox.getShape();
        int newX = l.getX() + innerX;
        int newY = l.getY() + innerY;
        return new Location(newX,newY,rect);
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0, new Rectangle(r.getWidth(),r.getHeight()));

    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        List<? extends Point> points = s.getPoints();
        // check when testing
        if(points.isEmpty()){
            return new Location(0,0, new Rectangle(0,0));
        }
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for(Point p : points){
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY,p.getY());
            maxX = Math.max(maxX,p.getX());
            maxY = Math.max(maxY,p.getY());
        }
        int width = maxX-minX;
        int height = maxY-minY;
        return new Location(minX,minY, new Rectangle(width,height));
    }
}
