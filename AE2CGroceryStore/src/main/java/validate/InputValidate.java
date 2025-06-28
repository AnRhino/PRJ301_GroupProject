/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class InputValidate {

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
     * @param input là giá trị của người dùng nhập.
     *
     * @return True nếu người dùng không nhập gì. False nếu người dùng có nhập
     * gì đó.
     */
    public static boolean checkEmptyInput(String input) {

        try {

            if (input == null || input.isBlank()) {
                throw new Exception();
            }

            return false;

        } catch (Exception e) {
            return true;
        }
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

        try {

            if (!input.matches(INTEGER_REGEX) || Integer.parseInt(input) > Integer.MAX_VALUE || Integer.parseInt(input) < Integer.MIN_VALUE) {
                throw new NumberFormatException();
            }

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

        try {

            if (!input.matches(DOUBLE_REGEX) || Double.parseDouble(input) > Double.MAX_VALUE || Double.parseDouble(input) < Double.MIN_VALUE) {
                throw new NumberFormatException();
            }

            return false;

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

        try {

            if (start > Integer.MAX_VALUE || end < Integer.MIN_VALUE || number < start || number > end) {
                throw new Exception();
            }

            return false;

        } catch (Exception e) {
            return true;
        }
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

        try {

            if (start > Double.MAX_VALUE || end < Double.MIN_VALUE || number < start || number > end) {
                throw new Exception();
            }

            return false;

        } catch (Exception e) {
            return true;
        }
    }
}
