package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String UserID, String UserName, String FirstName, String LastName, String Email,
			String Password, String Phone) {

		User userPayload = new User();

		userPayload.setID(Integer.parseInt(UserID));
		userPayload.setUserName(UserName);
		userPayload.setFirstName(FirstName);
		userPayload.setLastName(LastName);
		userPayload.setEmail(Email);
		userPayload.setPassword(Password);
		userPayload.setPhone(Phone);

		Response response = UserEndpoints.createUser(userPayload);

		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("Created Username: " + userPayload.getUserName());

	}
	//@Test(priority=2, dataProvider="userName", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName) {
		
		Response response =UserEndpoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		

	}

}
