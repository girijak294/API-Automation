import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Assert;

public class EmployeeTests {

    static String baseURI = "https://events.epam.com/api/v2/events";
    static int initialEmployeeCount;
    static int employeeId;  // To store the created employee ID

    public static void main(String[] args) {

        // 1. Get the list of all employees and verify the count
        initialEmployeeCount = getEmployeeCount();

        // 2. Create an employee and verify creation and count
        createEmployee();

        // 3. Get the details of the created employee and verify details
        getEmployeeDetails(employeeId);

        // 4. Update the employee details and verify update
        updateEmployeeDetails(employeeId);

        // 5. Verify the updated details
        verifyUpdatedEmployeeDetails(employeeId);

        // 6. Delete the employee and verify deletion
        deleteEmployee(employeeId);
    }

    // Function to get the employee count
    public static int getEmployeeCount() {
        Response response = given().when().get(baseURI + "/employees");
        
        // Validate the status code, content type, etc.
        response.then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType("application/json")
                .body("size()", greaterThan(0));

        // Return the number of employees
        return response.jsonPath().getList("$").size();
    }

    // Function to create a new employee
    public static void createEmployee() {
        String requestBody = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"salary\": \"5000\",\n" +
                "  \"age\": \"30\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "/create");

        // Verify the response
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"));

        // Store the employee ID
        employeeId = response.jsonPath().getInt("data.id");

        // Verify the employee count increased by 1
        Assert.assertEquals(getEmployeeCount(), initialEmployeeCount + 1);
    }

    // Function to get details of the created employee
    public static void getEmployeeDetails(int empId) {
        Response response = given()
                .pathParam("id", empId)
                .when()
                .get(baseURI + "/employee/{id}");

        // Verify the details of the employee
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("data.id", equalTo(empId))
                .body("data.name", equalTo("John Doe"))
                .body("data.salary", equalTo("5000"))
                .body("data.age", equalTo("30"));
    }

    // Function to update the employee details
    public static void updateEmployeeDetails(int empId) {
        String requestBody = "{\n" +
                "  \"salary\": \"7000\",\n" +
                "  \"age\": \"35\"\n" +
                "}";

        Response response = given()
                .pathParam("id", empId)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "/update/{id}");

        // Verify the update response
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"));
    }

    // Function to verify the updated details
    public static void verifyUpdatedEmployeeDetails(int empId) {
        Response response = given()
                .pathParam("id", empId)
                .when()
                .get(baseURI + "/employee/{id}");

        // Verify the updated details of the employee
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("data.salary", equalTo("7000"))
                .body("data.age", equalTo("35"));
    }

    // Function to delete the employee
    public static void deleteEmployee(int empId) {
        Response response = given()
                .pathParam("id", empId)
                .when()
                .delete(baseURI + "/delete/{id}");

        // Verify the delete response
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"));

        // Verify the employee count decreased by 1
        Assert.assertEquals(getEmployeeCount(), initialEmployeeCount);
    }
}
