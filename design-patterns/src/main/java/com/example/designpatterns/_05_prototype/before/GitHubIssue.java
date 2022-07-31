package com.example.designpatterns._05_prototype.before;

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
            // TODO: 이 복제본이 원본의 내부를 변경할 수 없도록 여기에 가변 상태 복사합니다
            return (GitHubIssue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
