package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by KVull on 12.11.2017.
 */

public class GameTools {

    //V2
    //https://stackoverflow.com/questions/13053061/circle-line-intersection-points
    public static List<PointF> getCircleLineIntersectionPoint(PointF pointA,
                                                             PointF pointB, PointF center, float radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        PointF p1 = new PointF(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        PointF p2 = new PointF(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    //V1
    //https://pastebin.com/xGprtfxs
    private class Intersection
    {
        public float cx, cy, time, nx, ny, ix, iy;
        public Intersection(float x, float y, float time, float nx, float ny, float ix, float iy)
        {
            this.cx = x;
            this.cy = y;
            this.time = time;
            this.nx = nx;
            this.ny = ny;
            this.ix = ix;
            this.iy = iy;
        }
    }

    private Intersection handleIntersection(RectF bounds, PointF start, PointF end, float radius)
    {
        final float L = bounds.left;
        final float T = bounds.top;
        final float R = bounds.right;
        final float B = bounds.bottom;

        // If the bounding box around the start and end points (+radius on all
        // sides) does not intersect with the rectangle, definitely not an
        // intersection
        if ((Math.max( start.x, end.x ) + radius < L) ||
                (Math.min( start.x, end.x ) - radius > R) ||
                (Math.max( start.y, end.y ) + radius < T) ||
                (Math.min( start.y, end.y ) - radius > B) )
        {
            return null;
        }

        final float dx = end.x - start.x;
        final float dy = end.y - start.y;
        final float invdx = (dx == 0.0f ? 0.0f : 1.0f / dx);
        final float invdy = (dy == 0.0f ? 0.0f : 1.0f / dy);
        float cornerX = Float.MAX_VALUE;
        float cornerY = Float.MAX_VALUE;

        // Calculate intersection times with each side's plane
        // Check each side's quadrant for single-side intersection
        // Calculate Corner

        /** Left Side **/
        // Does the circle go from the left side to the right side of the rectangle's left?
        if ( start.x - radius < L && end.x + radius > L )
        {
            float ltime = ((L - radius) - start.x) * invdx;
            if (ltime >= 0.0f && ltime <= 1.0f)
            {
                float ly = dy * ltime + start.y;
                // Does the collisions point lie on the left side?
                if (ly >= T && ly <= B)
                {
                    return new Intersection( dx * ltime + start.x, ly, ltime, -1, 0, L, ly );
                }
            }
            cornerX = L;
        }

        /** Right Side **/
        // Does the circle go from the right side to the left side of the rectangle's right?
        if ( start.x + radius > R && end.x - radius < R )
        {
            float rtime = (start.x - (R + radius)) * -invdx;
            if (rtime >= 0.0f && rtime <= 1.0f)
            {
                float ry = dy * rtime + start.y;
                // Does the collisions point lie on the right side?
                if (ry >= T && ry <= B)
                {
                    return new Intersection( dx * rtime + start.x, ry, rtime, 1, 0, R, ry );
                }
            }
            cornerX = R;
        }

        /** Top Side **/
        // Does the circle go from the top side to the bottom side of the rectangle's top?
        if (start.y - radius < T && end.y + radius > T)
        {
            float ttime = ((T - radius) - start.y) * invdy;
            if (ttime >= 0.0f && ttime <= 1.0f)
            {
                float tx = dx * ttime + start.x;
                // Does the collisions point lie on the top side?
                if (tx >= L && tx <= R)
                {
                    return new Intersection( tx, dy * ttime + start.y, ttime, 0, -1, tx, T );
                }
            }
            cornerY = T;
        }

        /** Bottom Side **/
        // Does the circle go from the bottom side to the top side of the rectangle's bottom?
        if (start.y + radius > B && end.y - radius < B)
        {
            float btime = (start.y - (B + radius)) * -invdy;
            if (btime >= 0.0f && btime <= 1.0f) {
                float bx = dx * btime + start.x;
                // Does the collisions point lie on the bottom side?
                if (bx >= L && bx <= R)
                {
                    return new Intersection( bx, dy * btime + start.y, btime, 0, 1, bx, B );
                }
            }
            cornerY = B;
        }

        // No intersection at all!
        if (cornerX == Float.MAX_VALUE && cornerY == Float.MAX_VALUE)
        {
            return null;
        }

        // Account for the times where we don't pass over a side but we do hit it's corner
        if (cornerX != Float.MAX_VALUE && cornerY == Float.MAX_VALUE)
        {
            cornerY = (dy > 0.0f ? B : T);
        }
        if (cornerY != Float.MAX_VALUE && cornerX == Float.MAX_VALUE)
        {
            cornerX = (dx > 0.0f ? R : L);
        }

        /* Solve the triangle between the start, corner, and intersection point.
         *
         *           +-----------T-----------+
         *           |                       |
         *          L|                       |R
         *           |                       |
         *           C-----------B-----------+
         *          / \
         *         /   \r     _.-E
         *        /     \ _.-'
         *       /    _.-I
         *      / _.-'
         *     S-'
         *
         * S = start of circle's path
         * E = end of circle's path
         * LTRB = sides of the rectangle
         * I = {ix, iY} = point at which the circle intersects with the rectangle
         * C = corner of intersection (and collision point)
         * C=>I (r) = {nx, ny} = radius and intersection normal
         * S=>C = cornerdist
         * S=>I = intersectionDistance
         * S=>E = lineLength
         * <S = innerAngle
         * <I = angle1
         * <C = angle2
         */

        double inverseRadius = 1.0 / radius;
        double lineLength = Math.sqrt( dx * dx + dy * dy );
        double cornerdx = cornerX - start.x;
        double cornerdy = cornerY - start.y;
        double cornerDistance = Math.sqrt( cornerdx * cornerdx + cornerdy * cornerdy );
        double innerAngle = Math.acos( (cornerdx * dx + cornerdy * dy) / (lineLength * cornerDistance) );

        // If the circle is too close, no intersection.
        if (cornerDistance < radius)
        {
            return null;
        }

        // If inner angle is zero, it's going to hit the corner straight on.
        if (innerAngle == 0.0f)
        {
            float time = (float)((cornerDistance - radius) / lineLength);

            // If time is outside the boundaries, return null. This algorithm can
            // return a negative time which indicates a previous intersection, and
            // can also return a time > 1.0f which can predict a corner intersection.
            if (time > 1.0f || time < 0.0f)
            {
                return null;
            }

            float ix = time * dx + start.x;
            float iy = time * dy + start.y;
            float nx = (float)(cornerdx / cornerDistance);
            float ny = (float)(cornerdy / cornerDistance);

            return new Intersection( ix, iy, time, nx, ny, cornerX, cornerY );
        }

        double innerAngleSin = Math.sin( innerAngle );
        double angle1Sin = innerAngleSin * cornerDistance * inverseRadius;

        // The angle is too large, there cannot be an intersection
        if (Math.abs( angle1Sin ) > 1.0f)
        {
            return null;
        }

        double angle1 = Math.PI - Math.asin( angle1Sin );
        double angle2 = Math.PI - innerAngle - angle1;
        double intersectionDistance = radius * Math.sin( angle2 ) / innerAngleSin;

        // Solve for time
        float time = (float)(intersectionDistance / lineLength);

        // If time is outside the boundaries, return null. This algorithm can
        // return a negative time which indicates a previous intersection, and
        // can also return a time > 1.0f which can predict a corner intersection.
        if (time > 1.0f || time < 0.0f)
        {
            return null;
        }

        // Solve the intersection and normal
        float ix = time * dx + start.x;
        float iy = time * dy + start.y;
        float nx = (float)((ix - cornerX) * inverseRadius);
        float ny = (float)((iy - cornerY) * inverseRadius);

        return new Intersection( ix, iy, time, nx, ny, cornerX, cornerY );
    }
}
