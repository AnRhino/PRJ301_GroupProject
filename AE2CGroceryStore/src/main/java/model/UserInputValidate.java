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

    public final static int ZERO_VALUE = 0;

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
     * @param input là gái trị của người dùng nhập.
     *
     * @return ErrorMessage nếu người dùng không nhập gì. Null nếu người dùng có
     * nhập gì đó.
     */
    public static ErrorMessage checkEmptyInput(String input) {

        try {

            if (input == null || input.isBlank()) {
                throw new Exception();
            }

            return null;

        } catch (Exception e) {
            return new ErrorMessage("Please input a valid integer number.");
        }
    }

    /**
     * Kiểm tra giá trị người dùng có nhập số nguyên hợp lệ hay không.
     *
     * @param input là giá trị nhập của người dùng.
     *
     * @return ErrorMessage nếu người dùng nhập 1 số nguyên không hợp lệ. Null
     * nếu người dùng nhập 1 số nguyên hợp lệ.
     */
    public static ErrorMessage checkValidIntegerNumber(String input) {

        try {

            if (!input.matches(INTEGER_REGEX) || Integer.parseInt(input) > Integer.MAX_VALUE || Integer.parseInt(input) < Integer.MIN_VALUE) {
                throw new NumberFormatException();
            }
            
            return null;

        } catch (NumberFormatException e) {
            return new ErrorMessage("Please enter a valid integer number.");
        }
    }

    /**
     * Kiểm tra giá trị người dùng có nhập số thực hợp lệ hay không.
     *
     * @param input là giá trị nhập của người dùng.
     *
     * @return ErrorMessage nếu người dùng nhập 1 số thực không hợp lệ. Null nếu
     * người dùng nhập 1 số thực hợp lệ.
     */
    public static ErrorMessage checkValidDoubleNumber(String input) {

        try {

            if (!input.matches(DOUBLE_REGEX) || Double.parseDouble(input) > Double.MAX_VALUE || Double.parseDouble(input) < Double.MIN_VALUE) {
                throw new NumberFormatException();
            }

            return null;

        } catch (NumberFormatException e) {
            return new ErrorMessage("Please enter a number.");
        }
    }

    /**
     * Kiểm tra số nguyên có thuộc khoảng hợp lệ hay không.
     *
     * @param number là số cần kiểm tra.
     * @param start là giới hạn đầu dưới.
     * @param end là giới hạn đầu trên.
     *
     * @return ErrorMessage nếu người dùng nhập 1 số nguyên nằm ở ngoài khoảng
     * hợp lệ. Null nếu người dùng nhập 1 số nguyên nằm trong khoảng hợp lệ.
     */
    public static ErrorMessage checkIntegerNumberInRange(int number, int start, int end) {

        try {

            if (start > Integer.MAX_VALUE || end < Integer.MIN_VALUE || number < start || number > end) {
                throw new Exception();
            }

            return null;

        } catch (Exception e) {
            return new ErrorMessage("Please enter a valid number.");
        }
    }

    /**
     * Kiểm tra số thực có thuộc khoảng hợp lệ hay không.
     *
     * @param number là số cần kiểm tra.
     * @param start là giới hạn đầu dưới.
     * @param end là giới hạn đầu trên.
     *
     * @return ErrorMessage nếu người dùng nhập 1 số thực nằm ở ngoài khoảng hợp
     * lệ. Null nếu người dùng nhập 1 số thực nằm trong khoảng hợp lệ.
     */
    public static ErrorMessage checkDoubleNumberInRange(double number, double start, double end) {

        try {

            if (start > Double.MAX_VALUE || end < Double.MIN_VALUE || number < start || number > end) {
                throw new Exception();
            }

            return null;

        } catch (Exception e) {
            return new ErrorMessage("Please enter a valid number.");
        }
    }
}
