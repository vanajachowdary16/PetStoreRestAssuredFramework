package api.endpoints;

//UserEndpoints.java 
//created for perform CRUD operation
//create, read, update, delete 
import api.payloads.User;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {

	public static Response createUser(User Payload) {

		Response response = given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(Payload)

				.when()
				.post(Routes.post_url);

		return response;

	}

	public static Response readUser(String userName) {

		Response response = given()
				.pathParam("username", userName)

				.when()
				.get(Routes.get_url);

		return response;

	}
	
	public static Response updateUser(String userName, User Payload) {

		Response response = given()
				.pathParam("username", userName)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(Payload)

				.when()
				.put(Routes.update_url);

		return response;

	}
	public static Response deleteUser(String userName) {

		Response response = given()
				.pathParam("username", userName)

				.when()
				.delete(Routes.delete_url);

		return response;

	}

}
