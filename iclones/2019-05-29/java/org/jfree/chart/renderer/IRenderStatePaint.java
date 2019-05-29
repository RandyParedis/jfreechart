package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;

import java.awt.*;

public interface IRenderStatePaint {
    /**
     * Returns the paint used to fill data items as they are drawn.
     * (this is typically the same for an entire series).
     * <p>
     * The default implementation passes control to the
     * {@code lookupSeriesPaint()} method. You can override this method
     * if you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The paint (never {@code null}).
     */
    Paint getItemPaint(int row, int column);

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series index (zero-based).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    Paint lookupSeriesPaint(int series);

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series index (zero-based).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesPaint(int, Paint)
     */
    Paint getSeriesPaint(int series);

    /**
     * Sets the paint used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesPaint(int)
     */
    void setSeriesPaint(int series, Paint paint);

    /**
     * Sets the paint used for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesPaint(int)
     */
    void setSeriesPaint(int series, Paint paint, boolean notify);

    /**
     * Clears the series paint settings for this renderer and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param notify notify listeners?
     * @since 1.0.11
     */
    void clearSeriesPaints(boolean notify);

    /**
     * Returns the default paint.
     *
     * @return The default paint (never {@code null}).
     * @see #setDefaultPaint(Paint)
     */
    Paint getDefaultPaint();

    /**
     * Sets the default paint and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultPaint()
     */
    void setDefaultPaint(Paint paint);

    /**
     * Sets the default paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultPaint()
     */
    void setDefaultPaint(Paint paint, boolean notify);

    /**
     * Returns the flag that controls whether or not the series paint list is
     * automatically populated when {@link #lookupSeriesPaint(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesPaint(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesPaint();

    /**
     * Sets the flag that controls whether or not the series paint list is
     * automatically populated when {@link #lookupSeriesPaint(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesPaint()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesPaint(boolean auto);

    /**
     * Returns the paint used to fill data items as they are drawn.  The
     * default implementation passes control to the
     * {@link #lookupSeriesFillPaint(int)} method - you can override this
     * method if you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The paint (never {@code null}).
     */
    Paint getItemFillPaint(int row, int column);

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    Paint lookupSeriesFillPaint(int series);

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @see #setSeriesFillPaint(int, Paint)
     */
    Paint getSeriesFillPaint(int series);

    /**
     * Sets the paint used for a series fill and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesFillPaint(int)
     */
    void setSeriesFillPaint(int series, Paint paint);

    /**
     * Sets the paint used to fill a series and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesFillPaint(int)
     */
    void setSeriesFillPaint(int series, Paint paint, boolean notify);

    /**
     * Returns the default fill paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultFillPaint(Paint)
     */
    Paint getDefaultFillPaint();

    /**
     * Sets the default fill paint and sends a {@link RendererChangeEvent} to
     * all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultFillPaint()
     */
    void setDefaultFillPaint(Paint paint);

    /**
     * Sets the default fill paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultFillPaint()
     */
    void setDefaultFillPaint(Paint paint, boolean notify);

    /**
     * Returns the flag that controls whether or not the series fill paint list
     * is automatically populated when {@link #lookupSeriesFillPaint(int)} is
     * called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesFillPaint(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesFillPaint();

    /**
     * Sets the flag that controls whether or not the series fill paint list is
     * automatically populated when {@link #lookupSeriesFillPaint(int)} is
     * called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesFillPaint()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesFillPaint(boolean auto);

    /**
     * Returns the paint used to outline data items as they are drawn.
     * (this is typically the same for an entire series).
     * <p>
     * The default implementation passes control to the
     * {@link #lookupSeriesOutlinePaint} method.  You can override this method
     * if you require different behaviour.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The paint (never {@code null}).
     */
    Paint getItemOutlinePaint(int row, int column);

    /**
     * Returns the paint used to outline an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    Paint lookupSeriesOutlinePaint(int series);

    /**
     * Returns the paint used to outline an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesOutlinePaint(int, Paint)
     */
    Paint getSeriesOutlinePaint(int series);

    /**
     * Sets the paint used for a series outline and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesOutlinePaint(int)
     */
    void setSeriesOutlinePaint(int series, Paint paint);

    /**
     * Sets the paint used to draw the outline for a series and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesOutlinePaint(int)
     */
    void setSeriesOutlinePaint(int series, Paint paint, boolean notify);

    /**
     * Returns the default outline paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultOutlinePaint(Paint)
     */
    Paint getDefaultOutlinePaint();

    /**
     * Sets the default outline paint and sends a {@link RendererChangeEvent} to
     * all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultOutlinePaint()
     */
    void setDefaultOutlinePaint(Paint paint);

    /**
     * Sets the default outline paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultOutlinePaint()
     */
    void setDefaultOutlinePaint(Paint paint, boolean notify);

    /**
     * Returns the flag that controls whether or not the series outline paint
     * list is automatically populated when
     * {@link #lookupSeriesOutlinePaint(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesOutlinePaint(boolean)
     * @since 1.0.6
     */
    boolean getAutoPopulateSeriesOutlinePaint();

    /**
     * Sets the flag that controls whether or not the series outline paint list
     * is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
     * is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesOutlinePaint()
     * @since 1.0.6
     */
    void setAutoPopulateSeriesOutlinePaint(boolean auto);
}

