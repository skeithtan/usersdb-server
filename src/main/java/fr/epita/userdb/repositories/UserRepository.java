package fr.epita.userdb.repositories;

import fr.epita.userdb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
