package com.example.paradise.user.entity;

import com.example.paradise.common.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // Timestamped(부모)와 독립
@Entity
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String status = "ACTIVE"; // 계정 상태 ex) ACTIVE, DELETED

    private String password;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void deactiveAccount() {
        this.status = "DELETED";
    }
}
