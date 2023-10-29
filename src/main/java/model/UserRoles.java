package model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Date;
@Entity
public class UserRoles extends AbstractEntity{
    @ManyToMany
    private User user;
    private Date startDate;
    private Date endDate;
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
