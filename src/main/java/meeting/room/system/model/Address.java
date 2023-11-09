package meeting.room.system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends AbstractEntity {
    private String street;
    private int streetNumber;
    private String city;


    @OneToMany(mappedBy = "address")
    private Set<Building> buildings;
}
