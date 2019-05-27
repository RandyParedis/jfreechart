/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2019, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------------
 * AbstractRenderer.java
 * ---------------------
 * (C) Copyright 2002-2019, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Nicolas Brodu;
 *
 * Changes:
 * --------
 * 22-Aug-2002 : Version 1, draws code out of AbstractXYItemRenderer to share
 *               with AbstractCategoryItemRenderer (DG);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 06-Nov-2002 : Moved to the com.jrefinery.chart.renderer package (DG);
 * 21-Nov-2002 : Added a paint table for the renderer to use (DG);
 * 17-Jan-2003 : Moved plot classes into a separate package (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 29-Apr-2003 : Added valueLabelFont and valueLabelPaint attributes, based on
 *               code from Arnaud Lelievre (DG);
 * 29-Jul-2003 : Amended code that doesn't compile with JDK 1.2.2 (DG);
 * 13-Aug-2003 : Implemented Cloneable (DG);
 * 15-Sep-2003 : Fixed serialization (NB);
 * 17-Sep-2003 : Changed ChartRenderingInfo --> PlotRenderingInfo (DG);
 * 07-Oct-2003 : Moved PlotRenderingInfo into RendererState to allow for
 *               multiple threads using a single renderer (DG);
 * 20-Oct-2003 : Added missing setOutlinePaint() method (DG);
 * 23-Oct-2003 : Split item label attributes into 'positive' and 'negative'
 *               values (DG);
 * 26-Nov-2003 : Added methods to get the positive and negative item label
 *               positions (DG);
 * 01-Mar-2004 : Modified readObject() method to prevent null pointer exceptions
 *               after deserialization (DG);
 * 19-Jul-2004 : Fixed bug in getItemLabelFont(int, int) method (DG);
 * 04-Oct-2004 : Updated equals() method, eliminated use of NumberUtils,
 *               renamed BooleanUtils --> BooleanUtilities, ShapeUtils -->
 *               ShapeUtilities (DG);
 * 15-Mar-2005 : Fixed serialization of baseFillPaint (DG);
 * 16-May-2005 : Base outline stroke should never be null (DG);
 * 01-Jun-2005 : Added hasListener() method for unit testing (DG);
 * 08-Jun-2005 : Fixed equals() method to handle GradientPaint (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Minor API doc update (DG);
 * 19-Feb-2007 : Fixes for clone() method (DG);
 * 28-Feb-2007 : Use cached event to signal changes (DG);
 * 19-Apr-2007 : Deprecated seriesVisible and seriesVisibleInLegend flags (DG);
 * 20-Apr-2007 : Deprecated paint, fillPaint, outlinePaint, stroke,
 *               outlineStroke, shape, itemLabelsVisible, itemLabelFont,
 *               itemLabelPaint, positiveItemLabelPosition,
 *               negativeItemLabelPosition and createEntities override
 *               fields (DG);
 * 13-Jun-2007 : Added new autoPopulate flags for core series attributes (DG);
 * 23-Oct-2007 : Updated lookup methods to better handle overridden
 *               methods (DG);
 * 04-Dec-2007 : Modified hashCode() implementation (DG);
 * 29-Apr-2008 : Minor API doc update (DG);
 * 17-Jun-2008 : Added legendShape, legendTextFont and legendTextPaint
 *               attributes (DG);
 * 18-Aug-2008 : Added clearSeriesPaints() and clearSeriesStrokes() (DG);
 * 28-Jan-2009 : Equals method doesn't test Shape equality correctly (DG);
 * 27-Mar-2009 : Added dataBoundsIncludesVisibleSeriesOnly attribute, and
 *               updated renderer events for series visibility changes (DG);
 * 01-Apr-2009 : Factored up the defaultEntityRadius field from the
 *               AbstractXYItemRenderer class (DG);
 * 28-Apr-2009 : Added flag to allow a renderer to treat the legend shape as
 *               a line (DG);
 * 05-Jul-2012 : No need for BooleanUtilities now that min JDK = 1.4.2 (DG);
 * 03-Jul-2013 : Use ParamChecks (DG);
 * 09-Apr-2014 : Remove use of ObjectList (DG);
 * 24-Aug-2014 : Add begin/endElementGroup() (DG);
 * 25-Apr-2016 : Fix cloning test failure (DG);
 *
 */

package org.jfree.chart.renderer;

import org.jfree.chart.ChartHints;
import org.jfree.chart.HashUtils;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.util.*;
import org.jfree.data.ItemKey;

import javax.swing.event.EventListenerList;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.*;

/**
 * Base class providing common services for renderers.  Most methods that update
 * attributes of the renderer will fire a {@link RendererChangeEvent}, which
 * normally means the plot that owns the renderer will receive notification that
 * the renderer has been changed (the plot will, in turn, notify the chart).
 *
 * <b>Subclassing</b>
 * If you create your own renderer that is a subclass of this, you should take
 * care to ensure that the renderer implements cloning correctly, to ensure
 * that {@link org.jfree.chart.JFreeChart JFreeChart} instances that use your renderer are also
 * cloneable.  It is recommended that you also implement the
 * {@link PublicCloneable} interface to provide simple access to the clone
 * method.
 */
public abstract class AbstractRenderer implements Cloneable, Serializable {

    /**
     * For serialization.
     */
    private static final long serialVersionUID = -828267569428206075L;

    /**
     * Zero represented as a {@code double}.
     */
    public static final Double ZERO = 0.0;

    /**
     * The default value label font.
     */
    public static final Font DEFAULT_VALUE_LABEL_FONT
            = new Font("SansSerif", Font.PLAIN, 10);

    /**
     * The default value label paint.
     */
    public static final Paint DEFAULT_VALUE_LABEL_PAINT = Color.BLACK;

    /**
     * Flags that control whether or not entities are generated for each
     * series.  This will be overridden by 'createEntities'.
     */
    private BooleanList createEntitiesList;

    /**
     * The default flag that controls whether or not entities are generated.
     * This flag is used when both the above flags return null.
     */
    private boolean defaultCreateEntities;

    /**
     * The per-series legend text font.
     *
     * @since 1.0.11
     */
    private Map<Integer, Font> legendTextFontMap;

    /**
     * The base legend font.
     *
     * @since 1.0.11
     */
    private Font defaultLegendTextFont;

    /**
     * The per series legend text paint settings.
     *
     * @since 1.0.11
     */
    private PaintList legendTextPaint;

    /**
     * The default paint for the legend text items (if this is
     * {@code null}, the {@link LegendTitle} class will determine the
     * text paint to use.
     *
     * @since 1.0.11
     */
    private transient Paint defaultLegendTextPaint;

    /**
     * The default radius for the entity 'hotspot'
     */
    private int defaultEntityRadius;

    /**
     * Storage for registered change listeners.
     */
    private transient EventListenerList listenerList;

    /**
     * An event for re-use.
     */
    private transient RendererChangeEvent event;

    private RenderStateVisibility renderStateVisibility = new RenderStateVisibility(this);
    private RenderStatePaint renderStatePaint = new RenderStatePaint(this);
    private RenderStateStroke renderStateStroke = new RenderStateStroke(this);
    private RenderStateShape renderStateShape = new RenderStateShape(this);
    private RenderStateItem renderStateItem = new RenderStateItem(this);

    /**
     * Default constructor.
     */
    public AbstractRenderer() {
        this.createEntitiesList = new BooleanList();
        this.defaultCreateEntities = true;

        this.defaultEntityRadius = 3;

        this.legendTextFontMap = new HashMap<Integer, Font>();
        this.defaultLegendTextFont = null;

        this.legendTextPaint = new PaintList();
        this.defaultLegendTextPaint = null;

        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the drawing supplier from the plot.
     *
     * @return The drawing supplier.
     */
    public abstract DrawingSupplier getDrawingSupplier();

    /**
     * Adds a {@code KEY_BEGIN_ELEMENT} hint to the graphics target.  This
     * hint is recognised by <b>JFreeSVG</b> (in theory it could be used by
     * other {@code Graphics2D} implementations also).
     *
     * @param g2  the graphics target ({@code null} not permitted).
     * @param key the key ({@code null} not permitted).
     * @see #endElementGroup(java.awt.Graphics2D)
     * @since 1.0.20
     */
    protected void beginElementGroup(Graphics2D g2, ItemKey key) {
        Args.nullNotPermitted(key, "key");
        Map m = new HashMap(1);
        m.put("ref", key.toJSONString());
        g2.setRenderingHint(ChartHints.KEY_BEGIN_ELEMENT, m);
    }

    /**
     * Adds a {@code KEY_END_ELEMENT} hint to the graphics target.
     *
     * @param g2 the graphics target ({@code null} not permitted).
     * @see #beginElementGroup(java.awt.Graphics2D, org.jfree.data.ItemKey)
     * @since 1.0.20
     */
    protected void endElementGroup(Graphics2D g2) {
        g2.setRenderingHint(ChartHints.KEY_END_ELEMENT, Boolean.TRUE);
    }

    // SERIES VISIBLE (not yet respected by all renderers)
    // SERIES VISIBLE IN LEGEND (not yet respected by all renderers)
    // ITEM LABEL VISIBILITY...
    public IRenderStateVisibility getVisibility() {
        return this.renderStateVisibility;
    }

    // PAINT
    // FILL PAINT
    // OUTLINE PAINT
    public IRenderStatePaint getPaint() {
        return this.renderStatePaint;
    }

    // STROKE
    // OUTLINE STROKE
    public IRenderStateStroke getStroke() {
        return renderStateStroke;
    }

    // SHAPE
    public IRenderStateShape getShape() {
        return renderStateShape;
    }

    // ITEM LABEL FONT
    // ITEM LABEL PAINT
    // POSITIVE ITEM LABEL POSITION
    // NEGATIVE ITEM LABEL POSITION
    public IRenderStateItem getItem() {
        return renderStateItem;
    }

    // CREATE ENTITIES

    /**
     * Returns a boolean that indicates whether or not the specified item
     * should have a chart entity created for it.
     *
     * @param series the series index.
     * @param item   the item index.
     * @return A boolean.
     */
    public boolean getItemCreateEntity(int series, int item) {
        Boolean b = getSeriesCreateEntities(series);
        if (b != null) {
            return b;
        }
        // otherwise...
        return this.defaultCreateEntities;
    }

    /**
     * Returns the flag that controls whether entities are created for a
     * series.
     *
     * @param series the series index (zero-based).
     * @return The flag (possibly {@code null}).
     * @see #setSeriesCreateEntities(int, Boolean)
     */
    public Boolean getSeriesCreateEntities(int series) {
        return this.createEntitiesList.getBoolean(series);
    }

    /**
     * Sets the flag that controls whether entities are created for a series,
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param create the flag ({@code null} permitted).
     * @see #getSeriesCreateEntities(int)
     */
    public void setSeriesCreateEntities(int series, Boolean create) {
        setSeriesCreateEntities(series, create, true);
    }

    /**
     * Sets the flag that controls whether entities are created for a series
     * and, if requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param series the series index.
     * @param create the flag ({@code null} permitted).
     * @param notify notify listeners?
     * @see #getSeriesCreateEntities(int)
     */
    public void setSeriesCreateEntities(int series, Boolean create,
                                        boolean notify) {
        this.createEntitiesList.setBoolean(series, create);
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the default flag for creating entities.
     *
     * @return The default flag for creating entities.
     * @see #setDefaultCreateEntities(boolean)
     */
    public boolean getDefaultCreateEntities() {
        return this.defaultCreateEntities;
    }

    /**
     * Sets the default flag that controls whether entities are created
     * for a series, and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param create the flag.
     * @see #getDefaultCreateEntities()
     */
    public void setDefaultCreateEntities(boolean create) {
        // defer argument checking...
        setDefaultCreateEntities(create, true);
    }

    /**
     * Sets the default flag that controls whether entities are created and,
     * if requested, sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param create the visibility.
     * @param notify notify listeners?
     * @see #getDefaultCreateEntities()
     */
    public void setDefaultCreateEntities(boolean create, boolean notify) {
        this.defaultCreateEntities = create;
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the radius of the circle used for the default entity area
     * when no area is specified.
     *
     * @return A radius.
     * @see #setDefaultEntityRadius(int)
     */
    public int getDefaultEntityRadius() {
        return this.defaultEntityRadius;
    }

    /**
     * Sets the radius of the circle used for the default entity area
     * when no area is specified.
     *
     * @param radius the radius.
     * @see #getDefaultEntityRadius()
     */
    public void setDefaultEntityRadius(int radius) {
        this.defaultEntityRadius = radius;
    }

    /**
     * Performs a lookup for the legend text font.
     *
     * @param series the series index.
     * @return The font (possibly {@code null}).
     * @since 1.0.11
     */
    public Font lookupLegendTextFont(int series) {
        Font result = getLegendTextFont(series);
        if (result == null) {
            result = this.defaultLegendTextFont;
        }
        return result;
    }

    /**
     * Returns the legend text font defined for the specified series (possibly
     * {@code null}).
     *
     * @param series the series index.
     * @return The font (possibly {@code null}).
     * @see #lookupLegendTextFont(int)
     * @since 1.0.11
     */
    public Font getLegendTextFont(int series) {
        return this.legendTextFontMap.get(series);
    }

    /**
     * Sets the font used for the legend text for the specified series, and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param font   the font ({@code null} permitted).
     * @since 1.0.11
     */
    public void setLegendTextFont(int series, Font font) {
        this.legendTextFontMap.put(series, font);
        fireChangeEvent();
    }

    /**
     * Returns the default legend text font, which may be {@code null}.
     *
     * @return The default legend text font.
     * @since 1.0.11
     */
    public Font getDefaultLegendTextFont() {
        return this.defaultLegendTextFont;
    }

    /**
     * Sets the default legend text font and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param font the font ({@code null} permitted).
     * @since 1.0.11
     */
    public void setDefaultLegendTextFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.defaultLegendTextFont = font;
        fireChangeEvent();
    }

    /**
     * Performs a lookup for the legend text paint.
     *
     * @param series the series index.
     * @return The paint (possibly {@code null}).
     * @since 1.0.11
     */
    public Paint lookupLegendTextPaint(int series) {
        Paint result = getLegendTextPaint(series);
        if (result == null) {
            result = this.defaultLegendTextPaint;
        }
        return result;
    }

    /**
     * Returns the legend text paint defined for the specified series (possibly
     * {@code null}).
     *
     * @param series the series index.
     * @return The paint (possibly {@code null}).
     * @see #lookupLegendTextPaint(int)
     * @since 1.0.11
     */
    public Paint getLegendTextPaint(int series) {
        return this.legendTextPaint.getPaint(series);
    }

    /**
     * Sets the paint used for the legend text for the specified series, and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series the series index.
     * @param paint  the paint ({@code null} permitted).
     * @since 1.0.11
     */
    public void setLegendTextPaint(int series, Paint paint) {
        this.legendTextPaint.setPaint(series, paint);
        fireChangeEvent();
    }

    /**
     * Returns the default legend text paint, which may be {@code null}.
     *
     * @return The default legend text paint.
     * @since 1.0.11
     */
    public Paint getDefaultLegendTextPaint() {
        return this.defaultLegendTextPaint;
    }

    /**
     * Sets the default legend text paint and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint the paint ({@code null} permitted).
     * @since 1.0.11
     */
    public void setDefaultLegendTextPaint(Paint paint) {
        this.defaultLegendTextPaint = paint;
        fireChangeEvent();
    }

    /**
     * Registers an object to receive notification of changes to the renderer.
     *
     * @param listener the listener ({@code null} not permitted).
     * @see #removeChangeListener(RendererChangeListener)
     */
    public void addChangeListener(RendererChangeListener listener) {
        Args.nullNotPermitted(listener, "listener");
        this.listenerList.add(RendererChangeListener.class, listener);
    }

    /**
     * Deregisters an object so that it no longer receives
     * notification of changes to the renderer.
     *
     * @param listener the object ({@code null} not permitted).
     * @see #addChangeListener(RendererChangeListener)
     */
    public void removeChangeListener(RendererChangeListener listener) {
        Args.nullNotPermitted(listener, "listener");
        this.listenerList.remove(RendererChangeListener.class, listener);
    }

    /**
     * Returns {@code true} if the specified object is registered with
     * the dataset as a listener.  Most applications won't need to call this
     * method, it exists mainly for use by unit testing code.
     *
     * @param listener the listener.
     * @return A boolean.
     */
    public boolean hasListener(EventListener listener) {
        List list = Arrays.asList(this.listenerList.getListenerList());
        return list.contains(listener);
    }

    /**
     * Sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @since 1.0.5
     */
    protected void fireChangeEvent() {

        // the commented out code would be better, but only if
        // RendererChangeEvent is immutable, which it isn't.  See if there is
        // a way to fix this...

        //if (this.event == null) {
        //    this.event = new RendererChangeEvent(this);
        //}
        //notifyListeners(this.event);

        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Notifies all registered listeners that the renderer has been modified.
     *
     * @param event information about the change event.
     */
    public void notifyListeners(RendererChangeEvent event) {
        Object[] ls = this.listenerList.getListenerList();
        for (int i = ls.length - 2; i >= 0; i -= 2) {
            if (ls[i] == RendererChangeListener.class) {
                ((RendererChangeListener) ls[i + 1]).rendererChanged(event);
            }
        }
    }

    /**
     * Tests this renderer for equality with another object.
     *
     * @param obj the object ({@code null} permitted).
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractRenderer)) {
            return false;
        }
        AbstractRenderer that = (AbstractRenderer) obj;
        if (this.defaultEntityRadius != that.defaultEntityRadius) {
            return false;
        }
        if (!ObjectUtils.equal(this.getVisibility(), that.getVisibility())) {
            return false;
        }
        if (!ObjectUtils.equal(this.getPaint(), that.getPaint())) {
            return false;
        }
        if (!ObjectUtils.equal(this.getStroke(), that.getStroke())) {
            return false;
        }
        if (!ObjectUtils.equal(this.getShape(), that.getShape())) {
            return false;
        }
        if (!ObjectUtils.equal(this.getItem(), that.getItem())) {
            return false;
        }
        if (!ObjectUtils.equal(this.createEntitiesList,
                that.createEntitiesList)) {
            return false;
        }
        if (this.defaultCreateEntities != that.defaultCreateEntities) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendTextFontMap,
                that.legendTextFontMap)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultLegendTextFont,
                that.defaultLegendTextFont)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendTextPaint,
                that.legendTextPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultLegendTextPaint,
                that.defaultLegendTextPaint)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hashcode for the renderer.
     *
     * @return The hashcode.
     */
    @Override
    public int hashCode() {
        int result = 193;
        result = HashUtils.hashCode(result, this.renderStateVisibility);
        result = HashUtils.hashCode(result, this.renderStatePaint);
        result = HashUtils.hashCode(result, this.renderStateStroke);
        result = HashUtils.hashCode(result, this.renderStateShape);
        result = HashUtils.hashCode(result, this.renderStateItem);
        // createEntityList
        // baseCreateEntities
        return result;
    }

    /**
     * Returns an independent copy of the renderer.
     *
     * @return A clone.
     * @throws CloneNotSupportedException if some component of the renderer
     *                                    does not support cloning.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractRenderer clone = (AbstractRenderer) super.clone();

        if (this.renderStateVisibility != null) {
            clone.renderStateVisibility = (RenderStateVisibility) this.renderStateVisibility.clone();
            clone.renderStateVisibility.setAbstractRenderer(clone);
        }

        if (this.renderStatePaint != null) {
            clone.renderStatePaint = (RenderStatePaint) this.renderStatePaint.clone();
            clone.renderStatePaint.setAbstractRenderer(clone);
        }

        if (this.renderStateStroke != null) {
            clone.renderStateStroke = (RenderStateStroke) this.renderStateStroke.clone();
            clone.renderStateStroke.setAbstractRenderer(clone);
        }

        if (this.renderStateShape != null) {
            clone.renderStateShape = (RenderStateShape) this.renderStateShape.clone();
            clone.renderStateShape.setAbstractRenderer(clone);
        }

        if (this.renderStateItem != null) {
            clone.renderStateItem = (RenderStateItem) this.renderStateItem.clone();
            clone.renderStateItem.setAbstractRenderer(clone);
        }

        // 'basePaint' : immutable, no need to clone reference

        if (this.createEntitiesList != null) {
            clone.createEntitiesList
                    = (BooleanList) this.createEntitiesList.clone();
        }

        if (this.legendTextFontMap != null) {
            // Font objects are immutable so just shallow copy the map
            clone.legendTextFontMap = new HashMap<Integer, Font>(
                    this.legendTextFontMap);
        }
        if (this.legendTextPaint != null) {
            clone.legendTextPaint = (PaintList) this.legendTextPaint.clone();
        }
        clone.listenerList = new EventListenerList();
        clone.event = null;
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream the output stream.
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.renderStatePaint.getDefaultPaint(), stream);
        SerialUtils.writePaint(this.renderStatePaint.getDefaultFillPaint(), stream);
        SerialUtils.writePaint(this.renderStatePaint.getDefaultOutlinePaint(), stream);
        SerialUtils.writeStroke(this.renderStateStroke.getDefaultStroke(), stream);
        SerialUtils.writeStroke(this.renderStateStroke.getDefaultOutlineStroke(), stream);
        SerialUtils.writeShape(this.renderStateShape.getDefaultShape(), stream);
        SerialUtils.writePaint(this.renderStateItem.getDefaultItemLabelPaint(), stream);
        SerialUtils.writeShape(this.renderStateShape.getDefaultLegendShape(), stream);
        SerialUtils.writePaint(this.defaultLegendTextPaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream the input stream.
     * @throws IOException            if there is an I/O error.
     * @throws ClassNotFoundException if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.renderStatePaint.setDefaultPaint(SerialUtils.readPaint(stream), false);
        this.renderStatePaint.setDefaultFillPaint(SerialUtils.readPaint(stream), false);
        this.renderStatePaint.setDefaultOutlinePaint(SerialUtils.readPaint(stream), false);
        this.renderStateStroke.setDefaultStroke(SerialUtils.readStroke(stream), false);
        this.renderStateStroke.setDefaultOutlineStroke(SerialUtils.readStroke(stream), false);
        this.renderStateShape.setDefaultShape(SerialUtils.readShape(stream), false);
        this.renderStateItem.setDefaultItemLabelPaint(SerialUtils.readPaint(stream), false);
        this.renderStateShape.setDefaultLegendShape(SerialUtils.readShape(stream), false);
        this.defaultLegendTextPaint = SerialUtils.readPaint(stream);

        // listeners are not restored automatically, but storage must be
        // provided...
        this.listenerList = new EventListenerList();
    }
}
