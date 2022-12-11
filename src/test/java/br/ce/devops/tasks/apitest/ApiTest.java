package br.ce.devops.tasks.apitest;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;

public class ApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://172.20.176.1:8001/tasks-backend";
    }

    @Test
    public void deveRetornarUmaListaDeTarefas() {
        RestAssured.given()
                .log().all()
            .when()
                .get("/todo")
            .then()
                .statusCode(SC_OK)
                .log().all();
    }

    @Test
    public void deveAdicionarUmaTerefaComSucesso() {
        RestAssured.given()
                .log().all()
                .body("{ \"task\": \"Test restassured\", \"dueDate\": \"2023-12-31\" }")
                .contentType(JSON)
            .when()
                .post("/todo")
            .then()
                .statusCode(SC_CREATED)
                .log().all();
    }

    @Test
    public void naoDeveAcidionarTarefaInvalida() {
        RestAssured.given()
                .log().all()
                .body("{ \"task\": \"Test restassured\", \"dueDate\": \"2020-12-31\" }")
                .contentType(JSON)
            .when()
                .post("/todo")
            .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", CoreMatchers.is("Due date must not be in past"))
                .log().all();
    }
}

