package movieAPI.resource;

import movieAPI.repository.movieRepository;
import movieAPI.entity.movies;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class movieResource {
    
    private static final Logger log = Logger.getLogger(movieResource.class);

    @Inject
    movieRepository movieRepo;

    
    @GET
    public List<movies> allMovies(){
        return movieRepo.listAll();
    }

    @GET
    @Path("/{id}")
    public movies getMovieById(@PathParam("id") String id){
        log.info(movieRepo.findById(id));
        return movieRepo.findById(id);
    }

    @POST
    @Transactional
    public movies addMovie(movies mov){
        movieRepo.persist(mov);
        return mov;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public movies updateMovie(@PathParam("id") String id, movies mov){
        movies entity = movieRepo.findById(id);
        entity.updateAllButId(mov);
        movieRepo.persist(entity);
        return entity;
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public movies partiallyUpdateMovie(@PathParam("id") String id, movies mov){
        movies entity = movieRepo.findById(id);
        entity.updateIfNotNullExceptId(mov);
        movieRepo.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteMovie(@PathParam("id") String id){
        movieRepo.deleteById(id);
    }

}
