package com.p3s.sursangam.controller;

import com.p3s.sursangam.exception.ResourceNotFoundException;
import com.p3s.sursangam.model.*;
import com.p3s.sursangam.payload.*;
import com.p3s.sursangam.repository.GroupRepository;
import com.p3s.sursangam.repository.PersonRepository;
import com.p3s.sursangam.repository.RoleRepository;
import com.p3s.sursangam.repository.UserRepository;
import com.p3s.sursangam.security.CurrentUser;
import com.p3s.sursangam.security.JwtTokenProvider;
import com.p3s.sursangam.security.UserPrincipal;
import com.p3s.sursangam.service.EventService;
import com.p3s.sursangam.service.FileStorageService;
import com.p3s.sursangam.service.GroupProfileService;
import com.p3s.sursangam.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/group")
public class GroupController {
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
    EventService eventService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private FileStorageService fileStorageService;

    //TODO: Create 'Group' and 'Group Type' table, Also 'Group Registration' Table

    @GetMapping("/me")
    @PreAuthorize("hasRole('GROUP')")
    public GroupSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        Groups person = groupRepository.findByUserId(user).orElseGet(Groups::new);
        GroupSummary groupSummary = new GroupSummary(currentUser.getId(),currentUser.getFirstName(),
                currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword(),
                currentUser.getPhoneNo());
        return groupSummary;
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

    @GetMapping("/{username}")
    public GroupProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long eventsCount = 0L;

        Groups group = groupRepository.findByUserId(user).orElseGet(Groups::new);
        GroupProfile userProfile = new GroupProfile(user.getId(), group.getName(), user.getUsername(),
                group.getPhoneNo(), group.getEmail(), group.getProfileImageUri(), group.getProfileImageType(),
                group.getProfileImageSize(), eventsCount, user.getCreatedAt());

        return userProfile;
    }

    @PostMapping("/uploadFile")
    @PreAuthorize("hasRole('GROUP')")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
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
    @PreAuthorize("hasRole('GROUP')")
    public Groups getGroupDetail(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        return groupRepository.findByUserId(user).orElseGet(Groups::new);
    }

    @PostMapping("/profile")
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<?> saveGroupProfile(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody GroupProfileRequest profileRequest) {
        if(!currentUser.getEmail().equals(profileRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(currentUser.getUsername(),false, "Email Address is different from your registered email!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(!currentUser.getPhoneNo().equals(profileRequest.getPhoneNo())) {
            return new ResponseEntity(new ApiResponse(currentUser.getUsername(),false, "Phone Number is different from your registered phone no!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(profileRequest.getEmail()).orElseGet(User::new);

        Groups result = groupProfileService.createGroupProfile(user,profileRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));

    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<?> registerUser(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody GroupRegistrationRequest userRegistrationRequest) {

        GroupRegistration result = groupProfileService.registerGroup(currentUser, userRegistrationRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }

    @PostMapping("/feepayment")
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<?> payRegistrationFee(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody RegistrationFeeRequest registrationFeeRequest) {

        Transaction result = groupProfileService.payRegistrationFee(currentUser, registrationFeeRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserId().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }

    @PostMapping("/addevent")
    @PreAuthorize("hasRole('GROUP')")
    public ResponseEntity<?> createEvent(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody EventRequest eventRequest) {
        Events result = eventService.createEvents(currentUser, eventRequest);
        User user = userRepository.findById(result.getCreatedBy()).orElseGet(User::new);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(currentUser.getUsername(),true, "Profile saved successfully"));
    }
}
