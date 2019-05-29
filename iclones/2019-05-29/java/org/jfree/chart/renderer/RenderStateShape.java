package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.util.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

// SHAPE
public class RenderStateShape implements Serializable, PublicCloneable, IRenderStateShape {
    private transient AbstractRenderer abstractRenderer;

    /**
     * The default shape.
     */
    public static final Shape DEFAULT_SHAPE = new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0);
    /**
     * A shape list.
     */
    private ShapeList shapeList;
    /**
     * A flag that controls whether or not the shapeList is auto-populated
     * in the {@link #lookupSeriesShape(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesShape;
    /**
     * The base shape.
     */
    private transient Shape defaultShape;
    /**
     * The per-series legend shape settings.
     *
     * @since 1.0.11
     */
    private ShapeList legendShapeList;
    /**
     * The base shape for legend items.  If this is {@code null}, the
     * series shape will be used.
     *
     * @since 1.0.11
     */
    private transient Shape defaultLegendShape;
    /**
     * A special flag that, if true, will cause the getLegendItem() method
     * to configure the legend shape as if it were a line.
     *
     * @since 1.0.14
     */
    private boolean treatLegendShapeAsLine;

    public RenderStateShape(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;

        this.shapeList = new ShapeList();
        this.defaultShape = DEFAULT_SHAPE;
        this.autoPopulateSeriesShape = true;

        this.legendShapeList = new ShapeList();
        this.defaultLegendShape = null;

        this.treatLegendShapeAsLine = false;
    }

    public void setAbstractRenderer(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;
    }

    /**
     * Returns a shape used to represent a data item.
     * <p>
     * The default implementation passes control to the
     * {@link #lookupSeriesShape(int)} method. You can override this method if
     * you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The shape (never {@code null}).
     */
    @Override
    public Shape getItemShape(int row, int column) {
        return lookupSeriesShape(row);
    }

    /**
     * Returns a shape used to represent the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The shape (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Shape lookupSeriesShape(int series) {

        Shape result = getSeriesShape(series);
        if (result == null && this.autoPopulateSeriesShape) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                result = supplier.getNextShape();
                setSeriesShape(series, result, false);
            }
        }
        if (result == null) {
            result = this.defaultShape;
        }
        return result;

    }

    /**
     * Returns a shape used to represent the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The shape (possibly {@code null}).
     * @see #setSeriesShape(int, Shape)
     */
    @Override
    public Shape getSeriesShape(int series) {
        return this.shapeList.getShape(series);
    }

    /**
     * Sets the shape used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param shape  the shape ({@code null} permitted).
     * @see #getSeriesShape(int)
     */
    @Override
    public void setSeriesShape(int series, Shape shape) {
        setSeriesShape(series, shape, true);
    }

    /**
     * Sets the shape for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero based).
     * @param shape  the shape ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesShape(int)
     */
    @Override
    public void setSeriesShape(int series, Shape shape, boolean notify) {
        this.shapeList.setShape(series, shape);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default shape.
     *
     * @return The shape (never {@code null}).
     * @see #setDefaultShape(Shape)
     */
    @Override
    public Shape getDefaultShape() {
        return this.defaultShape;
    }

    /**
     * Sets the default shape and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param shape the shape ({@code null} not permitted).
     * @see #getDefaultShape()
     */
    @Override
    public void setDefaultShape(Shape shape) {
        // defer argument checking...
        setDefaultShape(shape, true);
    }

    /**
     * Sets the default shape and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape  the shape ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultShape()
     */
    @Override
    public void setDefaultShape(Shape shape, boolean notify) {
        Args.nullNotPermitted(shape, "shape");
        this.defaultShape = shape;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series shape list is
     * automatically populated when {@link #lookupSeriesShape(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesShape(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesShape() {
        return this.autoPopulateSeriesShape;
    }

    /**
     * Sets the flag that controls whether or not the series shape list is
     * automatically populated when {@link #lookupSeriesShape(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesShape()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesShape(boolean auto) {
        this.autoPopulateSeriesShape = auto;
    }

    /**
     * Performs a lookup for the legend shape.
     *
     * @param series the series index.
     * @return The shape (possibly {@code null}).
     * @since 1.0.11
     */
    @Override
    public Shape lookupLegendShape(int series) {
        Shape result = getLegendShape(series);
        if (result == null) {
            result = this.defaultLegendShape;
        }
        if (result == null) {
            result = lookupSeriesShape(series);
        }
        return result;
    }

    /**
     * Returns the legend shape defined for the specified series (possibly
     * {@code null}).
     *
     * @param series the series index.
     * @return The shape (possibly {@code null}).
     * @see #lookupLegendShape(int)
     * @since 1.0.11
     */
    @Override
    public Shape getLegendShape(int series) {
        return this.legendShapeList.getShape(series);
    }

    /**
     * Sets the shape used for the legend item for the specified series, and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param shape  the shape ({@code null} permitted).
     * @since 1.0.11
     */
    @Override
    public void setLegendShape(int series, Shape shape) {
        setLegendShape(series, shape, true);
    }

    /**
     * Sets the shape used for the legend item for the specified series and,
     * if requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series the series index.
     * @param shape  the shape ({@code null} permitted).
     * @param notify notify listeners?
     */
    @Override
    public void setLegendShape(int series, Shape shape, boolean notify) {
        this.legendShapeList.setShape(series, shape);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default legend shape, which may be {@code null}.
     *
     * @return The default legend shape.
     * @since 1.0.11
     */
    @Override
    public Shape getDefaultLegendShape() {
        return this.defaultLegendShape;
    }

    /**
     * Sets the default legend shape and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape the shape ({@code null} permitted).
     * @since 1.0.11
     */
    @Override
    public void setDefaultLegendShape(Shape shape) {
        setDefaultLegendShape(shape, true);
    }

    /**
     * Sets the default legend shape and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape  the shape ({@code null} permitted).
     * @param notify notify listeners?
     */
    @Override
    public void setDefaultLegendShape(Shape shape, boolean notify) {
        this.defaultLegendShape = shape;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items.
     *
     * @return A boolean.
     * @since 1.0.14
     */
    @Override
    public boolean getTreatLegendShapeAsLine() {
        return this.treatLegendShapeAsLine;
    }

    /**
     * Sets the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items.
     *
     * @param treatAsLine the new flag value.
     * @since 1.0.14
     */
    @Override
    public void setTreatLegendShapeAsLine(boolean treatAsLine) {
        setTreatLegendShapeAsLine(treatAsLine, true);
    }

    /**
     * Sets the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items, if requested sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param treatAsLine the new flag value.
     * @param notify      notify listeners?
     * @see #getDefaultShape()
     */
    @Override
    public void setTreatLegendShapeAsLine(boolean treatAsLine, boolean notify) {
        if (this.treatLegendShapeAsLine != treatAsLine) {
            this.treatLegendShapeAsLine = treatAsLine;
            if (notify) {
                abstractRenderer.fireChangeEvent();
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RenderStateShape clone = (RenderStateShape) super.clone();

        if (this.shapeList != null) {
            clone.shapeList = (ShapeList) this.shapeList.clone();
        }
        if (this.getDefaultShape() != null) {
            clone.setDefaultShape(ShapeUtils.clone(this.getDefaultShape()));
        }

        if (this.legendShapeList != null) {
            clone.legendShapeList = (ShapeList) this.legendShapeList.clone();
        }

        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RenderStateShape)) {
            return false;
        }
        RenderStateShape that = (RenderStateShape) obj;

        if (this.getTreatLegendShapeAsLine() != that.getTreatLegendShapeAsLine()) {
            return false;
        }
        if (!ObjectUtils.equal(this.shapeList, that.shapeList)) {
            return false;
        }
        if (!ShapeUtils.equal(this.getDefaultShape(), that.getDefaultShape())) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendShapeList,
                that.legendShapeList)) {
            return false;
        }
        if (!ShapeUtils.equal(this.getDefaultLegendShape(),
                that.getDefaultLegendShape())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        // shapeList
        // baseShape
        return result;
    }
}

