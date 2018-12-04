package com.p3s.sursangam.controller;

import com.p3s.sursangam.exception.ResourceNotFoundException;
import com.p3s.sursangam.model.*;
import com.p3s.sursangam.payload.*;
import com.p3s.sursangam.repository.*;
import com.p3s.sursangam.security.CurrentUser;
import com.p3s.sursangam.security.JwtTokenProvider;
import com.p3s.sursangam.security.UserPrincipal;
import com.p3s.sursangam.service.EventService;
import com.p3s.sursangam.service.FileStorageService;
import com.p3s.sursangam.service.UserProfileService;
import com.p3s.sursangam.util.AppConstants;
import com.p3s.sursangam.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.acl.Group;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    EventService eventService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/me")
    public Summary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        String userType = Utilities.getUserType(user);

        if(userType.equals("USER")) {
            Person person = personRepository.findByUserId(user);
            UserSummary userSummary = new UserSummary(currentUser.getFirstName(), currentUser.getMiddleName(), currentUser.getLastName(),
                    currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword(),
                    person.getGender(), person.getDateOfBirth(), currentUser.getPhoneNo());
            return userSummary;
        } else {
            Groups person = groupRepository.findByUserId(user).orElseGet(Groups::new);
            GroupSummary groupSummary = new GroupSummary(currentUser.getId(),currentUser.getFirstName(),
                    currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword(),
                    currentUser.getPhoneNo());
            return groupSummary;
        }
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkPhoneAvailability")
    public UserIdentityAvailability checkPhoneAvailability(@RequestParam(value = "phoneNo") String phoneNo) {
        Boolean isAvailable = !userRepository.existsByPhoneNo(phoneNo);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{username}")
    public Profile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        String userType = Utilities.getUserType(user);
        if(userType.equals("USER")) {
            long eventsCount = 0L;

            Person person = personRepository.findByUserId(user);
            if (person != null) {
                Long eventCount = participantRepository.findParticipatedEventIdsByUserId(user.getId());
                UserProfile userProfile = new UserProfile(user.getId(), person.getFirstName(), person.getMiddleName(),
                        person.getLastName(), user.getUsername(), person.getGender(), person.getDateOfBirth(),
                        person.getPhoneNo(), person.getEmail(), person.getProfileImageUri(), person.getProfileImageType(),
                        person.getProfileImageSize(), eventsCount, 0L, user.getCreatedAt());
                return userProfile;
            } else {
                return new UserProfile();
            }
        } else if(userType.equals("GROUP")) {
            long eventsCount = 0L;

            Groups group = groupRepository.findByUserId(user).orElseGet(Groups::new);
            if (group != null) {
                GroupProfile groupProfile = new GroupProfile(user.getId(), group.getName(),
                        user.getUsername(), group.getPhoneNo(), group.getEmail(),
                        group.getProfileImageUri(), group.getProfileImageType(),
                        group.getProfileImageSize(), eventsCount, user.getCreatedAt());
                return groupProfile;
            } else {
                return new GroupProfile();
            }
        } else {
            return new UserProfile();
        }
    }

    @GetMapping("/{username}/events")
    public PagedResponse<EventResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return eventService.getEventParticipatedBy(username, currentUser, page, size);
    }
//
//
//    @GetMapping("/{username}/votes")
//    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
//                                                       @CurrentUser UserPrincipal currentUser,
//                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//        return pollService.getPollsVotedBy(username, currentUser, page, size);
//    }

    @PostMapping("/uploadFile")
    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFile(@CurrentUser UserPrincipal currentUser, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Resource> downloadFile(@CurrentUser UserPrincipal currentUser, @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('USER')")
    public Person getUserDetail(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        return personRepository.findByUserId(user);
    }

    @PostMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveUserProfile(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProfileRequest profileRequest) {
        if(!currentUser.getEmail().equals(profileRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(currentUser.getUsername(),false, "Email Address is different from your registered email!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(!currentUser.getPhoneNo().equals(profileRequest.getPhoneNo())) {
            return new ResponseEntity(new ApiResponse(currentUser.getUsername(),false, "Phone Number is different from your registered phone no!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(profileRequest.getEmail()).orElseGet(User::new);

        Person result = userProfileService.createPersonProfile(user,profileRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));

    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> registerUser(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {

        UserRegistration result = userProfileService.registerUser(currentUser, userRegistrationRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }

    @PostMapping("/feepayment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> payRegistrationFee(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody RegistrationFeeRequest registrationFeeRequest) {

        Transaction result = userProfileService.payRegistrationFee(currentUser, registrationFeeRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }
}
