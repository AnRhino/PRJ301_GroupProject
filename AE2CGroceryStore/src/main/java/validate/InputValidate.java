/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

import java.text.Normalizer;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class InputValidate {

    public final static int ZERO_VALUE = 0;
    public final static int MIN_RATING_VALUE = 1;
    public final static int MAX_RATING_VALUE = 5;
    
    public final static String EMPTY_STRING = "";
    public final static String SPECIAL_CHARACTER_REGEX = "[^a-zA-Z0-9 ]";
    public final static String SPACE_REGEX = "\\s+";

    public final static String INTEGER_REGEX = "^"
            + "(-)?"
            + "(\\d)+"
            + "$";

    public final static String DOUBLE_REGEX = "^"
            + "(-)?"
            + "(\\d)+"
            + "(.(\\d)+)?"
            + "$";

    /**
     * Kiểm tra giá trị nhập vào từ người dùng có rỗng không.
     *
     * @param input là giá trị của người dùng nhập.
     *
     * @return True nếu người dùng không nhập gì. False nếu người dùng có nhập
     * gì đó.
     */
    public static boolean checkEmptyInput(String input) {
         return input == null || input.isBlank();
    }

    /**
     * Kiểm tra giá trị người dùng có nhập số nguyên hợp lệ hay không.
     *
     * @param input là giá trị nhập của người dùng.
     *
     * @return True nếu người dùng nhập 1 số nguyên không hợp lệ. Fasle nếu
     * người dùng nhập 1 số nguyên hợp lệ.
     */
    public static boolean checkValidIntegerNumber(String input) {
        
        if (!input.matches(INTEGER_REGEX)) return true;
        try {
            Integer.parseInt(input);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Kiểm tra giá trị người dùng có nhập số thực hợp lệ hay không.
     *
     * @param input là giá trị nhập của người dùng.
     *
     * @return True nếu người dùng nhập 1 số thực không hợp lệ. False nếu người
     * dùng nhập 1 số thực hợp lệ.
     */
    public static boolean checkValidDoubleNumber(String input) {
        
        if (!input.matches(DOUBLE_REGEX)) return true;
        try {
            double value = Double.parseDouble(input);
            return Double.isNaN(value) || Double.isInfinite(value);
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Kiểm tra số nguyên có thuộc khoảng hợp lệ hay không.
     *
     * @param number là số cần kiểm tra.
     * @param start là giới hạn đầu dưới.
     * @param end là giới hạn đầu trên.
     *
     * @return True nếu người dùng nhập 1 số nguyên nằm ở ngoài khoảng hợp lệ.
     * False nếu người dùng nhập 1 số nguyên nằm trong khoảng hợp lệ.
     */
    public static boolean checkIntegerNumberInRange(int number, int start, int end) {

//        try {
//
//            if (start > Integer.MAX_VALUE || end < Integer.MIN_VALUE || number < start || number > end) {
//                throw new Exception();
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            return true;
//        }
        
        return number < start || number > end;
    }
    
    /**
     * Kiểm tra xem số nguyên này có nhỏ hơn số kia không.
     * 
     * @param numberOne là số nguyên thứ nhất.
     * @param numberTwo là số nguyên thứ hai.
     * 
     * @return True nếu số nguyên thứ nhất nhỏ hơn số nguyên thứ hai. 
     */
    public static boolean checkIntegerNumberHaveSmallerValueThanOther(int numberOne, int numberTwo) {
        return numberOne < numberTwo;
    }

    /**
     * Kiểm tra số thực có thuộc khoảng hợp lệ hay không.
     *
     * @param number là số cần kiểm tra.
     * @param start là giới hạn đầu dưới.
     * @param end là giới hạn đầu trên.
     *
     * @return True nếu người dùng nhập 1 số thực nằm ở ngoài khoảng hợp lệ.
     * False nếu người dùng nhập 1 số thực nằm trong khoảng hợp lệ.
     */
    public static boolean checkDoubleNumberInRange(double number, double start, double end) {

//        try {
//
//            if (start > Double.MAX_VALUE || end < Double.MIN_VALUE || number < start || number > end) {
//                throw new Exception();
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            return true;
//        }

        return number < start || number > end;
    }
    
    /**
     * Kiểm tra xem số thực này có nhỏ hơn số thực kia không.
     * 
     * @param numberOne là số thực thứ nhất.
     * @param numberTwo là số thực thứ hai.
     * 
     * @return True nếu số thứ thực nhất nhỏ hơn số thực thứ hai. 
     */
    public static boolean checkDoubleNumberHaveSmallerValueThanOther(double numberOne, double numberTwo) {
        return numberOne < numberTwo;
    }
    
    /**
     * Biến chuỗi đầu vào thành 1 chuỗi chữ được chuẩn hóa.
     * 
     * Không khoảng cách thừa ở giữa chuỗi và 2 đầu.
     * Không kí tự đặc biệt.
     * Không viết hoa.
     * 
     * @param input là chuỗi cần được chuẩn hóa.
     * 
     * @return chuỗi đã được chuẩn hóa.
     */
    public static String normalizeInputString(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        
        normalizedString = normalizedString.trim(); // Xóa khoảng trắng ở 2 đầu.
        normalizedString = normalizedString.replaceAll(SPACE_REGEX, EMPTY_STRING); // Xóa khoảng trắng.
        normalizedString = normalizedString.replaceAll(SPECIAL_CHARACTER_REGEX, EMPTY_STRING); // Xóa kí tự đặc biệt.
        normalizedString = normalizedString.toLowerCase(); // Viết thường kí tự.
                
        return normalizedString;
    }
    
    /**
     * Hàm xóa hết những khoảng trắng thừa ra khỏi chuỗi.
     * 
     * @param input là chuỗi cần xóa khoảng trắng.
     * 
     * @return chuỗi đã xóa khoảng trắng.
     */
    public static String removeSpaceFromString(String input) {
        return input.replaceAll(SPACE_REGEX, EMPTY_STRING);
    }
}
