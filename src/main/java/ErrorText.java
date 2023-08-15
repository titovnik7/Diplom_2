public class ErrorText {
    public static final int SUCCESS_CREATE_USER_STATUS_CODE = 200;
    public static final int DUPLICATE_CREATE_USER_STATUS_CODE = 403;
    public static final String DUPLICATE_CREATE_USER_TEXT_MESSAGE = "User already exists";
    public static final int REGISTRATED_WITHOUT_REQUIRED_FIELD_STATUS_CODE = 403;
    public static final String REGISTRATED_WITHOUT_REQUIRED_FIELD_TEXT_MESSAGE = "Email, password and name are required fields";
    public static final int EMAIL_OR_PASSWORD_ARE_INCORRECT_STATUS_CODE = 401;
    public static final String EMAIL_OR_PASSWORD_ARE_INCORRECT_TEXT_MESSAGE = "email or password are incorrect";
    public static final int SUCCESS_LOGIN_STATUS_CODE = 200;
    public static final int SUCCESS_CHANGE_USER_STATUS_CODE = 200;
    public static final int UNSUCCESSFUL_CHANGE_WITHOUT_AUTHORIZATION_STATUS_CODE = 401;
    public static final String UNSUCCESSFUL_CHANGE_WITHOUT_AUTHORIZATION_TEXT_MESSAGE = "You should be authorised";
    public static final int SUCCESS_CREATE_ORDER_STATUS_CODE = 200;
    public static final int CREATE_ORDER_WITHOUT_INGREDIENT_STATUS_CODE = 400;
    public static final String CREATE_ORDER_WITHOUT_INGREDIENT_TEXT_MESSAGE = "Ingredient ids must be provided";
    public static final int CREATE_ORDER_WITH_INCORRECT_HASH_STATUS_CODE = 400;
    public static final String CREATE_ORDER_WITH_INCORRECT_HASH_TEXT_MESSAGE = "One or more ids provided are incorrect";
    public static final int USER_ORDER_WITH_AUTHORIZATION_STATUS_CODE = 200;
    public static final int USER_ORDER_WITHOUT_AUTHORIZATION_STATUS_CODE = 401;
    public static final String USER_ORDER_WITHOUT_AUTHORIZATION_TEXT_MESSAGE = "You should be authorised";
}
