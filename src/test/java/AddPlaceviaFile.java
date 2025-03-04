import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddPlaceviaFile {

    @Test
    public static void addPlacefile() throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        System.out.println("-----------Verify POST---------------------------");
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Sneha\\Rest Assured Training\\AddPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String place_id = js.getString("place_id");

        String delete = given().header("Content-Type", "Application/json")
                .queryParam("key", "qaclick123")
                .body("{\n" +
                        "    \"place_id\":\"" + place_id + "\"\n" +
                        "}")
                .when().log().all().delete("/maps/api/place/delete/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsDelete = ReusableMethods.rawToJson(delete);
        String extractedStatus = jsDelete.getString("status");

        Assert.assertEquals(extractedStatus, "OK");
    }
}
