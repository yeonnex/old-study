package com.example.designpatterns._05_prototype.after;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubIssue implements Cloneable{
    private Long id;
    private String title;
    private GithubRepository repository;

    public GitHubIssue(GithubRepository repository) {
        this.repository = repository;
    }

    public String getUrl() {
        return String.format("https://github.com/%s/%s/issues/%d",
                repository.getUser(),
                repository.getName(),
                this.getId()
        );
    }

    @Override
    public GitHubIssue clone() {
        try {
            return (GitHubIssue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
