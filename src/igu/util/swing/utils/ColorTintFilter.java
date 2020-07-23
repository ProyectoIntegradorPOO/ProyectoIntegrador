// 
// Decompiled by Procyon v0.5.36
// 

package igu.util.swing.utils;

import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class ColorTintFilter extends AbstractFilter
{
    private final Color mixColor;
    private final float mixValue;
    private int[] preMultipliedRed;
    private int[] preMultipliedGreen;
    private int[] preMultipliedBlue;
    
    public ColorTintFilter(final Color mixColor, float mixValue) {
        if (mixColor == null) {
            throw new IllegalArgumentException("mixColor cannot be null");
        }
        this.mixColor = mixColor;
        if (mixValue < 0.0f) {
            mixValue = 0.0f;
        }
        else if (mixValue > 1.0f) {
            mixValue = 1.0f;
        }
        this.mixValue = mixValue;
        final int mix_r = (int)(mixColor.getRed() * mixValue);
        final int mix_g = (int)(mixColor.getGreen() * mixValue);
        final int mix_b = (int)(mixColor.getBlue() * mixValue);
        final float factor = 1.0f - mixValue;
        this.preMultipliedRed = new int[256];
        this.preMultipliedGreen = new int[256];
        this.preMultipliedBlue = new int[256];
        for (int i = 0; i < 256; ++i) {
            final int value = (int)(i * factor);
            this.preMultipliedRed[i] = value + mix_r;
            this.preMultipliedGreen[i] = value + mix_g;
            this.preMultipliedBlue[i] = value + mix_b;
        }
    }
    
    public float getMixValue() {
        return this.mixValue;
    }
    
    public Color getMixColor() {
        return this.mixColor;
    }
    
    @Override
    public BufferedImage filter(final BufferedImage src, BufferedImage dst) {
        if (dst == null) {
            dst = this.createCompatibleDestImage(src, null);
        }
        final int width = src.getWidth();
        final int height = src.getHeight();
        final int[] pixels = new int[width * height];
        GraphicsUtilities.getPixels(src, 0, 0, width, height, pixels);
        this.mixColor(pixels);
        GraphicsUtilities.setPixels(dst, 0, 0, width, height, pixels);
        return dst;
    }
    
    private void mixColor(final int[] pixels) {
        for (int i = 0; i < pixels.length; ++i) {
            final int argb = pixels[i];
            pixels[i] = ((argb & 0xFF000000) | this.preMultipliedRed[argb >> 16 & 0xFF] << 16 | this.preMultipliedGreen[argb >> 8 & 0xFF] << 8 | this.preMultipliedBlue[argb & 0xFF]);
        }
    }
}
