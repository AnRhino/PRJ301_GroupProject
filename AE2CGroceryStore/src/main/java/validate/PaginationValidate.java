/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

import utils.PaginationUtil;

/**
 *
 * @author Le Thien Tri - CE191249
 */
public class PaginationValidate {

    private static final String PAGE_NUMBER = "^[0-9]+$";

    public static boolean isInvalidPageNumber(String input) {
        return input == null || input.matches(PAGE_NUMBER);
    }
}
