// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing.tabbedPane;

import java.awt.geom.Rectangle2D;
import java.awt.font.TextLayout;
import java.awt.Rectangle;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.FontMetrics;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.plaf.TabbedPaneUI;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JTabbedPane;

public class TabbedPaneHeader extends JTabbedPane
{
    private static final Insets NO_INSETS;
    protected Color colorDeSombra;
    protected BufferedImage left;
    protected BufferedImage right;
    protected BufferedImage buttonHighlight;
    protected BufferedImage background;
    
    public TabbedPaneHeader() {
        this.colorDeSombra = new Color(173, 173, 173);
        this.left = loadImage("/resources/header-slash-left.png");
        this.right = loadImage("/resources/header-slash-right.png");
        this.buttonHighlight = loadImage("/resources/header-halo.png");
        this.background = loadImage("/resources/header-gradient.png");
        this.setFont(new Font("Arial", 1, 14));
        this.setForeground(Color.WHITE);
        this.setUI(new TabbedPaneUI());
    }
    
    private static BufferedImage loadImage(final String fileName) {
        try {
            return ImageIO.read(TabbedPaneHeader.class.getResource(fileName));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public Color getColorDeBorde() {
        return this.colorDeSombra;
    }
    
    public void setColorDeBorde(final Color colorDeBorde) {
        this.colorDeSombra = colorDeBorde;
    }
    
    @Override
    public void setTabPlacement(final int tabPlacement) {
        if (tabPlacement == 1 | tabPlacement == 3) {
            super.setTabPlacement(tabPlacement);
        }
    }
    
    static {
        NO_INSETS = new Insets(0, 0, 0, 0);
    }
    
    private class TabbedPaneUI extends BasicTabbedPaneUI
    {
        private int buttonHeight;
        private int leftInset;
        
        private TabbedPaneUI() {
            this.buttonHeight = TabbedPaneHeader.this.background.getHeight();
            this.leftInset = 0;
        }
        
        @Override
        protected void installComponents() {
            super.installComponents();
        }
        
        @Override
        protected void installDefaults() {
            super.installDefaults();
            this.tabAreaInsets.left = this.leftInset;
            this.selectedTabPadInsets = new Insets(0, 0, 0, 0);
        }
        
        @Override
        public int getTabRunCount(final JTabbedPane pane) {
            return super.getTabRunCount(pane);
        }
        
        @Override
        protected Insets getContentBorderInsets(final int tabPlacement) {
            return TabbedPaneHeader.NO_INSETS;
        }
        
        @Override
        protected int calculateTabHeight(final int tabPlacement, final int tabIndex, final int fontHeight) {
            if (tabPlacement == tabIndex) {
                return this.buttonHeight;
            }
            return this.buttonHeight;
        }
        
        @Override
        protected int calculateTabWidth(final int tabPlacement, final int tabIndex, final FontMetrics metrics) {
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + this.buttonHeight;
        }
        
        @Override
        protected void paintTabArea(final Graphics g, final int tabPlacement, final int selectedIndex) {
            final int x = 0;
            int y = 0;
            final int tw = this.tabPane.getBounds().width;
            int th = this.buttonHeight;
            try {
                th = this.rects[selectedIndex].height;
            }
            catch (Exception ex) {}
            if (tabPlacement == 3) {
                y = TabbedPaneHeader.this.getHeight() - th;
            }
            final Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(this.gradiente(x, y, th));
            g2.drawImage(TabbedPaneHeader.this.background, x, y, tw, th, null);
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }
        
        private Paint gradiente(final int x, final int y, final int th) {
            final LinearGradientPaint gr = new LinearGradientPaint((float)x, (float)y, (float)x, (float)(y + th), new float[] { 0.0f, 0.5f, 1.0f }, new Color[] { Color.white, TabbedPaneHeader.this.getBackground().brighter(), TabbedPaneHeader.this.getBackground() });
            return gr;
        }
        
        @Override
        protected void paintTab(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect, final Rectangle textRect) {
            super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
        }
        
        @Override
        protected void paintTabBorder(final Graphics g, final int tabPlacement, final int tabIndex, final int tx, final int ty, final int tw, final int th, final boolean isSelected) {
            final int count = TabbedPaneHeader.this.getTabCount();
            final Graphics2D g2d = (Graphics2D)g;
            if (tabPlacement == 1) {
                g2d.translate(tx, 0);
            }
            if (tabPlacement == 3) {
                g2d.translate(tx, ty);
            }
            final Graphics2D g2 = (Graphics2D)g.create();
            g2.drawImage(TabbedPaneHeader.this.background, 0, 0, tw, th, null);
            if (tabIndex != count - 1) {
                g2.drawImage(TabbedPaneHeader.this.left, tw - TabbedPaneHeader.this.left.getWidth(), 0, null);
            }
            if (tabIndex != 0) {
                g2.drawImage(TabbedPaneHeader.this.right, 0, 0, null);
            }
            if (isSelected) {
                g2.drawImage(TabbedPaneHeader.this.buttonHighlight, 0, 0, tw, th, null);
            }
            if (tabPlacement == 1) {
                g2d.translate(-1 * tx, 0);
            }
            if (tabPlacement == 3) {
                g2d.translate(-1 * tx, -1 * ty);
            }
        }
        
        @Override
        protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title, final Rectangle textRect, final boolean isSelected) {
            final Rectangle r = this.rects[tabIndex];
            final Graphics2D g2d = (Graphics2D)g;
            if (tabPlacement == 1) {
                g2d.translate(r.x, 0);
            }
            if (tabPlacement == 3) {
                g2d.translate(r.x, r.y);
            }
            final FontMetrics fm = this.getFontMetrics();
            final TextLayout layout = new TextLayout(title, TabbedPaneHeader.this.getFont(), g2d.getFontRenderContext());
            final Rectangle2D bounds = layout.getBounds();
            g2d.setColor(TabbedPaneHeader.this.colorDeSombra);
            final int x = (int)(r.width - bounds.getWidth()) / 2;
            int y = (r.height - fm.getMaxAscent() - fm.getMaxDescent()) / 2;
            y += fm.getAscent() - 1;
            layout.draw(g2d, (float)x, (float)y);
            if (isSelected) {
                g2d.setColor(TabbedPaneHeader.this.getForeground());
            }
            else {
                g2d.setColor(TabbedPaneHeader.this.getForeground().darker());
            }
            layout.draw(g2d, (float)x, (float)y);
            if (tabPlacement == 1) {
                g2d.translate(-1 * r.x, 0);
            }
            if (tabPlacement == 3) {
                g2d.translate(-1 * r.x, -1 * r.y);
            }
        }
        
        @Override
        protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h, final boolean isSelected) {
        }
        
        @Override
        protected void paintFocusIndicator(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
        }
        
        @Override
        protected void paintContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex) {
        }
    }
}
