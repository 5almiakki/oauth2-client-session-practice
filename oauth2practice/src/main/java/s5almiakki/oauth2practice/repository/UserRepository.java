package s5almiakki.oauth2practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s5almiakki.oauth2practice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
