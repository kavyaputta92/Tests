package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTests {

    private static int createdItemId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:5000";
    }

    @Test
    @Order(1)
    public void testLoginSuccess() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\": \"admin\", \"password\": \"admin\"}")
        .when()
            .post("/login")
        .then()
            .statusCode(200)
            .body("message", equalTo("Login successful"));
    }

    @Test
    @Order(2)
    public void testLoginFailure() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\": \"admin\", \"password\": \"wrong\"}")
        .when()
            .post("/login")
        .then()
            .statusCode(401)
            .body("message", equalTo("Invalid credentials"));
    }

    @Test
    @Order(3)
    public void testAddItem() {
        createdItemId =
            given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Test Item\"}")
            .when()
                .post("/items")
            .then()
                .statusCode(201)
                .body("name", equalTo("Test Item"))
                .extract().path("id");
    }

    @Test
    @Order(4)
    public void testGetItems() {
        when()
            .get("/items")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    @Order(5)
    public void testUpdateItem() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"Updated Item\"}")
        .when()
            .put("/items/" + createdItemId)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Item"));
    }

    @Test
    @Order(6)
    public void testDeleteItem() {
        when()
            .delete("/items/" + createdItemId)
        .then()
            .statusCode(200)
            .body("message", equalTo("Item deleted"));
    }

    @Test
    @Order(7)
    public void testDeleteInvalidItem() {
        when()
            .delete("/items/99999")
        .then()
            .statusCode(404)
            .body("message", equalTo("Item not found"));
    }
}