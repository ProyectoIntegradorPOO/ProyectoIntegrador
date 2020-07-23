// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Paint;
import java.awt.GradientPaint;
import java.awt.geom.GeneralPath;
import java.util.Map;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelCurves extends JPanel
{
    protected RenderingHints hints;
    protected int counter;
    protected Color start;
    protected Color end;
    
    public PanelCurves() {
        this(new BorderLayout());
        this.hints = this.createRenderingHints();
        this.startAnimation();
    }
    
    public PanelCurves(final LayoutManager manager) {
        super(manager);
        this.counter = 0;
        this.start = new Color(255, 255, 255, 200);
        this.end = new Color(255, 255, 255, 0);
        this.hints = this.createRenderingHints();
    }
    
    private void startAnimation() {
        final Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PanelCurves.this.animate();
                PanelCurves.this.repaint();
            }
        });
        timer.start();
    }
    
    protected RenderingHints createRenderingHints() {
        final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return renderHints;
    }
    
    public void animate() {
        ++this.counter;
    }
    
    @Override
    public boolean isOpaque() {
        return false;
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g;
        final RenderingHints oldHints = g2.getRenderingHints();
        g2.setRenderingHints(this.hints);
        final float width = (float)this.getWidth();
        final float height = (float)this.getHeight();
        g2.translate(0, -30);
        this.drawCurve(g2, 20.0f, -10.0f, 20.0f, -10.0f, width / 2.0f - 40.0f, 10.0f, 0.0f, -5.0f, width / 2.0f + 40.0f, 1.0f, 0.0f, 5.0f, 50.0f, 5.0f, false);
        g2.translate(0, 30);
        g2.translate(0.0, height - 60.0f);
        this.drawCurve(g2, 30.0f, -15.0f, 50.0f, 15.0f, width / 2.0f - 40.0f, 1.0f, 15.0f, -25.0f, width / 2.0f, 0.5f, 0.0f, 25.0f, 15.0f, 9.0f, false);
        g2.translate(0.0, -height + 60.0f);
        this.drawCurve(g2, height - 35.0f, -5.0f, height - 50.0f, 10.0f, width / 2.0f - 40.0f, 1.0f, height - 35.0f, -25.0f, width / 2.0f, 0.5f, height - 20.0f, 25.0f, 25.0f, 7.0f, true);
        g2.setRenderingHints(oldHints);
    }
    
    protected void drawCurve(final Graphics2D g2, final float y1, final float y1_offset, final float y2, final float y2_offset, final float cx1, final float cx1_offset, final float cy1, final float cy1_offset, final float cx2, final float cx2_offset, final float cy2, final float cy2_offset, final float thickness, final float speed, final boolean invert) {
        final float width = (float)this.getWidth();
        final float offset = (float)Math.sin(this.counter / (speed * 3.141592653589793));
        final float start_x = 0.0f;
        final float start_y = offset * y1_offset + y1;
        final float end_x = width;
        final float end_y = offset * y2_offset + y2;
        final float ctrl1_x = offset * cx1_offset + cx1;
        final float ctrl1_y = offset * cy1_offset + cy1;
        final float ctrl2_x = offset * cx2_offset + cx2;
        final float ctrl2_y = offset * cy2_offset + cy2;
        final GeneralPath thickCurve = new GeneralPath();
        thickCurve.moveTo(start_x, start_y);
        thickCurve.curveTo(ctrl1_x, ctrl1_y, ctrl2_x, ctrl2_y, end_x, end_y);
        thickCurve.lineTo(end_x, end_y + thickness);
        thickCurve.curveTo(ctrl2_x, ctrl2_y + thickness, ctrl1_x, ctrl1_y + thickness, start_x, start_y + thickness);
        thickCurve.lineTo(start_x, start_y);
        final Rectangle bounds = thickCurve.getBounds();
        if (!bounds.intersects(g2.getClipBounds())) {
            return;
        }
        final GradientPaint painter = new GradientPaint(0.0f, (float)bounds.y, invert ? this.end : this.start, 0.0f, (float)(bounds.y + bounds.height), invert ? this.start : this.end);
        final Paint oldPainter = g2.getPaint();
        g2.setPaint(painter);
        g2.fill(thickCurve);
        g2.setPaint(oldPainter);
    }
}
