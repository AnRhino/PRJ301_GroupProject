/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

/**
 *
 * @author Le Thien Tri - CE191249
 */
public class ProfileValidate {

    public static final int MIN_LENGTH_FULL_NAME = 1;

    public static final int MAX_LENGTH_FULL_NAME = 50;

    public static final String FULLNAME_VALIDATE = "^"
            + "[A-Za-zÀ-ÿ'\\- ]"
            + "{2,50}"
            + "$";

    public static final String EMAIL_REGEX = "^"
            + "[A-Za-z0-9+_.-]+" // The local part (before the @) must contain one or more of: letters, digits, plus (+), underscore (_), dot (.), hyphen (-)
            + "@" // @ symbol separating local and domain parts
            + "[A-Za-z0-9.-]+" // The domain part (after the @) must contain one or more of: letters, digits, dot (.), or hyphen (-)
            + "\\.[A-Za-z]{2,}"
            + "$";

    public static boolean maxAndMinFullNameLength(String input) {
        try {
            if (input.length() <= MIN_LENGTH_FULL_NAME || input.length() > MAX_LENGTH_FULL_NAME) {
                throw new Exception();
            }
            return false;

        } catch (Exception e) {
            return true;
        }
    }

    public static String checkSpacing(String input){
        try{
            String output = input.replaceAll("\\s+", " ");
            return output.trim();
        } catch(Exception e){
            return null;
        }           
    }
    
    public static boolean checkEmptyInput(String input) {
        try {

            if (input == null || input.isBlank() || input.isEmpty()) {
                throw new Exception();
            }

            return false;

        } catch (Exception e) {
            return true;
        }
    }

    public static boolean fullNameValidate(String input) {
        try {
            if (!input.matches(FULLNAME_VALIDATE)) {
                throw new Exception();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean emailValidate(String input) {
        try {
            if (!input.matches(EMAIL_REGEX)) {
                throw new Exception();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
