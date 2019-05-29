package org.jfree.chart.renderer;

import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.util.*;

import java.awt.*;
import java.io.Serializable;

// PAINT
// FILL PAINT
// OUTLINE PAINT
public class RenderStatePaint implements Serializable, PublicCloneable, IRenderStatePaint {
    private transient AbstractRenderer abstractRenderer;

    /**
     * The default paint.
     */
    public static final Paint DEFAULT_PAINT = Color.BLUE;
    /**
     * The default outline paint.
     */
    public static final Paint DEFAULT_OUTLINE_PAINT = Color.GRAY;
    /**
     * The paint list.
     */
    private PaintList paintList;
    /**
     * A flag that controls whether or not the paintList is auto-populated
     * in the {@link #lookupSeriesPaint(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesPaint;
    /**
     * The default paint, used when there is no paint assigned for a series.
     */
    private transient Paint defaultPaint;
    /**
     * The fill paint list.
     */
    private PaintList fillPaintList;
    /**
     * A flag that controls whether or not the fillPaintList is auto-populated
     * in the {@link #lookupSeriesFillPaint(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesFillPaint;
    /**
     * The base fill paint.
     */
    private transient Paint defaultFillPaint;
    /**
     * The outline paint list.
     */
    private PaintList outlinePaintList;
    /**
     * A flag that controls whether or not the outlinePaintList is
     * auto-populated in the {@link #lookupSeriesOutlinePaint(int)} method.
     *
     * @since 1.0.6
     */
    private boolean autoPopulateSeriesOutlinePaint;
    /**
     * The base outline paint.
     */
    private transient Paint defaultOutlinePaint;

    public RenderStatePaint(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;

        this.paintList = new PaintList();
        this.defaultPaint = RenderStatePaint.DEFAULT_PAINT;
        this.autoPopulateSeriesPaint = true;

        this.fillPaintList = new PaintList();
        this.defaultFillPaint = Color.WHITE;
        this.autoPopulateSeriesFillPaint = false;

        this.outlinePaintList = new PaintList();
        this.defaultOutlinePaint = RenderStatePaint.DEFAULT_OUTLINE_PAINT;
        this.autoPopulateSeriesOutlinePaint = false;
    }

    public void setAbstractRenderer(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;
    }

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
    @Override
    public Paint getItemPaint(int row, int column) {
        return lookupSeriesPaint(row);
    }

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series index (zero-based).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Paint lookupSeriesPaint(int series) {

        Paint seriesPaint = getSeriesPaint(series);
        if (seriesPaint == null && this.autoPopulateSeriesPaint) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                seriesPaint = supplier.getNextPaint();
                setSeriesPaint(series, seriesPaint, false);
            }
        }
        if (seriesPaint == null) {
            seriesPaint = this.defaultPaint;
        }
        return seriesPaint;

    }

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series index (zero-based).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesPaint(int, Paint)
     */
    @Override
    public Paint getSeriesPaint(int series) {
        return this.paintList.getPaint(series);
    }

    /**
     * Sets the paint used for a series and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesPaint(int)
     */
    @Override
    public void setSeriesPaint(int series, Paint paint) {
        setSeriesPaint(series, paint, true);
    }

    /**
     * Sets the paint used for a series and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesPaint(int)
     */
    @Override
    public void setSeriesPaint(int series, Paint paint, boolean notify) {
        this.paintList.setPaint(series, paint);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Clears the series paint settings for this renderer and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param notify notify listeners?
     * @since 1.0.11
     */
    @Override
    public void clearSeriesPaints(boolean notify) {
        this.paintList.clear();
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default paint.
     *
     * @return The default paint (never {@code null}).
     * @see #setDefaultPaint(Paint)
     */
    @Override
    public Paint getDefaultPaint() {
        return this.defaultPaint;
    }

    /**
     * Sets the default paint and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultPaint()
     */
    @Override
    public void setDefaultPaint(Paint paint) {
        // defer argument checking...
        setDefaultPaint(paint, true);
    }

    /**
     * Sets the default paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultPaint()
     */
    @Override
    public void setDefaultPaint(Paint paint, boolean notify) {
        this.defaultPaint = paint;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series paint list is
     * automatically populated when {@link #lookupSeriesPaint(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesPaint(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesPaint() {
        return this.autoPopulateSeriesPaint;
    }

    /**
     * Sets the flag that controls whether or not the series paint list is
     * automatically populated when {@link #lookupSeriesPaint(int)} is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesPaint()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesPaint(boolean auto) {
        this.autoPopulateSeriesPaint = auto;
    }

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
    @Override
    public Paint getItemFillPaint(int row, int column) {
        return lookupSeriesFillPaint(row);
    }

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Paint lookupSeriesFillPaint(int series) {

        Paint seriesFillPaint = getSeriesFillPaint(series);
        if (seriesFillPaint == null && this.autoPopulateSeriesFillPaint) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                seriesFillPaint = supplier.getNextFillPaint();
                setSeriesFillPaint(series, seriesFillPaint, false);
            }
        }
        if (seriesFillPaint == null) {
            seriesFillPaint = this.defaultFillPaint;
        }
        return seriesFillPaint;

    }

    /**
     * Returns the paint used to fill an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @see #setSeriesFillPaint(int, Paint)
     */
    @Override
    public Paint getSeriesFillPaint(int series) {
        return this.fillPaintList.getPaint(series);
    }

    /**
     * Sets the paint used for a series fill and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesFillPaint(int)
     */
    @Override
    public void setSeriesFillPaint(int series, Paint paint) {
        setSeriesFillPaint(series, paint, true);
    }

    /**
     * Sets the paint used to fill a series and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesFillPaint(int)
     */
    @Override
    public void setSeriesFillPaint(int series, Paint paint, boolean notify) {
        this.fillPaintList.setPaint(series, paint);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default fill paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultFillPaint(Paint)
     */
    @Override
    public Paint getDefaultFillPaint() {
        return this.defaultFillPaint;
    }

    /**
     * Sets the default fill paint and sends a {@link RendererChangeEvent} to
     * all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultFillPaint()
     */
    @Override
    public void setDefaultFillPaint(Paint paint) {
        // defer argument checking...
        setDefaultFillPaint(paint, true);
    }

    /**
     * Sets the default fill paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultFillPaint()
     */
    @Override
    public void setDefaultFillPaint(Paint paint, boolean notify) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultFillPaint = paint;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series fill paint list
     * is automatically populated when {@link #lookupSeriesFillPaint(int)} is
     * called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesFillPaint(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesFillPaint() {
        return this.autoPopulateSeriesFillPaint;
    }

    /**
     * Sets the flag that controls whether or not the series fill paint list is
     * automatically populated when {@link #lookupSeriesFillPaint(int)} is
     * called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesFillPaint()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesFillPaint(boolean auto) {
        this.autoPopulateSeriesFillPaint = auto;
    }

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
    @Override
    public Paint getItemOutlinePaint(int row, int column) {
        return lookupSeriesOutlinePaint(row);
    }

    /**
     * Returns the paint used to outline an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (never {@code null}).
     * @since 1.0.6
     */
    @Override
    public Paint lookupSeriesOutlinePaint(int series) {

        Paint seriesOutlinePaint = getSeriesOutlinePaint(series);
        if (seriesOutlinePaint == null && this.autoPopulateSeriesOutlinePaint) {
            DrawingSupplier supplier = abstractRenderer.getDrawingSupplier();
            if (supplier != null) {
                seriesOutlinePaint = supplier.getNextOutlinePaint();
                setSeriesOutlinePaint(series, seriesOutlinePaint, false);
            }
        }
        if (seriesOutlinePaint == null) {
            seriesOutlinePaint = this.defaultOutlinePaint;
        }
        return seriesOutlinePaint;

    }

    /**
     * Returns the paint used to outline an item drawn by the renderer.
     *
     * @param series the series (zero-based index).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesOutlinePaint(int, Paint)
     */
    @Override
    public Paint getSeriesOutlinePaint(int series) {
        return this.outlinePaintList.getPaint(series);
    }

    /**
     * Sets the paint used for a series outline and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesOutlinePaint(int)
     */
    @Override
    public void setSeriesOutlinePaint(int series, Paint paint) {
        setSeriesOutlinePaint(series, paint, true);
    }

    /**
     * Sets the paint used to draw the outline for a series and, if requested,
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesOutlinePaint(int)
     */
    @Override
    public void setSeriesOutlinePaint(int series, Paint paint, boolean notify) {
        this.outlinePaintList.setPaint(series, paint);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default outline paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultOutlinePaint(Paint)
     */
    @Override
    public Paint getDefaultOutlinePaint() {
        return this.defaultOutlinePaint;
    }

    /**
     * Sets the default outline paint and sends a {@link RendererChangeEvent} to
     * all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultOutlinePaint()
     */
    @Override
    public void setDefaultOutlinePaint(Paint paint) {
        // defer argument checking...
        setDefaultOutlinePaint(paint, true);
    }

    /**
     * Sets the default outline paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify notify listeners?
     * @see #getDefaultOutlinePaint()
     */
    @Override
    public void setDefaultOutlinePaint(Paint paint, boolean notify) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultOutlinePaint = paint;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the flag that controls whether or not the series outline paint
     * list is automatically populated when
     * {@link #lookupSeriesOutlinePaint(int)} is called.
     *
     * @return A boolean.
     * @see #setAutoPopulateSeriesOutlinePaint(boolean)
     * @since 1.0.6
     */
    @Override
    public boolean getAutoPopulateSeriesOutlinePaint() {
        return this.autoPopulateSeriesOutlinePaint;
    }

    /**
     * Sets the flag that controls whether or not the series outline paint list
     * is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
     * is called.
     *
     * @param auto the new flag value.
     * @see #getAutoPopulateSeriesOutlinePaint()
     * @since 1.0.6
     */
    @Override
    public void setAutoPopulateSeriesOutlinePaint(boolean auto) {
        this.autoPopulateSeriesOutlinePaint = auto;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RenderStatePaint clone = (RenderStatePaint) super.clone();

        // 'paint' : immutable, no need to clone reference
        if (this.paintList != null) {
            clone.paintList = (PaintList) this.paintList.clone();
        }
        // 'basePaint' : immutable, no need to clone reference

        if (this.fillPaintList != null) {
            clone.fillPaintList = (PaintList) this.fillPaintList.clone();
        }
        // 'outlinePaint' : immutable, no need to clone reference
        if (this.outlinePaintList != null) {
            clone.outlinePaintList = (PaintList) this.outlinePaintList.clone();
        }
        // 'baseOutlinePaint' : immutable, no need to clone reference

        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RenderStatePaint)) {
            return false;
        }
        RenderStatePaint that = (RenderStatePaint) obj;

        if (!ObjectUtils.equal(this.paintList, that.paintList)) {
            return false;
        }
        if (!PaintUtils.equal(this.getDefaultPaint(), that.getDefaultPaint())) {
            return false;
        }
        if (!ObjectUtils.equal(this.fillPaintList, that.fillPaintList)) {
            return false;
        }
        if (!PaintUtils.equal(this.getDefaultFillPaint(),
                that.getDefaultFillPaint())) {
            return false;
        }
        if (!ObjectUtils.equal(this.outlinePaintList,
                that.outlinePaintList)) {
            return false;
        }
        if (!PaintUtils.equal(this.getDefaultOutlinePaint(),
                that.getDefaultOutlinePaint())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = HashUtils.hashCode(result, this.paintList);
        result = HashUtils.hashCode(result, this.getDefaultPaint());
        result = HashUtils.hashCode(result, this.fillPaintList);
        result = HashUtils.hashCode(result, this.getDefaultFillPaint());
        result = HashUtils.hashCode(result, this.outlinePaintList);
        result = HashUtils.hashCode(result, this.getDefaultOutlinePaint());
        return result;
    }
}

