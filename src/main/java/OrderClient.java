import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String CREATE_ORDER_PATH = "api/orders";
    private static final String GET_INGREDIENT_FOR_ORDER_PATH = "api/ingredients";
    private static final String GET_ORDER_FOR_USER = "api/orders";

    @Step("Получение данных об ингридиентах")
    public ValidatableResponse get(String bearerToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(bearerToken)
                .when()
                .get(GET_INGREDIENT_FOR_ORDER_PATH)
                .then()
                .assertThat();
    }

    @Step("Создание заказа")
    public ValidatableResponse create(String ingredient, String bearerToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(bearerToken)
                .body("{" + '"' + "ingredients" + '"' + ':' + ingredient + "}")
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .assertThat();
    }

    @Step("Получение заказов пользователя")
    public ValidatableResponse getForUser(String bearerToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(bearerToken)
                .when()
                .get(GET_ORDER_FOR_USER)
                .then()
                .assertThat();
    }
}
