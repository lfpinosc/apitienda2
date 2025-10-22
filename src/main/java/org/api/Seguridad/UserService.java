package org.api.Seguridad;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    public User findByUsername(String username) {
        return User.find("username", username).firstResult();
    }

    @Transactional
    public User create(String username, String plainPassword, java.util.Set<String> roles) {
        User u = new User();
        u.username = username;
        u.passwordHash = org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
        u.roles = roles;
        u.persist();
        return u;
    }
}

