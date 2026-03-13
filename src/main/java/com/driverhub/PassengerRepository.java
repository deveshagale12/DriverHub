package com.driverhub;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
Optional<Passenger> findByEmail(String email);
}