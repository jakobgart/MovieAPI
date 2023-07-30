package movieAPI.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class movies extends PanacheEntityBase {
    
    @Id
    public String imdbID;

    public String title;

    public Integer yearMade;

    public String description;

}
