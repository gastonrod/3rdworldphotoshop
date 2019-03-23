package model;

public class Utils {
    public static final int L = 256;

    private Utils() {}
    public static int byteToInt(byte val){
        return val < 0 ? 256 + val : val;
    }
}
