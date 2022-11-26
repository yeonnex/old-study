package com.example.querydslexample;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
@ToString
public class Member{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String memberName;
    private int age;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String memberName, int age, Team team) {
        this.memberName = memberName;
        this.age = age;
        this.team = team;
    }

    public Member(String memberName, int age) {
        this(memberName, age, null);
    }

    public Member(String memberName) {
        this(memberName, 0);
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
