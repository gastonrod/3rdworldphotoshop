package view;


import view.error.ErrorCodes;
import view.error.ErrorsWindowController;

public class Utils {
    public static final int L = 256;
    private Utils() {}

    public static int sanitizeNumberInput(String textField, int maxValue){
       return sanitizeNumberInput(textField, maxValue, -1);
    }

    public static double sanitizeNumberInput(String textField, double maxValue){
        return sanitizeNumberInput(textField, maxValue, -1);
    }

    public static int sanitizeNumberInput(String textField, int maxValue, int defaultValue){
        if(textField.equals("")) {
            return defaultValue;
        }
        int value;
        try {
            value = Integer.parseInt(textField);
        } catch(Exception e) {
            ErrorsWindowController.newErrorCode(ErrorCodes.ENTER_VALID_NUMBER);
            return -1;
        }
        if(value > maxValue || value < 0){
            ErrorsWindowController.newErrorCode(ErrorCodes.ENTER_VALID_NUMBER);
            return -1;
        }
        return value;
    }

    public static double sanitizeNumberInput(String textField, double maxValue, double defaultValue){
        if(textField.equals("")) {
            return defaultValue;
        }
        double value;
        try {
            value = Double.parseDouble(textField);
        } catch(Exception e) {
            ErrorsWindowController.newErrorCode(ErrorCodes.ENTER_VALID_NUMBER);
            return -1;
        }
        if(value > maxValue || value < 0){
            ErrorsWindowController.newErrorCode(ErrorCodes.ENTER_VALID_NUMBER);
            return -1;
        }
        return value;
    }
}
