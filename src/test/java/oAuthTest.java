import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class oAuthTest {
    public static void main(String[] args) {

        String accessTokenResponse = given().queryParams("code","4%2F4wHmv9gggwV3ICXDzJdsT6sn2Kvi6HcmPENUaEO5b0MH1U37nO1nKhhFMwCIXYMvKR67ymHF1lnaD4SW_YMduao").
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type","authorization_code").
        when().
        post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");


      String response =  given().
                queryParam("access_token", accessToken).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);

    }
}