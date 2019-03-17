package model;

import javafx.scene.image.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class RawImage implements CustomImage{

    private WritableImage writableImage;
    private byte[][] imageRedBytes;
    private byte[][] imageGreenBytes;
    private byte[][] imageBlueBytes;
    private byte[][] alphaBytes;
    private int width;
    private int height;

    private RawImage imageWithPaintedArea;

    // Grayscale raw image
    public RawImage(byte[][] reds, byte[][] greens, byte[][] blues, byte[][] alphas){
        width = reds[0].length;
        height = reds.length;
        imageRedBytes = reds;
        imageGreenBytes = greens;
        imageBlueBytes = blues;
        alphaBytes = alphas;
        createImage();
    }

    public RawImage(@NotNull File file) {
        setWidthAndHeight(file.getName());

        try (FileInputStream fileInputStream = new FileInputStream(file)){
            imageRedBytes = new byte[height][width];
            imageGreenBytes = new byte[height][width];
            imageBlueBytes = new byte[height][width];
            alphaBytes = new byte[height][width];
            for(int n = 0; n < width * height * 4; n+=4) {
                byte currentPixel = (byte) fileInputStream.read();
                imageRedBytes[(n/4)/width][(n/4)%width] = currentPixel;
                imageGreenBytes[(n/4)/width][(n/4)%width] = currentPixel;
                imageBlueBytes[(n/4)/width][(n/4)%width] = currentPixel;
                alphaBytes[(n/4)/width][(n/4)%width] = (byte)255;
            }
            createImage();
        } catch (IOException e) {
           throw new RuntimeException("Error loading RAW image: " + e.getMessage());
        }
    }

    private void createImage() {
        this.writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = this.writableImage.getPixelWriter();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
    }

    private void setWidthAndHeight(@NotNull String name) {
        String[] s = name.split("\\.")[0].split("_");
        width = Integer.parseInt(s[1]);
        height = Integer.parseInt(s[2]);
    }


    @Override
    public WritableImage asWritableImage() {
        return imageWithPaintedArea == null ? writableImage : imageWithPaintedArea.asWritableImage();
    }

    @Override
    public void save(@NotNull File file) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(processBytesForSaving());
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void modifyPixel(@NotNull int value, @NotNull Point pos) {
        byte val = (byte) value;
        imageRedBytes[pos.x][pos.y] = val;
        imageGreenBytes[pos.x][pos.y] = val;
        imageBlueBytes[pos.x][pos.y] = val;
        alphaBytes[pos.x][pos.y] = (byte)255;
        PixelWriter pixelWriter = this.writableImage.getPixelWriter();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
    }

    @Override
    public void copySection(@NotNull CustomImage destinationImage, @NotNull Point x1y1, @NotNull Point x2y2, @NotNull Point destinationPos) {
        int x = Math.min(x1y1.x, x2y2.x);
        int y = Math.min(x1y1.y, x2y2.y);
        int w = Math.max(x1y1.y, x2y2.y) - y;
        int h = Math.max(x1y1.x, x2y2.x) - x;
        byte[][] auxRed = new byte[w][h];
        byte[][] auxGreen= new byte[w][h];
        byte[][] auxBlue = new byte[w][h];
        byte[][] auxAlpha = new byte[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                auxRed[i][j] = imageRedBytes[y+i][x+j];
                auxGreen[i][j] = imageGreenBytes[y+i][x+j];
                auxBlue[i][j] = imageBlueBytes[y+i][x+j];
                auxAlpha[i][j] = alphaBytes[y+i][x+j];
            }
        }
        destinationImage.pasteImage(auxRed, auxGreen, auxBlue, auxAlpha, destinationPos);
    }

    @Override
    public void pasteImage(byte[][] redBytes, byte[][] greenBytes, byte[][] blueBytes, byte[][] alpha, @NotNull Point pos) {
        System.out.println(redBytes.length);
        System.out.println(redBytes[0].length);
        for(int i = 0; i < redBytes.length; i++){
            for(int j = 0; j < redBytes[0].length; j++){
                imageRedBytes[i+pos.y][j+pos.x] = redBytes[i][j];
                imageGreenBytes[i+pos.y][j+pos.x] = greenBytes[i][j];
                imageBlueBytes[i+pos.y][j+pos.x] = blueBytes[i][j];
                alphaBytes[i+pos.y][j+pos.x] = alpha[i][j];
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
        byte[] imageBytes = new byte[width * height * 4];
        for(int n = 0; n < width * height * 4; n+=4) {
            imageBytes[n]   = imageBlueBytes[(n/4)/width][(n/4)%width];
            imageBytes[n+1] = imageGreenBytes[(n/4)/width][(n/4)%width];
            imageBytes[n+2] = imageRedBytes[(n/4)/width][(n/4)%width];
            imageBytes[n+3] = alphaBytes[(n/4)/width][(n/4)%width];
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
                averageRed += imageRedBytes[i][j];
                averageGreen += imageGreenBytes[i][j];
                averageBlue += imageBlueBytes[i][j];
            }
        }
        int operations = (x2 - x1) * (y2 - y1);
        int[] ans = {averageRed / operations, averageGreen / operations, averageBlue / operations };
        return ans;
    }

    @Override
    public void markArea(@NotNull Point p1, @NotNull Point p2) {
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        byte[][] auxRed = new byte[imageRedBytes.length][imageRedBytes[0].length];
        byte[][] auxGreen = new byte[imageRedBytes.length][imageRedBytes[0].length];
        byte[][] auxBlue = new byte[imageRedBytes.length][imageRedBytes[0].length];
        for(int i = 0; i < imageRedBytes.length; i++){
            auxRed[i] = imageRedBytes[i].clone();
            auxGreen[i] = imageGreenBytes[i].clone();
            auxBlue[i] = imageBlueBytes[i].clone();
        }

        for(int i = y1; i < y2; i++){
            auxRed[i][x1] = (byte)255;
            auxRed[i][x2] = (byte)255;
            auxGreen[i][x1] = (byte)0;
            auxGreen[i][x2] = (byte)0;
            auxBlue[i][x1] = (byte)0;
            auxBlue[i][x2] = (byte)0;
        }
        for(int j = x1; j < x2; j++){
            auxRed[y1][j] = (byte)255;
            auxRed[y2][j] = (byte)255;
            auxGreen[y1][j] = (byte)0;
            auxGreen[y2][j] = (byte)0;
            auxBlue[y1][j] = (byte)0;
            auxBlue[y2][j] = (byte)0;
        }
        imageWithPaintedArea = new RawImage(auxRed, auxGreen, auxBlue, alphaBytes);
    }

}
