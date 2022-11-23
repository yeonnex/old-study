package com.example.querydslexample;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class Team {
    @Id @GeneratedValue
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team") // 연관관계의 주인이 아님!
    @ToString.Exclude
    private List<Member> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
