package com.p3s.sursangam.service;

import com.p3s.sursangam.exception.BadRequestException;
import com.p3s.sursangam.exception.ResourceNotFoundException;
import com.p3s.sursangam.model.*;
import com.p3s.sursangam.payload.EventRequest;
import com.p3s.sursangam.payload.EventResponse;
import com.p3s.sursangam.payload.PagedResponse;
import com.p3s.sursangam.payload.ParticipantRequest;
import com.p3s.sursangam.repository.*;
import com.p3s.sursangam.security.UserPrincipal;
import com.p3s.sursangam.util.AppConstants;
import com.p3s.sursangam.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ParticipantTypeRepository participantTypeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;


    public Events createEvents(UserPrincipal currentUser, EventRequest eventRequest) {
        Events event = new Events();
        event.setCity(cityRepository.findByName(eventRequest.getCity()));
        event.setDetails(eventRequest.getDetails());
        event.setDuration(eventRequest.getDuration());
        event.setEventDate(eventRequest.getEventDate());
        event.setLatLong(eventRequest.getLatLong());
        event.setName(eventRequest.getName());
        event.setVenue(eventRequest.getVenue());
        Events result =  eventRepository.save(event);
        return result;
    }


    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public PagedResponse<EventResponse> getAllEvents(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Events> events = eventRepository.findAll(pageable);

        if(events.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), events.getNumber(),
                    events.getSize(), events.getTotalElements(), events.getTotalPages(), events.isLast());
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<Long> pollIds = events.map(Events::getId).getContent();
        Map<Long, User> creatorMap = getPollCreatorMap(events.getContent());

        List<EventResponse> pollResponses = events.map(poll -> {
            return ModelMapper.mapEventToEventResponse(poll,
                    creatorMap.get(poll.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(pollResponses, events.getNumber(),
                events.getSize(), events.getTotalElements(), events.getTotalPages(), events.isLast());
    }

    public PagedResponse<EventResponse> getEventsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all polls created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Events> polls = eventRepository.findByCreatedBy(user.getId(), pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<Long> pollIds = polls.map(Events::getId).getContent();

        List<EventResponse> pollResponses = polls.map(poll -> {
            return ModelMapper.mapEventToEventResponse(poll,user);
        }).getContent();

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponse<EventResponse> getEventParticipatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all pollIds in which the given username has voted
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Long> userVotedPollIds = participantRepository.findParticipatedEventIdsByUserId(user.getId(), pageable);

        if (userVotedPollIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userVotedPollIds.getNumber(),
                    userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(),
                    userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
        }

        // Retrieve all poll details from the voted pollIds.
        List<Long> pollIds = userVotedPollIds.getContent();

        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        List<Events> polls = eventRepository.findByIdIn(pollIds, sort);

        // Map Polls to PollResponses containing vote counts and poll creator details
        Map<Long, User> creatorMap = getPollCreatorMap(polls);

        List<EventResponse> pollResponses = polls.stream().map(poll -> {
            return ModelMapper.mapEventToEventResponse(poll,
                    creatorMap.get(poll.getCreatedBy()));
        }).collect(Collectors.toList());

        return new PagedResponse<>(pollResponses, userVotedPollIds.getNumber(), userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(), userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
    }

    public Participant addParticipant(UserPrincipal currentUser, Long eventId, ParticipantRequest participantRequest) {
        Participant participant = new Participant();
        participant.setEventId(eventRepository.findById(eventId).orElseGet(Events::new));
        ParticipantType participantType = participantTypeRepository.findById(participantRequest.getChoiceId()).orElseGet(ParticipantType::new);
        participant.setParticipantTypeId(participantType);
        participant.setUserId(userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new));
        Participant result = participantRepository.save(participant);
        return result;
    }
//
//
//    public Poll createPoll(PollRequest pollRequest) {
//        Poll poll = new Poll();
//        poll.setQuestion(pollRequest.getQuestion());
//
//        pollRequest.getChoices().forEach(choiceRequest -> {
//            poll.addChoice(new Choice(choiceRequest.getText()));
//        });
//
//        Instant now = Instant.now();
//        Instant expirationDateTime = now.plus(Duration.ofDays(pollRequest.getPollLength().getDays()))
//                .plus(Duration.ofHours(pollRequest.getPollLength().getHours()));
//
//        poll.setExpirationDateTime(expirationDateTime);
//
//        return pollRepository.save(poll);
//    }
//
//    public PollResponse getPollById(Long pollId, UserPrincipal currentUser) {
//        Poll poll = pollRepository.findById(pollId).orElseThrow(
//                () -> new ResourceNotFoundException("Poll", "id", pollId));
//
//        // Retrieve Vote Counts of every choice belonging to the current poll
//        List<ChoiceVoteCount> votes = voteRepository.countByPollIdGroupByChoiceId(pollId);
//
//        Map<Long, Long> choiceVotesMap = votes.stream()
//                .collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));
//
//        // Retrieve poll creator details
//        User creator = userRepository.findById(poll.getCreatedBy())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));
//
//        // Retrieve vote done by logged in user
//        Vote userVote = null;
//        if(currentUser != null) {
//            userVote = voteRepository.findByUserIdAndPollId(currentUser.getId(), pollId);
//        }
//
//        return ModelMapper.mapPollToPollResponse(poll, choiceVotesMap,
//                creator, userVote != null ? userVote.getChoice().getId(): null);
//    }
//
//    public PollResponse castVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser) {
//        Poll poll = pollRepository.findById(pollId)
//                .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));
//
//        if(poll.getExpirationDateTime().isBefore(Instant.now())) {
//            throw new BadRequestException("Sorry! This Poll has already expired");
//        }
//
//        User user = userRepository.getOne(currentUser.getId());
//
//        Choice selectedChoice = poll.getChoices().stream()
//                .filter(choice -> choice.getId().equals(voteRequest.getChoiceId()))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", voteRequest.getChoiceId()));
//
//        Vote vote = new Vote();
//        vote.setPoll(poll);
//        vote.setUser(user);
//        vote.setChoice(selectedChoice);
//
//        try {
//            vote = voteRepository.save(vote);
//        } catch (DataIntegrityViolationException ex) {
//            logger.info("User {} has already voted in Poll {}", currentUser.getId(), pollId);
//            throw new BadRequestException("Sorry! You have already cast your vote in this poll");
//        }
//
//        //-- Vote Saved, Return the updated Poll Response now --
//
//        // Retrieve Vote Counts of every choice belonging to the current poll
//        List<ChoiceVoteCount> votes = voteRepository.countByPollIdGroupByChoiceId(pollId);
//
//        Map<Long, Long> choiceVotesMap = votes.stream()
//                .collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));
//
//        // Retrieve poll creator details
//        User creator = userRepository.findById(poll.getCreatedBy())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));
//
//        return ModelMapper.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.getChoice().getId());
//    }
//
//
    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
//
//
//    private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
//        // Retrieve Votes done by the logged in user to the given pollIds
//        Map<Long, Long> pollUserVoteMap = null;
//        if(currentUser != null) {
//            List<Vote> userVotes = voteRepository.findByUserIdAndPollIdIn(currentUser.getId(), pollIds);
//
//            pollUserVoteMap = userVotes.stream()
//                    .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getChoice().getId()));
//        }
//        return pollUserVoteMap;
//    }

    Map<Long, User> getPollCreatorMap(List<Events> events) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = events.stream()
                .map(Events::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
