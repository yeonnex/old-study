package com.example.designpatterns._05_prototype.java;

import com.example.designpatterns._05_prototype.after.GitHubIssue;
import com.example.designpatterns._05_prototype.after.GithubRepository;
import org.modelmapper.ModelMapper;

public class ModelMapperExample {
    public static void main(String[] args) {

        GithubRepository repository = new GithubRepository();
        repository.setName("java-study");
        repository.setUser("yeonnex");

        GitHubIssue issue = new GitHubIssue(repository);
        issue.setRepository(repository);
        issue.setId(1L);
        issue.setTitle("회원 수정 리팩토링");

        // model-mapper
        GitHubIssueData gitHubIssueData = new GitHubIssueData();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(issue,gitHubIssueData);

        System.out.println(gitHubIssueData);


    }
}
