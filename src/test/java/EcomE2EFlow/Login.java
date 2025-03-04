package EcomE2EFlow;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class Login {
    public static void main(String[] args) {

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

        LoginRequest LoginRequest = new LoginRequest();
        LoginRequest.setUserEmail("test9878@gmail.com");
        LoginRequest.setUserPassword("Password11*");

        RequestSpecification reqLogin = given().spec(req).body(LoginRequest);
        LoginResponse LoginResponse = reqLogin.when().post("api/ecom/auth/login")
                .then().assertThat().statusCode(200).extract().response().as(LoginResponse.class);

        String LoginToken = LoginResponse.getToken();
        String UserID = LoginResponse.getUserId();
        String Message = LoginResponse.getMessage();

        Assert.assertEquals(Message, "Login Successfully");



    }
}
