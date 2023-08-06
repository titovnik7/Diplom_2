import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    private List<String> responseIngredient;

    public String Ingredients(List<String> responseIngredient) {
        String infoIngredient = String.valueOf(responseIngredient.get(0));
        String[] split = infoIngredient.split(",");
        String ingredientId = split[0];
        String id = '"' + ingredientId.replace("{_id=", "") + '"';
        ArrayList<String> ingredientForOrder = new ArrayList<>();
        ingredientForOrder.add(id);
        return ingredientForOrder.toString();
    }

    public String IngredientsRandom() {
        Faker faker = new Faker();
        String ingredientId = RandomStringUtils.randomNumeric(24);
        String id = '"' + ingredientId.replace("{_id=", "") + '"';
        ArrayList<String> ingredientForOrder = new ArrayList<>();
        ingredientForOrder.add(id);
        return ingredientForOrder.toString();
    }
}
