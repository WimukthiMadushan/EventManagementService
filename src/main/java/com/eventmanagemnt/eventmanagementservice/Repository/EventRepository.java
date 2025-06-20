package com.eventmanagemnt.eventmanagementservice.Repository;

import com.eventmanagemnt.eventmanagementservice.Model.Event;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByHostId(String hostId);
    List<Event> findByCategory(String category);
    List<Event> findByIdGreaterThanEqualOrderByIdAsc(String id, PageRequest pageable);
    @Query("{ '$or': [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<Event> searchEventsByQuery(String query, Pageable pageable);
}
