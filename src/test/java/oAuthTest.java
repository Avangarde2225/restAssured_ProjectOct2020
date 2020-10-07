import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.ResponseGetCourse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class oAuthTest {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\suler\\Desktop\\Selenium\\chromedriver\\chromedriver.exe");
//        WebDriver driver;
//        driver = new ChromeDriver();

        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyddss&code=4%2F4wE899oPFPjpzmH17BB5Hx_vqlmRS4UWoVCpNNmgIyIWUz0HSkHrBfwS8NdmxJNWfzyn7ggGvejsbaCptzZLyxQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";

        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);


        String accessTokenResponse = given().urlEncodingEnabled(false).
                queryParams("code",code).
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type","authorization_code").
        when().log().all().
        post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");


      ResponseGetCourse response =  given().
                queryParam("access_token", accessToken).
              expect().defaultParser(Parser.JSON).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").as(ResponseGetCourse.class);
        //System.out.println(response);

    }
}