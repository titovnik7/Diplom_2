import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderCreateTest {

    Ingredients ingredients;
    private OrderClient orderClient;
    private UserClient userClient;
    private User user;
    private String clientBearerToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        orderClient = new OrderClient();
        ingredients = new Ingredients();
        ValidatableResponse createResponse = userClient.create(user);
        clientBearerToken = createResponse.extract().path("accessToken");
        clientBearerToken = clientBearerToken.replace("Bearer ", "");

    }

    @After
    public void cleanUp() {
        try {
            userClient.delete(clientBearerToken);
        } catch (NullPointerException exception) {
        }
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с указанными ингредиентами")
    public void createOrderWithAuthorizationAndIngredients() {
        ValidatableResponse allIngredient = orderClient.get(clientBearerToken);
        List<String> responseIngredient = allIngredient.extract().path("data");
        String ingredientsForOrder = ingredients.Ingredients(responseIngredient);
        ValidatableResponse createOrderResponse = orderClient.create(ingredientsForOrder, clientBearerToken);
        int statusCodeCreateOrder = createOrderResponse.extract().statusCode();
        boolean success = createOrderResponse.extract().path("success");
        assertEquals(ErrorText.SUCCESS_CREATE_ORDER_STATUS_CODE, statusCodeCreateOrder);
        assertTrue(success);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с указанием ингредиентов")
    public void createOrderWithoutAuthorizationAndWithIngredients() {
        String token = "";
        ValidatableResponse allIngredient = orderClient.get(clientBearerToken);
        List<String> responseIngredient = allIngredient.extract().path("data");
        String ingredientsForOrder = ingredients.Ingredients(responseIngredient);
        ValidatableResponse createOrderResponse = orderClient.create(ingredientsForOrder, token);
        int statusCodeCreateOrder = createOrderResponse.extract().statusCode();
        boolean success = createOrderResponse.extract().path("success");
        assertTrue(success);
        assertEquals(ErrorText.SUCCESS_CREATE_ORDER_STATUS_CODE, statusCodeCreateOrder);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем без ингредиентов")
    public void createOrderWithoutIngredient() {
        String token = "";
        String ingredientsForOrder = "{}";
        ValidatableResponse createOrderResponse = orderClient.create(ingredientsForOrder, token);
        int statusCodeCreateOrder = createOrderResponse.extract().statusCode();
        String textMessage = createOrderResponse.extract().path("message");
        assertEquals(ErrorText.CREATE_ORDER_WITHOUT_INGREDIENT_STATUS_CODE, statusCodeCreateOrder);
        assertEquals(ErrorText.CREATE_ORDER_WITHOUT_INGREDIENT_TEXT_MESSAGE, textMessage);
    }

    @Test
    @DisplayName("Создание заказа с указанием некорректного хэша ингредиента")
    public void createOrderIncorrectHash() {
        String token = "";
        String ingredientsForOrder = ingredients.IngredientsRandom();
        ValidatableResponse createOrderResponse = orderClient.create(ingredientsForOrder, token);
        int statusCodeCreateOrder = createOrderResponse.extract().statusCode();
        String textMessage = createOrderResponse.extract().path("message");
        assertEquals(ErrorText.CREATE_ORDER_WITH_INCORRECT_HASH_STATUS_CODE, statusCodeCreateOrder);
        assertEquals(ErrorText.CREATE_ORDER_WITH_INCORRECT_HASH_TEXT_MESSAGE, textMessage);
    }
}
