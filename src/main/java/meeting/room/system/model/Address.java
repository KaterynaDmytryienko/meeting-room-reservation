package meeting.room.system.model;

import jakarta.persistence.Column;
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
    @Column(nullable = false, name = "street_name")
    private String street;
    @Column(nullable = false, name = "street_number")
    private int streetNumber;

    @Column(nullable = false, name = "city_name")
    private String city;


    @OneToMany(mappedBy = "address")
    private Set<Building> buildings;
}
