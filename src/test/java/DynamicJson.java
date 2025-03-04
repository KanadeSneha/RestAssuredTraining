import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider ="BooksData")
    public static void addBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";

        String addBook = given().header("Content-Type", "Application/Json")
                .body(payload.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(addBook);

        String ID = js.getString("ID");
        String message = js.getString("Msg");
        System.out.println(message);
        System.out.println(ID);


        //get book details
//        String getBook = given().header("Content-Type","Application/Json")
//                .when().get("/Library/GetBook.php?AuthorName=somename")
//                .then().assertThat().statusCode(200)
//                .extract().response().asString();


        //Delete book
        String deletedResponse = given().header("Content-Type", "Application/Json")
                .body(payload.DeleteBook(ID))
                .when().post("/Library/DeleteBook.php")
                .then().extract().response().asString();


        JsonPath js1 = ReusableMethods.rawToJson(deletedResponse);
        String deletedMsg = js1.getString("msg");
        System.out.println(deletedMsg);
        System.out.println("*************************************************************");

    }

    @DataProvider(name="BooksData")
    public Object[][] getData(){
       return new Object[][]{{"A", "1"},{"B", "2"},{"C", "3"}};
    }
}
