package com.app.data;

import com.app.domain.User;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * A data access object repository for user entities.
 *
 * @author Akis Papadopoulos
 */
@Repository
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

    public boolean isUsernameTaken(String username) {
        User user = findByUsername(username);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }
}
