package movieAPI.resource;

import movieAPI.repository.movieRepository;
import movieAPI.entity.movies;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class movieResource {
    
    @Inject
    movieRepository movieRepo;

    @GET
    public List<movies> allMovies(){
        return movieRepo.listAll();
    }

}
