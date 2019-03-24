package model.images;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import model.Utils;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractImage implements CustomImage {

    protected WritableImage writableImage;
    protected Color[][] colors;
    protected int width;
    protected int height;

    protected RawImage imageWithPaintedArea;

    protected AbstractImage(){}

    // RGB byte array
    void byteArrayToMatrices(byte[] imageBytes){
        colors = new Color[height][width];
        for(int n = 0; n < imageBytes.length; n+= 4) {
            colors[(n/4)/width][(n/4)%width] = new Color(
                    Utils.byteToInt(imageBytes[n]),
                    Utils.byteToInt(imageBytes[n+1]),
                    Utils.byteToInt(imageBytes[n+2]),
                    Utils.byteToInt(imageBytes[n+3])
                    );
        }
    }

    protected void createImage() {
        this.writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = this.writableImage.getPixelWriter();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
    }

    protected void setWidthAndHeight(@NotNull String name) {
        String[] s = name.split("\\.")[0].split("_");
        width = Integer.parseInt(s[1]);
        height = Integer.parseInt(s[2]);
    }


    @Override
    public WritableImage asWritableImage() {
        return imageWithPaintedArea == null ? writableImage : imageWithPaintedArea.asWritableImage();
    }

    @Override
    public void save(@NotNull File file){
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(processBytesForSaving());
        } catch (IOException e) {
            throw new RuntimeException("Exception wringing on file: " + file.getName(),e);
        }
    }

    @Override
    public void modifyPixel(@NotNull int value, @NotNull Point pos) {
        colors[pos.y][pos.x] = new Color(value, value, value, 255);
        PixelWriter pixelWriter = this.writableImage.getPixelWriter();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
    }

    @Override
    public void copySection(@NotNull CustomImage destinationImage, @NotNull Point x1y1, @NotNull Point x2y2, @NotNull Point destinationPos) {
        int x = Math.min(x1y1.x, x2y2.x);
        int y = Math.min(x1y1.y, x2y2.y);
        int w = Math.max(x1y1.y, x2y2.y) - y;
        int h = Math.max(x1y1.x, x2y2.x) - x;
        Color[][] auxColors = new Color[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                auxColors[i][j] = colors[y+i][x+j];
            }
        }
        destinationImage.pasteImage(auxColors, destinationPos);
    }

    @Override
    public void pasteImage(Color[][] newColors, @NotNull Point pos) {
        for(int i = 0; i < newColors.length; i++){
            for(int j = 0; j < newColors[0].length; j++){
                colors[i+pos.y][j+pos.x] = newColors[i][j];
            }
        }
        writableImage.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
    }

    private byte[] processBytesForSaving(){
        byte[] greyScaleBytes = new byte[width*height];
        byte[] imageBytes = getImageBytes();
        for(int i = 0; i < greyScaleBytes.length; i++){
            greyScaleBytes[i] = imageBytes[i*4];
        }
        return greyScaleBytes;
    }

    private byte[] getImageBytes() {
        return getImageBytes(colors);
    }

    private byte[] getImageBytes(Color[][] m1) {
        byte[] imageBytes = new byte[width * height * 4];
        for(int n = 0; n < width * height * 4; n+=4) {
            imageBytes[n]   = (byte)m1[(n/4)/width][(n/4)%width].getBlue();
            imageBytes[n+1] = (byte)m1[(n/4)/width][(n/4)%width].getGreen();
            imageBytes[n+2] = (byte)m1[(n/4)/width][(n/4)%width].getRed();
            imageBytes[n+3] = (byte)m1[(n/4)/width][(n/4)%width].getAlpha();
        }
        return imageBytes;
    }

    public int[] getAverage(@NotNull Point p1, @NotNull Point p2){
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        int averageRed = 0;
        int averageGreen = 0;
        int averageBlue = 0;
        for(int i = y1; i < y2; i++) {
            for(int j = x1; j < x2; j++) {
                averageRed   += colors[i][j].getRed();
                averageGreen += colors[i][j].getGreen();
                averageBlue  += colors[i][j].getBlue();
            }
        }
        int operations = (x2 - x1) * (y2 - y1);
        return new int[]{averageRed / operations, averageGreen / operations, averageBlue / operations };
    }

    @Override
    public void markArea(@NotNull Point p1, @NotNull Point p2) {
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        Color[][] auxColors = new Color[colors.length][colors[0].length];
        for(int i = 0; i < auxColors.length; i++){
            auxColors = colors.clone();
        }

        Color auxColor = new Color(255, 0, 0, 255);
        for(int i = y1; i < y2; i++){
            auxColors[i][x1] = auxColor;
            auxColors[i][x2] = auxColor;
        }
        for(int j = x1; j < x2; j++){
            auxColors[y1][j] = auxColor;
            auxColors[y2][j] = auxColor;
        }
        imageWithPaintedArea = new RawImage(auxColors);
    }

    @Override
    public ArrayList<CustomImage> getHSVRepresentations() {
        byte[][] hue = new byte[height][width];
        byte[][] saturation = new byte[height][width];
        byte[][] value = new byte[height][width];
        float[] hsvAux = new float[3];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Color.RGBtoHSB(colors[i][j].getRed(), colors[i][j].getGreen(), colors[i][j].getBlue(), hsvAux);
                hue[i][j] = (byte)(hsvAux[0]*255);
                saturation[i][j] = (byte)(hsvAux[1]*255);
                value[i][j] = (byte)(hsvAux[2]*255);
            }
        }
        ArrayList<CustomImage> images = new ArrayList<>(3);
        images.add(0, new RawImage(hue, hue, hue));
        images.add(1, new RawImage(saturation, saturation, saturation));
        images.add(2, new RawImage(value, value, value));
        return images;
    }

    @Override
    public Color[][] getRGBRepresentation() {
        Color[][] rgb = new Color[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                rgb[i][j] = new Color(colors[i][j].getRed(),colors[i][j].getGreen(),colors[i][j].getBlue());
            }
        }
        return rgb;
    }

    @Override
    public int[] getColorsRepetition(){
        int[] values = new int[Utils.L];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                int col = (colors[i][j].getRed() + colors[i][j].getGreen() +colors[i][j].getBlue()) / 3;
                values[col]++;
            }
        }
        return values;
    }

    @Override
    public int getAmountOfPixels() { return width * height; }
}
