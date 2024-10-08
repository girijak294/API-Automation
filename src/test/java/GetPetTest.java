import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetPetTest {

    public static void main(String[] args) {
        // Set the base URI for the petstore
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";

        // GET call to retrieve the pet
        Response response = given()
                .pathParam("petId", 12345)
                .when()
                .get("/{petId}");

        // Validate the response
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("category.name", equalTo("dog"))
                .body("name", equalTo("snoopie"))
                .body("status", equalTo("pending"));

        // Print response for confirmation
        System.out.println("Get Pet Response: " + response.asString());
    }
}
