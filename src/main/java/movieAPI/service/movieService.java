package movieAPI.service;

import java.util.List;



import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import movieAPI.repository.movieRepository;
import jakarta.inject.Inject;

import movieAPI.entity.movies;

@ApplicationScoped
public class movieService {
    
    @Inject
    movieRepository movieRepo;

    public List<movies> allMovies(String sortBy, String sortDirection, int page, int pageSize){
        PanacheQuery<movies> query = movieRepo.findAll(
                Sort.by(sortBy, sortDirection.equals("asc") ? Direction.Ascending : Direction.Descending));

        if (pageSize > 0) {
            query = query.page(Page.of(page, pageSize));
        }

        return query.list();
    }

    public movies getMovieById(String id){
        return movieRepo.findById(id);
    }

    @Transactional
    public movies addMovie(movies mov){
        movieRepo.persist(mov);
        return mov;
    }

    @Transactional
    public movies updateMovie(String id, movies mov) {
        movies entity = movieRepo.findById(id);
        entity.title = mov.title;
        entity.yearMade = mov.yearMade;
        entity.description = mov.description;

        movieRepo.persist(entity);
        return entity;
    }

    @Transactional
    public movies partiallyUpdateMovie(String id, movies mov) {
        movies entity = movieRepo.findById(id);
        entity.title = (mov.title==null) ? entity.title : mov.title;
        entity.yearMade = (mov.yearMade==null) ? entity.yearMade : mov.yearMade;
        entity.description = (mov.description==null) ? entity.description : mov.description;
        
        movieRepo.persist(entity);
        return entity;
    }

    @Transactional
    public void deleteMovie(String id) {
        movieRepo.deleteById(id);
    }

}
