package com.p3s.sursangam.controller;

import com.p3s.sursangam.model.ParticipantType;
import com.p3s.sursangam.repository.ParticipantTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/list")
public class ListController {
    @Autowired
    ParticipantTypeRepository participantTypeRepository;

    @GetMapping("/participationtypes")
    public List<ParticipantType> getParticipationTypeList() {
        return participantTypeRepository.findAll();
    }
}
