package com.eventmanagemnt.eventmanagementservice.Repository;

import com.eventmanagemnt.eventmanagementservice.Model.EventWishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventWishlistRepository extends MongoRepository<EventWishlist, String> {
    Optional<EventWishlist> findByEventId(String eventId);
}
