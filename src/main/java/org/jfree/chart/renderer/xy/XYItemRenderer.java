/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2017, by Object Refinery Limited and Contributors.
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
 * -------------------
 * XYItemRenderer.java
 * -------------------
 * (C) Copyright 2001-2017, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Mark Watson (www.markwatson.com);
 *                   Sylvain Vieujot;
 *                   Focus Computer Services Limited;
 *                   Richard Atkinson;
 *
 * Changes
 * -------
 * 19-Oct-2001 : Version 1, based on code by Mark Watson (DG);
 * 22-Oct-2001 : Renamed DataSource.java --> Dataset.java etc. (DG);
 * 13-Dec-2001 : Changed return type of drawItem from void --> Shape.  The area
 *               returned can be used as the tooltip region.
 * 23-Jan-2002 : Added DrawInfo parameter to drawItem() method (DG);
 * 28-Mar-2002 : Added a property change listener mechanism.  Now renderers do
 *               not have to be immutable (DG);
 * 04-Apr-2002 : Added the initialise() method (DG);
 * 09-Apr-2002 : Removed the translated zero from the drawItem method, it can
 *               be calculated inside the initialise method if it is required.
 *               Added a new getToolTipGenerator() method.  Changed the return
 *               type for drawItem() to void (DG);
 * 24-May-2002 : Added ChartRenderingInfo the initialise method API (DG);
 * 25-Jun-2002 : Removed redundant import (DG);
 * 20-Aug-2002 : Added get/setURLGenerator methods to interface (DG);
 * 02-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 18-Nov-2002 : Added methods for drawing grid lines (DG);
 * 17-Jan-2003 : Moved plot classes into a separate package (DG);
 * 27-Jan-2003 : Added shape lookup table (DG);
 * 05-Jun-2003 : Added domain and range grid bands (sponsored by Focus Computer
 *               Services Ltd) (DG);
 * 27-Jul-2003 : Added getRangeType() to support stacked XY area charts (RA);
 * 16-Sep-2003 : Changed ChartRenderingInfo --> PlotRenderingInfo (DG);
 * 25-Feb-2004 : Replaced CrosshairInfo with CrosshairState.  Renamed
 *               XYToolTipGenerator --> XYItemLabelGenerator (DG);
 * 26-Feb-2004 : Added lots of new methods (DG);
 * 30-Apr-2004 : Added getRangeExtent() method (DG);
 * 06-May-2004 : Added methods for controlling item label visibility (DG);
 * 13-May-2004 : Removed property change listener mechanism (DG);
 * 18-May-2004 : Added item label font and paint methods (DG);
 * 10-Sep-2004 : Removed redundant getRangeType() method (DG);
 * 06-Oct-2004 : Replaced getRangeExtent() with findRangeBounds() and added
 *               findDomainBounds (DG);
 * 23-Nov-2004 : Changed drawRangeGridLine() --> drawRangeLine() (DG);
 * 07-Jan-2005 : Removed deprecated method (DG);
 * 24-Feb-2005 : Now extends LegendItemSource (DG);
 * 20-Apr-2005 : Renamed XYLabelGenerator --> XYItemLabelGenerator (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 19-Apr-2007 : Deprecated seriesVisible and seriesVisibleInLegend flags (DG);
 * 20-Apr-2007 : Deprecated paint, fillPaint, outlinePaint, stroke,
 *               outlineStroke, shape, itemLabelsVisible, itemLabelFont,
 *               itemLabelPaint, positiveItemLabelPosition,
 *               negativeItemLabelPosition and createEntities override
 *               fields (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.*;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;

/**
 * Interface for rendering the visual representation of a single (x, y) item on
 * an {@link XYPlot}.
 * <p>
 * To support cloning charts, it is recommended that renderers implement both
 * the {@link Cloneable} and {@code PublicCloneable} interfaces.
 */
public interface XYItemRenderer extends LegendItemSource {

    /**
     * Returns the series visible render state for the renderer.
     *
     * @return Series visible render state.
     */
    IRenderStateVisibility getVisibility();

    /**
     * Returns the paint render state for the renderer.
     *
     * @return Paint render state.
     */
    IRenderStatePaint getPaint();

    /**
     * Returns the stroke render state for the renderer.
     *
     * @return Stroke render state.
     */
    IRenderStateStroke getStroke();

    /**
     * Returns the shape render state for the renderer.
     *
     * @return Shape render state.
     */
    IRenderStateShape getShape();

    /**
     * Returns the item render state for the renderer.
     *
     * @return Item render state.
     */
    IRenderStateItemLabel getItemLabel();

    /**
     * Returns the plot that this renderer has been assigned to.
     *
     * @return The plot.
     */
    XYPlot getPlot();

    /**
     * Sets the plot that this renderer is assigned to.  This method will be
     * called by the plot class...you do not need to call it yourself.
     *
     * @param plot  the plot.
     */
    void setPlot(XYPlot plot);

    /**
     * Returns the number of passes through the data required by the renderer.
     *
     * @return The pass count.
     */
    int getPassCount();

    /**
     * Returns the lower and upper bounds (range) of the x-values in the
     * specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range.
     */
    Range findDomainBounds(XYDataset dataset);

    /**
     * Returns the lower and upper bounds (range) of the y-values in the
     * specified dataset.  The implementation of this method will take
     * into account the presentation used by the renderers (for example,
     * a renderer that "stacks" values will return a bigger range than
     * a renderer that doesn't).
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range (or {@code null} if the dataset is
     *         {@code null} or empty).
     */
    Range findRangeBounds(XYDataset dataset);

    /**
     * Add a renderer change listener.
     *
     * @param listener  the listener.
     *
     * @see #removeChangeListener(RendererChangeListener)
     */
    void addChangeListener(RendererChangeListener listener);

    /**
     * Removes a change listener.
     *
     * @param listener  the listener.
     *
     * @see #addChangeListener(RendererChangeListener)
     */
    void removeChangeListener(RendererChangeListener listener);

    //// LEGEND ITEMS /////////////////////////////////////////////////////////

    /**
     * Returns a legend item for a series from a dataset.
     *
     * @param datasetIndex  the dataset index.
     * @param series  the series (zero-based index).
     *
     * @return The legend item (possibly {@code null}).
     */
    LegendItem getLegendItem(int datasetIndex, int series);


    //// LEGEND ITEM LABEL GENERATOR //////////////////////////////////////////

    /**
     * Returns the legend item label generator.
     *
     * @return The legend item label generator (never {@code null}).
     *
     * @see #setLegendItemLabelGenerator(XYSeriesLabelGenerator)
     */
    XYSeriesLabelGenerator getLegendItemLabelGenerator();

    /**
     * Sets the legend item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} not permitted).
     */
    void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator);

    void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator, boolean notify);


    //// TOOL TIP GENERATOR ///////////////////////////////////////////////////

    /**
     * Returns the tool tip generator for a data item.
     *
     * @param row  the row index (zero based).
     * @param column  the column index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    XYToolTipGenerator getToolTipGenerator(int row, int column);

    /**
     * Returns the tool tip generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setSeriesToolTipGenerator(int, XYToolTipGenerator)
     */
    XYToolTipGenerator getSeriesToolTipGenerator(int series);

    /**
     * Sets the tool tip generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getSeriesToolTipGenerator(int)
     */
    void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator);

    void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator, boolean notify);

    /**
     * Returns the default tool tip generator.
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setDefaultToolTipGenerator(XYToolTipGenerator)
     */
    XYToolTipGenerator getDefaultToolTipGenerator();

    /**
     * Sets the default tool tip generator and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getDefaultToolTipGenerator()
     */
    void setDefaultToolTipGenerator(XYToolTipGenerator generator);

    void setDefaultToolTipGenerator(XYToolTipGenerator generator, boolean notify);

    //// URL GENERATOR ////////////////////////////////////////////////////////

    /**
     * Returns the URL generator for HTML image maps.
     *
     * @return The URL generator (possibly null).
     */
    XYURLGenerator getURLGenerator();

    /**
     * Sets the URL generator for HTML image maps.
     *
     * @param urlGenerator the URL generator (null permitted).
     */
    void setURLGenerator(XYURLGenerator urlGenerator);

    void setURLGenerator(XYURLGenerator urlGenerator, boolean notify);

    //// ITEM LABEL GENERATOR /////////////////////////////////////////////////

    /**
     * Returns the item label generator for a data item.
     *
     * @param row  the row index (zero based).
     * @param column  the column index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    XYItemLabelGenerator getItemLabelGenerator(int row, int column);

    /**
     * Returns the item label generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setSeriesItemLabelGenerator(int, XYItemLabelGenerator)
     */
    XYItemLabelGenerator getSeriesItemLabelGenerator(int series);

    /**
     * Sets the item label generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getSeriesItemLabelGenerator(int)
     */
    void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator);

    void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator, boolean notify);

    /**
     * Returns the default item label generator.
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setDefaultItemLabelGenerator(XYItemLabelGenerator)
     */
    XYItemLabelGenerator getDefaultItemLabelGenerator();

    /**
     * Sets the default item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getDefaultItemLabelGenerator()
     */
    void setDefaultItemLabelGenerator(XYItemLabelGenerator generator);

    void setDefaultItemLabelGenerator(XYItemLabelGenerator generator, boolean notify);

    // CREATE ENTITIES

    boolean getItemCreateEntity(int series, int item);

    Boolean getSeriesCreateEntities(int series);

    void setSeriesCreateEntities(int series, Boolean create);

    void setSeriesCreateEntities(int series, Boolean create,
                                 boolean notify);

    boolean getDefaultCreateEntities();

    void setDefaultCreateEntities(boolean create);

    void setDefaultCreateEntities(boolean create, boolean notify);

    //// ANNOTATIONS //////////////////////////////////////////////////////////

    /**
     * Adds an annotation and sends a {@link RendererChangeEvent} to all
     * registered listeners.  The annotation is added to the foreground
     * layer.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     */
    void addAnnotation(XYAnnotation annotation);

    /**
     * Adds an annotation to the specified layer.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     * @param layer  the layer ({@code null} not permitted).
     */
    void addAnnotation(XYAnnotation annotation, Layer layer);

    void addAnnotation(XYAnnotation annotation, Layer layer, boolean notify);

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
    boolean removeAnnotation(XYAnnotation annotation);

    boolean removeAnnotation(XYAnnotation annotation, boolean notify);

    /**
     * Removes all annotations and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     */
    void removeAnnotations();

    void removeAnnotations(boolean notify);

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
    void drawAnnotations(Graphics2D g2, Rectangle2D dataArea,
                         ValueAxis domainAxis, ValueAxis rangeAxis, Layer layer,
                         PlotRenderingInfo info);

    //// DRAWING //////////////////////////////////////////////////////////////

    /**
     * Initialises the renderer then returns the number of 'passes' through the
     * data that the renderer will require (usually just one).  This method
     * will be called before the first item is rendered, giving the renderer
     * an opportunity to initialise any state information it wants to maintain.
     * The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param dataset  the dataset.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The number of passes the renderer requires.
     */
    XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea,
                                   XYPlot plot, XYDataset dataset, PlotRenderingInfo info);

    /**
     * Called for each item to be plotted.
     * <p>
     * The {@link XYPlot} can make multiple passes through the dataset,
     * depending on the value returned by the renderer's initialise() method.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area within which the data is being rendered.
     * @param info  collects drawing info.
     * @param plot  the plot (can be used to obtain standard color
     *              information etc).
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param crosshairState  crosshair information for the plot
     *                        ({@code null} permitted).
     * @param pass  the pass index.
     */
    void drawItem(Graphics2D g2, XYItemRendererState state,
                  Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
                  ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
                  int series, int item, CrosshairState crosshairState, int pass);

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
    void fillDomainGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
                            Rectangle2D dataArea, double start, double end);

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
    void fillRangeGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
                           Rectangle2D dataArea, double start, double end);

    /**
     * Draws a grid line against the domain axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data.
     * @param value  the value.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     */
    void drawDomainLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
                        Rectangle2D dataArea, double value, Paint paint, Stroke stroke);

    /**
     * Draws a line perpendicular to the range axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data.
     * @param value  the data value.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     */
    void drawRangeLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
                       Rectangle2D dataArea, double value, Paint paint, Stroke stroke);

    /**
     * Draws the specified {@code marker} against the domain axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param marker  the marker.
     * @param dataArea  the axis data area.
     */
    void drawDomainMarker(Graphics2D g2, XYPlot plot, ValueAxis axis,
                          Marker marker, Rectangle2D dataArea);

    /**
     * Draws a horizontal line across the chart to represent a 'range marker'.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param marker  the marker line.
     * @param dataArea  the axis data area.
     */
    void drawRangeMarker(Graphics2D g2, XYPlot plot, ValueAxis axis,
                         Marker marker, Rectangle2D dataArea);

}
