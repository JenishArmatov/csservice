package com.example.csservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {
    private Long id;
    private String nameClient;
    private Long imageId;
}