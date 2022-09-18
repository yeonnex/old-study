package com.example.demorestapi.accounts;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id @GeneratedValue
    private Integer id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
