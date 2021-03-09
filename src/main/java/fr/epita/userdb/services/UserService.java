package fr.epita.userdb.services;

import fr.epita.userdb.datatransfer.UserCredentialsDTO;
import fr.epita.userdb.datatransfer.UserRegistrationDTO;
import fr.epita.userdb.entities.User;
import fr.epita.userdb.repositories.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Configurable
public class UserService {
    private final UserRepository repository;

    @Value("${userdb.jwtsecret}")
    private String jwtSecret;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean userWithUsernameExists(String username) {
        var example = makeExampleFromUsername(username);
        return repository.exists(example);
    }

    public Optional<User> findUserWithUsername(String username) {
        var example = makeExampleFromUsername(username);
        return repository.findOne(example);
    }

    public Example<User> makeExampleFromUsername(String username) {
        var user = new User();
        user.setUsername(username);

        var matcher = ExampleMatcher.matchingAny()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("roles", "secret", "id");

        return Example.of(user, matcher);
    }

    public User createUser(UserRegistrationDTO userRegistrationDTO) {
        var newUser = new User();
        var userCredentialsDTO = userRegistrationDTO.getCredentials();
        var contact = userRegistrationDTO.getContact().toEntity();
        var address = userRegistrationDTO.getBillingAddress().toEntity();
        contact.setBillingAddress(address);

        newUser.setContact(contact);
        newUser.setUsername(userCredentialsDTO.getUsername());
        newUser.setSecret(BCrypt.hashpw(userCredentialsDTO.getPassword(), BCrypt.gensalt()));

        // Default permissions
        newUser.setRoles(new User.Role[]{
                User.Role.VIEW_MOVIES,
                User.Role.RATE_MOVIES,
        });

        return repository.save(newUser);
    }

    public boolean credentialsAreValid(UserCredentialsDTO userCredentialsDTO, User targetUser) {
        return BCrypt.checkpw(userCredentialsDTO.getPassword(), targetUser.getSecret());
    }

    public String makeJWT(User user) {
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles())
                .claim("name", user.getContact().getName())
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
