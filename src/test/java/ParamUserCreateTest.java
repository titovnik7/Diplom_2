import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParamUserCreateTest {
    private final User userParam;
    private UserClient userClient;

    public ParamUserCreateTest(User userParam) {
        this.userParam = userParam;
    }

    @Parameterized.Parameters
    public static Object[][] getTestParameters() {
        return new Object[][]{
                {UserGenerator.getRandomWithoutEmail()},
                {UserGenerator.getRandomWithoutPassword()},
                {UserGenerator.getRandomWithoutName()}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Отсутствие обязательного поля при регистрации")
    public void userCanNotBeCreated() {
        ValidatableResponse createResponse = userClient.create(userParam);
        int actualStatusCode = createResponse.extract().statusCode();
        String actualTextError = createResponse.extract().path("message");
        assertEquals(ErrorText.REGISTRATED_WITHOUT_REQUIRED_FIELD_STATUS_CODE, actualStatusCode);
        assertEquals(ErrorText.REGISTRATED_WITHOUT_REQUIRED_FIELD_TEXT_MESSAGE, actualTextError);
    }
}
