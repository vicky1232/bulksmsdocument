package com.bulkSms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long role_id;
    @Column(name = "role")
    private String role;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;
}
