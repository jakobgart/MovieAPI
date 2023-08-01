package movieAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import movieAPI.entity.movies;

import static io.restassured.RestAssured.given;


@QuarkusTest
public class movieResourceCRUDTest {

    

    
    @Test
    public void testGET1(){
        given().get("/").then().statusCode(200);
    }

    // Testing sorting and paging
    @Test
    public void testGET2(){


        // Add four movies
        movies tMovie1 = new movies("123", "test1", 100, "First movie.");
        addMovie(tMovie1, 200);

        movies tMovie2 = new movies("124", "test2", 101, "Second movie.");
        addMovie(tMovie2, 200);
        
        movies tMovie3 = new movies("125", "test3", 102, "Third movie.");
        addMovie(tMovie3, 200);

        movies tMovie4 = new movies("126", "test4", 104, "Fourth movie.");
        addMovie(tMovie4, 200);

        // Get the earliest three
        movies[] movieList = given().params("orderBy", "yearMade", "orderDirection", "asc", "page", "0", "pageSize", "3").get("/").as(movies[].class);
        
        // Check if match
        assertTrue(movieList.length==3);
        assertEquals(tMovie1, movieList[0]);
        assertEquals(tMovie2, movieList[1]);
        assertEquals(tMovie3, movieList[2]);

        // Get the next three
        movieList = given().params("orderBy", "yearMade", "orderDirection", "asc", "page", "1", "pageSize", "3").get("/").as(movies[].class);
        
        // Check if they match
        assertTrue(movieList.length==3);
        assertEquals(tMovie4, movieList[0]);

        // Delete movies
        deleteMovie(tMovie1.imdbID, 204);
        deleteMovie(tMovie2.imdbID, 204);
        deleteMovie(tMovie3.imdbID, 204);
        deleteMovie(tMovie4.imdbID, 204);

    }


    @Test
    public void testPOST1(){
        
        movies tMovie = createTestMovie();

        addMovie(tMovie, 200);

        // Check if exists
        movies recieved = given().get("/" + tMovie.imdbID).then().statusCode(200).extract().as(movies.class);
        assertEquals(tMovie, recieved);

        deleteMovie(tMovie.imdbID, 204);
    }

    
    @Test
    public void testPATCH1(){
        
        movies tMovie = createTestMovie();

        addMovie(tMovie, 200);
        
        // Patching info
        movies nMovie = new movies(tMovie.imdbID, null, 1999, null);
        
        // Patch movie
        given().contentType(ContentType.JSON).body(nMovie).when().patch("/" + nMovie.imdbID).then().statusCode(200);

        // Create expected final movie
        nMovie.title=tMovie.title;
        nMovie.description=tMovie.description;

        // Check if patched
        movies recieved = given().get("/" + tMovie.imdbID).then().statusCode(200).extract().as(movies.class);
        assertEquals(nMovie, recieved);

        // Delete movie
        deleteMovie(nMovie.imdbID, 204);
    }

    @Test
    public void testPUT1(){
        
        movies tMovie = createTestMovie();

        // Add movie
        addMovie(tMovie, 200);
        
        // Make changes
        movies nMovie = cloneMovie(tMovie);
        nMovie.title="test 2";
        nMovie.yearMade=1999;
        
        // Put movie
        given().contentType(ContentType.JSON).body(nMovie).when().put("/" + nMovie.imdbID).then().statusCode(200);

        // Check if updated
        movies recieved = given().get("/" + tMovie.imdbID).then().statusCode(200).extract().as(movies.class);
        assertEquals(nMovie, recieved);

        // Delete movie
        deleteMovie(nMovie.imdbID, 204);
    }





    









    /* Help functions */
    
    public void addMovie(movies m, int statusCode){
        given().contentType(ContentType.JSON).body(m).when().post("/").then().statusCode(statusCode);
    }

    public void deleteMovie(String mID, int statusCode){
        given().delete("/" + mID).then().statusCode(statusCode);
    }



    // Starter test movie
    public movies createTestMovie(){
        return new movies("123", "test", 2004, "Test movie");
    }
    // Clone movie
    public movies cloneMovie(movies m){
        return new movies(m.imdbID, m.title, m.yearMade, m.description);
    }
}
