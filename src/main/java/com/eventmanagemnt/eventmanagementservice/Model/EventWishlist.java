package com.eventmanagemnt.eventmanagementservice.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "event_wishlists")
public class EventWishlist {
    @Id
    private String id;

    private String eventId;
    private List<String> travellerIds = new ArrayList<>();

    public EventWishlist() {}

    public EventWishlist(String eventId) {
        this.eventId = eventId;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<String> getTravellerIds() {
        return travellerIds;
    }

    public void setTravellerIds(List<String> travellerIds) {
        this.travellerIds = travellerIds;
    }
}
