package com.example.paradise.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    // PK id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 유저명
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String username;
    // 이메일(계정명)
    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Column(unique = true)
    private String email;
    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 하며, 최소 8자 이상이어야 합니다.") // 정규 표현식 이용
    private String password;
    // 생성일
    @CreatedDate
    @Column(updatable = false) // 한번 생성되면 업데이트 x
    private LocalDateTime createdAt;
    // 수정일
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
