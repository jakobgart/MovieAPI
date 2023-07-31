package movieAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import movieAPI.entity.movies;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



@QuarkusTest
public class movieResourceCRUDTest {

    // Starter test movie
    public movies createStarterMovie(){
        return new movies("123", "test", 2004, "Test movie");
    }

    public movies cloneMovie(movies m){
        return new movies(m.imdbID, m.title, m.yearMade, m.description);
    }

    
    @Test
    public void testGET1(){
        given().get("/").then().statusCode(200);
    }

    // Testing sorting and paging
    @Test
    public void testGET2(){

        movies tMovie = createStarterMovie();

        // Add four movies
        tMovie.title="test1";
        tMovie.yearMade=100;
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);
        tMovie.imdbID="124";
        tMovie.title="test2";
        tMovie.yearMade=101;
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);
        tMovie.imdbID="125";
        tMovie.title="test3";
        tMovie.yearMade=102;
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);
        tMovie.imdbID="126";
        tMovie.title="test4";
        tMovie.yearMade=103;
        given().contentType(ContentType.JSON).body(tMovie).when().post("/").then().statusCode(200);

        // Get the earliest three
        movies[] movieList = given().params("orderBy", "yearMade", "orderDirection", "asc", "page", "0", "pageSize", "3").get("/").as(movies[].class);
        
        // Check if match
        assertTrue(movieList.length==3);
        assertEquals("test1", movieList[0].title);
        assertEquals("test2", movieList[1].title);
        assertEquals("test3", movieList[2].title);
        
        // Get the next three
        movieList = given().params("orderBy", "yearMade", "orderDirection", "asc", "page", "1", "pageSize", "3").get("/").as(movies[].class);
        
        // Check if they match
        assertTrue(movieList.length==3);
        assertEquals("test4", movieList[0].title);

        // Delete movies
        given().delete("/" + "123").then().statusCode(204);
        given().delete("/" + "124").then().statusCode(204);
        given().delete("/" + "125").then().statusCode(204);
        given().delete("/" + "126").then().statusCode(204);


    }


    @Test
    public void testPOST1(){
        
        movies tMovie = createStarterMovie();

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
        
        movies tMovie = createStarterMovie();

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
        
        movies tMovie = createStarterMovie();

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
