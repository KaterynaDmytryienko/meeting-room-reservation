package meeting.room.system.dao;

import meeting.room.system.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDao extends BaseDao<Address> {

    protected AddressDao() {
        super(Address.class);
    }
}
