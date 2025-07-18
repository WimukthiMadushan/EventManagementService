package com.eventmanagemnt.eventmanagementservice.Service;


import com.eventmanagemnt.eventmanagementservice.Dto.EventRequest;
import com.eventmanagemnt.eventmanagementservice.Dto.EventResponse;
import com.eventmanagemnt.eventmanagementservice.Event.AddEvent;
import com.eventmanagemnt.eventmanagementservice.Model.Event;
import com.eventmanagemnt.eventmanagementservice.Model.EventWishlist;
import com.eventmanagemnt.eventmanagementservice.Model.TravellerWishlist;
import com.eventmanagemnt.eventmanagementservice.Repository.EventRepository;
import com.eventmanagemnt.eventmanagementservice.Repository.EventWishlistRepository;
import com.eventmanagemnt.eventmanagementservice.Repository.TravellerWishlistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final EventWishlistRepository eventWishlistRepository;
    private final TravellerWishlistRepository travellerWishlistRepository;
    private final KafkaTemplate<String, AddEvent> kafkaTemplate;

    public EventResponse createEvent(EventRequest eventRequest, String coverImageUrl, String coverImageThumbnailUrl, List<String> imageUrls, List<String> imageThumbnails, String email) {
        int wishCount = eventRequest.wishCount() != null ? eventRequest.wishCount() : 0;
        Event event = Event.builder()
                .hostId(eventRequest.hostId())
                .name(eventRequest.name())
                .description(eventRequest.description())
                .country(eventRequest.country())
                .city(eventRequest.city())
                .venue(eventRequest.venue())
                .longitude(eventRequest.longitude())
                .latitude(eventRequest.latitude())
                .startDate(eventRequest.startDate())
                .endDate(eventRequest.endDate())
                .category(eventRequest.category())
                .coverImage(coverImageUrl)
                .coverImageThumbnail(coverImageThumbnailUrl)
                .images(imageUrls)
                .imageThumbnails(imageThumbnails)
                .wishCount(wishCount)
                .build();

        eventRepository.save(event);
        //AddEvent addEvent = new AddEvent(eventRequest.id(),eventRequest.hostId(),eventRequest.name(), eventRequest.city(),eventRequest.country(), email) ;
        //kafkaTemplate.send("Add-Event", addEvent)
        //        .whenComplete((result, ex) -> {
        //            if (ex == null) {
        //                log.info("Event sent successfully to Kafka topic Add-Event: {}", addEvent);
        //            } else {
        //                log.error("Failed to send event to Kafka topic Add-Event", ex);
        //            }
        //        });
        return new EventResponse(
                event.getId(),
                event.getHostId(),
                event.getName(),
                event.getDescription(),
                event.getCountry(),
                event.getCity(),
                event.getVenue(),
                event.getLatitude(),
                event.getLongitude(),
                event.getStartDate(),
                event.getEndDate(),
                event.getCategory(),
                event.getCoverImage(),
                event.getCoverImageThumbnail(),
                event.getImages(),
                event.getImageThumbnails(),
                event.getWishCount()
        );

    }
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public List<EventResponse> getEventById(String id) {
        return eventRepository.findById(id).stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public List<EventResponse> getEventByHost(String id) {
        return eventRepository.findByHostId(id).stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public List<EventResponse> getEventByCategory(String category) {
        return eventRepository.findByCategory(category).stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public List<EventResponse> getTrendingEvents() {
        return eventRepository.findAll().stream()
                .sorted((event1, event2) -> event2.getWishCount() - event1.getWishCount())
                .limit(8)
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public List<EventResponse> getEvents(String id, String limit) {
        int limitNumber = Integer.parseInt(limit);
        PageRequest pageable = PageRequest.of(0, limitNumber);

        return eventRepository.findByIdGreaterThanEqualOrderByIdAsc(id, pageable).stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }

    public EventResponse deleteEvent(String id, String hostId) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        if (event.getHostId().equals(hostId)) {
            eventRepository.delete(event);
            return new EventResponse(
                    event.getId(),
                    event.getHostId(),
                    event.getName(),
                    event.getDescription(),
                    event.getCountry(),
                    event.getCity(),
                    event.getVenue(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getCategory(),
                    event.getCoverImage(),
                    event.getCoverImageThumbnail(),
                    event.getImages(),
                    event.getImageThumbnails(),
                    event.getWishCount()
            );
        } else {
            throw new RuntimeException("You are not authorized to delete this event");
        }
    }

    public List<EventResponse> searchEvents(String query, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<Event> events = eventRepository.searchEventsByQuery(query, pageable);
        return events.stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getHostId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCountry(),
                        event.getCity(),
                        event.getVenue(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getCategory(),
                        event.getCoverImage(),
                        event.getCoverImageThumbnail(),
                        event.getImages(),
                        event.getImageThumbnails(),
                        event.getWishCount()
                )).toList();
    }
    public void addToWishlist(String eventId, String travellerId) {
        // Add travellerId to the list for the event
        EventWishlist eventWishlist = eventWishlistRepository.findByEventId(eventId)
                .orElseGet(() -> new EventWishlist(eventId));

        if (!eventWishlist.getTravellerIds().contains(travellerId)) {
            eventWishlist.getTravellerIds().add(travellerId);
            eventWishlistRepository.save(eventWishlist);
        }

        // Add eventId to the list for the traveller
        TravellerWishlist travellerWishlist = travellerWishlistRepository.findByTravellerId(travellerId)
                .orElseGet(() -> new TravellerWishlist(travellerId));

        if (!travellerWishlist.getEventIds().contains(eventId)) {
            travellerWishlist.getEventIds().add(eventId);
            travellerWishlistRepository.save(travellerWishlist);
        }
    }

    public void removeFromWishlist(String eventId, String travellerId) {
        // Remove travellerId from the event wishlist
        eventWishlistRepository.findByEventId(eventId).ifPresent(eventWishlist -> {
            if (eventWishlist.getTravellerIds().remove(travellerId)) {
                eventWishlistRepository.save(eventWishlist);
            }
        });

        // Remove eventId from the traveller wishlist
        travellerWishlistRepository.findByTravellerId(travellerId).ifPresent(travellerWishlist -> {
            if (travellerWishlist.getEventIds().remove(eventId)) {
                travellerWishlistRepository.save(travellerWishlist);
            }
        });
    }

    public List<String> getWishlistByTraveller(String travellerId) {
        return travellerWishlistRepository.findByTravellerId(travellerId)
                .map(TravellerWishlist::getEventIds)
                .orElseGet(ArrayList::new);
    }

    public List<String> getWishlistByEvent(String eventId) {
        return eventWishlistRepository.findByEventId(eventId)
                .map(EventWishlist::getTravellerIds)
                .orElseGet(ArrayList::new);
    }
}
