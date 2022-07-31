package com.example.designpatterns._05_prototype.java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitHubIssueData {
    private int id;
    private String title;
    private String repositoryUser;
    private String repositoryName;
}
