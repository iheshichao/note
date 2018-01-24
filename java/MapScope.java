package com.hualala;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过辐射法计算某点是否在某个范围之内
 *
 * @author heshichao
 * 2018/1/24 下午3:29
 */
public class MapScope {

    /**
     * 坐标类，只包含纬度值：lat和经度值：lng
     */
    static class Point {

        /**
         * 纬度值
         */
        double lat;

        /**
         * 经度值
         */
        double lng;

        Point(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point p = (Point) obj;
                return (p.lat == this.lat && p.lng == this.lng);
            }
            return false;
        }
    }

    /**
     * 判断点是否在多边形内
     * 如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    private boolean isPointInPoly(Point point, List<Point> pts) {

        if (null == point || null == pts || pts.isEmpty()) {
            return false;
        }

        final int len = pts.size();
        // cross points count of x
        int intersectCount = 0;
        // 浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        // neighbour bound vertices
        Point p1, p2;

        p1 = pts.get(0);
        for (int i = 1; i <= len; ++i) {
            if (point.equals(p1)) {
                return true;
            }

            p2 = pts.get(i % len);
            if (point.lat < Math.min(p1.lat, p2.lat) || point.lat > Math.max(p1.lat, p2.lat)) {
                p1 = p2;
                continue;
            }

            if (point.lat > Math.min(p1.lat, p2.lat) && point.lat < Math.max(p1.lat, p2.lat)) {
                if (point.lng <= Math.max(p1.lng, p2.lng)) {
                    if (p1.lat == p2.lat && point.lng >= Math.min(p1.lng, p2.lng)) {
                        return true;
                    }

                    if (p1.lng == p2.lng) {
                        if (p1.lng == point.lng) {
                            return true;
                        } else {
                            ++intersectCount;
                        }
                    } else {
                        double xinters = (point.lat - p1.lat) * (p2.lng - p1.lng) / (p2.lat - p1.lat) + p1.lng;
                        if (Math.abs(point.lng - xinters) < precision) {
                            return true;
                        }

                        if (point.lng < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            } else {
                if (point.lat == p2.lat && point.lng <= p2.lng) {
                    Point p3 = pts.get((i + 1) % len);
                    if (point.lat >= Math.min(p1.lat, p3.lat) && point.lat <= Math.max(p1.lat, p3.lat)) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }

        //偶数在多边形外, 奇数在多边形内
        return intersectCount % 2 != 0;

    }

    public static void main(String[] args) {
        Point point = new Point(15, 5);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 10);
        Point p3 = new Point(10, 10);
        Point p4 = new Point(10, 1);
        List<Point> list = new ArrayList<>(4);
        list.add(p1);list.add(p2);list.add(p3);list.add(p4);
        boolean b = new MapScope().isPointInPoly(point, list);
        System.out.println(b);
    }

}
