package Files;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class sumvalidation {

    public void sumOfCourses() {
        int calAmount = 0;

        JsonPath js = new JsonPath(payload.CoursePrice());
        int coursesCount = js.getInt("courses.size()");
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");

        for (int i = 0; i < coursesCount; i++) {
            calAmount = calAmount + (js.getInt("courses[" + i + "].price")) * (js.getInt("courses[" + i + "].copies"));
        }

        Assert.assertEquals(purchaseAmount, calAmount);
    }

}
