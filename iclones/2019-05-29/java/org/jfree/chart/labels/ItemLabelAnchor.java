/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * --------------------
 * ItemLabelAnchor.java
 * --------------------
 * (C) Copyright 2003-2016, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Apr-2003 : Version 1 (DG);
 * 19-Feb-2004 : Moved to org.jfree.chart.labels package, added readResolve()
 *               method (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0
 *               release (DG);
 * 08-Jul-2018 : Made ItemLabelAnchor an enum (TH);
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.plot.PlotOrientation;

import java.awt.geom.Point2D;

/**
 * An enumeration of the positions that a value label can take, relative to an
 * item in a {@link org.jfree.chart.plot.CategoryPlot}.
 */
public enum ItemLabelAnchor {

    /** CENTER. */
    CENTER,

    /** INSIDE1. */
    INSIDE1,

    /** INSIDE2. */
    INSIDE2,

    /** INSIDE3. */
    INSIDE3,

    /** INSIDE4. */
    INSIDE4,

    /** INSIDE5. */
    INSIDE5,

    /** INSIDE6. */
    INSIDE6,

    /** INSIDE7. */
    INSIDE7,

    /** INSIDE8. */
    INSIDE8,

    /** INSIDE9. */
    INSIDE9,

    /** INSIDE10. */
    INSIDE10,

    /** INSIDE11. */
    INSIDE11,

    /** INSIDE12. */
    INSIDE12,

    /** OUTSIDE1. */
    OUTSIDE1,

    /** OUTSIDE2. */
    OUTSIDE2,

    /** OUTSIDE3. */
    OUTSIDE3,

    /** OUTSIDE4. */
    OUTSIDE4,

    /** OUTSIDE5. */
    OUTSIDE5,

    /** OUTSIDE6. */
    OUTSIDE6,

    /** OUTSIDE7. */
    OUTSIDE7,

    /** OUTSIDE8. */
    OUTSIDE8,

    /** OUTSIDE9. */
    OUTSIDE9,

    /** OUTSIDE10. */
    OUTSIDE10,

    /** OUTSIDE11. */
    OUTSIDE11,

    /** OUTSIDE12. */
    OUTSIDE12;

    /** The adjacent offset. */
    private static final double ADJ = Math.cos(Math.PI / 6.0);
    /** The opposite offset. */
    private static final double OPP = Math.sin(Math.PI / 6.0);

    /**
     * Calculates the item label anchor point.
     *
     * @param anchor  the anchor.
     * @param x  the x coordinate.
     * @param y  the y coordinate.
     * @param orientation  the plot orientation.
     * @param itemLabelAnchorOffset the item label anchor offset
     *
     * @return The anchor point (never {@code null}).
     */
    public static Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor,
                                                    double x, double y, PlotOrientation orientation,
                                                    double itemLabelAnchorOffset) {
        Point2D result = null;
        if (anchor == CENTER) {
            result = new Point2D.Double(x, y);
        }
        else if (anchor == INSIDE1) {
            result = new Point2D.Double(x + OPP * itemLabelAnchorOffset,
                    y - ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE2) {
            result = new Point2D.Double(x + ADJ * itemLabelAnchorOffset,
                    y - OPP * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE3) {
            result = new Point2D.Double(x + itemLabelAnchorOffset, y);
        }
        else if (anchor == INSIDE4) {
            result = new Point2D.Double(x + ADJ * itemLabelAnchorOffset,
                    y + OPP * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE5) {
            result = new Point2D.Double(x + OPP * itemLabelAnchorOffset,
                    y + ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE6) {
            result = new Point2D.Double(x, y + itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE7) {
            result = new Point2D.Double(x - OPP * itemLabelAnchorOffset,
                    y + ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE8) {
            result = new Point2D.Double(x - ADJ * itemLabelAnchorOffset,
                    y + OPP * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE9) {
            result = new Point2D.Double(x - itemLabelAnchorOffset, y);
        }
        else if (anchor == INSIDE10) {
            result = new Point2D.Double(x - ADJ * itemLabelAnchorOffset,
                    y - OPP * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE11) {
            result = new Point2D.Double(x - OPP * itemLabelAnchorOffset,
                    y - ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == INSIDE12) {
            result = new Point2D.Double(x, y - itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE1) {
            result = new Point2D.Double(
                    x + 2.0 * OPP * itemLabelAnchorOffset,
                    y - 2.0 * ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE2) {
            result = new Point2D.Double(
                    x + 2.0 * ADJ * itemLabelAnchorOffset,
                    y - 2.0 * OPP * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE3) {
            result = new Point2D.Double(x + 2.0 * itemLabelAnchorOffset,
                    y);
        }
        else if (anchor == OUTSIDE4) {
            result = new Point2D.Double(
                    x + 2.0 * ADJ * itemLabelAnchorOffset,
                    y + 2.0 * OPP * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE5) {
            result = new Point2D.Double(
                    x + 2.0 * OPP * itemLabelAnchorOffset,
                    y + 2.0 * ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE6) {
            result = new Point2D.Double(x,
                    y + 2.0 * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE7) {
            result = new Point2D.Double(
                    x - 2.0 * OPP * itemLabelAnchorOffset,
                    y + 2.0 * ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE8) {
            result = new Point2D.Double(
                    x - 2.0 * ADJ * itemLabelAnchorOffset,
                    y + 2.0 * OPP * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE9) {
            result = new Point2D.Double(x - 2.0 * itemLabelAnchorOffset,
                    y);
        }
        else if (anchor == OUTSIDE10) {
            result = new Point2D.Double(
                    x - 2.0 * ADJ * itemLabelAnchorOffset,
                    y - 2.0 * OPP * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE11) {
            result = new Point2D.Double(
                x - 2.0 * OPP * itemLabelAnchorOffset,
                y - 2.0 * ADJ * itemLabelAnchorOffset);
        }
        else if (anchor == OUTSIDE12) {
            result = new Point2D.Double(x,
                    y - 2.0 * itemLabelAnchorOffset);
        }
        return result;
    }
}

