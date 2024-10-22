package com.example.paradise.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    // PK id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 유저명
    @NotBlank(message = "사용자명은 필수 입력 값입니다.")
    private String username;
    // 이메일(계정명)
    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Column(unique = true)
    private String email;
    // 이메일 status
    @Column(nullable = false)
    private String status = "ACTIVE"; // 계정 상태 ex) ACTIVE, DELETED
    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 하며, 최소 8자 이상이어야 합니다.") // 정규 표현식 이용
    private String password;

    // 비밀번호 변경
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
    // 계정 삭제 시 이메일 저장(Soft Delete)
    public void deactiveAccount() {
        this.status = "DELETED";
    }
}
