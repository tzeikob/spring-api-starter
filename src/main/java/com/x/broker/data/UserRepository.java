package com.x.broker.data;

import com.x.broker.domain.User;
import java.util.List;
import org.hibernate.Query;

/**
 * A data access object repository for user entities.
 *
 * @author Akis Papadopoulos
 */
public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from User where username = :username")
                .setString("username", username);

        List<User> list = (List<User>) query.list();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }
}
