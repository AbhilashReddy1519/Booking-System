package com.app.bs.booking_system.modules.shows.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowDTO {

    @NotNull(message = "startDateTime should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @NotNull(message = "endDateTime should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;

    @NotNull(message = "Movie ID should not be empty")
    private UUID movieId;

    @NotNull(message = "Screen ID should not be empty")
    private UUID screenId;

    @NotNull(message = "Language ID should not be empty")
    private UUID languageId;
}