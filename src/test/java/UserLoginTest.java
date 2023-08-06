import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserLoginTest {
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
            userClient.delete(clientBearerToken);
        } catch (NullPointerException exception) {
        }
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void userCanBeLogin() {
        ValidatableResponse createResponse = userClient.create(user);
        clientBearerToken = createResponse.extract().path("accessToken");
        clientBearerToken = clientBearerToken.replace("Bearer ", "");

        ValidatableResponse responseLogin = userClient.login(UserCredentials.from(user), clientBearerToken);
        int statusCodeSuccessLogin = responseLogin.extract().statusCode();
        boolean textMessage = responseLogin.extract().path("success");
        assertTrue(textMessage);
        assertEquals(ErrorText.getSuccessLoginStatusCode(), statusCodeSuccessLogin);
    }

    @Test
    @DisplayName("Неверный логин и пароль")
    public void userCanNotBeLoginWithIncorrectLoginAndPassword() {
        ValidatableResponse createResponse = userClient.create(user);
        clientBearerToken = createResponse.extract().path("accessToken");
        clientBearerToken = clientBearerToken.replace("Bearer ", "");
        userClient.delete(clientBearerToken);

        ValidatableResponse responseLogin = userClient.login(UserCredentials.from(user), clientBearerToken);
        int statusCodeUnsuccessfulLogin = responseLogin.extract().statusCode();
        String textMessageError = responseLogin.extract().path("message");
        assertEquals(ErrorText.getEmailOrPasswordAreIncorrectStatusCode(), statusCodeUnsuccessfulLogin);
        assertEquals(ErrorText.getEmailOrPasswordAreIncorrectTextMessage(), textMessageError);
    }
}
