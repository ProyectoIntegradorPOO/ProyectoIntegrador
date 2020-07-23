// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing.label;

import igu.util.swing.utils.ColorTintFilter;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.Paint;
import java.awt.font.TextLayout;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import igu.util.swing.tabbedPane.TabbedPaneHeader;
import java.awt.Font;
import javax.swing.Icon;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JLabel;

public class LabelHeader extends JLabel
{
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Color colorDeSombra;
    private Color color;
    private int direccionDeSombra;
    private int distanciaDeSombra;
    private boolean vertical;
    protected BufferedImage background;
    private boolean colored;
    
    public LabelHeader(final Icon icon) {
        super(icon);
        this.colorDeSombra = new Color(0, 0, 0);
        this.color = new Color(0, 0, 0);
        this.direccionDeSombra = 60;
        this.distanciaDeSombra = 1;
        this.vertical = true;
        this.colored = false;
    }
    
    public LabelHeader() {
        this.colorDeSombra = new Color(0, 0, 0);
        this.color = new Color(0, 0, 0);
        this.direccionDeSombra = 60;
        this.distanciaDeSombra = 1;
        this.vertical = true;
        this.setOpaque(this.colored = false);
        this.background = loadImage("/resources/header-background.png");
        this.setFont(new Font("Arial", 1, 14));
        this.setForeground(new Color(255, 255, 255));
    }
    
    private void computeShadow() {
        final double rads = Math.toRadians(this.direccionDeSombra);
        this.shadowOffsetX = (float)Math.cos(rads) * this.distanciaDeSombra;
        this.shadowOffsetY = (float)Math.sin(rads) * this.distanciaDeSombra;
    }
    
    private static BufferedImage loadImage(final String fileName) {
        try {
            return ImageIO.read(TabbedPaneHeader.class.getResource("/igu/imgs/icons/minimizeIcon - copia.png"));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        this.computeShadow();
        final Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Paint oldPaint = g2.getPaint();
        g2.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
        final FontMetrics fm = this.getFontMetrics(this.getFont());
        final TextLayout layout = new TextLayout(this.getText(), this.getFont(), g2.getFontRenderContext());
        final Rectangle2D bounds = layout.getBounds();
        final int x = (int)(this.getWidth() - bounds.getWidth()) / 2;
        int y = (this.getHeight() - fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;
        g2.setColor(this.colorDeSombra);
        layout.draw(g2, (float)(x + (int)Math.ceil(this.shadowOffsetX)), (float)(y + (int)Math.ceil(this.shadowOffsetY)));
        g2.setColor(this.getForeground());
        layout.draw(g2, (float)x, (float)y);
        g2.setPaint(oldPaint);
    }
    
    @Override
    protected void paintBorder(final Graphics g) {
    }
    
    public Color getColorDeSombra() {
        return this.colorDeSombra;
    }
    
    public void setColorDeSombra(final Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
        this.repaint();
    }
    
    public int getDireccionDeSombra() {
        return this.direccionDeSombra;
    }
    
    public void setDireccionDeSombra(final int direccionDeSombra) {
        this.direccionDeSombra = direccionDeSombra;
        this.repaint();
    }
    
    public int getDistanciaDeSombra() {
        return this.distanciaDeSombra;
    }
    
    public void setDistanciaDeSombra(final int distanciaDeSombra) {
        this.distanciaDeSombra = distanciaDeSombra;
        this.repaint();
    }
    
    public boolean isVertical() {
        return this.vertical;
    }
    
    public void setVertical(final boolean vertical) {
        this.vertical = vertical;
        this.repaint();
    }
    
    public boolean isColored() {
        return this.colored;
    }
    
    public void setColored(final boolean colored) {
        this.colored = colored;
        final ColorTintFilter tint = new ColorTintFilter(this.getColor(), 0.5f);
        this.background = loadImage("/resources/header-background.png");
        if (colored) {
            tint.filter(this.background, this.background);
        }
        this.repaint();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(final Color color) {
        this.color = color;
    }
}
