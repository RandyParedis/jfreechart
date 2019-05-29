package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;

import java.awt.*;

public interface IRenderStateStroke {
    /**
     * Returns the stroke used to draw data items.
     * <p>
     * The default implementation passes control to the getSeriesStroke method.
     * You can override this method if you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The stroke (never {@code null}).
     */
    Stroke getItemStroke(int row, int column);

    /**
     * Returns the stroke used to draw the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (never {@code null}).
     * @since 1.0.6
     */
    Stroke lookupSeriesStroke(int series);

    /**
     * Returns the stroke used to draw the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (possibly {@code null}).
     * @see #setSeriesStroke(int, Stroke)
     */
    Stroke getSeriesStroke(int series);

    /**
     * Sets the stroke used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @see #getSeriesStroke(int)
     */
    void setSeriesStroke(int series, Stroke stroke);

    /**
     * Sets the stroke for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesStroke(int)
     */
    void setSeriesStroke(int series, Stroke stroke, boolean notify);

    /**
     * Clears the series stroke settings for this renderer and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param notify notify listeners?
     * @since 1.0.11
     */
    void clearSeriesStrokes(boolean notify);

    /**
     * Returns the default stroke.
     *
     * @return The default stroke (never {@code null}).
     * @see #setDefaultStroke(Stroke)
     */
    Stroke getDefaultStroke();

    /**
     * Sets the default stroke and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @see #getDefaultStroke()
     */
    void setDefaultStroke(Stroke stroke);

    /**
     * Sets the base stroke and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultStroke()
     */
    void setDefaultStroke(Stroke stroke, boolean notify);

    /**
     * Returns the flag that controls whether or not the series stroke list is
     * automatically populated when {@link #lookupSeriesStroke(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesStroke(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesStroke();

    /**
     * Sets the flag that controls whether or not the series stroke list is
     * automatically populated when {@link #lookupSeriesStroke(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesStroke()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesStroke(boolean auto);

    /**
     * Returns the stroke used to outline data items.  The default
     * implementation passes control to the
     * {@link #lookupSeriesOutlineStroke(int)} method. You can override this
     * method if you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The stroke (never {@code null}).
     */
    Stroke getItemOutlineStroke(int row, int column);

    /**
     * Returns the stroke used to outline the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (never {@code null}).
     * @since 1.0.6
     */
    Stroke lookupSeriesOutlineStroke(int series);

    /**
     * Returns the stroke used to outline the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (possibly {@code null}).
     * @see #setSeriesOutlineStroke(int, Stroke)
     */
    Stroke getSeriesOutlineStroke(int series);

    /**
     * Sets the outline stroke used for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @see #getSeriesOutlineStroke(int)
     */
    void setSeriesOutlineStroke(int series, Stroke stroke);

    /**
     * Sets the outline stroke for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param stroke the stroke ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesOutlineStroke(int)
     */
    void setSeriesOutlineStroke(int series, Stroke stroke,
                                boolean notify);

    /**
     * Returns the default outline stroke.
     *
     * @return The stroke (never {@code null}).
     * @see #setDefaultOutlineStroke(Stroke)
     */
    Stroke getDefaultOutlineStroke();

    /**
     * Sets the default outline stroke and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @see #getDefaultOutlineStroke()
     */
    void setDefaultOutlineStroke(Stroke stroke);

    /**
     * Sets the default outline stroke and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultOutlineStroke()
     */
    void setDefaultOutlineStroke(Stroke stroke, boolean notify);

    /**
     * Returns the flag that controls whether or not the series outline stroke
     * list is automatically populated when
     * {@link #lookupSeriesOutlineStroke(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesOutlineStroke(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesOutlineStroke();

    /**
     * Sets the flag that controls whether or not the series outline stroke list
     * is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
     * is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesOutlineStroke()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesOutlineStroke(boolean auto);
}

