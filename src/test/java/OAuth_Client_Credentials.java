import Files.ReusableMethods;
import Pojo.Api;
import Pojo.GetCourseDetails;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;

import static io.restassured.RestAssured.given;

public class OAuth_Client_Credentials {

    public static void main(String[] args) {

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String access_token = js.getString("access_token");

        GetCourseDetails actual_response = given().queryParam("access_token", access_token)
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourseDetails.class);

        System.out.println(actual_response.getExpertise());
        System.out.println(actual_response.getCourses().getApi().get(1).getCourseTitle());

        List<Api> ApiCourses = actual_response.getCourses().getApi();

        for (Api apiCourse : ApiCourses) {
            if (apiCourse.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourse.getPrice());
            }
        }
        ArrayList<String> actualWebAutomationCourse = new ArrayList<>();
        List<WebAutomation> webAutomation = actual_response.getCourses().getWebAutomation();

        for (int j = 0; j < webAutomation.size(); j++) {
            actualWebAutomationCourse.add(webAutomation.get(j).getCourseTitle());
        }
    }

    String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

    List<String> expectedList = Arrays.asList(courseTitles);
//    Assert.AssertTrue(actualWebAutomationCourse.equals(expectedList));


}
