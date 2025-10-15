package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

import java.util.List;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // Done entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXed
        this.paint = paint; // FIXed
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        paint.setColor(c.getColor());
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        paint.setStyle(Paint.Style.FILL);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        for(Shape shape : g.getShapes()) {
            shape.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        //TODO
        canvas.save();
        canvas.translate(l.getX(),l.getY());
      //  l
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0,0,r.getWidth(),r.getHeight(),paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        paint.setStyle(Paint.Style.STROKE);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        List<? extends Point>points = s.getPoints();
        final float[] pts = new float[points.size()*2];
        int i = 0;
        for(Point p : points){
            pts[i++] = p.getX();
            pts[i++] = p.getY();
        }
        canvas.drawLines(pts, paint);
        return null;
    }
}
