package movieAPI;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import movieAPI.entity.movies;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



@QuarkusTest
public class movieResourceCRUDTest {

    // Starter test movie
    movies tMovie = new movies("123", "test", 2004, "Test movie");

    public movies cloneMovie(movies m){
        return new movies(m.imdbID, m.title, m.yearMade, m.description);
    }

    
    @Test
    public void testGET1(){
        given().get("/").then().statusCode(200);
    }


    @Test
    public void testPOST1(){
        // Add movie
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);

        // Check if exists
        given().get("/" + tMovie.imdbID).then().statusCode(200)
        .body("imdbID", equalTo(tMovie.imdbID))
        .body("title", equalTo(tMovie.title))
        .body("yearMade", equalTo(tMovie.yearMade))
        .body("description", equalTo(tMovie.description));

        // Delete movie
        given().delete("/" + tMovie.imdbID).then().statusCode(204);
    }

    
    @Test
    public void testPATCH1(){
        // Add movie
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);
        
        // Make changes
        movies nMovie = cloneMovie(tMovie);
        nMovie.title=null;
        nMovie.yearMade=1999;
        nMovie.description=null;
        
        // Patch movie
        given().contentType(ContentType.JSON).body(nMovie).when().patch("/" + tMovie.imdbID).then().statusCode(200);

        // Check contents
        given().get("/" + tMovie.imdbID).then().statusCode(200)
        .body("imdbID", equalTo(tMovie.imdbID))
        .body("title", equalTo(tMovie.title))
        .body("yearMade", equalTo(nMovie.yearMade))
        .body("description", equalTo(tMovie.description));

        // Delete movie
        given().delete("/" + tMovie.imdbID).then().statusCode(204);
    }

    @Test
    public void testPUT1(){

        // Add movie
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);
        
        // Make changes
        movies nMovie = cloneMovie(tMovie);
        nMovie.title="test 2";
        nMovie.yearMade=1999;
        
        // Put movie
        given().contentType(ContentType.JSON).body(nMovie).when().put("/" + tMovie.imdbID).then().statusCode(200);

        // Check contents
        given().get("/" + tMovie.imdbID).then().statusCode(200)
        .body("imdbID", equalTo(tMovie.imdbID))
        .body("title", equalTo(nMovie.title))
        .body("yearMade", equalTo(nMovie.yearMade))
        .body("description", equalTo(tMovie.description));

        // Delete movie
        given().delete("/" + tMovie.imdbID).then().statusCode(204);
    }
    
}
