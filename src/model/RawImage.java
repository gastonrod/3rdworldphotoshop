package model;

import javafx.scene.image.*;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;
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

    // Grayscale raw image
    public RawImage(@NotNull File file) {
        setWidthAndHeight(file.getName());

        try (FileInputStream fileInputStream = new FileInputStream(file)){
            imageRedBytes = new byte[width][height];
            imageGreenBytes = new byte[width][height];
            imageBlueBytes = new byte[width][height];
            alphaBytes = new byte[width][height];
            for (int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    byte currentPixel = (byte) fileInputStream.read();
                    imageRedBytes[i][j] = currentPixel;
                    imageGreenBytes[i][j] = currentPixel;
                    imageBlueBytes[i][j] = currentPixel;
                    alphaBytes[i][j] = (byte)255;
                }
            }
            this.writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = this.writableImage.getPixelWriter();
            pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), getImageBytes(), 0, width * 4);
        } catch (IOException e) {
           throw new RuntimeException("Error loading RAW image: " + e.getMessage());
        }
    }

    private void setWidthAndHeight(@NotNull String name) {
        String[] s = name.split("\\.")[0].split("_");
        width = Integer.parseInt(s[1]);
        height = Integer.parseInt(s[2]);
    }


    @Override
    public WritableImage asWritableImage() {
        return this.writableImage;
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
        int w = Math.max(x1y1.x, x2y2.x) - x;
        int h = Math.max(x1y1.y, x2y2.y) - y;
        byte[][] auxRed = new byte[w][h];
        byte[][] auxGreen= new byte[w][h];
        byte[][] auxBlue = new byte[w][h];
        byte[][] auxAlpha = new byte[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                auxRed[i][j] = imageRedBytes[x+i][y+j];
                auxGreen[i][j] = imageGreenBytes[x+i][y+j];
                auxBlue[i][j] = imageBlueBytes[x+i][y+j];
                auxAlpha[i][j] = alphaBytes[x+i][y+j];
            }
        }
        destinationImage.pasteImage(auxRed, auxGreen, auxBlue, auxAlpha, destinationPos);
    }

    @Override
    public void pasteImage(byte[][] redBytes, byte[][] greenBytes, byte[][] blueBytes, byte[][] alpha, @NotNull Point pos) {
        for(int i = 0; i < redBytes.length; i++){
            for(int j = 0; j < redBytes.length; j++){
                imageRedBytes[i+pos.x][j+pos.y] = redBytes[i][j];
                imageGreenBytes[i+pos.x][j+pos.y] = greenBytes[i][j];
                imageBlueBytes[i+pos.x][j+pos.y] = blueBytes[i][j];
                alphaBytes[i+pos.x][j+pos.y] = alpha[i][j];
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
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch(j%4) {
                    case 0:
                        imageBytes[i * width + j] = imageRedBytes[i][j];
                        break;
                    case 1:
                        imageBytes[i * width + j] = imageGreenBytes[i][j];
                        break;
                    case 2:
                        imageBytes[i * width + j] = imageBlueBytes[i][j];
                        break;
                    case 3:
                        imageBytes[i * width + j] = alphaBytes[i][j];
                        break;
                }
            }
        }
        System.out.println(Arrays.toString(imageBytes));
//        for(int i = 0; i < imageBytes.length; i+=4) {
//            imageBytes[i] = imageRedBytes[i/width][i%height];
//            imageBytes[i+1] = imageGreenBytes[i/width][i%height];
//            imageBytes[i+2] = imageBlueBytes[i/width][i%height];
//            imageBytes[i+3] = alphaBytes[i/width][i%height];
//        }
        return imageBytes;
    }
}
