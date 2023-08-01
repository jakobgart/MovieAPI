package movieAPI.resource;

import movieAPI.service.movieService;
import movieAPI.entity.movies;

import java.util.List;


import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class movieResource {

    
    @Inject
    movieService movieSer;

    @GET
    public List<movies> allMovies(@DefaultValue("title") @QueryParam("orderBy") String sortBy,
            @DefaultValue("desc") @QueryParam("orderDirection") String sortDirection,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("-1") @QueryParam("pageSize") int size) {
        return movieSer.allMovies(sortBy, sortDirection, page, size);
    }

    @GET
    @Path("/{id}")
    public movies getMovieById(@PathParam("id") String id) {
        return movieSer.getMovieById(id);
    }


    @POST
    public movies addMovie(movies mov) {
        return movieSer.addMovie(mov);
    }

    @PUT
    @Path("/{id}")
    public movies updateMovie(@PathParam("id") String id, movies mov) {
        return movieSer.updateMovie(id, mov);
    }

    @PATCH
    @Path("/{id}")
    public movies partiallyUpdateMovie(@PathParam("id") String id, movies mov) {
        return movieSer.partiallyUpdateMovie(id, mov);
    }

    @DELETE
    @Path("/{id}")
    public void deleteMovie(@PathParam("id") String id) {
        movieSer.deleteMovie(id);
    }

}
