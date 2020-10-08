import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ValidateResponse {


    @Test
    public void verifyResponse(){

        RestAssured.baseURI = "https://maps.googleapis.com";
        given().
                param("location", "-33.8670522,151.1957362").
                param("radius", "500").
                param("type", "restaurant").
                param("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").
                when().
                get("/maps/api/place/nearbysearch/json").
                then().assertThat().statusCode(200).and().log().body().
                contentType(ContentType.JSON).and().
                body("results[0].name", equalTo("The Little Snail Restaurant"));

    }

}

