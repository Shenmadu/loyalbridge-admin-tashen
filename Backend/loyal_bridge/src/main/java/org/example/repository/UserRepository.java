package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u " +
            "WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:phone IS NULL OR u.phone LIKE %:phone%) " +
            "AND (:status IS NULL OR u.status = :status)")
    List<User> findByFilters(@Param("name") String name,
                             @Param("phone") String phone,
                             @Param("status") User.Status status);

}
