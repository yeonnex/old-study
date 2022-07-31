package com.example.designpatterns._05_prototype.before;

public class App {
    public static void main(String[] args) {
        GithubRepository repository = new GithubRepository();
        repository.setName("spring-study");
        repository.setUser("yeonnex");

        GitHubIssue issue = new GitHubIssue(repository);
        issue.setRepository(repository);
        issue.setId(1L);
        issue.setTitle("회원 수정 리팩토링");

        GitHubIssue issue2 = new GitHubIssue(repository);
        issue.setRepository(repository);
        issue.setId(2L);
        issue.setTitle("댓글 알림 엔티티 추가");

        String url = issue.getUrl();
        System.out.println(url);

    }
}
