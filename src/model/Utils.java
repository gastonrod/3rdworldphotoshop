package model;

public class Utils {
    public static int byteToInt(byte val) {
        return val < 0 ? 256 + val : val;
    }
}
