package com.project.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest  guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    @NotBlank
    private LocalDate checkIn;
    private LocalDate checkOut;

}
