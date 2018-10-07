package tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author Hardeep Aujla
 *
 */
public class Test1 {

	private Response response;
	private JsonPath jsonPath;

	@BeforeTest
	public void testInitialize() {
		RestAssured.baseURI = "https://api.tmsandbox.co.nz/v1";
		RequestSpecification httpRequest = RestAssured.given();
		response = httpRequest.get("/Categories/6328/Details.json");
		jsonPath = response.jsonPath();
	}

	@Test
	public void verifyName() {
		Reporter.log("Name = " + jsonPath.get("Name"), true);
		Assert.assertEquals(jsonPath.get("Name"), "Badges");
	}

	@Test
	public void verifyCanListClassifieds() {
		Reporter.log("CanListClassifieds = " + jsonPath.get("CanListClassifieds"), true);
		Assert.assertEquals(jsonPath.getBoolean("CanListClassifieds"), false);
	}

	@Test
	public void verifyTagline() {
		String charities = response.path("Charities.find { it.Description == 'Plunket' }.Tagline");
		Reporter.log("The charities element with Description Plunket has a Tagline = " + charities, true);
		Assert.assertTrue(charities.contains("well child health services"));
	}

}
