package com.example.conference_reg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Registration {
    @Id
    @GeneratedValue
    private int rid;

    // @JsonIgnore

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "eventId")
    private Event event;

    // @JsonIgnore

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "attendeeId")
    private Attendee attendee;
    @Future(message="enter date must be in future")
    private Date rdate;
    @Min(value=1,message="amount must be greater than 1")
    private long ramount;

}
