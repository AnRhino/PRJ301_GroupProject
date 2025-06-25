/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class UserInputValidate {

    public final static int MIN_VALUE = 0;
    
    public final static String INTEGER_FORMAT = "^"
            + "(-)?"
            + "(0|[0-9])+"
            + "$";
    
    public final static String DOUBLE_FORMAT = "^"
            + "(-)?"
            + "([0-9])+"
            + "(.([0-9])+)?"
            + "$";
    
    public ErrorMessage checkEmptyInput(String input) {
        
        try {
            
            if (input == null) {
                throw new Exception();
            }
            
            return null;
            
        } catch (Exception e) {
            return new ErrorMessage("Please input a number.");
        } 
    }
    
    public ErrorMessage checkValidIntegerNumber(String input) {
        
        try {
            
            if (!input.matches(INTEGER_FORMAT)) {
                throw new NumberFormatException();
            }
            
            return null;
            
        } catch (NumberFormatException e) {
            return new ErrorMessage("Please enter a number.");
        } 
    }
    
    public ErrorMessage checkValidDoubleNumber(String input) {
        
        try {
            
            if (!input.matches(DOUBLE_FORMAT)) {
                throw new NumberFormatException();
            }
            
            return null;
            
        } catch (NumberFormatException e) {
            return new ErrorMessage("Please enter a number.");
        } 
    }
    
    public ErrorMessage checkNumberInRange(int number, int start, int end) {
        
        try {
            
            if (number < start || number > start) {
                throw new Exception();
            }
            
            return null;
            
        } catch (Exception e) {
            return new ErrorMessage("Please enter a valid number.");
        } 
    }
}
