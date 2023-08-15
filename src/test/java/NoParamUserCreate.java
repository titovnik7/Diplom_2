import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoParamUserCreate {
    private UserClient userClient;
    private String clientBearerToken;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
    }

    @After
    public void cleanUp() {
        try {
            clientBearerToken = clientBearerToken.replace("Bearer ", "");
            userClient.delete(clientBearerToken);
        } catch (NullPointerException exception) {
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void userCanBeCreate() {
        ValidatableResponse createResponse = userClient.create(user);
        clientBearerToken = createResponse.extract().path("accessToken");
        int statusCodeCreateUser = createResponse.extract().statusCode();
        boolean successCreate = createResponse.extract().path("success");
        assertEquals(successCreate, true);
        assertEquals(ErrorText.SUCCESS_CREATE_USER_STATUS_CODE, statusCodeCreateUser);
    }

    @Test
    @DisplayName("Регистрация дубликата")
    public void userDuplicateCreate() {
        userClient.create(user);
        ValidatableResponse createResponse = userClient.create(user);
        int statusCodeDuplicateCreateUser = createResponse.extract().statusCode();
        String textMessage = createResponse.extract().path("message");
        assertEquals(ErrorText.DUPLICATE_CREATE_USER_STATUS_CODE, statusCodeDuplicateCreateUser);
        assertEquals(ErrorText.DUPLICATE_CREATE_USER_TEXT_MESSAGE, textMessage);
    }
}
