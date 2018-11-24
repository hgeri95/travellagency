package bme.swarch.travellagency.agencyservice.repository;

import bme.swarch.travellagency.agencyservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUsername(String username);
}
