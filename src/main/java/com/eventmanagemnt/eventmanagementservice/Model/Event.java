package com.eventmanagemnt.eventmanagementservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(value = "Event")
public class Event {
    @Id
    private String id;
    private String hostId;
    private String name;
    private String description;
    private String country;
    private String city;
    private String venue;
    private double latitude;
    private double longitude;
    private String startDate;
    private String endDate;
    private String category;
    private String coverImage;
    private String coverImageThumbnail;
    private List<String> images;
    private List<String> imageThumbnails;
    private Integer wishCount;
}
