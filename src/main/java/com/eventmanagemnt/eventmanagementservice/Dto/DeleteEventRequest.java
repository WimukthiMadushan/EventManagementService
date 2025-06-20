package com.eventmanagemnt.eventmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class DeleteEventRequest {
    private String eventId;
    private String hostId;
}
