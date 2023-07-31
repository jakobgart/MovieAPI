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

    public movies(){}

    public movies(String id, String ti, Integer ym, String des){
        this.imdbID = id;
        this.title = ti;
        this.yearMade = ym;
        this.description = des;
    }

    public void updateAllButId(movies mov){
        this.title = mov.title;
        this.yearMade = mov.yearMade;
        this.description = mov.description;
    }

    public void updateIfNotNullExceptId(movies mov){
        this.title = (mov.title==null) ? this.title : mov.title;
        this.yearMade = (mov.yearMade==null) ? this.yearMade : mov.yearMade;
        this.description = (mov.description==null) ? this.description : mov.description;
    }

    public String toString(){
        return String.format("\n%s\n%s(%d)\n%s\n", imdbID, title, yearMade, description);
    }

}
