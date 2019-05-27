package org.jfree.chart.renderer;

import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.util.*;

import java.awt.*;
import java.io.Serializable;

// STROKE
// OUTLINE STROKE
public class RenderStateStroke implements Serializable, PublicCloneable, IRenderStateStroke {
    private transient AbstractRenderer abstractRenderer;

    /**
     * The default stroke.
     */
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
    /**
     * The default outline stroke.
     */
    public static final Stroke DEFAULT_OUTLINE_STROKE = new BasicStroke(1.0f);
    /**
     * The stroke list.
     */
    private StrokeList strokeList;
    /**
     * A flag that controls whether or not the strokeList is auto-populated
     * in the {@link #lookupSeriesStroke(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesStroke;
    /**
     * The base stroke.
     */
    private transient Stroke defaultStroke;
    /**
     * The outline stroke list.
     */
    private StrokeList outlineStrokeList;
    /**
     * The base outline stroke.
     */
    private transient Stroke defaultOutlineStroke;
    /**
     * A flag that controls whether or not the outlineStrokeList is
     * auto-populated in the {@link #lookupSeriesOutlineStroke(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesOutlineStroke;

    public RenderStateStroke(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;

        this.strokeList = new StrokeList();
        this.defaultStroke = DEFAULT_STROKE;
        this.autoPopulateSeriesStroke = true;

        this.outlineStrokeList = new StrokeList();
        this.defaultOutlineStroke = DEFAULT_OUTLINE_STROKE;
        this.autoPopulateSeriesOutlineStroke = false;
    }

    public void setAbstractRenderer(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;
    }

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
    @Override
    public Stroke getItemStroke(int row, int column) {
        return lookupSeriesStroke(row);
    }

    /**
     * Returns the stroke used to draw the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Stroke lookupSeriesStroke(int series) {

        Stroke result = getSeriesStroke(series);
        if (result == null && this.autoPopulateSeriesStroke) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                result = supplier.getNextStroke();
                setSeriesStroke(series, result, false);
            }
        }
        if (result == null) {
            result = this.defaultStroke;
        }
        return result;

    }

    /**
     * Returns the stroke used to draw the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (possibly {@code null}).
     * @see #setSeriesStroke(int, Stroke)
     */
    @Override
    public Stroke getSeriesStroke(int series) {
        return this.strokeList.getStroke(series);
    }

    /**
     * Sets the stroke used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @see #getSeriesStroke(int)
     */
    @Override
    public void setSeriesStroke(int series, Stroke stroke) {
        setSeriesStroke(series, stroke, true);
    }

    /**
     * Sets the stroke for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesStroke(int)
     */
    @Override
    public void setSeriesStroke(int series, Stroke stroke, boolean notify) {
        this.strokeList.setStroke(series, stroke);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Clears the series stroke settings for this renderer and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param notify notify listeners?
     * @since 1.0.11
     */
    @Override
    public void clearSeriesStrokes(boolean notify) {
        this.strokeList.clear();
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default stroke.
     *
     * @return The default stroke (never {@code null}).
     * @see #setDefaultStroke(Stroke)
     */
    @Override
    public Stroke getDefaultStroke() {
        return this.defaultStroke;
    }

    /**
     * Sets the default stroke and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @see #getDefaultStroke()
     */
    @Override
    public void setDefaultStroke(Stroke stroke) {
        // defer argument checking...
        setDefaultStroke(stroke, true);
    }

    /**
     * Sets the base stroke and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultStroke()
     */
    @Override
    public void setDefaultStroke(Stroke stroke, boolean notify) {
        Args.nullNotPermitted(stroke, "stroke");
        this.defaultStroke = stroke;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series stroke list is
     * automatically populated when {@link #lookupSeriesStroke(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesStroke(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesStroke() {
        return this.autoPopulateSeriesStroke;
    }

    /**
     * Sets the flag that controls whether or not the series stroke list is
     * automatically populated when {@link #lookupSeriesStroke(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesStroke()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesStroke(boolean auto) {
        this.autoPopulateSeriesStroke = auto;
    }

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
    @Override
    public Stroke getItemOutlineStroke(int row, int column) {
        return lookupSeriesOutlineStroke(row);
    }

    /**
     * Returns the stroke used to outline the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Stroke lookupSeriesOutlineStroke(int series) {

        Stroke result = getSeriesOutlineStroke(series);
        if (result == null && this.autoPopulateSeriesOutlineStroke) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                result = supplier.getNextOutlineStroke();
                setSeriesOutlineStroke(series, result, false);
            }
        }
        if (result == null) {
            result = this.defaultOutlineStroke;
        }
        return result;

    }

    /**
     * Returns the stroke used to outline the items in a series.
     *
     * @param series the series (zero-based index).
     * @return The stroke (possibly {@code null}).
     * @see #setSeriesOutlineStroke(int, Stroke)
     */
    @Override
    public Stroke getSeriesOutlineStroke(int series) {
        return this.outlineStrokeList.getStroke(series);
    }

    /**
     * Sets the outline stroke used for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke ({@code null} permitted).
     * @see #getSeriesOutlineStroke(int)
     */
    @Override
    public void setSeriesOutlineStroke(int series, Stroke stroke) {
        setSeriesOutlineStroke(series, stroke, true);
    }

    /**
     * Sets the outline stroke for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param stroke the stroke ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesOutlineStroke(int)
     */
    @Override
    public void setSeriesOutlineStroke(int series, Stroke stroke,
                                       boolean notify) {
        this.outlineStrokeList.setStroke(series, stroke);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default outline stroke.
     *
     * @return The stroke (never {@code null}).
     * @see #setDefaultOutlineStroke(Stroke)
     */
    @Override
    public Stroke getDefaultOutlineStroke() {
        return this.defaultOutlineStroke;
    }

    /**
     * Sets the default outline stroke and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @see #getDefaultOutlineStroke()
     */
    @Override
    public void setDefaultOutlineStroke(Stroke stroke) {
        setDefaultOutlineStroke(stroke, true);
    }

    /**
     * Sets the default outline stroke and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultOutlineStroke()
     */
    @Override
    public void setDefaultOutlineStroke(Stroke stroke, boolean notify) {
        Args.nullNotPermitted(stroke, "stroke");
        this.defaultOutlineStroke = stroke;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series outline stroke
     * list is automatically populated when
     * {@link #lookupSeriesOutlineStroke(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesOutlineStroke(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesOutlineStroke() {
        return this.autoPopulateSeriesOutlineStroke;
    }

    /**
     * Sets the flag that controls whether or not the series outline stroke list
     * is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
     * is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesOutlineStroke()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesOutlineStroke(boolean auto) {
        this.autoPopulateSeriesOutlineStroke = auto;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        RenderStateStroke clone = (RenderStateStroke) super.clone();

        // 'stroke' : immutable, no need to clone reference
        if (this.strokeList != null) {
            clone.strokeList = (StrokeList) this.strokeList.clone();
        }
        // 'baseStroke' : immutable, no need to clone reference

        // 'outlineStroke' : immutable, no need to clone reference
        if (this.outlineStrokeList != null) {
            clone.outlineStrokeList = (StrokeList) this.outlineStrokeList.clone();
        }
        // 'baseOutlineStroke' : immutable, no need to clone reference


        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RenderStateStroke)) {
            return false;
        }
        RenderStateStroke that = (RenderStateStroke) obj;

        if (!ObjectUtils.equal(this.strokeList, that.strokeList)) {
            return false;
        }
        if (!ObjectUtils.equal(this.getDefaultStroke(), that.getDefaultStroke())) {
            return false;
        }
        if (!ObjectUtils.equal(this.outlineStrokeList,
                that.outlineStrokeList)) {
            return false;
        }
        if (!ObjectUtils.equal(this.getDefaultOutlineStroke(),
                that.getDefaultOutlineStroke())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = HashUtils.hashCode(result, this.strokeList);
        result = HashUtils.hashCode(result, this.getDefaultStroke());
        result = HashUtils.hashCode(result, this.outlineStrokeList);
        result = HashUtils.hashCode(result, this.getDefaultOutlineStroke());
        return result;
    }
}