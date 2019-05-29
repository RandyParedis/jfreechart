package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.ItemLabelPosition;

import java.awt.*;

public interface IRenderStateItemLabel {
    /**
     * Returns the font for an item label.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The font (never {@code null}).
     */
    Font getItemLabelFont(int row, int column);

    /**
     * Returns the font for all the item labels in a series.
     *
     * @param series the series index (zero-based).
     * @return The font (possibly {@code null}).
     * @see #setSeriesItemLabelFont(int, Font)
     */
    Font getSeriesItemLabelFont(int series);

    /**
     * Sets the item label font for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param font   the font ({@code null} permitted).
     * @see #getSeriesItemLabelFont(int)
     */
    void setSeriesItemLabelFont(int series, Font font);

    /**
     * Sets the item label font for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero based).
     * @param font   the font ({@code null} permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getSeriesItemLabelFont(int)
     */
    void setSeriesItemLabelFont(int series, Font font, boolean notify);

    /**
     * Returns the default item label font (this is used when no other font
     * setting is available).
     *
     * @return The font (never {@code null}).
     * @see #setDefaultItemLabelFont(Font)
     */
    Font getDefaultItemLabelFont();

    /**
     * Sets the default item label font and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param font the font ({@code null} not permitted).
     * @see #getDefaultItemLabelFont()
     */
    void setDefaultItemLabelFont(Font font);

    /**
     * Sets the base item label font and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param font   the font ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultItemLabelFont()
     */
    void setDefaultItemLabelFont(Font font, boolean notify);

    /**
     * Returns the paint used to draw an item label.
     *
     * @param row    the row index (zero based).
     * @param column the column index (zero based).
     * @return The paint (never {@code null}).
     */
    Paint getItemLabelPaint(int row, int column);

    /**
     * Returns the paint used to draw the item labels for a series.
     *
     * @param series the series index (zero based).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesItemLabelPaint(int, Paint)
     */
    Paint getSeriesItemLabelPaint(int series);

    /**
     * Sets the item label paint for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series (zero based index).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesItemLabelPaint(int)
     */
    void setSeriesItemLabelPaint(int series, Paint paint);

    /**
     * Sets the item label paint for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero based).
     * @param paint  the paint ({@code null} permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getSeriesItemLabelPaint(int)
     */
    void setSeriesItemLabelPaint(int series, Paint paint,
                                 boolean notify);

    /**
     * Returns the default item label paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultItemLabelPaint(Paint)
     */
    Paint getDefaultItemLabelPaint();

    /**
     * Sets the default item label paint and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultItemLabelPaint()
     */
    void setDefaultItemLabelPaint(Paint paint);

    /**
     * Sets the default item label paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners..
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultItemLabelPaint()
     */
    void setDefaultItemLabelPaint(Paint paint, boolean notify);

    /**
     * Returns the item label position for positive values.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #getNegativeItemLabelPosition(int, int)
     */
    ItemLabelPosition getPositiveItemLabelPosition(int row, int column);

    /**
     * Returns the item label position for all positive values in a series.
     *
     * @param series the series index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #setSeriesPositiveItemLabelPosition(int, ItemLabelPosition)
     */
    ItemLabelPosition getSeriesPositiveItemLabelPosition(int series);

    /**
     * Sets the item label position for all positive values in a series and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @see #getSeriesPositiveItemLabelPosition(int)
     */
    void setSeriesPositiveItemLabelPosition(int series,
                                            ItemLabelPosition position);

    /**
     * Sets the item label position for all positive values in a series and (if
     * requested) sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @param notify   notify registered listeners?
     * @see #getSeriesPositiveItemLabelPosition(int)
     */
    void setSeriesPositiveItemLabelPosition(int series,
                                            ItemLabelPosition position, boolean notify);

    /**
     * Returns the default positive item label position.
     *
     * @return The position (never {@code null}).
     * @see #setDefaultPositiveItemLabelPosition(ItemLabelPosition)
     */
    ItemLabelPosition getDefaultPositiveItemLabelPosition();

    /**
     * Sets the default positive item label position.
     *
     * @param position the position ({@code null} not permitted).
     * @see #getDefaultPositiveItemLabelPosition()
     */
    void setDefaultPositiveItemLabelPosition(
            ItemLabelPosition position);

    /**
     * Sets the default positive item label position and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @param notify   notify registered listeners?
     * @see #getDefaultPositiveItemLabelPosition()
     */
    void setDefaultPositiveItemLabelPosition(ItemLabelPosition position,
                                             boolean notify);

    /**
     * Returns the item label position for negative values.  This method can be
     * overridden to provide customisation of the item label position for
     * individual data items.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #getPositiveItemLabelPosition(int, int)
     */
    ItemLabelPosition getNegativeItemLabelPosition(int row, int column);

    /**
     * Returns the item label position for all negative values in a series.
     *
     * @param series the series index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #setSeriesNegativeItemLabelPosition(int, ItemLabelPosition)
     */
    ItemLabelPosition getSeriesNegativeItemLabelPosition(int series);

    /**
     * Sets the item label position for negative values in a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @see #getSeriesNegativeItemLabelPosition(int)
     */
    void setSeriesNegativeItemLabelPosition(int series,
                                            ItemLabelPosition position);

    /**
     * Sets the item label position for negative values in a series and (if
     * requested) sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @param notify   notify registered listeners?
     * @see #getSeriesNegativeItemLabelPosition(int)
     */
    void setSeriesNegativeItemLabelPosition(int series,
                                            ItemLabelPosition position, boolean notify);

    /**
     * Returns the base item label position for negative values.
     *
     * @return The position (never {@code null}).
     * @see #setDefaultNegativeItemLabelPosition(ItemLabelPosition)
     */
    ItemLabelPosition getDefaultNegativeItemLabelPosition();

    /**
     * Sets the default item label position for negative values and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @see #getDefaultNegativeItemLabelPosition()
     */
    void setDefaultNegativeItemLabelPosition(
            ItemLabelPosition position);

    /**
     * Sets the default negative item label position and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @param notify   notify registered listeners?
     * @see #getDefaultNegativeItemLabelPosition()
     */
    void setDefaultNegativeItemLabelPosition(ItemLabelPosition position,
                                             boolean notify);

    /**
     * Returns the item label anchor offset.
     *
     * @return The offset.
     * @see #setItemLabelAnchorOffset(double)
     */
    double getItemLabelAnchorOffset();

    /**
     * Sets the item label anchor offset.
     *
     * @param offset the offset.
     * @see #getItemLabelAnchorOffset()
     */
    void setItemLabelAnchorOffset(double offset);

    /**
     * Returns {@code true} if an item label is visible, and
     * {@code false} otherwise.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return A boolean.
     */
    boolean isItemLabelVisible(int row, int column);

    /**
     * Returns {@code true} if the item labels for a series are visible,
     * and {@code false} otherwise.
     *
     * @param series the series index (zero-based).
     * @return A boolean.
     */
    boolean isSeriesItemLabelsVisible(int series);

    /**
     * Sets a flag that controls the visibility of the item labels for a series,
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag.
     */
    void setSeriesItemLabelsVisible(int series, boolean visible);

    /**
     * Sets the visibility of the item labels for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     */
    void setSeriesItemLabelsVisible(int series, Boolean visible);

    /**
     * Sets the visibility of item labels for a series and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the visible flag.
     * @param notify  a flag that controls whether or not listeners are
     *                notified.
     */
    void setSeriesItemLabelsVisible(int series, Boolean visible,
                                    boolean notify);

    /**
     * Returns the base setting for item label visibility.  A {@code null}
     * result should be interpreted as equivalent to {@code Boolean.FALSE}.
     *
     * @return A flag (possibly {@code null}).
     * @see #setDefaultItemLabelsVisible(boolean)
     */
    boolean getDefaultItemLabelsVisible();

    /**
     * Sets the base flag that controls whether or not item labels are visible,
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultItemLabelsVisible()
     */
    void setDefaultItemLabelsVisible(boolean visible);

    /**
     * Sets the base visibility for item labels and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag ({@code null} is permitted, and viewed
     *                as equivalent to {@code Boolean.FALSE}).
     * @param notify  a flag that controls whether or not listeners are
     *                notified.
     * @see #getDefaultItemLabelsVisible()
     */
    void setDefaultItemLabelsVisible(boolean visible, boolean notify);
}

