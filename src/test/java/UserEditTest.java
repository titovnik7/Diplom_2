import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserEditTest {
    private static UserCredentials credentials;
    private UserClient userClient;
    private User user;
    private User userChange;
    private String clientBearerToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        userChange = UserGenerator.getRandom();
        credentials = credentials.from(userChange);
    }

    @After
    public void cleanUp() {
        try {
            userClient.delete(clientBearerToken);
        } catch (NullPointerException exception) {
        }
    }

    @Test
    @DisplayName("Редактирование авторизованного пользователя")
    public void userChangeWithAuthorization() {
        ValidatableResponse createResponse = userClient.create(user);
        clientBearerToken = createResponse.extract().path("accessToken");
        clientBearerToken = clientBearerToken.replace("Bearer ", "");
        ValidatableResponse changeResponse = userClient.change(credentials, clientBearerToken);
        int changeStatusCode = changeResponse.extract().statusCode();
        boolean success = changeResponse.extract().path("success");
        assertTrue(success);
        assertEquals(ErrorText.getSuccessChangeUserStatusCode(), changeStatusCode);
    }

    @Test
    @DisplayName("Редактирование неавторизованного пользователя")
    public void userChangeWithoutAuthorization() {
        clientBearerToken = "";
        ValidatableResponse changeResponse = userClient.change(credentials, clientBearerToken);
        int unsuccessfulChangeStatusCode = changeResponse.extract().statusCode();
        String unsuccessfulChangeTextMessage = changeResponse.extract().path("message");
        assertEquals(ErrorText.getUnsuccessfulChangeWithoutAuthorizationStatusCode(),
                unsuccessfulChangeStatusCode);
        assertEquals(ErrorText.getUnsuccessfulChangeWithoutAuthorizationTextMessage(),
                unsuccessfulChangeTextMessage);

    }
}
