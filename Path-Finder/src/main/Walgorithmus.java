package main;


import java.awt.image.BufferedImage;
import java.util.*;

public class Walgorithmus {

    private BufferedImage img;
    private int startx, starty, endx, endy;
    private int pixelBreiteDesBlocks;
    private double[][] m;
    private List<PathPoint> open;

    public Walgorithmus(BufferedImage image, int sx, int sy, int ex, int ey, int pb) {

        img = image;
        startx = sx / pixelBreiteDesBlocks;
        starty = sy / pixelBreiteDesBlocks;
        endx = ex / pixelBreiteDesBlocks;
        endx = ey / pixelBreiteDesBlocks;
        pixelBreiteDesBlocks = pb;


        m = new double[img.getHeight() / pixelBreiteDesBlocks][img.getWidth() / pixelBreiteDesBlocks];

        m[starty][startx] = 0;

        open = new ArrayList<>();

        open.add(new PathPoint(startx, starty, Math.sqrt((endx - startx) ^ 2 + (endy - starty) ^ 2))); // Startpunkt

    }

    public double[][] step(int stepCount) {

        PathPoint currentPoint;
        boolean atEnd;

        for (int i = 0; i < stepCount; i++) {

            currentPoint = open.get(0);

            for (PathPoint point : open) {
                if ((currentPoint.getcostRemaningDirectWay() + m[currentPoint.gety()][currentPoint.getx()]) > point.getcostRemaningDirectWay() + m[currentPoint.gety()][currentPoint.getx()]) {
                    currentPoint = point;
                }
            }

            for (int blockY = 0; blockY < 3; blockY++) {
                for (int blockX = 0; blockX < 3; blockX++) {
                    if (walkable(currentPoint.getx() + blockX - 1, currentPoint.gety() + blockY - 1)) {
                        if (blockY != 1 || blockX != 1) {
                            if (blockY == 1 || blockX == 1) {
                                m[currentPoint.gety() + blockY - 1][currentPoint.getx() + blockX - 1] = m[currentPoint.gety()][currentPoint.getx()] + 1;
                            } else {
                                m[currentPoint.gety() + blockY - 1][currentPoint.getx() + blockX - 1] = m[currentPoint.gety()][currentPoint.getx()] + Math.sqrt(2);
                            }
                            open.add(new PathPoint(currentPoint.getx() + blockX - 1, currentPoint.gety() + blockY - 1, Math.sqrt((endx - currentPoint.getx() + blockX - 1) ^ 2 + (endy - currentPoint.gety() + blockY - 1) ^ 2)));
                        }
                    } else {
                        m[currentPoint.gety()][currentPoint.getx()] = -1;
                    }
                }
            }
        }
        return m;
    }


    private boolean walkable(int x, int y) {
        int p;
        boolean w = true;
        for (int i = 0; i < pixelBreiteDesBlocks; i++) {
            for (int j = 0; j < pixelBreiteDesBlocks; j++) {
                p = img.getRGB((x * pixelBreiteDesBlocks + j), (y * pixelBreiteDesBlocks + i));
                if (p != 0xFFFFFFFF) {
                    w = false;
                }
            }
        }
        return w;
    }

}
