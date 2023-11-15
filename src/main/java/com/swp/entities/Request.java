package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "request")
@Getter
@Setter
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"requested_by_user_id\"")
    private User requestedByUser;

    @Column(name = "request_detail")
    private String requestDetail;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"reviewed_by_user_id\"")
    private User reviewedByAdmin;

    @Column(name = "reviewed_date")
    private LocalDateTime reviewedTime;

    @Column(name = "admin_message")
    private String adminMessage;

}
