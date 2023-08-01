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

    public String toString(){
        return String.format("\n\"%s\"\n\"%s\" (%d)\n\"%s\"\n", imdbID, title, yearMade, description);
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null || obj.getClass() != this.getClass()) return false;
        final movies m = (movies) obj;
        return this.imdbID.equals(m.imdbID) && this.title.equals(m.title) && this.yearMade.equals(m.yearMade) && this.description.equals(m.description);
    }

}
