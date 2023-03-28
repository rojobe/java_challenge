package com.java.challenge.java_challenge.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {

    private LocalDate timeStamp;

    private String code;

    private String detailMessage;

}
