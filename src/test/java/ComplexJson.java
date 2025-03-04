import Files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJson {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.CoursePrice());
        //Print No of courses returned by API
        int coursesCount = js.getInt("courses.size()");
        System.out.println(coursesCount);
        System.out.println("***********************************");
        //2.Print Purchase Amount

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);
        System.out.println("***********************************");

        //3. Print Title of the first course

        System.out.println(js.getString("courses[0].title"));
        System.out.println("***********************************");
        //4. Print All course titles and their respective Prices

        for (int i = 0; i < coursesCount; i++) {
            System.out.println(js.getString("courses[" + i + "].title"));
            System.out.println(js.getString("courses[" + i + "].price"));
        }

        System.out.println("***********************************");
        //5. Print no of copies sold by RPA Course
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equals("RPA")) {
                System.out.println(js.getInt("courses[" + i + "].copies"));
            }

        }
        System.out.println("***********************************");
        //6. Verify if Sum of all Course prices matches with Purchase Amount
        int coursePrice=0;
        for (int i = 0; i < coursesCount; i++) {
            coursePrice = coursePrice + js.getInt("courses[" + i + "].price");

        }
        System.out.println(coursePrice);
        System.out.println("***********************************");
        //6. Verify if Sum of all Course prices matches with Purchase Amount

        int calAmount=0;

        for (int i = 0; i < coursesCount; i++) {
            calAmount =calAmount+ (js.getInt("courses[" + i + "].price"))*(js.getInt("courses[" + i + "].copies"));
        }

        Assert.assertEquals(purchaseAmount,calAmount);
    }

}



