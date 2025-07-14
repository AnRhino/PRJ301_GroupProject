/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

import model.ErrorMessage;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
public class UserCredsValidate {

    // Validating Regex
    private static final String EMAIL_REGEX = "^"
            + "[A-Za-z0-9+_.-]+" // The local part (before the @) must contain one or more of: letters, digits, plus (+), underscore (_), dot (.), hyphen (-)
            + "@" // @ symbol separating local and domain parts
            + "[A-Za-z0-9.-]+" // The domain part (after the @) must contain one or more of: letters, digits, dot (.), or hyphen (-)
            + "\\.[A-Za-z]{2,}"
            + "$";
    private static final String FULL_NAME_REGEX = "^"
            + "[A-Za-zÀ-ỹ'\\- ]" // Allowed characters: English letters, accented letters (like é, ñ), apostrophes ('), hyphens (-), and spaces
            + "{2,50}"// Length must be between 2 - 50 characters
            + "$";
    private static final String USERNAME_REGEX = "^"
            + "[A-Za-z0-9_.]" // Allowed characters: letters, digits, underscore (_), and dot (.)
            + "{3,20}" // Length must be between 3 - 20 characters
            + "$";
    private static final String PASSWORD_REGEX = "^"
            + "(?=.*[a-z])" // Positive lookahead: ensures at least one lowercase letter
            + "(?=.*[A-Z])" // Positive lookahead: ensures at least one uppercase letter
            + "(?=.*\\d)" // Positive lookahead: ensures at least one digit (\d = 0–9)
            + "(?=.*[@#$!%*?&])" // Positive lookahead: ensures at least one special character from the set
            + "[A-Za-z\\d@#$!%*?&]" // The actual characters allowed in the password
            + "{8,}" // at least 8 character
            + "$";
    public static ErrorMessage emailValid(String email){
        if (email == null || email.isBlank()){
            ErrorMessage msg = new ErrorMessage("Email should not be blank");
            return msg;
        }
        if (!email.matches(EMAIL_REGEX)) {
            ErrorMessage msg = new ErrorMessage("Email is not valid");
            return msg;
        }
        return null;
    } 
    public static ErrorMessage fullNameValid(String fullName){
        if (fullName == null || fullName.isBlank()){
            ErrorMessage msg = new ErrorMessage("Name should not be blank");
            return msg;
        }
        if (!fullName.matches(FULL_NAME_REGEX)) {
            ErrorMessage msg = new ErrorMessage("Name is not valid");
            return msg;
        }
        return null;
    } 
    public static ErrorMessage usernameValid(String username){
        if (username == null || username.isBlank()){
            ErrorMessage msg = new ErrorMessage("Username should not be blank");
            return msg;
        }
        if (!username.matches(USERNAME_REGEX)) {
            ErrorMessage msg = new ErrorMessage("Username is not valid");
            return msg;
        }
        return null;
    } 
    public static ErrorMessage passwordValid(String password){
        if (password == null || password.isBlank()){
            ErrorMessage msg = new ErrorMessage("Password should not be blank");
            return msg;
        }
        if (!password.matches(PASSWORD_REGEX)) {
            ErrorMessage msg = new ErrorMessage("Password is not valid");
            return msg;
        }
        return null;
    } 
}
