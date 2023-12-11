package meeting.room.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles extends AbstractEntity{
    @JsonBackReference
    @ManyToMany(mappedBy = "userRoles")
    private List<User> users;
    private Date startDate;
    private Date endDate;
}
