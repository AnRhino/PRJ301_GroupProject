/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class MessageConstants {

    // Error message for all error.
    public final static String UNKNOWN_ERROR_MESSAGE = "There is an error.";

    // Error message for empty input.
    public final static String EMPTY_INPUT_MESSAGE = "Please enter something.";
    
    // Error message for invalid integer.
    public final static String INVALID_INTEGER_INPUT_MESSAGE = "Please enter a valid integer number.";

    // Error message for date type.
    public final static String INVALID_DATE = "An error occurred. Please select date again.";

    // Error message for Cart.
    public final static String INVALID_CART_INPUT_MESSAGE = "The number is not available.";
    public final static String SUCCESS_CART_INPUT_MESSAGE = "Add to your cart successfully.";

    // Error message for Comment.
    public final static String EMPTY_COMMENT_INPUT_MESSAGE = "Please enter a comment for this product.";
    public final static String EMPTY_RATING_INPUT_MESSAGE = "Please select a rate for this product.";
    public final static String INVALID_RATING_INPUT_MESSAGE = "Please choose a valid rate for this product.";
    public final static String UNKNOWN_COMMENT_MESSAGE = "There is an error with this comment. Please try again.";
    public final static String ERROR_EDIT_COMMENT_MESSAGE = "You must enter something in the comment.";
    public final static String ERROR_DELETE_COMMENT_MESSAGE = "Fail to delete the comment.";
    
    // Success message for comment.
    public final static String SUCCESS_EDIT_COMMENT_MESSAGE = "Successfully edit the comment.";
    public final static String SUCCESS_DELETE_COMMENT_MESSAGE = "Successfully remove the comment.";

    // Error message for Rating.
    public final static String OUT_OF_RANGE_RATING_INPUT_MESSAGE = "Rating only from 1 to 5.";
    
    // Error message for Order.
    public final static String DELIVERY_DATE_BEFORE_TODAY_MESSAGE = "Delivery date must not be before today";
    public final static String ERROR_CREATE_NEW_ORDER = "Fail to create new order";

    // Error message for Order Item.
    public final static String ERROR_ADD_ITEM_INTO_ORDER = "Fail to add items into order";

    // Error message for Discount Code.
    public final static String DISCOUND_CODE_NOT_FOUND = "Discount code is not found";
    
    // Error message for empty category name.
    public final static String EMPTY_CATEGORY_NAME_MESSAGE = "Category name cannot be empty.";
    
    // Error message for upload image.
    public final static String ERROR_IMAGE_UPLOAD_MESSAGE = "Error processing image upload: ";
    public final static String UNEXIST_DIRECTORY_MESSAGE = "Failed to create upload directory.";
    public final static String NO_NAME_FILE_MESSAGE = "Uploaded file has no name.";
    public final static String INVALID_FILE_NAME_MESSAGE = "Invalid file name: ";
    public final static String PREFIX_UNALLOWED_EXTENSION_MESSAGE = "Only ";
    public final static String POSTFIX_UNALLOWED_EXTENSION_MESSAGE = " files are allowed.";
    public final static String FAILED_DELETE_FILE_MESSAGE = "Failed to replace existing image.";
    public final static String UNKNOWN_FILE_ERROR_MESSAGE = "Error processing image: ";
}
