package com.eventmanagemnt.eventmanagementservice.Repository;

import com.eventmanagemnt.eventmanagementservice.Model.TravellerWishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TravellerWishlistRepository extends MongoRepository<TravellerWishlist, String> {
    Optional<TravellerWishlist> findByTravellerId(String travellerId);
}
