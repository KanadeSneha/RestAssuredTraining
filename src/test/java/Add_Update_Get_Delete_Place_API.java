import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Add_Update_Get_Delete_Place_API {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        System.out.println("-----------Verify POST---------------------------");
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String place_id = js.getString("place_id");

        //Update Place
        String newAddress = "Summer Walk, Africa";

        System.out.println("-----------Verify PUT---------------------------");
        given().header("Content-Type", "application/json")
                .queryParam("key", "qaclick123")
                .body("{\n" +
                        "\"place_id\":\"" + place_id + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
        System.out.println("-----------Verify Get---------------------------");

        //get new address and verify
        System.out.println("-----------Verify GET---------------------------");
        String response1 = given().queryParam("key", "qaclick123")
                .queryParam("place_id", place_id)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(response1);
        String extractedAddress = js1.getString("address");
        System.out.println(extractedAddress);
        Assert.assertEquals(extractedAddress, newAddress);

        System.out.println("-----------Verify Delete---------------------------");

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
