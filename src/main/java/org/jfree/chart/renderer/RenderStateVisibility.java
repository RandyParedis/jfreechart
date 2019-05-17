package org.jfree.chart.renderer;

import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.util.BooleanList;

import java.io.Serializable;

// SERIES VISIBLE (not yet respected by all renderers)
// SERIES VISIBLE IN LEGEND (not yet respected by all renderers)
public class RenderStateVisibility implements Serializable, Cloneable, IRendererVisibility {
    private final AbstractRenderer abstractRenderer;
    /**
     * A list of flags that controls whether or not each series is visible.
     */
    public BooleanList seriesVisibleList = new BooleanList();
    /**
     * The default visibility for all series.
     */
    public boolean defaultSeriesVisible = true;
    /**
     * A list of flags that controls whether or not each series is visible in
     * the legend.
     */
    public BooleanList seriesVisibleInLegendList = new BooleanList();
    /**
     * The default visibility for each series in the legend.
     */
    public boolean defaultSeriesVisibleInLegend = true;

    public RenderStateVisibility(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;
    }

    /**
     * Returns a boolean that indicates whether or not the specified item
     * should be drawn.
     *
     * @param series the series index.
     * @param item   the item index.
     * @return A boolean.
     */
    public boolean getItemVisible(int series, int item) {
        return isSeriesVisible(series);
    }

    /**
     * Returns a boolean that indicates whether or not the specified series
     * should be drawn.  In fact this method should be named
     * lookupSeriesVisible() to be consistent with the other series
     * attributes and avoid confusion with the getSeriesVisible() method.
     *
     * @param series the series index.
     * @return A boolean.
     */
    public boolean isSeriesVisible(int series) {
        boolean result = this.defaultSeriesVisible;
        Boolean b = this.seriesVisibleList.getBoolean(series);
        if (b != null) {
            result = b;
        }
        return result;
    }

    /**
     * Returns the flag that controls whether a series is visible.
     *
     * @param series the series index (zero-based).
     * @return The flag (possibly {@code null}).
     * @see #setSeriesVisible(int, Boolean)
     */
    public Boolean getSeriesVisible(int series) {
        return this.seriesVisibleList.getBoolean(series);
    }

    /**
     * Sets the flag that controls whether a series is visible and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     * @see #getSeriesVisible(int)
     */
    public void setSeriesVisible(int series, Boolean visible) {
        setSeriesVisible(series, visible, true);
    }

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
    public void setSeriesVisible(int series, Boolean visible, boolean notify) {
        this.seriesVisibleList.setBoolean(series, visible);
        if (notify) {
            // we create an event with a special flag set...the purpose of
            // this is to communicate to the plot (the default receiver of
            // the event) that series visibility has changed so the axis
            // ranges might need updating...
            RendererChangeEvent e = new RendererChangeEvent(abstractRenderer, true);
            abstractRenderer.notifyListeners(e);
        }
    }

    /**
     * Returns the default visibility for all series.
     *
     * @return The default visibility.
     * @see #setDefaultSeriesVisible(boolean)
     */
    public boolean getDefaultSeriesVisible() {
        return this.defaultSeriesVisible;
    }

    /**
     * Sets the default series visibility and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultSeriesVisible()
     */
    public void setDefaultSeriesVisible(boolean visible) {
        // defer argument checking...
        setDefaultSeriesVisible(visible, true);
    }

    /**
     * Sets the default series visibility and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the visibility.
     * @param notify  notify listeners?
     * @see #getDefaultSeriesVisible()
     */
    public void setDefaultSeriesVisible(boolean visible, boolean notify) {
        this.defaultSeriesVisible = visible;
        if (notify) {
            // we create an event with a special flag set...the purpose of
            // this is to communicate to the plot (the default receiver of
            // the event) that series visibility has changed so the axis
            // ranges might need updating...
            RendererChangeEvent e = new RendererChangeEvent(abstractRenderer, true);
            abstractRenderer.notifyListeners(e);
        }
    }

    /**
     * Returns {@code true} if the series should be shown in the legend,
     * and {@code false} otherwise.
     *
     * @param series the series index.
     * @return A boolean.
     */
    public boolean isSeriesVisibleInLegend(int series) {
        boolean result = this.defaultSeriesVisibleInLegend;
        Boolean b = this.seriesVisibleInLegendList.getBoolean(series);
        if (b != null) {
            result = b;
        }
        return result;
    }

    /**
     * Returns the flag that controls whether a series is visible in the
     * legend.  This method returns only the "per series" settings - to
     * incorporate the default settings as well, you need to use the
     * {@link #isSeriesVisibleInLegend(int)} method.
     *
     * @param series the series index (zero-based).
     * @return The flag (possibly {@code null}).
     * @see #setSeriesVisibleInLegend(int, Boolean)
     */
    public Boolean getSeriesVisibleInLegend(int series) {
        return this.seriesVisibleInLegendList.getBoolean(series);
    }

    /**
     * Sets the flag that controls whether a series is visible in the legend
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     * @see #getSeriesVisibleInLegend(int)
     */
    public void setSeriesVisibleInLegend(int series, Boolean visible) {
        setSeriesVisibleInLegend(series, visible, true);
    }

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
    public void setSeriesVisibleInLegend(int series, Boolean visible,
                                         boolean notify) {
        this.seriesVisibleInLegendList.setBoolean(series, visible);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default visibility in the legend for all series.
     *
     * @return The default visibility.
     * @see #setDefaultSeriesVisibleInLegend(boolean)
     */
    public boolean getDefaultSeriesVisibleInLegend() {
        return this.defaultSeriesVisibleInLegend;
    }

    /**
     * Sets the default visibility in the legend and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultSeriesVisibleInLegend()
     */
    public void setDefaultSeriesVisibleInLegend(boolean visible) {
        // defer argument checking...
        setDefaultSeriesVisibleInLegend(visible, true);
    }

    /**
     * Sets the default visibility in the legend and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the visibility.
     * @param notify  notify listeners?
     * @see #getDefaultSeriesVisibleInLegend()
     */
    public void setDefaultSeriesVisibleInLegend(boolean visible,
                                                boolean notify) {
        this.defaultSeriesVisibleInLegend = visible;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RenderStateVisibility clone = (RenderStateVisibility) super.clone();

        if (this.seriesVisibleList != null) {
            clone.seriesVisibleList = (BooleanList) this.seriesVisibleList.clone();
        }

        if (this.seriesVisibleInLegendList != null) {
            clone.seriesVisibleInLegendList = (BooleanList) this.seriesVisibleInLegendList.clone();
        }

        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RenderStateVisibility)) {
            return false;
        }
        RenderStateVisibility that = (RenderStateVisibility) obj;

        if (!this.seriesVisibleList.equals(that.seriesVisibleList)) {
            return false;
        }
        if (this.getDefaultSeriesVisible() != that.getDefaultSeriesVisible()) {
            return false;
        }
        if (!this.seriesVisibleInLegendList.equals(
                that.seriesVisibleInLegendList)) {
            return false;
        }
        if (this.getDefaultSeriesVisibleInLegend()
                != that.getDefaultSeriesVisibleInLegend()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = HashUtils.hashCode(result, this.seriesVisibleList);
        result = HashUtils.hashCode(result, this.getDefaultSeriesVisible());
        result = HashUtils.hashCode(result, this.seriesVisibleInLegendList);
        result = HashUtils.hashCode(result, this.getDefaultSeriesVisibleInLegend());
        return result;
    }
}