package org.jfree.chart.renderer;

import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.util.BooleanList;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PublicCloneable;

import java.io.Serializable;

// SERIES VISIBLE (not yet respected by all renderers)
// SERIES VISIBLE IN LEGEND (not yet respected by all renderers)
public class RenderStateVisibility implements Serializable, PublicCloneable, IRenderStateVisibility {
    private transient AbstractRenderer abstractRenderer;

    /**
     * A list of flags that controls whether or not each series is visible.
     */
    private BooleanList seriesVisibleList = new BooleanList();
    /**
     * The default visibility for all series.
     */
    private boolean defaultSeriesVisible = true;
    /**
     * A list of flags that controls whether or not each series is visible in
     * the legend.
     */
    private BooleanList seriesVisibleInLegendList = new BooleanList();
    /**
     * The default visibility for each series in the legend.
     */
    private boolean defaultSeriesVisibleInLegend = true;

    /**
     * Visibility of the item labels PER series.
     */
    private BooleanList itemLabelsVisibleList;

    /**
     * The base item labels visible.
     */
    private boolean defaultItemLabelsVisible;

    /**
     * A flag that controls whether or not the renderer will include the
     * non-visible series when calculating the data bounds.
     *
     * @since 1.0.13
     */
    private boolean dataBoundsIncludesVisibleSeriesOnly = true;

    public RenderStateVisibility(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;

        this.itemLabelsVisibleList = new BooleanList();
        this.defaultItemLabelsVisible = false;
    }

    public void setAbstractRenderer(AbstractRenderer abstractRenderer) {
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void setDefaultSeriesVisibleInLegend(boolean visible,
                                                boolean notify) {
        this.defaultSeriesVisibleInLegend = visible;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns {@code true} if an item label is visible, and
     * {@code false} otherwise.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return A boolean.
     */
    @Override
    public boolean isItemLabelVisible(int row, int column) {
        return isSeriesItemLabelsVisible(row);
    }

    /**
     * Returns {@code true} if the item labels for a series are visible,
     * and {@code false} otherwise.
     *
     * @param series the series index (zero-based).
     * @return A boolean.
     */
    @Override
    public boolean isSeriesItemLabelsVisible(int series) {
        Boolean b = this.itemLabelsVisibleList.getBoolean(series);
        if (b == null) {
            return this.defaultItemLabelsVisible;
        }
        return b;
    }

    /**
     * Sets a flag that controls the visibility of the item labels for a series,
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag.
     */
    @Override
    public void setSeriesItemLabelsVisible(int series, boolean visible) {
        setSeriesItemLabelsVisible(series, Boolean.valueOf(visible));
    }

    /**
     * Sets the visibility of the item labels for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the flag ({@code null} permitted).
     */
    @Override
    public void setSeriesItemLabelsVisible(int series, Boolean visible) {
        setSeriesItemLabelsVisible(series, visible, true);
    }

    /**
     * Sets the visibility of item labels for a series and, if requested, sends
     * a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param visible the visible flag.
     * @param notify  a flag that controls whether or not listeners are
     *                notified.
     */
    @Override
    public void setSeriesItemLabelsVisible(int series, Boolean visible,
                                           boolean notify) {
        this.itemLabelsVisibleList.setBoolean(series, visible);
        if (notify) {
            this.abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the base setting for item label visibility.  A {@code null}
     * result should be interpreted as equivalent to {@code Boolean.FALSE}.
     *
     * @return A flag (possibly {@code null}).
     * @see #setDefaultItemLabelsVisible(boolean)
     */
    @Override
    public boolean getDefaultItemLabelsVisible() {
        return this.defaultItemLabelsVisible;
    }

    /**
     * Sets the base flag that controls whether or not item labels are visible,
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visible the flag.
     * @see #getDefaultItemLabelsVisible()
     */
    @Override
    public void setDefaultItemLabelsVisible(boolean visible) {
        setDefaultItemLabelsVisible(visible, true);
    }

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
    @Override
    public void setDefaultItemLabelsVisible(boolean visible, boolean notify) {
        this.defaultItemLabelsVisible = visible;
        if (notify) {
            this.abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the data bounds reported
     * by this renderer will exclude non-visible series.
     *
     * @return A boolean.
     * @since 1.0.13
     */
    @Override
    public boolean getDataBoundsIncludesVisibleSeriesOnly() {
        return this.dataBoundsIncludesVisibleSeriesOnly;
    }

    /**
     * Sets the flag that controls whether or not the data bounds reported
     * by this renderer will exclude non-visible series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param visibleOnly include only visible series.
     * @since 1.0.13
     */
    @Override
    public void setDataBoundsIncludesVisibleSeriesOnly(boolean visibleOnly) {
        this.dataBoundsIncludesVisibleSeriesOnly = visibleOnly;
        this.abstractRenderer.notifyListeners(new RendererChangeEvent(this, true));
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

        // 'itemLabelsVisible' : immutable, no need to clone reference
        if (this.itemLabelsVisibleList != null) {
            clone.itemLabelsVisibleList
                    = (BooleanList) this.itemLabelsVisibleList.clone();
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
        if (this.dataBoundsIncludesVisibleSeriesOnly
                != that.dataBoundsIncludesVisibleSeriesOnly) {
            return false;
        }
        if (!ObjectUtils.equal(this.itemLabelsVisibleList,
                that.itemLabelsVisibleList)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultItemLabelsVisible,
                that.defaultItemLabelsVisible)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = HashUtils.hashCode(result, this.seriesVisibleList);
        result = HashUtils.hashCode(result, this.defaultSeriesVisible);
        result = HashUtils.hashCode(result, this.seriesVisibleInLegendList);
        result = HashUtils.hashCode(result, this.defaultSeriesVisibleInLegend);
        result = HashUtils.hashCode(result, this.itemLabelsVisibleList);
        result = HashUtils.hashCode(result, this.defaultItemLabelsVisible);
        return result;
    }
}