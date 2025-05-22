/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * ----------------------
 * MouseWheelHandler.java
 * ----------------------
 * (C) Copyright 2009-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Ulrich Voigt - patch 2686040;
 *                   Jim Goodwin - bug fix;
 * 
 * Changes
 * -------
 * 13-Jan-2025 : make it compatible with ChartComposite and eclipse swt (RS);
 */

 package org.jfree.chart.swt;

 import org.eclipse.swt.events.MouseEvent;
 import org.eclipse.swt.events.MouseWheelListener;

import java.awt.Point;
import java.awt.geom.Point2D;
 import java.io.Serializable;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
 import org.jfree.chart.plot.Plot;
 import org.jfree.chart.plot.PlotRenderingInfo;
 import org.jfree.chart.plot.Zoomable;
 
 /**
  * A class that handles mouse wheel events for the {@link ChartPanel} class.
  */
 public class MouseWheelHandler implements MouseWheelListener, Serializable {
 
     /** The chart panel. */
     private transient ChartComposite chartComposite;
 
     /** The zoom factor. */
     double zoomFactor;
 
     /**
      * Creates a new instance for the specified chart panel.
      *
      * @param chartPanel  the chart panel ({@code null} not permitted).
      */
     public MouseWheelHandler(ChartComposite chartPanel) {
         this.chartComposite = chartPanel;
         this.zoomFactor = 0.10;
         this.chartComposite.addMouseWheelListener(this);
     }
 
     /**
      * Returns the current zoom factor.  The default value is 0.10 (ten
      * percent).
      *
      * @return The zoom factor.
      *
      * @see #setZoomFactor(double)
      */
     public double getZoomFactor() {
         return this.zoomFactor;
     }
 
     /**
      * Sets the zoom factor.
      *
      * @param zoomFactor  the zoom factor.
      *
      * @see #getZoomFactor()
      */
     public void setZoomFactor(double zoomFactor) {
         this.zoomFactor = zoomFactor;
     }
 
     /**
      * Handle the case where a plot implements the {@link Zoomable} interface.
      *
      * @param zoomable  the zoomable plot.
      * @param e  the mouse wheel event.
      */
     private void handleZoomable(Zoomable zoomable, MouseEvent e) {
         // don't zoom unless the mouse pointer is in the plot's data area
         ChartRenderingInfo info = this.chartComposite.getChartRenderingInfo();
         PlotRenderingInfo pinfo = info.getPlotInfo();
         Point2D p = this.chartComposite.translateScreenToJava2D(new Point(e.x, e.y));
         if (!pinfo.getDataArea().contains(p)) {
             return;
         }
 
         Plot plot = (Plot) zoomable;
         // do not notify while zooming each axis
         boolean notifyState = plot.isNotify();
         plot.setNotify(false);
         
         double zf = 1.0 + this.zoomFactor;
         if (e.count < 0) {
             zf = 1.0 / zf;
         }
         if (chartComposite.isDomainZoomable()) {
             zoomable.zoomDomainAxes(zf, pinfo, p, true);
         }
         if (chartComposite.isRangeZoomable()) {
             zoomable.zoomRangeAxes(zf, pinfo, p, true);
         }
         plot.setNotify(notifyState);  // this generates the change event too
     }

    @Override
    public void mouseScrolled(MouseEvent e) {
        JFreeChart chart = this.chartComposite.getChart();
        if (chart == null) {
            return;
        }
        Plot plot = chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable zoomable = (Zoomable) plot;
            handleZoomable(zoomable, e);
        }
        else if (plot instanceof PiePlot) {
            PiePlot pp = (PiePlot) plot;
            pp.handleMouseWheelRotation(e.count);
        }
    }
 
 }