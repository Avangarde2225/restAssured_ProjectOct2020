import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.ResponseGetCourse;

import java.util.List;

import static io.restassured.RestAssured.*;


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

        System.out.println("Linkedin profile " + response.getLinkedin());
        System.out.println(response.getIntructor());
        System.out.println(response.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = response.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            if(apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices Testing")){
                System.out.println("SoapUI Course Price " + apiCourses.get(i).getPrice());
            }
        }

        //System.out.println(response);

    }
}