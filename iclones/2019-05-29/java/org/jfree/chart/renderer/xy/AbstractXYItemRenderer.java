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
 * ---------------------------
 * AbstractXYItemRenderer.java
 * ---------------------------
 * (C) Copyright 2002-2019, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Richard Atkinson;
 *                   Focus Computer Services Limited;
 *                   Tim Bardzil;
 *                   Sergei Ivanov;
 *                   Peter Kolb (patch 2809117);
 *                   Martin Krauskopf;
 *
 * Changes:
 * --------
 * 15-Mar-2002 : Version 1 (DG);
 * 09-Apr-2002 : Added a getToolTipGenerator() method reflecting the change in
 *               the XYItemRenderer interface (DG);
 * 05-Aug-2002 : Added a urlGenerator member variable to support HTML image
 *               maps (RA);
 * 20-Aug-2002 : Added property change events for the tooltip and URL
 *               generators (DG);
 * 22-Aug-2002 : Moved property change support into AbstractRenderer class (DG);
 * 23-Sep-2002 : Fixed errors reported by Checkstyle tool (DG);
 * 18-Nov-2002 : Added methods for drawing grid lines (DG);
 * 17-Jan-2003 : Moved plot classes into a separate package (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 01-May-2003 : Modified initialise() return type and drawItem() method
 *               signature (DG);
 * 15-May-2003 : Modified to take into account the plot orientation (DG);
 * 21-May-2003 : Added labels to markers (DG);
 * 05-Jun-2003 : Added domain and range grid bands (sponsored by Focus Computer
 *               Services Ltd) (DG);
 * 27-Jul-2003 : Added getRangeType() to support stacked XY area charts (RA);
 * 31-Jul-2003 : Deprecated all but the default constructor (DG);
 * 13-Aug-2003 : Implemented Cloneable (DG);
 * 16-Sep-2003 : Changed ChartRenderingInfo --> PlotRenderingInfo (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 05-Nov-2003 : Fixed marker rendering bug (833623) (DG);
 * 11-Feb-2004 : Updated labelling for markers (DG);
 * 25-Feb-2004 : Added updateCrosshairValues() method.  Moved deprecated code
 *               to bottom of source file (DG);
 * 16-Apr-2004 : Added support for IntervalMarker in drawRangeMarker() method
 *               - thanks to Tim Bardzil (DG);
 * 05-May-2004 : Fixed bug (948310) where interval markers extend beyond axis
 *               range (DG);
 * 03-Jun-2004 : Fixed more bugs in drawing interval markers (DG);
 * 26-Aug-2004 : Added the addEntity() method (DG);
 * 29-Sep-2004 : Added annotation support (with layers) (DG);
 * 30-Sep-2004 : Moved drawRotatedString() from RefineryUtilities -->
 *               TextUtilities (DG);
 * 06-Oct-2004 : Added findDomainBounds() method and renamed
 *               getRangeExtent() --> findRangeBounds() (DG);
 * 07-Jan-2005 : Removed deprecated code (DG);
 * 27-Jan-2005 : Modified getLegendItem() to omit hidden series (DG);
 * 24-Feb-2005 : Added getLegendItems() method (DG);
 * 08-Mar-2005 : Fixed positioning of marker labels (DG);
 * 20-Apr-2005 : Renamed XYLabelGenerator --> XYItemLabelGenerator and
 *               added generators for legend labels, tooltips and URLs (DG);
 * 01-Jun-2005 : Handle one dimension of the marker label adjustment
 *               automatically (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 20-Jul-2006 : Set dataset and series indices in LegendItem (DG);
 * 24-Oct-2006 : Respect alpha setting in markers (see patch 1567843 by Sergei
 *               Ivanov) (DG);
 * 24-Oct-2006 : Added code to draw outlines for interval markers (DG);
 * 24-Nov-2006 : Fixed cloning for legend item generators (DG);
 * 06-Feb-2007 : Added new updateCrosshairValues() method that takes into
 *               account multiple axis plots (see bug 1086307) (DG);
 * 20-Feb-2007 : Fixed equals() method implementation (DG);
 * 01-Mar-2007 : Fixed interval marker drawing (patch 1670686 thanks to
 *               Sergei Ivanov) (DG);
 * 22-Mar-2007 : Modified the tool tip generator look up (DG);
 * 23-Mar-2007 : Added drawDomainLine() method (DG);
 * 20-Apr-2007 : Updated getLegendItem() for renderer change, and deprecated
 *               itemLabelGenerator and toolTipGenerator override fields (DG);
 * 18-May-2007 : Set dataset and seriesKey for LegendItem (DG);
 * 12-Nov-2007 : Fixed domain and range band drawing methods (DG);
 * 07-Apr-2008 : Minor API doc update (DG);
 * 14-May-2008 : Updated addEntity() method to take plot orientation into
 *               account when the incoming area is null (DG);
 * 02-Jun-2008 : Added isPointInRect() method (DG);
 * 17-Jun-2008 : Apply legend shape, font and paint attributes (DG);
 * 09-Mar-2009 : Added getAnnotations() method (DG);
 * 27-Mar-2009 : Added new findDomainBounds() and findRangeBounds() methods to
 *               take account of hidden series (DG);
 * 01-Apr-2009 : Moved defaultEntityRadius up to superclass (DG);
 * 28-Apr-2009 : Updated getLegendItem() method to observe new
 *               'treatLegendShapeAsLine' flag (DG);
 * 24-Jun-2009 : Added support for annotation events - see patch 2809117
 *               by PK (DG);
 * 01-Sep-2009 : Bug 2840132 - set renderer index when drawing
 *               annotations (DG);
 * 06-Oct-2011 : Add utility methods to work with 1.4 API in GeneralPath (MK)
 * 03-Jul-2013 : Use ParamChecks (DG);
 * 11-Jan-2014 : Fix error in fillDomainGridBand method (DG);
 * 07-Apr-2014 : Don't use ObjectList anymore (DG);
 * 29-Jul-2014 : Add rendering hint to normalise domain and range lines (DG);
 * 24-Aug-2014 : Add beginElementGroup() method, part of JFreeSVG support (DG);
 * 18-Feb-2017 : Fix for crosshairs with multiple datasets / axes - see 
 *               bug #36 (DG);
 */

package org.jfree.chart.renderer.xy;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.Annotation;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.*;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.CloneUtils;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYItemKey;

/**
 * A base class that can be used to create new {@link XYItemRenderer}
 * implementations.  
 * 
 * <b>Subclassing</b>
 * If you create your own subclass of this renderer, please refer to the 
 * Javadocs for {@link AbstractRenderer} for important information about
 * cloning.
 */
public abstract class AbstractXYItemRenderer extends AbstractRenderer
        implements XYItemRenderer, AnnotationChangeListener,
        Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8019124836026607990L;

    /** The plot. */
    private XYPlot plot;

    /** A list of item label generators (one per series). */
    private Map<Integer, XYItemLabelGenerator> itemLabelGeneratorMap;

    /** The default item label generator. */
    private XYItemLabelGenerator defaultItemLabelGenerator;

    /** A list of tool tip generators (one per series). */
    private Map<Integer, XYToolTipGenerator> toolTipGeneratorMap;

    /** The default tool tip generator. */
    private XYToolTipGenerator defaultToolTipGenerator;

    /** The URL text generator. */
    private XYURLGenerator urlGenerator;

    /**
     * Annotations to be drawn in the background layer ('underneath' the data
     * items).
     */
    private List<XYAnnotation> backgroundAnnotations;

    /**
     * Annotations to be drawn in the foreground layer ('on top' of the data
     * items).
     */
    private List<XYAnnotation> foregroundAnnotations;

    /** The legend item label generator. */
    private XYSeriesLabelGenerator legendItemLabelGenerator;

    /** The legend item tool tip generator. */
    private XYSeriesLabelGenerator legendItemToolTipGenerator;

    /** The legend item URL generator. */
    private XYSeriesLabelGenerator legendItemURLGenerator;

    /**
     * Creates a renderer where the tooltip generator and the URL generator are
     * both {@code null}.
     */
    protected AbstractXYItemRenderer() {
        super();
        this.itemLabelGeneratorMap = new HashMap<Integer, XYItemLabelGenerator>();
        this.toolTipGeneratorMap = new HashMap<Integer, XYToolTipGenerator>();
        this.urlGenerator = null;
        this.backgroundAnnotations = new ArrayList<XYAnnotation>();
        this.foregroundAnnotations = new ArrayList<XYAnnotation>();
        this.legendItemLabelGenerator = new StandardXYSeriesLabelGenerator("{0}");
    }

    /**
     * Returns the number of passes through the data that the renderer requires
     * in order to draw the chart.  Most charts will require a single pass, but
     * some require two passes.
     *
     * @return The pass count.
     */
    @Override
    public int getPassCount() {
        return 1;
    }

    /**
     * Returns the plot that the renderer is assigned to.
     *
     * @return The plot (possibly {@code null}).
     */
    @Override
    public XYPlot getPlot() {
        return this.plot;
    }

    /**
     * Sets the plot that the renderer is assigned to.
     *
     * @param plot  the plot ({@code null} permitted).
     */
    @Override
    public void setPlot(XYPlot plot) {
        this.plot = plot;
    }

    /**
     * Initialises the renderer and returns a state object that should be
     * passed to all subsequent calls to the drawItem() method.
     * <P>
     * This method will be called before the first item is rendered, giving the
     * renderer an opportunity to initialise any state information it wants to
     * maintain.  The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param dataset  the dataset.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The renderer state (never {@code null}).
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2,
                                          Rectangle2D dataArea,
                                          XYPlot plot,
                                          XYDataset dataset,
                                          PlotRenderingInfo info) {
        return new XYItemRendererState(info);
    }

    /**
     * Adds a {@code KEY_BEGIN_ELEMENT} hint to the graphics target.  This
     * hint is recognised by <b>JFreeSVG</b> (in theory it could be used by 
     * other {@code Graphics2D} implementations also).
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param seriesKey  the series key that identifies the element 
     *     ({@code null} not permitted).
     * @param itemIndex  the item index. 
     * 
     * @since 1.0.20
     */
    protected void beginElementGroup(Graphics2D g2, Comparable seriesKey, int itemIndex) {
        beginElementGroup(g2, new XYItemKey(seriesKey, itemIndex));    
    }

    // ITEM LABEL GENERATOR

    /**
     * Returns the label generator for a data item.  This implementation simply
     * passes control to the {@link #getSeriesItemLabelGenerator(int)} method.
     * If, for some reason, you want a different generator for individual
     * items, you can override this method.
     *
     * @param series  the series index (zero based).
     * @param item  the item index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getItemLabelGenerator(int series, int item) {

        // otherwise look up the generator table
        XYItemLabelGenerator generator = this.itemLabelGeneratorMap.get(series);
        if (generator == null) {
            generator = this.defaultItemLabelGenerator;
        }
        return generator;
    }

    /**
     * Returns the item label generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getSeriesItemLabelGenerator(int series) {
        return this.itemLabelGeneratorMap.get(series);
    }

    /**
     * Sets the item label generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator) {
        setSeriesItemLabelGenerator(series, generator, true);
    }

    @Override
    public void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator, boolean notify) {
        this.itemLabelGeneratorMap.put(series, generator);
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the default item label generator.
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getDefaultItemLabelGenerator() {
        return this.defaultItemLabelGenerator;
    }

    /**
     * Sets the default item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setDefaultItemLabelGenerator(XYItemLabelGenerator generator) {
        setDefaultItemLabelGenerator(generator, true);
    }

    @Override
    public void setDefaultItemLabelGenerator(XYItemLabelGenerator generator, boolean notify) {
        this.defaultItemLabelGenerator = generator;
        if (notify) {
            fireChangeEvent();
        }
    }

    // TOOL TIP GENERATOR

    /**
     * Returns the tool tip generator for a data item.  If, for some reason,
     * you want a different generator for individual items, you can override
     * this method.
     *
     * @param series  the series index (zero based).
     * @param item  the item index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYToolTipGenerator getToolTipGenerator(int series, int item) {

        // otherwise look up the generator table
        XYToolTipGenerator generator = this.toolTipGeneratorMap.get(series);
        if (generator == null) {
            generator = this.defaultToolTipGenerator;
        }
        return generator;
    }

    /**
     * Returns the tool tip generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYToolTipGenerator getSeriesToolTipGenerator(int series) {
        return this.toolTipGeneratorMap.get(series);
    }

    /**
     * Sets the tool tip generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator) {
        setSeriesToolTipGenerator(series, generator, true);
    }

    @Override
    public void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator, boolean notify) {
        this.toolTipGeneratorMap.put(series, generator);
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the default tool tip generator.
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setDefaultToolTipGenerator(XYToolTipGenerator)
     */
    @Override
    public XYToolTipGenerator getDefaultToolTipGenerator() {
        return this.defaultToolTipGenerator;
    }

    /**
     * Sets the default tool tip generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getDefaultToolTipGenerator()
     */
    @Override
    public void setDefaultToolTipGenerator(XYToolTipGenerator generator) {
        setDefaultToolTipGenerator(generator, true);
    }

    @Override
    public void setDefaultToolTipGenerator(XYToolTipGenerator generator, boolean notify) {
        this.defaultToolTipGenerator = generator;
        if (notify) {
            fireChangeEvent();
        }
    }

    // URL GENERATOR

    /**
     * Returns the URL generator for HTML image maps.
     *
     * @return The URL generator (possibly {@code null}).
     */
    @Override
    public XYURLGenerator getURLGenerator() {
        return this.urlGenerator;
    }

    /**
     * Sets the URL generator for HTML image maps and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param urlGenerator  the URL generator ({@code null} permitted).
     */
    @Override
    public void setURLGenerator(XYURLGenerator urlGenerator) {
        setURLGenerator(urlGenerator, true);
    }

    @Override
    public void setURLGenerator(XYURLGenerator urlGenerator, boolean notify) {
        this.urlGenerator = urlGenerator;
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Adds an annotation and sends a {@link RendererChangeEvent} to all
     * registered listeners.  The annotation is added to the foreground
     * layer.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     */
    @Override
    public void addAnnotation(XYAnnotation annotation) {
        // defer argument checking
        addAnnotation(annotation, Layer.FOREGROUND);
    }

    /**
     * Adds an annotation to the specified layer and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     * @param layer  the layer ({@code null} not permitted).
     */
    @Override
    public void addAnnotation(XYAnnotation annotation, Layer layer) {
        addAnnotation(annotation, layer, true);
    }

    @Override
    public void addAnnotation(XYAnnotation annotation, Layer layer, boolean notify) {
        Args.nullNotPermitted(annotation, "annotation");
        if (layer.equals(Layer.FOREGROUND)) {
            this.foregroundAnnotations.add(annotation);
        }
        else if (layer.equals(Layer.BACKGROUND)) {
            this.backgroundAnnotations.add(annotation);
        }
        else {
            // should never get here
            throw new RuntimeException("Unknown layer.");
        }

        annotation.addChangeListener(this);
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Removes the specified annotation and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param annotation  the annotation to remove ({@code null} not
     *                    permitted).
     *
     * @return A boolean to indicate whether or not the annotation was
     *         successfully removed.
     */
    @Override
    public boolean removeAnnotation(XYAnnotation annotation) {
        return removeAnnotation(annotation, true);
    }

    @Override
    public boolean removeAnnotation(XYAnnotation annotation, boolean notify) {
        boolean removed = this.foregroundAnnotations.remove(annotation);
        removed = removed & this.backgroundAnnotations.remove(annotation);
        annotation.removeChangeListener(this);
        if (notify) {
            fireChangeEvent();
        }
        return removed;
    }

    /**
     * Removes all annotations and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     */
    @Override
    public void removeAnnotations() {
        removeAnnotations(true);
    }

    @Override
    public void removeAnnotations(boolean notify) {
        for (XYAnnotation annotation : this.foregroundAnnotations) {
            annotation.removeChangeListener(this);
        }
        for (XYAnnotation annotation : this.backgroundAnnotations) {
            annotation.removeChangeListener(this);
        }
        this.foregroundAnnotations.clear();
        this.backgroundAnnotations.clear();
        if (notify) {
            fireChangeEvent();
        }
    }


    /**
     * Receives notification of a change to an {@link Annotation} added to
     * this renderer.
     *
     * @param event  information about the event (not used here).
     *
     * @since 1.0.14
     */
    @Override
    public void annotationChanged(AnnotationChangeEvent event) {
        fireChangeEvent();
    }

    /**
     * Returns a collection of the annotations that are assigned to the
     * renderer.
     *
     * @return A collection of annotations (possibly empty but never
     *     {@code null}).
     * 
     * @since 1.0.13
     */
    public Collection<XYAnnotation> getAnnotations() {
        List<XYAnnotation> result 
                = new ArrayList<XYAnnotation>(this.foregroundAnnotations);
        result.addAll(this.backgroundAnnotations);
        return result;
    }

    /**
     * Returns the legend item label generator.
     *
     * @return The label generator (never {@code null}).
     *
     * @see #setLegendItemLabelGenerator(XYSeriesLabelGenerator)
     */
    @Override
    public XYSeriesLabelGenerator getLegendItemLabelGenerator() {
        return this.legendItemLabelGenerator;
    }

    /**
     * Sets the legend item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} not permitted).
     *
     * @see #getLegendItemLabelGenerator()
     */
    @Override
    public void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator) {
        setLegendItemLabelGenerator(generator, true);
    }

    @Override
    public void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator, boolean notify) {
        Args.nullNotPermitted(generator, "generator");
        this.legendItemLabelGenerator = generator;
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the legend item tool tip generator.
     *
     * @return The tool tip generator (possibly {@code null}).
     *
     * @see #setLegendItemToolTipGenerator(XYSeriesLabelGenerator)
     */
    public XYSeriesLabelGenerator getLegendItemToolTipGenerator() {
        return this.legendItemToolTipGenerator;
    }

    /**
     * Sets the legend item tool tip generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getLegendItemToolTipGenerator()
     */
    public void setLegendItemToolTipGenerator(XYSeriesLabelGenerator generator) {
        setLegendItemToolTipGenerator(generator, true);
    }

    public void setLegendItemToolTipGenerator(XYSeriesLabelGenerator generator, boolean notify) {
        this.legendItemToolTipGenerator = generator;
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the legend item URL generator.
     *
     * @return The URL generator (possibly {@code null}).
     *
     * @see #setLegendItemURLGenerator(XYSeriesLabelGenerator)
     */
    public XYSeriesLabelGenerator getLegendItemURLGenerator() {
        return this.legendItemURLGenerator;
    }

    /**
     * Sets the legend item URL generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getLegendItemURLGenerator()
     */
    public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator) {
        setLegendItemURLGenerator(generator, true);
    }

    public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator, boolean notify) {
        this.legendItemURLGenerator = generator;
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the lower and upper bounds (range) of the x-values in the
     * specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @see #findRangeBounds(XYDataset)
     */
    @Override
    public Range findDomainBounds(XYDataset dataset) {
        return findDomainBounds(dataset, false);
    }

    /**
     * Returns the lower and upper bounds (range) of the x-values in the
     * specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     * @param includeInterval  include the interval (if any) for the dataset?
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @since 1.0.13
     */
    protected Range findDomainBounds(XYDataset dataset,
            boolean includeInterval) {
        if (dataset == null) {
            return null;
        }
        if (getVisibility().getDataBoundsIncludesVisibleSeriesOnly()) {
            List visibleSeriesKeys = new ArrayList();
            int seriesCount = dataset.getSeriesCount();
            for (int s = 0; s < seriesCount; s++) {
                if (getVisibility().isSeriesVisible(s)) {
                    visibleSeriesKeys.add(dataset.getSeriesKey(s));
                }
            }
            return DatasetUtils.findDomainBounds(dataset,
                    visibleSeriesKeys, includeInterval);
        }
        return DatasetUtils.findDomainBounds(dataset, includeInterval);
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @see #findDomainBounds(XYDataset)
     */
    @Override
    public Range findRangeBounds(XYDataset dataset) {
        return findRangeBounds(dataset, false);
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     * @param includeInterval  include the interval (if any) for the dataset?
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @since 1.0.13
     */
    protected Range findRangeBounds(XYDataset dataset,
            boolean includeInterval) {
        if (dataset == null) {
            return null;
        }
        if (getVisibility().getDataBoundsIncludesVisibleSeriesOnly()) {
            List visibleSeriesKeys = new ArrayList();
            int seriesCount = dataset.getSeriesCount();
            for (int s = 0; s < seriesCount; s++) {
                if (getVisibility().isSeriesVisible(s)) {
                    visibleSeriesKeys.add(dataset.getSeriesKey(s));
                }
            }
            // the bounds should be calculated using just the items within
            // the current range of the x-axis...if there is one
            Range xRange = null;
            XYPlot p = getPlot();
            if (p != null) {
                ValueAxis xAxis = null;
                int index = p.getIndexOf(this);
                if (index >= 0) {
                    xAxis = this.plot.getDomainAxisForDataset(index);
                }
                if (xAxis != null) {
                    xRange = xAxis.getRange();
                }
            }
            if (xRange == null) {
                xRange = new Range(Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY);
            }
            return DatasetUtils.findRangeBounds(dataset,
                    visibleSeriesKeys, xRange, includeInterval);
        }
        return DatasetUtils.findRangeBounds(dataset, includeInterval);
    }

    /**
     * Returns a (possibly empty) collection of legend items for the series
     * that this renderer is responsible for drawing.
     *
     * @return The legend item collection (never {@code null}).
     */
    @Override
    public LegendItemCollection getLegendItems() {
        if (this.plot == null) {
            return new LegendItemCollection();
        }
        LegendItemCollection result = new LegendItemCollection();
        int index = this.plot.getIndexOf(this);
        XYDataset dataset = this.plot.getDataset(index);
        if (dataset != null) {
            int seriesCount = dataset.getSeriesCount();
            for (int i = 0; i < seriesCount; i++) {
                if (getVisibility().isSeriesVisibleInLegend(i)) {
                    LegendItem item = getLegendItem(index, i);
                    if (item != null) {
                        result.add(item);
                    }
                }
            }

        }
        return result;
    }

    /**
     * Returns a default legend item for the specified series.  Subclasses
     * should override this method to generate customised items.
     *
     * @param datasetIndex  the dataset index (zero-based).
     * @param series  the series index (zero-based).
     *
     * @return A legend item for the series.
     */
    @Override
    public LegendItem getLegendItem(int datasetIndex, int series) {
        XYPlot xyplot = getPlot();
        if (xyplot == null) {
            return null;
        }
        XYDataset dataset = xyplot.getDataset(datasetIndex);
        if (dataset == null) {
            return null;
        }
        String label = this.legendItemLabelGenerator.generateLabel(dataset,
                series);
        String description = label;
        String toolTipText = null;
        if (getLegendItemToolTipGenerator() != null) {
            toolTipText = getLegendItemToolTipGenerator().generateLabel(
                    dataset, series);
        }
        String urlText = null;
        if (getLegendItemURLGenerator() != null) {
            urlText = getLegendItemURLGenerator().generateLabel(dataset,
                    series);
        }
        Shape shape = getShape().lookupLegendShape(series);
        Paint paint = getPaint().lookupSeriesPaint(series);
        LegendItem item = new LegendItem(label, paint);
        item.setToolTipText(toolTipText);
        item.setURLText(urlText);
        item.setLabelFont(lookupLegendTextFont(series));
        Paint labelPaint = lookupLegendTextPaint(series);
        if (labelPaint != null) {
            item.setLabelPaint(labelPaint);
        }
        item.setSeriesKey(dataset.getSeriesKey(series));
        item.setSeriesIndex(series);
        item.setDataset(dataset);
        item.setDatasetIndex(datasetIndex);

        if (getShape().getTreatLegendShapeAsLine()) {
            item.setLineVisible(true);
            item.setLine(shape);
            item.setLinePaint(paint);
            item.setShapeVisible(false);
        }
        else {
            Paint outlinePaint = getPaint().lookupSeriesOutlinePaint(series);
            Stroke outlineStroke = getStroke().lookupSeriesOutlineStroke(series);
            item.setOutlinePaint(outlinePaint);
            item.setOutlineStroke(outlineStroke);
        }
        return item;
    }

    /**
     * Fills a band between two values on the axis.  This can be used to color
     * bands between the grid lines.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the domain axis.
     * @param dataArea  the data area.
     * @param start  the start value.
     * @param end  the end value.
     */
    @Override
    public void fillDomainGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double start, double end) {

        double x1 = axis.valueToJava2D(start, dataArea,
                plot.getDomainAxisEdge());
        double x2 = axis.valueToJava2D(end, dataArea,
                plot.getDomainAxisEdge());
        Rectangle2D band;
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
            band = new Rectangle2D.Double(Math.min(x1, x2), dataArea.getMinY(),
                    Math.abs(x2 - x1), dataArea.getHeight());
        }
        else {
            band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(x1, x2),
                    dataArea.getWidth(), Math.abs(x2 - x1));
        }
        Paint paint = plot.getDomainTickBandPaint();

        if (paint != null) {
            g2.setPaint(paint);
            g2.fill(band);
        }

    }

    /**
     * Fills a band between two values on the range axis.  This can be used to
     * color bands between the grid lines.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the range axis.
     * @param dataArea  the data area.
     * @param start  the start value.
     * @param end  the end value.
     */
    @Override
    public void fillRangeGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double start, double end) {

        double y1 = axis.valueToJava2D(start, dataArea,
                plot.getRangeAxisEdge());
        double y2 = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
        Rectangle2D band;
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
            band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(y1, y2),
                dataArea.getWidth(), Math.abs(y2 - y1));
        }
        else {
            band = new Rectangle2D.Double(Math.min(y1, y2), dataArea.getMinY(),
                    Math.abs(y2 - y1), dataArea.getHeight());
        }
        Paint paint = plot.getRangeTickBandPaint();

        if (paint != null) {
            g2.setPaint(paint);
            g2.fill(band);
        }

    }

    /**
     * Draws a line perpendicular to the domain axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data (not yet adjusted for any 3D
     *                  effect).
     * @param value  the value at which the grid line should be drawn.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @since 1.0.5
     */
    public void drawDomainLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {

        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();
        Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, 
                plot.getDomainAxisEdge());
        if (orientation.isHorizontal()) {
            line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(),
                    v);
        } else if (orientation.isVertical()) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(line);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }

    /**
     * Draws a line perpendicular to the range axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data (not yet adjusted for any 3D
     *                  effect).
     * @param value  the value at which the grid line should be drawn.
     * @param paint  the paint.
     * @param stroke  the stroke.
     */
    @Override
    public void drawRangeLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {

        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();
        Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());      
        if (orientation == PlotOrientation.HORIZONTAL) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        } else if (orientation == PlotOrientation.VERTICAL) {
            line = new Line2D.Double(dataArea.getMinX(), v,
                    dataArea.getMaxX(), v);
        }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(line);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }

    /**
     * Draws a line on the chart perpendicular to the x-axis to mark
     * a value or range of values.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param marker  the marker line.
     * @param dataArea  the axis data area.
     */
    @Override
    public void drawDomainMarker(Graphics2D g2, XYPlot plot, 
            ValueAxis domainAxis, Marker marker, Rectangle2D dataArea) {

        if (marker instanceof ValueMarker) {
            drawValueMarker(marker, g2, plot, domainAxis, dataArea, false);
        } else if (marker instanceof IntervalMarker) {
            drawIntervalMarker(marker, g2, plot, domainAxis, dataArea, false);
        }
    }

    private PlotOrientation getDrawMarkerPlotOrientation(XYPlot plot, boolean flip) {
        PlotOrientation orientation = plot.getOrientation();
        if(flip) {
            if(orientation == PlotOrientation.HORIZONTAL) {
                orientation = PlotOrientation.VERTICAL;
            } else if(orientation == PlotOrientation.VERTICAL) {
                orientation = PlotOrientation.HORIZONTAL;
            }
        }
        return orientation;
    }

    private void drawValueMarker(Marker marker, Graphics2D g2, XYPlot plot,
                                 ValueAxis axis, Rectangle2D dataArea, boolean flipToRange) {
        ValueMarker vm = (ValueMarker) marker;
        double value = vm.getValue();
        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        RectangleEdge axisEdge = plot.getDomainAxisEdge();
        if(flipToRange) {
            axisEdge = plot.getRangeAxisEdge();
        }

        double v = axis.valueToJava2D(value, dataArea, axisEdge);
        PlotOrientation orientation = getDrawMarkerPlotOrientation(plot, flipToRange);
        Line2D line = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            line = new Line2D.Double(dataArea.getMinX(), v,
                    dataArea.getMaxX(), v);
        } else if (orientation == PlotOrientation.VERTICAL) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        } else {
            throw new IllegalStateException("Unrecognised orientation.");
        }

        final Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, marker.getAlpha()));
        g2.setPaint(marker.getPaint());
        g2.setStroke(marker.getStroke());
        g2.draw(line);

        String label = marker.getLabel();
        RectangleAnchor anchor = marker.getLabelAnchor();
        if (label != null) {
            Font labelFont = marker.getLabelFont();
            g2.setFont(labelFont);
            Point2D coords = calculateDomainMarkerTextAnchorPoint(
                    g2, orientation, dataArea, line.getBounds2D(),
                    marker.getLabelOffset(),
                    LengthAdjustmentType.EXPAND, anchor);
            Rectangle2D r = TextUtils.calcAlignedStringBounds(label,
                    g2, (float) coords.getX(), (float) coords.getY(),
                    marker.getLabelTextAnchor());
            g2.setPaint(marker.getLabelBackgroundColor());
            g2.fill(r);
            g2.setPaint(marker.getLabelPaint());
            TextUtils.drawAlignedString(label, g2,
                    (float) coords.getX(), (float) coords.getY(),
                    marker.getLabelTextAnchor());
        }
        g2.setComposite(originalComposite);
    }

    private void drawIntervalMarker(Marker marker, Graphics2D g2, XYPlot plot, ValueAxis axis,
                                    Rectangle2D dataArea, boolean flipToRange) {
        IntervalMarker im = (IntervalMarker) marker;
        double start = im.getStartValue();
        double end = im.getEndValue();
        Range range = axis.getRange();
        if (!(range.intersects(start, end))) {
            return;
        }
        RectangleEdge axisEdge = plot.getDomainAxisEdge();
        if(flipToRange) {
            axisEdge = plot.getRangeAxisEdge();
        }

        double start2d = axis.valueToJava2D(start, dataArea, axisEdge);
        double end2d = axis.valueToJava2D(end, dataArea, axisEdge);
        double low = Math.min(start2d, end2d);
        double high = Math.max(start2d, end2d);

        PlotOrientation orientation = getDrawMarkerPlotOrientation(plot, flipToRange);
        Rectangle2D rect = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            // clip top and bottom bounds to data area
            low = Math.max(low, dataArea.getMinY());
            high = Math.min(high, dataArea.getMaxY());
            rect = new Rectangle2D.Double(dataArea.getMinX(),
                    low, dataArea.getWidth(),
                    high - low);
        } else if (orientation == PlotOrientation.VERTICAL) {
            // clip left and right bounds to data area
            low = Math.max(low, dataArea.getMinX());
            high = Math.min(high, dataArea.getMaxX());
            rect = new Rectangle2D.Double(low,
                    dataArea.getMinY(), high - low,
                    dataArea.getHeight());
        }

        final Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, marker.getAlpha()));
        Paint p = marker.getPaint();
        if (p instanceof GradientPaint) {
            GradientPaint gp = (GradientPaint) p;
            GradientPaintTransformer t = im.getGradientPaintTransformer();
            if (t != null) {
                gp = t.transform(gp, rect);
            }
            g2.setPaint(gp);
        } else {
            g2.setPaint(p);
        }
        g2.fill(rect);

        // now draw the outlines, if visible...
        if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
            if (orientation == PlotOrientation.VERTICAL) {
                Line2D line = new Line2D.Double();
                double y0 = dataArea.getMinY();
                double y1 = dataArea.getMaxY();
                g2.setPaint(im.getOutlinePaint());
                g2.setStroke(im.getOutlineStroke());
                if (range.contains(start)) {
                    line.setLine(start2d, y0, start2d, y1);
                    g2.draw(line);
                }
                if (range.contains(end)) {
                    line.setLine(end2d, y0, end2d, y1);
                    g2.draw(line);
                }
            } else { // PlotOrientation.HORIZONTAL
                Line2D line = new Line2D.Double();
                double x0 = dataArea.getMinX();
                double x1 = dataArea.getMaxX();
                g2.setPaint(im.getOutlinePaint());
                g2.setStroke(im.getOutlineStroke());
                if (range.contains(start)) {
                    line.setLine(x0, start2d, x1, start2d);
                    g2.draw(line);
                }
                if (range.contains(end)) {
                    line.setLine(x0, end2d, x1, end2d);
                    g2.draw(line);
                }
            }

        }

        String label = marker.getLabel();
        RectangleAnchor anchor = marker.getLabelAnchor();
        if (label != null) {
            Font labelFont = marker.getLabelFont();
            g2.setFont(labelFont);
            Point2D coords;
            if(flipToRange) {
                coords = calculateRangeMarkerTextAnchorPoint(
                        g2, orientation, dataArea, rect,
                        marker.getLabelOffset(), marker.getLabelOffsetType(),
                        anchor);
            } else {
                coords = calculateDomainMarkerTextAnchorPoint(
                        g2, orientation, dataArea, rect,
                        marker.getLabelOffset(), marker.getLabelOffsetType(),
                        anchor);
            }
            Rectangle2D r = TextUtils.calcAlignedStringBounds(label,
                    g2, (float) coords.getX(), (float) coords.getY(),
                    marker.getLabelTextAnchor());
            g2.setPaint(marker.getLabelBackgroundColor());
            g2.fill(r);
            g2.setPaint(marker.getLabelPaint());
            TextUtils.drawAlignedString(label, g2,
                    (float) coords.getX(), (float) coords.getY(),
                    marker.getLabelTextAnchor());
        }
        g2.setComposite(originalComposite);
    }

    /**
     * Calculates the {@code (x, y)} coordinates for drawing a marker label.
     *
     * @param g2  the graphics device.
     * @param orientation  the plot orientation.
     * @param dataArea  the data area.
     * @param markerArea  the rectangle surrounding the marker area.
     * @param markerOffset  the marker label offset.
     * @param labelOffsetType  the label offset type.
     * @param anchor  the label anchor.
     *
     * @return The coordinates for drawing the marker label.
     */
    protected Point2D calculateDomainMarkerTextAnchorPoint(Graphics2D g2,
            PlotOrientation orientation, Rectangle2D dataArea,
            Rectangle2D markerArea, RectangleInsets markerOffset,
            LengthAdjustmentType labelOffsetType, RectangleAnchor anchor) {

        Rectangle2D anchorRect = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    LengthAdjustmentType.CONTRACT, labelOffsetType);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    labelOffsetType, LengthAdjustmentType.CONTRACT);
        }
        return anchor.getAnchorPoint(anchorRect);

    }

    /**
     * Draws a line on the chart perpendicular to the y-axis to mark a value
     * or range of values.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param rangeAxis  the range axis.
     * @param marker  the marker line.
     * @param dataArea  the axis data area.
     */
    @Override
    public void drawRangeMarker(Graphics2D g2, XYPlot plot, ValueAxis rangeAxis,
            Marker marker, Rectangle2D dataArea) {

        if (marker instanceof ValueMarker) {
            drawValueMarker(marker, g2, plot, rangeAxis, dataArea, true);
        } else if (marker instanceof IntervalMarker) {
            drawIntervalMarker(marker, g2, plot, rangeAxis, dataArea, true);
        }
    }

    /**
     * Calculates the (x, y) coordinates for drawing a marker label.
     *
     * @param g2  the graphics device.
     * @param orientation  the plot orientation.
     * @param dataArea  the data area.
     * @param markerArea  the marker area.
     * @param markerOffset  the marker offset.
     * @param labelOffsetForRange  ??
     * @param anchor  the label anchor.
     *
     * @return The coordinates for drawing the marker label.
     */
    private Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2,
           PlotOrientation orientation, Rectangle2D dataArea,
           Rectangle2D markerArea, RectangleInsets markerOffset,
           LengthAdjustmentType labelOffsetForRange, RectangleAnchor anchor) {

        Rectangle2D anchorRect = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    labelOffsetForRange, LengthAdjustmentType.CONTRACT);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    LengthAdjustmentType.CONTRACT, labelOffsetForRange);
        }
        return anchor.getAnchorPoint(anchorRect);

    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the renderer does not support
     *         cloning.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractXYItemRenderer clone = (AbstractXYItemRenderer) super.clone();
        // 'plot' : just retain reference, not a deep copy

        clone.itemLabelGeneratorMap = CloneUtils.cloneMapValues(this.itemLabelGeneratorMap);
        clone.defaultItemLabelGenerator = CloneUtils.clone(this.defaultItemLabelGenerator);

        clone.toolTipGeneratorMap = CloneUtils.cloneMapValues(this.toolTipGeneratorMap);

        clone.defaultToolTipGenerator = CloneUtils.clone(this.defaultToolTipGenerator);

        clone.legendItemLabelGenerator = CloneUtils.clone(this.legendItemLabelGenerator);

        clone.legendItemToolTipGenerator = CloneUtils.clone(this.legendItemToolTipGenerator);

        clone.legendItemURLGenerator = CloneUtils.clone(this.legendItemURLGenerator);

        clone.foregroundAnnotations = CloneUtils.cloneList(this.foregroundAnnotations);
        clone.backgroundAnnotations = CloneUtils.cloneList(this.backgroundAnnotations);

        return clone;
    }

    /**
     * Tests this renderer for equality with another object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractXYItemRenderer)) {
            return false;
        }
        AbstractXYItemRenderer that = (AbstractXYItemRenderer) obj;
        if (!this.itemLabelGeneratorMap.equals(that.itemLabelGeneratorMap)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultItemLabelGenerator,
                that.defaultItemLabelGenerator)) {
            return false;
        }
        if (!this.toolTipGeneratorMap.equals(that.toolTipGeneratorMap)) {
            return false;
        }
        if (!ObjectUtils.equal(this.defaultToolTipGenerator,
                that.defaultToolTipGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.urlGenerator, that.urlGenerator)) {
            return false;
        }
        if (!this.foregroundAnnotations.equals(that.foregroundAnnotations)) {
            return false;
        }
        if (!this.backgroundAnnotations.equals(that.backgroundAnnotations)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendItemLabelGenerator,
                that.legendItemLabelGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendItemToolTipGenerator,
                that.legendItemToolTipGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendItemURLGenerator,
                that.legendItemURLGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns the drawing supplier from the plot.
     *
     * @return The drawing supplier (possibly {@code null}).
     */
    @Override
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        XYPlot p = getPlot();
        if (p != null) {
            result = p.getDrawingSupplier();
        }
        return result;
    }

    /**
     * Considers the current (x, y) coordinate and updates the crosshair point
     * if it meets the criteria (usually means the (x, y) coordinate is the
     * closest to the anchor point so far).
     *
     * @param crosshairState  the crosshair state ({@code null} permitted,
     *                        but the method does nothing in that case).
     * @param x  the x-value (in data space).
     * @param y  the y-value (in data space).
     * @param datasetIndex  the index of the dataset for the point.
     * @param transX  the x-value translated to Java2D space.
     * @param transY  the y-value translated to Java2D space.
     * @param orientation  the plot orientation ({@code null} not
     *                     permitted).
     *
     * @since 1.0.20
     */
    protected void updateCrosshairValues(CrosshairState crosshairState,
            double x, double y, int datasetIndex,
            double transX, double transY, PlotOrientation orientation) {

        Args.nullNotPermitted(orientation, "orientation");
        if (crosshairState != null) {
            // do we need to update the crosshair values?
            if (this.plot.isDomainCrosshairLockedOnData()) {
                if (this.plot.isRangeCrosshairLockedOnData()) {
                    // both axes
                    crosshairState.updateCrosshairPoint(x, y, datasetIndex,
                            transX, transY, orientation);
                }
                else {
                    // just the domain axis...
                    crosshairState.updateCrosshairX(x, transX, datasetIndex);
                }
            }
            else {
                if (this.plot.isRangeCrosshairLockedOnData()) {
                    // just the range axis...
                    crosshairState.updateCrosshairY(y, transY, datasetIndex);
                }
            }
        }

    }

    /**
     * Draws an item label.
     *
     * @param g2  the graphics device.
     * @param orientation  the orientation.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param x  the x coordinate (in Java2D space).
     * @param y  the y coordinate (in Java2D space).
     * @param negative  indicates a negative value (which affects the item
     *                  label position).
     */
    protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation,
            XYDataset dataset, int series, int item, double x, double y,
            boolean negative) {

        XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
        if (generator != null) {
            Font labelFont = getItemLabel().getItemLabelFont(series, item);
            Paint paint = getItemLabel().getItemLabelPaint(series, item);
            g2.setFont(labelFont);
            g2.setPaint(paint);
            String label = generator.generateLabel(dataset, series, item);

            // get the label position..
            ItemLabelPosition position;
            if (!negative) {
                position = getItemLabel().getPositiveItemLabelPosition(series, item);
            }
            else {
                position = getItemLabel().getNegativeItemLabelPosition(series, item);
            }

            // work out the label anchor point...
            Point2D anchorPoint = ItemLabelAnchor.calculateLabelAnchorPoint(
                    position.getItemLabelAnchor(), x, y, orientation, getItemLabel().getItemLabelAnchorOffset());
            TextUtils.drawRotatedString(label, g2,
                    (float) anchorPoint.getX(), (float) anchorPoint.getY(),
                    position.getTextAnchor(), position.getAngle(),
                    position.getRotationAnchor());
        }

    }

    /**
     * Draws all the annotations for the specified layer.
     *
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param layer  the layer.
     * @param info  the plot rendering info.
     */
    @Override
    public void drawAnnotations(Graphics2D g2, Rectangle2D dataArea,
            ValueAxis domainAxis, ValueAxis rangeAxis, Layer layer,
            PlotRenderingInfo info) {

        List<XYAnnotation> toDraw = new ArrayList<XYAnnotation>();
        if (layer.equals(Layer.FOREGROUND)) {
            toDraw.addAll(this.foregroundAnnotations);
        }
        else if (layer.equals(Layer.BACKGROUND)) {
            toDraw.addAll(this.backgroundAnnotations);
        }
        else {
            // should not get here
            throw new RuntimeException("Unknown layer.");
        }
        int index = this.plot.getIndexOf(this);
        for (XYAnnotation annotation : toDraw) {
            annotation.draw(g2, this.plot, dataArea, domainAxis, rangeAxis,
                    index, info);
        }

    }

    /**
     * Adds an entity to the collection.  Note the the {@code entityX} and
     * {@code entityY} coordinates are in Java2D space, should already be 
     * adjusted for the plot orientation, and will only be used if 
     * {@code hotspot} is {@code null}.
     *
     * @param entities  the entity collection being populated.
     * @param hotspot  the entity area (if {@code null} a default will be
     *              used).
     * @param dataset  the dataset.
     * @param series  the series.
     * @param item  the item.
     * @param entityX  the entity x-coordinate (in Java2D space, only used if 
     *         {@code hotspot} is {@code null}).
     * @param entityY  the entity y-coordinate (in Java2D space, only used if 
     *         {@code hotspot} is {@code null}).
     */
    protected void addEntity(EntityCollection entities, Shape hotspot,
            XYDataset dataset, int series, int item, double entityX, 
            double entityY) {
        
        if (!getItemCreateEntity(series, item)) {
            return;
        }

        // if not hotspot is provided, we create a default based on the 
        // provided data coordinates (which are already in Java2D space)
        if (hotspot == null) {
            double r = getDefaultEntityRadius();
            double w = r * 2;
            hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
        }
        String tip = null;
        XYToolTipGenerator generator = getToolTipGenerator(series, item);
        if (generator != null) {
            tip = generator.generateToolTip(dataset, series, item);
        }
        String url = null;
        if (getURLGenerator() != null) {
            url = getURLGenerator().generateURL(dataset, series, item);
        }
        XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item,
                tip, url);
        entities.add(entity);
    }

    /**
     * Utility method delegating to {@link GeneralPath#moveTo} taking double as
     * parameters.
     *
     * @param hotspot  the region under construction ({@code null} not 
     *           permitted);
     * @param x  the x coordinate;
     * @param y  the y coordinate;
     *
     * @since 1.0.14
     */
    protected static void moveTo(GeneralPath hotspot, double x, double y) {
        hotspot.moveTo((float) x, (float) y);
    }

    /**
     * Utility method delegating to {@link GeneralPath#lineTo} taking double as
     * parameters.
     *
     * @param hotspot  the region under construction ({@code null} not 
     *           permitted);
     * @param x  the x coordinate;
     * @param y  the y coordinate;
     *
     * @since 1.0.14
     */
    protected static void lineTo(GeneralPath hotspot, double x, double y) {
        hotspot.lineTo((float) x, (float) y);
    }
 
}

