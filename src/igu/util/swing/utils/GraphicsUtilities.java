// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing.utils;

import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;

public class GraphicsUtilities
{
    private GraphicsUtilities() {
    }
    
    private static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
    
    public static BufferedImage createColorModelCompatibleImage(final BufferedImage image) {
        final ColorModel cm = image.getColorModel();
        return new BufferedImage(cm, cm.createCompatibleWritableRaster(image.getWidth(), image.getHeight()), cm.isAlphaPremultiplied(), null);
    }
    
    public static BufferedImage createCompatibleImage(final BufferedImage image) {
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }
    
    public static BufferedImage createCompatibleImage(final BufferedImage image, final int width, final int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
    }
    
    public static BufferedImage createCompatibleImage(final int width, final int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height);
    }
    
    public static BufferedImage createCompatibleTranslucentImage(final int width, final int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, 3);
    }
    
    public static BufferedImage loadCompatibleImage(final URL resource) throws IOException {
        final BufferedImage image = ImageIO.read(resource);
        return toCompatibleImage(image);
    }
    
    public static BufferedImage toCompatibleImage(final BufferedImage image) {
        if (image.getColorModel().equals(getGraphicsConfiguration().getColorModel())) {
            return image;
        }
        final BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
        final Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return compatibleImage;
    }
    
    public static BufferedImage createThumbnailFast(final BufferedImage image, final int newSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > height) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than the image width");
            }
            if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must be greater than 0");
            }
            final float ratio = width / (float)height;
            width = newSize;
            height = (int)(newSize / ratio);
        }
        else {
            if (newSize >= height) {
                throw new IllegalArgumentException("newSize must be lower than the image height");
            }
            if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must be greater than 0");
            }
            final float ratio = height / (float)width;
            height = newSize;
            width = (int)(newSize / ratio);
        }
        final BufferedImage temp = createCompatibleImage(image, width, height);
        final Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    }
    
    public static BufferedImage createThumbnailFast(final BufferedImage image, final int newWidth, final int newHeight) {
        if (newWidth >= image.getWidth() || newHeight >= image.getHeight()) {
            throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
        }
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
        }
        final BufferedImage temp = createCompatibleImage(image, newWidth, newHeight);
        final Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    }
    
    public static BufferedImage createThumbnail(final BufferedImage image, final int newSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        final boolean isWidthGreater = width > height;
        if (isWidthGreater) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than the image width");
            }
        }
        else if (newSize >= height) {
            throw new IllegalArgumentException("newSize must be lower than the image height");
        }
        if (newSize <= 0) {
            throw new IllegalArgumentException("newSize must be greater than 0");
        }
        final float ratioWH = width / (float)height;
        final float ratioHW = height / (float)width;
        BufferedImage thumb = image;
        BufferedImage temp = null;
        Graphics2D g2 = null;
        int previousWidth = width;
        int previousHeight = height;
        do {
            if (isWidthGreater) {
                width /= 2;
                if (width < newSize) {
                    width = newSize;
                }
                height = (int)(width / ratioWH);
            }
            else {
                height /= 2;
                if (height < newSize) {
                    height = newSize;
                }
                width = (int)(height / ratioHW);
            }
            if (temp == null) {
                temp = createCompatibleImage(image, width, height);
                g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
            g2.drawImage(thumb, 0, 0, width, height, 0, 0, previousWidth, previousHeight, null);
            previousWidth = width;
            previousHeight = height;
            thumb = temp;
        } while (newSize != (isWidthGreater ? width : height));
        g2.dispose();
        if (width != thumb.getWidth() || height != thumb.getHeight()) {
            temp = createCompatibleImage(image, width, height);
            g2 = temp.createGraphics();
            g2.drawImage(thumb, 0, 0, null);
            g2.dispose();
            thumb = temp;
        }
        return thumb;
    }
    
    public static BufferedImage createThumbnail(final BufferedImage image, final int newWidth, final int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (newWidth >= width || newHeight >= height) {
            throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
        }
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
        }
        BufferedImage thumb = image;
        BufferedImage temp = null;
        Graphics2D g2 = null;
        int previousWidth = width;
        int previousHeight = height;
        do {
            if (width > newWidth) {
                width /= 2;
                if (width < newWidth) {
                    width = newWidth;
                }
            }
            if (height > newHeight) {
                height /= 2;
                if (height < newHeight) {
                    height = newHeight;
                }
            }
            if (temp == null) {
                temp = createCompatibleImage(image, width, height);
                g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
            g2.drawImage(thumb, 0, 0, width, height, 0, 0, previousWidth, previousHeight, null);
            previousWidth = width;
            previousHeight = height;
            thumb = temp;
        } while (width != newWidth || height != newHeight);
        g2.dispose();
        if (width != thumb.getWidth() || height != thumb.getHeight()) {
            temp = createCompatibleImage(image, width, height);
            g2 = temp.createGraphics();
            g2.drawImage(thumb, 0, 0, null);
            g2.dispose();
            thumb = temp;
        }
        return thumb;
    }
    
    public static int[] getPixels(final BufferedImage img, final int x, final int y, final int w, final int h, int[] pixels) {
        if (w == 0 || h == 0) {
            return new int[0];
        }
        if (pixels == null) {
            pixels = new int[w * h];
        }
        else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length >= w*h");
        }
        final int imageType = img.getType();
        if (imageType == 2 || imageType == 1) {
            final Raster raster = img.getRaster();
            return (int[])raster.getDataElements(x, y, w, h, pixels);
        }
        return img.getRGB(x, y, w, h, pixels, 0, w);
    }
    
    public static void setPixels(final BufferedImage img, final int x, final int y, final int w, final int h, final int[] pixels) {
        if (pixels == null || w == 0 || h == 0) {
            return;
        }
        if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length >= w*h");
        }
        final int imageType = img.getType();
        if (imageType == 2 || imageType == 1) {
            final WritableRaster raster = img.getRaster();
            raster.setDataElements(x, y, w, h, pixels);
        }
        else {
            img.setRGB(x, y, w, h, pixels, 0, w);
        }
    }
}
