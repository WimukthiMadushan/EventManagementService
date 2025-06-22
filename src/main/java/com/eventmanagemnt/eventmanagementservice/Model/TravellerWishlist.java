package com.eventmanagemnt.eventmanagementservice.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "traveller_wishlists")
public class TravellerWishlist {
    @Id
    private String id;

    private String travellerId;
    private List<String> eventIds = new ArrayList<>();

    public TravellerWishlist() {}

    public TravellerWishlist(String travellerId) {
        this.travellerId = travellerId;
    }

    public String getId() {
        return id;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public List<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<String> eventIds) {
        this.eventIds = eventIds;
    }
}
