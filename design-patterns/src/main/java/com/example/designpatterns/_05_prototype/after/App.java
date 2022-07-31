package com.example.designpatterns._05_prototype.after;

public class App {
    public static void main(String[] args) {
        GithubRepository repository = new GithubRepository();
        repository.setName("spring-study");
        repository.setUser("yeonnex");

        GitHubIssue issue = new GitHubIssue(repository);
        issue.setRepository(repository);
        issue.setId(1L);
        issue.setTitle("회원 수정 리팩토링");

        String url = issue.getUrl();
        System.out.println(url);

        GitHubIssue clone = issue.clone();

        System.out.println();
        System.out.println(clone != issue);
        System.out.println(clone.equals(issue));


        clone.setId(2L);
        clone.setTitle("엔티티 추가");

        System.out.println(clone.getUrl());



    }
}
