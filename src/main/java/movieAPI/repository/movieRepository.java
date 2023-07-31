package movieAPI.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


import movieAPI.entity.movies;

@ApplicationScoped
public class movieRepository implements  PanacheRepository<movies>{

    
    public movies findById(String id){
        return find("imdbID", id).firstResult();
    }

    public void deleteById(String id){
        delete("imdbID", id);
    }
    
}

