package org.jfree.chart.renderer;

import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.*;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RenderStateItem implements Serializable, PublicCloneable, IRendererItem {
    private transient AbstractRenderer abstractRenderer;

    /**
     * The item label font list (one font per series).
     */
    private Map<Integer, Font> itemLabelFontMap;
    /**
     * The base item label font.
     */
    private Font defaultItemLabelFont;
    /**
     * The item label paint list (one paint per series).
     */
    private PaintList itemLabelPaintList;
    /**
     * The base item label paint.
     */
    private transient Paint defaultItemLabelPaint;
    /**
     * The positive item label position (per series).
     */
    private Map<Integer, ItemLabelPosition> positiveItemLabelPositionMap;
    /**
     * The fallback positive item label position.
     */
    private ItemLabelPosition defaultPositiveItemLabelPosition;
    /**
     * The negative item label position (per series).
     */
    private Map<Integer, ItemLabelPosition> negativeItemLabelPositionMap;
    /**
     * The fallback negative item label position.
     */
    private ItemLabelPosition defaultNegativeItemLabelPosition;
    /**
     * The item label anchor offset.
     */
    private double itemLabelAnchorOffset = 2.0;

    public RenderStateItem(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;

        this.itemLabelFontMap = new HashMap<Integer, Font>();
        this.defaultItemLabelFont = new Font("SansSerif", Font.PLAIN, 10);

        this.itemLabelPaintList = new PaintList();
        this.defaultItemLabelPaint = Color.BLACK;

        this.positiveItemLabelPositionMap
                = new HashMap<Integer, ItemLabelPosition>();
        this.defaultPositiveItemLabelPosition = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);

        this.negativeItemLabelPositionMap
                = new HashMap<Integer, ItemLabelPosition>();
        this.defaultNegativeItemLabelPosition = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
    }

    public void setAbstractRenderer(AbstractRenderer abstractRenderer) {
        this.abstractRenderer = abstractRenderer;
    }

    /**
     * Returns the font for an item label.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The font (never {@code null}).
     */
    @Override
    public Font getItemLabelFont(int row, int column) {
        Font result = getSeriesItemLabelFont(row);
        if (result == null) {
            result = this.defaultItemLabelFont;
        }
        return result;
    }

    /**
     * Returns the font for all the item labels in a series.
     *
     * @param series the series index (zero-based).
     * @return The font (possibly {@code null}).
     * @see #setSeriesItemLabelFont(int, Font)
     */
    @Override
    public Font getSeriesItemLabelFont(int series) {
        return this.itemLabelFontMap.get(series);
    }

    /**
     * Sets the item label font for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param font   the font ({@code null} permitted).
     * @see #getSeriesItemLabelFont(int)
     */
    @Override
    public void setSeriesItemLabelFont(int series, Font font) {
        setSeriesItemLabelFont(series, font, true);
    }

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
    @Override
    public void setSeriesItemLabelFont(int series, Font font, boolean notify) {
        this.itemLabelFontMap.put(series, font);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default item label font (this is used when no other font
     * setting is available).
     *
     * @return The font (never {@code null}).
     * @see #setDefaultItemLabelFont(Font)
     */
    @Override
    public Font getDefaultItemLabelFont() {
        return this.defaultItemLabelFont;
    }

    /**
     * Sets the default item label font and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param font the font ({@code null} not permitted).
     * @see #getDefaultItemLabelFont()
     */
    @Override
    public void setDefaultItemLabelFont(Font font) {
        Args.nullNotPermitted(font, "font");
        setDefaultItemLabelFont(font, true);
    }

    /**
     * Sets the base item label font and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param font   the font ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultItemLabelFont()
     */
    @Override
    public void setDefaultItemLabelFont(Font font, boolean notify) {
        this.defaultItemLabelFont = font;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the paint used to draw an item label.
     *
     * @param row    the row index (zero based).
     * @param column the column index (zero based).
     * @return The paint (never {@code null}).
     */
    @Override
    public Paint getItemLabelPaint(int row, int column) {
        Paint result = getSeriesItemLabelPaint(row);
        if (result == null) {
            result = this.defaultItemLabelPaint;
        }
        return result;
    }

    /**
     * Returns the paint used to draw the item labels for a series.
     *
     * @param series the series index (zero based).
     * @return The paint (possibly {@code null}).
     * @see #setSeriesItemLabelPaint(int, Paint)
     */
    @Override
    public Paint getSeriesItemLabelPaint(int series) {
        return this.itemLabelPaintList.getPaint(series);
    }

    /**
     * Sets the item label paint for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series (zero based index).
     * @param paint  the paint ({@code null} permitted).
     * @see #getSeriesItemLabelPaint(int)
     */
    @Override
    public void setSeriesItemLabelPaint(int series, Paint paint) {
        setSeriesItemLabelPaint(series, paint, true);
    }

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
    @Override
    public void setSeriesItemLabelPaint(int series, Paint paint,
                                        boolean notify) {
        this.itemLabelPaintList.setPaint(series, paint);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default item label paint.
     *
     * @return The paint (never {@code null}).
     * @see #setDefaultItemLabelPaint(Paint)
     */
    @Override
    public Paint getDefaultItemLabelPaint() {
        return this.defaultItemLabelPaint;
    }

    /**
     * Sets the default item label paint and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param paint the paint ({@code null} not permitted).
     * @see #getDefaultItemLabelPaint()
     */
    @Override
    public void setDefaultItemLabelPaint(Paint paint) {
        // defer argument checking...
        setDefaultItemLabelPaint(paint, true);
    }

    /**
     * Sets the default item label paint and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners..
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param notify a flag that controls whether or not listeners are
     *               notified.
     * @see #getDefaultItemLabelPaint()
     */
    @Override
    public void setDefaultItemLabelPaint(Paint paint, boolean notify) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultItemLabelPaint = paint;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the item label position for positive values.
     *
     * @param row    the row (or series) index (zero-based).
     * @param column the column (or category) index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #getNegativeItemLabelPosition(int, int)
     */
    @Override
    public ItemLabelPosition getPositiveItemLabelPosition(int row, int column) {
        return getSeriesPositiveItemLabelPosition(row);
    }

    /**
     * Returns the item label position for all positive values in a series.
     *
     * @param series the series index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #setSeriesPositiveItemLabelPosition(int, ItemLabelPosition)
     */
    @Override
    public ItemLabelPosition getSeriesPositiveItemLabelPosition(int series) {
        // otherwise look up the position table
        ItemLabelPosition position = (ItemLabelPosition)
                this.positiveItemLabelPositionMap.get(series);
        if (position == null) {
            position = this.defaultPositiveItemLabelPosition;
        }
        return position;
    }

    /**
     * Sets the item label position for all positive values in a series and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @see #getSeriesPositiveItemLabelPosition(int)
     */
    @Override
    public void setSeriesPositiveItemLabelPosition(int series,
                                                   ItemLabelPosition position) {
        setSeriesPositiveItemLabelPosition(series, position, true);
    }

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
    @Override
    public void setSeriesPositiveItemLabelPosition(int series,
                                                   ItemLabelPosition position, boolean notify) {
        this.positiveItemLabelPositionMap.put(series, position);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the default positive item label position.
     *
     * @return The position (never {@code null}).
     * @see #setDefaultPositiveItemLabelPosition(ItemLabelPosition)
     */
    @Override
    public ItemLabelPosition getDefaultPositiveItemLabelPosition() {
        return this.defaultPositiveItemLabelPosition;
    }

    /**
     * Sets the default positive item label position.
     *
     * @param position the position ({@code null} not permitted).
     * @see #getDefaultPositiveItemLabelPosition()
     */
    @Override
    public void setDefaultPositiveItemLabelPosition(
            ItemLabelPosition position) {
        // defer argument checking...
        setDefaultPositiveItemLabelPosition(position, true);
    }

    /**
     * Sets the default positive item label position and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @param notify   notify registered listeners?
     * @see #getDefaultPositiveItemLabelPosition()
     */
    @Override
    public void setDefaultPositiveItemLabelPosition(ItemLabelPosition position,
                                                    boolean notify) {
        Args.nullNotPermitted(position, "position");
        this.defaultPositiveItemLabelPosition = position;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

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
    @Override
    public ItemLabelPosition getNegativeItemLabelPosition(int row, int column) {
        return getSeriesNegativeItemLabelPosition(row);
    }

    /**
     * Returns the item label position for all negative values in a series.
     *
     * @param series the series index (zero-based).
     * @return The item label position (never {@code null}).
     * @see #setSeriesNegativeItemLabelPosition(int, ItemLabelPosition)
     */
    @Override
    public ItemLabelPosition getSeriesNegativeItemLabelPosition(int series) {
        // otherwise look up the position list
        ItemLabelPosition position
                = this.negativeItemLabelPositionMap.get(series);
        if (position == null) {
            position = this.defaultNegativeItemLabelPosition;
        }
        return position;
    }

    /**
     * Sets the item label position for negative values in a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series   the series index (zero-based).
     * @param position the position ({@code null} permitted).
     * @see #getSeriesNegativeItemLabelPosition(int)
     */
    @Override
    public void setSeriesNegativeItemLabelPosition(int series,
                                                   ItemLabelPosition position) {
        setSeriesNegativeItemLabelPosition(series, position, true);
    }

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
    @Override
    public void setSeriesNegativeItemLabelPosition(int series,
                                                   ItemLabelPosition position, boolean notify) {
        this.negativeItemLabelPositionMap.put(series, position);
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the base item label position for negative values.
     *
     * @return The position (never {@code null}).
     * @see #setDefaultNegativeItemLabelPosition(ItemLabelPosition)
     */
    @Override
    public ItemLabelPosition getDefaultNegativeItemLabelPosition() {
        return this.defaultNegativeItemLabelPosition;
    }

    /**
     * Sets the default item label position for negative values and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @see #getDefaultNegativeItemLabelPosition()
     */
    @Override
    public void setDefaultNegativeItemLabelPosition(
            ItemLabelPosition position) {
        setDefaultNegativeItemLabelPosition(position, true);
    }

    /**
     * Sets the default negative item label position and, if requested, sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param position the position ({@code null} not permitted).
     * @param notify   notify registered listeners?
     * @see #getDefaultNegativeItemLabelPosition()
     */
    @Override
    public void setDefaultNegativeItemLabelPosition(ItemLabelPosition position,
                                                    boolean notify) {
        Args.nullNotPermitted(position, "position");
        this.defaultNegativeItemLabelPosition = position;
        if (notify) {
            abstractRenderer.fireChangeEvent();
        }
    }

    /**
     * Returns the item label anchor offset.
     *
     * @return The offset.
     * @see #setItemLabelAnchorOffset(double)
     */
    @Override
    public double getItemLabelAnchorOffset() {
        return this.itemLabelAnchorOffset;
    }

    /**
     * Sets the item label anchor offset.
     *
     * @param offset the offset.
     * @see #getItemLabelAnchorOffset()
     */
    @Override
    public void setItemLabelAnchorOffset(double offset) {
        this.itemLabelAnchorOffset = offset;
        abstractRenderer.fireChangeEvent();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RenderStateItem clone = (RenderStateItem) super.clone();

        // 'itemLabelFont' : immutable, no need to clone reference
        if (this.itemLabelFontMap != null) {
            clone.itemLabelFontMap = new HashMap<Integer, Font>(this.itemLabelFontMap);
        }
        // 'baseItemLabelFont' : immutable, no need to clone reference

        // 'itemLabelPaint' : immutable, no need to clone reference
        if (this.itemLabelPaintList != null) {
            clone.itemLabelPaintList = (PaintList) this.itemLabelPaintList.clone();
        }
        // 'baseItemLabelPaint' : immutable, no need to clone reference

        if (this.positiveItemLabelPositionMap != null) {
            clone.positiveItemLabelPositionMap = new HashMap<Integer, ItemLabelPosition>(
                    this.positiveItemLabelPositionMap);
        }

        if (this.negativeItemLabelPositionMap != null) {
            clone.negativeItemLabelPositionMap = new HashMap<Integer, ItemLabelPosition>(
                    this.negativeItemLabelPositionMap);
        }

        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RenderStateItem)) {
            return false;
        }
        RenderStateItem that = (RenderStateItem) obj;

        if (!ObjectUtils.equal(this.itemLabelFontMap,
                that.itemLabelFontMap)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultItemLabelFont,
                that.defaultItemLabelFont)) {
            return false;
        }

        if (!ObjectUtils.equal(this.itemLabelPaintList,
                that.itemLabelPaintList)) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultItemLabelPaint,
                that.defaultItemLabelPaint)) {
            return false;
        }

        if (!ObjectUtils.equal(this.positiveItemLabelPositionMap,
                that.positiveItemLabelPositionMap)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultPositiveItemLabelPosition,
                that.defaultPositiveItemLabelPosition)) {
            return false;
        }

        if (!ObjectUtils.equal(this.negativeItemLabelPositionMap,
                that.negativeItemLabelPositionMap)) {
            return false;
        }

        if (!ObjectUtils.equal(this.defaultNegativeItemLabelPosition,
                that.defaultNegativeItemLabelPosition)) {
            return false;
        }
        if (this.itemLabelAnchorOffset != that.itemLabelAnchorOffset) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 37;
        // itemLabelFontList
        // baseItemLabelFont
        // itemLabelPaintList
        // baseItemLabelPaint
        // positiveItemLabelPositionList
        // basePositiveItemLabelPosition
        // negativeItemLabelPositionList
        // baseNegativeItemLabelPosition
        // itemLabelAnchorOffset
        return result;
    }
}