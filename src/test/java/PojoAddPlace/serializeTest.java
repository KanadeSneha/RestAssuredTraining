package PojoAddPlace;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://google.com");
        List<String> typeList = new ArrayList<String>();
        typeList.add("shoe park");
        typeList.add("shop");
        p.setTypes(typeList);

        location l = new location();
        p.setLocation(l);
        l.setLat(-38.383494);
        l.setLng(33.427362);


        String response = given().queryParam("key", "qaclick123")
                .body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
