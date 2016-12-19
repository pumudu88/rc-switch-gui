package pumudu.arduino.ui.utils;

import java.util.regex.Pattern;

/**
 * Created by pumudu on 12/19/16.
 */
public class TextFieldValidators {


    public static boolean validateStringForIntegers(String string) {

        if (Pattern.matches("-?[0-9]+",string)) {
            return true;
        } else {
            return false;
        }
    }
}
