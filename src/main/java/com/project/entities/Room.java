package com.project.entities;

import com.project.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @NotNull
    private Integer roomNumber;

    @NotBlank(message = "Room type is required")
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

}
