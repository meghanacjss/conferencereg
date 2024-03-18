package com.example.conference_reg.entity;

import com.example.conference_reg.validation.EventTypeValidation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue
    private int eid;
    @NotBlank(message = "Event name is required")
    @Size(max = 255, message = "Event name must not exceed 255 characters")
    @EventTypeValidation
    private String ename;
    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private Date date;
    @NotBlank(message = "Event venue is required")
    private String venue;
}
