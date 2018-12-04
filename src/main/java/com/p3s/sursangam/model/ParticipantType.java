package com.p3s.sursangam.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "participant_type")
public class ParticipantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private ParticipantTypeName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParticipantTypeName getName() {
        return name;
    }

    public void setName(ParticipantTypeName name) {
        this.name = name;
    }
}