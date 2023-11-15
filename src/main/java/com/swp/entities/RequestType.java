package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "request_type")
@Getter
@Setter
public class RequestType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_info")
    private String requestInfo;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

}
