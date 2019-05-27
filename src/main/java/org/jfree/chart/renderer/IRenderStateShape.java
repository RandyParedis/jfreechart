package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;

import java.awt.*;

public interface IRenderStateShape {
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
    Shape getItemShape(int row, int column);

    /**
     * Returns a shape used to represent the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The shape (never {@code null}).
     * @since 1.0.6
     */
    Shape lookupSeriesShape(int series);

    /**
     * Returns a shape used to represent the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The shape (possibly {@code null}).
     * @see #setSeriesShape(int, Shape)
     */
    Shape getSeriesShape(int series);

    /**
     * Sets the shape used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param shape  the shape ({@code null} permitted).
     * @see #getSeriesShape(int)
     */
    void setSeriesShape(int series, Shape shape);

    /**
     * Sets the shape for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero based).
     * @param shape  the shape ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesShape(int)
     */
    void setSeriesShape(int series, Shape shape, boolean notify);

    /**
     * Returns the default shape.
     *
     * @return The shape (never {@code null}).
     * @see #setDefaultShape(Shape)
     */
    Shape getDefaultShape();

    /**
     * Sets the default shape and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param shape the shape ({@code null} not permitted).
     * @see #getDefaultShape()
     */
    void setDefaultShape(Shape shape);

    /**
     * Sets the default shape and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape  the shape ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultShape()
     */
    void setDefaultShape(Shape shape, boolean notify);

    /**
     * Returns the flag that controls whether or not the series shape list is
     * automatically populated when {@link #lookupSeriesShape(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesShape(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesShape();

    /**
     * Sets the flag that controls whether or not the series shape list is
     * automatically populated when {@link #lookupSeriesShape(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesShape()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesShape(boolean auto);

    /**
     * Performs a lookup for the legend shape.
     *
     * @param series the series index.
     * @return The shape (possibly {@code null}).
     * @since 1.0.11
     */
    Shape lookupLegendShape(int series);

    /**
     * Returns the legend shape defined for the specified series (possibly
     * {@code null}).
     *
     * @param series the series index.
     * @return The shape (possibly {@code null}).
     * @see #lookupLegendShape(int)
     * @since 1.0.11
     */
    Shape getLegendShape(int series);

    /**
     * Sets the shape used for the legend item for the specified series, and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param shape  the shape ({@code null} permitted).
     * @since 1.0.11
     */
    void setLegendShape(int series, Shape shape);

    /**
     * Sets the shape used for the legend item for the specified series and,
     * if requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series the series index.
     * @param shape  the shape ({@code null} permitted).
     * @param notify notify listeners?
     */
    void setLegendShape(int series, Shape shape, boolean notify);

    /**
     * Returns the default legend shape, which may be {@code null}.
     *
     * @return The default legend shape.
     * @since 1.0.11
     */
    Shape getDefaultLegendShape();

    /**
     * Sets the default legend shape and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape the shape ({@code null} permitted).
     * @since 1.0.11
     */
    void setDefaultLegendShape(Shape shape);

    /**
     * Sets the default legend shape and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param shape the shape ({@code null} permitted).
     * @param notify notify listeners?
     */
    void setDefaultLegendShape(Shape shape, boolean notify);

    /**
     * Returns the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items.
     *
     * @return A boolean.
     * @since 1.0.14
     */
    boolean getTreatLegendShapeAsLine();

    /**
     * Sets the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items.
     *
     * @param treatAsLine the new flag value.
     * @since 1.0.14
     */
    void setTreatLegendShapeAsLine(boolean treatAsLine);

    /**
     * Sets the flag that controls whether or not the legend shape is
     * treated as a line when creating legend items, if requested sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param treatAsLine the new flag value.
     * @param notify notify listeners?
     * @see #getDefaultShape()
     */
    void setTreatLegendShapeAsLine(boolean treatAsLine, boolean notify);
}
