package com.p3s.sursangam.util;

import com.p3s.sursangam.model.Events;
import com.p3s.sursangam.model.Participant;
import com.p3s.sursangam.model.Person;
import com.p3s.sursangam.model.User;
import com.p3s.sursangam.payload.EventResponse;
import com.p3s.sursangam.payload.GroupSummary;
import com.p3s.sursangam.payload.UserSummary;
import com.p3s.sursangam.repository.EventRepository;
import com.p3s.sursangam.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.acl.Group;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static UserSummary mapUserToUserSummary(User user) {
        UserSummary userSummary = new UserSummary();
        user.setId(user.getId());
        userSummary.setEmail(user.getEmail());
        userSummary.setPassword(user.getPassword());
        userSummary.setFirstName(user.getFirstName());
        userSummary.setMiddleName(user.getMiddleName());
        userSummary.setLastName(user.getLastName());
        userSummary.setUsername(user.getUsername());
        return userSummary;
    }

    public static EventResponse mapEventToEventResponse(Events event, User creator) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setCity(event.getCity().getName());
        response.setCreationDateTime(event.getCreatedAt());
        response.setDetails(event.getDetails());
        response.setDuration(event.getDuration());
        response.setEventDate(event.getEventDate());
        response.setLatLong(event.getLatLong());
        response.setName(event.getName());
        response.setParticipants(event.getParticipants());
        response.setVenue(event.getVenue());
        GroupSummary creatorSummary = new GroupSummary(creator.getId(), creator.getFirstName(), creator.getUsername(),creator.getEmail(), creator.getPassword(), creator.getPhoneNo());
        response.setCreatedBy(creatorSummary);
        return response;
    }
}
