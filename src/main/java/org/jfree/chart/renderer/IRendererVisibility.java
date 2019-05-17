package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;

public interface IRendererVisibility {

    //// VISIBLE //////////////////////////////////////////////////////////////

    /**
     * Returns a boolean that indicates whether or not the specified item
     * should be drawn (this is typically used to hide an entire series).
     *
     * @param series the series index.
     * @param item   the item index.
     * @return A boolean.
     */
    boolean getItemVisible(int series, int item);

    /**
     * Returns a boolean that indicates whether or not the specified series
     * should be drawn (this is typically used to hide an entire series).
     *
     * @param series the series index.
     * @return A boolean.
     */
    boolean isSeriesVisible(int series);

    /**
     * Returns the flag that controls whether a series is visible.
     *
     * @param series the series index (zero-based).
     * @return The flag (possibly {@code null}).
     * @see #setSeriesVisible(int, Boolean)
     */
    Boolean getSeriesVisible(int series);

    /**
     * Sets the flag that controls whether a series is visible and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     * @see #getSeriesVisible(int)
     */
    void setSeriesVisible(int series, Boolean visible);

    /**
     * Sets the flag that controls whether a series is visible and, if
     * requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series  the series index.
     * @param visible the flag ({@code null} permitted).
     * @param notify  notify listeners?
     * @see #getSeriesVisible(int)
     */
    void setSeriesVisible(int series, Boolean visible, boolean notify);

    /**
     * Returns the default visibility for all series.
     *
     * @return The default visibility.
     * @see #setDefaultSeriesVisible(boolean)
     */
    boolean getDefaultSeriesVisible();

    /**
     * Sets the default visibility and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultSeriesVisible()
     */
    void setDefaultSeriesVisible(boolean visible);

    /**
     * Sets the default visibility and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the visibility.
     * @param notify  notify listeners?
     * @see #getDefaultSeriesVisible()
     */
    void setDefaultSeriesVisible(boolean visible, boolean notify);

    // SERIES VISIBLE IN LEGEND (not yet respected by all renderers)

    /**
     * Returns {@code true} if the series should be shown in the legend,
     * and {@code false} otherwise.
     *
     * @param series the series index.
     * @return A boolean.
     */
    boolean isSeriesVisibleInLegend(int series);

    /**
     * Returns the flag that controls whether a series is visible in the
     * legend.  This method returns only the "per series" settings - to
     * incorporate the override and base settings as well, you need to use the
     * {@link #isSeriesVisibleInLegend(int)} method.
     *
     * @param series the series index (zero-based).
     * @return The flag (possibly {@code null}).
     * @see #setSeriesVisibleInLegend(int, Boolean)
     */
    Boolean getSeriesVisibleInLegend(int series);

    /**
     * Sets the flag that controls whether a series is visible in the legend
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     * @see #getSeriesVisibleInLegend(int)
     */
    void setSeriesVisibleInLegend(int series, Boolean visible);

    /**
     * Sets the flag that controls whether a series is visible in the legend
     * and, if requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series  the series index.
     * @param visible the flag ({@code null} permitted).
     * @param notify  notify listeners?
     * @see #getSeriesVisibleInLegend(int)
     */
    void setSeriesVisibleInLegend(int series, Boolean visible,
                                  boolean notify);

    /**
     * Returns the default visibility in the legend for all series.
     *
     * @return The default visibility.
     * @see #setDefaultSeriesVisibleInLegend(boolean)
     */
    boolean getDefaultSeriesVisibleInLegend();

    /**
     * Sets the default visibility in the legend and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultSeriesVisibleInLegend()
     */
    void setDefaultSeriesVisibleInLegend(boolean visible);

    /**
     * Sets the default visibility in the legend and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the visibility.
     * @param notify  notify listeners?
     * @see #getDefaultSeriesVisibleInLegend()
     */
    void setDefaultSeriesVisibleInLegend(boolean visible, boolean notify);

}
