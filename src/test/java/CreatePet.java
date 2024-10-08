import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreatePet {

    public static void main(String[] args) {
        // Define the base URI
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";

        // Create the pet
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": 12345,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"dog\"\n" +
                        "  },\n" +
                        "  \"name\": \"snoopie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"pending\"\n" +
                        "}")
                .when()
                .post();

        // Print response for confirmation
        System.out.println("Response: " + response.asString());
    }
}
