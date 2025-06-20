package com.eventmanagemnt.eventmanagementservice.Event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddEvent {
    private String id;
    private String hostId;
    private String name;
    private String city;
    private String country;
    private String email;

    public AddEvent(String id, String hostId, String name, String city, String country, String email) {
        this.id = id;
        this.hostId = hostId;
        this.name = name;
        this.city = city;
        this.country = country;
        this.email = email;

    }





}
