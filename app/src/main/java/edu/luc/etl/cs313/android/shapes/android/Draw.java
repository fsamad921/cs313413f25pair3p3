package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

import java.util.List;

import static android.graphics.Paint.Style.STROKE;

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
        final int oldColor = paint.getColor();
        paint.setColor(c.getColor()); // ✅ use the actual color
        c.getShape().accept(this);
        paint.setColor(oldColor);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        f.getShape().accept(this);
        paint.setStyle(STROKE);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        for (Shape shape : g.getShapes()) {
            shape.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        paint.setStyle(STROKE);
        o.getShape().accept(this);
        paint.setStyle(STROKE);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        List<? extends Point> points = s.getPoints();
        final float[] pts = new float[(points.size() - 1) * 4];
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            pts[i * 4]     = p1.getX();
            pts[i * 4 + 1] = p1.getY();
            pts[i * 4 + 2] = p2.getX();
            pts[i * 4 + 3] = p2.getY();
        }
        canvas.drawLines(pts, paint);
        return null;
    }
}
