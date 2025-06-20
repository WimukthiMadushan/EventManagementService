package com.eventmanagemnt.eventmanagementservice.Controller;


import com.eventmanagemnt.eventmanagementservice.Dto.EventRequest;
import com.eventmanagemnt.eventmanagementservice.Dto.EventResponse;
import com.eventmanagemnt.eventmanagementservice.Dto.DeleteEventRequest;
import com.eventmanagemnt.eventmanagementservice.Service.CloudinaryService;
import com.eventmanagemnt.eventmanagementservice.Service.EventService;
import com.eventmanagemnt.eventmanagementservice.Utility.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException; // import the class correctly
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/event")
public class EventController {
    private static final String SECRET_KEY_STRING = "your-very-strong-secret-key-which-is-at-least-32-characters";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    private final EventService eventService;

    @Autowired
    private CloudinaryService cloudinaryService;

    //Correct........
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@ModelAttribute EventRequest eventRequest, @RequestHeader("Authorization") String authHeader) throws IOException {
        log.info("Creating event with request: {}", eventRequest);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid token");
        }
        String token = authHeader.substring(7);
        JwtUtil jwtUtil = new JwtUtil(SECRET_KEY);
        Claims claims;

        try {
            claims = jwtUtil.extractClaims(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long hostId = claims.get("id", Integer.class).longValue();
        String email = claims.get("email", String.class);

        log.info("Extracted hostId: {}, email: {}", hostId, email);

        if (hostId == null || email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing user info in token");
        }

        String coverImageUrl = null;
        String coverImageThumbnailUrl = null;

        if (eventRequest.coverImage() != null && !eventRequest.coverImage().isEmpty()) {
            coverImageUrl = cloudinaryService.uploadImage(eventRequest.coverImage());
            coverImageThumbnailUrl = cloudinaryService.uploadThumbnail(eventRequest.coverImage());
        }

        List<String> imageUrls = new ArrayList<>();
        List<String> imageThumbnails = new ArrayList<>();

        if (eventRequest.images() != null) {
            for (MultipartFile image : eventRequest.images()) {
                if (image != null && !image.isEmpty()) {
                    imageUrls.add(cloudinaryService.uploadImage(image));
                    imageThumbnails.add(cloudinaryService.uploadThumbnail(image));
                }
            }
        }

        return eventService.createEvent(eventRequest, coverImageUrl, coverImageThumbnailUrl, imageUrls, imageThumbnails, email);
    }

    @PostMapping(value = "/create/test", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@ModelAttribute EventRequest eventRequest) throws IOException {

        String coverImageUrl = null;
        String coverImageThumbnailUrl = null;

        if (eventRequest.coverImage() != null && !eventRequest.coverImage().isEmpty()) {
            coverImageUrl = cloudinaryService.uploadImage(eventRequest.coverImage());
            coverImageThumbnailUrl = cloudinaryService.uploadThumbnail(eventRequest.coverImage());
        }

        List<String> imageUrls = new ArrayList<>();
        List<String> imageThumbnails = new ArrayList<>();

        if (eventRequest.images() != null) {
            for (MultipartFile image : eventRequest.images()) {
                if (image != null && !image.isEmpty()) {
                    imageUrls.add(cloudinaryService.uploadImage(image));
                    imageThumbnails.add(cloudinaryService.uploadThumbnail(image));
                }
            }
        }
        String email = "wimukthimadushan6@gmail.com";

        return eventService.createEvent(eventRequest, coverImageUrl, coverImageThumbnailUrl, imageUrls, imageThumbnails, email);
    }

    @GetMapping("/search")
    public List<EventResponse> searchEvents(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.searchEvents(query, page, size);
    }

    //Correct.......
    @GetMapping("/getAll")
    public List<EventResponse> getAllEvent(){
        return eventService.getAllEvents();
    }
    @GetMapping("/getEvents")
    public List<EventResponse> getEvents(@RequestParam Map<String,String> request){
        String id = request.get("id");
        String limit = request.get("limit");
        return eventService.getEvents(id,limit);
    }
    //Correct........
    @GetMapping("/get/{id}")
    public List<EventResponse> getEventById(@PathVariable String id){
        return eventService.getEventById(id);
    }
    //Correct........
    @PostMapping("/getEventByHost")
    public List<EventResponse> getEventByHost(@RequestBody Map<String,String> request){
        String id = request.get("host_id");
        return eventService.getEventByHost(id);
    }
    //Correct........
    @GetMapping("/getEventByCategory")
    public List<EventResponse> getEventByCategory(@RequestParam Map<String,String> request){
        String category = request.get("category");
        return eventService.getEventByCategory(category);
    }
    //Correct........
    @GetMapping("/getTrendingEvents")
    public List<EventResponse> getTrendingEvents(){
        return eventService.getTrendingEvents();
    }

    //Correct........
    @DeleteMapping("/deleteEvent")
    public EventResponse deleteEvent(@RequestBody DeleteEventRequest request){
        return eventService.deleteEvent(request.getEventId(),request.getHostId());
    }
}
