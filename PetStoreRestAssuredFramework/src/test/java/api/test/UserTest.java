package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTest {
	
	public Logger logger;
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setUpData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setID(faker.idNumber().hashCode());
		userPayload.setUserName(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("********Creating the user*******");
		
		Response response =UserEndpoints.createUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("Created Username: " + userPayload.getUserName());
		
		logger.info("User is created");
	}
	
	
	@Test(priority=2, dependsOnMethods = {"testPostUser"})
	public void testGetUserByName() throws InterruptedException {
		Thread.sleep(2000);
		
		Response response = UserEndpoints.readUser(this.userPayload.getUserName());
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	
	@Test(priority=3)
	public void testUpdateUserByName() throws InterruptedException {
		//update data using Payload
		Thread.sleep(2000);
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userPayload.getUserName(), userPayload);		
		response.then().log().body();		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after update
		
		Response responseAfterUpdate = UserEndpoints.readUser(this.userPayload.getUserName());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() throws InterruptedException {
		Thread.sleep(2000);
		
		Response response =UserEndpoints.deleteUser(this.userPayload.getUserName());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}


}
