package PojoAddPlace;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTestSpecBuilder {
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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(req)
                .body(p);

       Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(resp).extract().response();
       String responsesString = response.asString();
        System.out.println(responsesString);
    }
}
