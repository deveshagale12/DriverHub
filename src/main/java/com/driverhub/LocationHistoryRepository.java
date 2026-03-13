
package com.driverhub;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
@Repository

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    // Finds all points for a passenger since a specific time, ordered by the most recent first
    List<LocationHistory> findByPassengerIdAndTimestampAfterOrderByTimestampDesc(Long passengerId, LocalDateTime time);
    
    void deleteByTimestampBefore(LocalDateTime expiryDate);
}