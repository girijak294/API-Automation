import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetUsersTest {

    public static void main(String[] args) {
        // Set the base URI for JSONPlaceholder
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // GET call to retrieve users
        Response response = given()
                .when()
                .get("/users");

        // Validate the response
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(3))
                .body("name", hasItem("Ervin Howell"));

        // Print response for confirmation
        System.out.println("Get Users Response: " + response.asString());
    }
}
