package com.company;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Wall extends Line2D.Double {
    private Point2D.Double pt1, pt2;

    public Wall(int x1, int y1, int x2, int y2) {
        pt1 = new Point2D.Double(x1, y1);
        pt2 = new Point2D.Double(x2, y2);
        super.setLine(pt1, pt2);
    }

    public Point2D.Double getPt1() {
        return pt1;
    }

    public Point2D.Double getPt2() {
        return pt2;
    }

    public Point2D.Double getMidpoint() {
        return new Point2D.Double((pt1.getX() + pt2.getX()) / 2, (pt1.getY() + pt2.getY()) / 2);
    }
}
