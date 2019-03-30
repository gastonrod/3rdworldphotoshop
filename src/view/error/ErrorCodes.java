package view.error;

public enum ErrorCodes {
    LOAD_MAIN("You must load a main createimage first."),
    LOAD_SECONDARY("You must load two images first."),
    ENTER_VALID_NUMBER("You must enter a valid number."),
    ONLY_ONE("This is the last createimage, you cant go back!");
    private String s;

    ErrorCodes(String s) {
        this.s = s;
    }

    public String getMessage() { return s; }
}
