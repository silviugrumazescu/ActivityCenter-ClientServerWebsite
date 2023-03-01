package com.ActivityCenterServer.database.repository;

import com.ActivityCenterServer.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.username = ?1 ORDER BY e.datetime")
    List<Event> findAllByUsername(String username);

}
