// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing.utils;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public abstract class AbstractFilter implements BufferedImageOp
{
    @Override
    public abstract BufferedImage filter(final BufferedImage p0, final BufferedImage p1);
    
    @Override
    public Rectangle2D getBounds2D(final BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }
    
    @Override
    public BufferedImage createCompatibleDestImage(final BufferedImage src, ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }
        return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
    }
    
    @Override
    public Point2D getPoint2D(final Point2D srcPt, final Point2D dstPt) {
        return (Point2D)srcPt.clone();
    }
    
    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
}
