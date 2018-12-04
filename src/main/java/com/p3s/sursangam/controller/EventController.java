package com.p3s.sursangam.controller;

import com.p3s.sursangam.model.Events;
import com.p3s.sursangam.model.Participant;
import com.p3s.sursangam.model.User;
import com.p3s.sursangam.model.UserRegistration;
import com.p3s.sursangam.payload.*;
import com.p3s.sursangam.repository.GroupRepository;
import com.p3s.sursangam.repository.RoleRepository;
import com.p3s.sursangam.repository.UserRegistrationRepository;
import com.p3s.sursangam.repository.UserRepository;
import com.p3s.sursangam.security.CurrentUser;
import com.p3s.sursangam.security.JwtTokenProvider;
import com.p3s.sursangam.security.UserPrincipal;
import com.p3s.sursangam.service.EventService;
import com.p3s.sursangam.service.FileStorageService;
import com.p3s.sursangam.service.GroupProfileService;
import com.p3s.sursangam.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupProfileService groupProfileService;

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    EventService eventService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public PagedResponse<EventResponse> getEvents(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return eventService.getAllEvents(currentUser, page, size);
    }

    @GetMapping("/{username}/list")
    @PreAuthorize("hasRole('GROUP')")
    public PagedResponse<EventResponse> getEventsCreatedBy(@PathVariable(value = "username") String username,
                                                           @CurrentUser UserPrincipal currentUser,
                                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return eventService.getEventsCreatedBy(username, currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<?> createEvent(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody EventRequest eventRequest) {
        Events result = eventService.createEvents(currentUser, eventRequest);
        User user = userRepository.findById(result.getCreatedBy()).orElseGet(User::new);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }

    @PostMapping("/{eventId}/participate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> participateEvent(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable(value = "eventId") Long eventId,
                                              @Valid @RequestBody ParticipantRequest participantRequest) {
        User user = userRepository.findById(currentUser.getId()).orElseGet(User::new);
        UserRegistration userType = userRegistrationRepository.findAllByUserIdAndIsActive(user, Boolean.TRUE).orElseGet(UserRegistration::new);
        if(userType != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(user.getUsername()).toUri();
            return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Sorry! You cannot participate to the Event, as you dont have Membership"));
        } else {
            Participant result = eventService.addParticipant(currentUser, eventId, participantRequest);
            User createdBy = userRepository.findById(result.getCreatedBy()).orElseGet(User::new);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(createdBy.getUsername()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Your participation to event '" + result.getEventId().getName() + "' as '" + result.getParticipantTypeId().getName().name() + "' has been marked successfully"));
        }
    }
}
