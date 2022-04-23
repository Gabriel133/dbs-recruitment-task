package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/repositories")
public class Controller {
    @Autowired
    IGitRepo iGitRepo;

    WebClient gihbuhClient = WebClient.create("https://api.github.com/");

    @GetMapping("/{owner}/{repoName}")
    private Mono<GitRepo> getGitRepoFromGithub(@PathVariable String owner, @PathVariable String repoName) {

        Mono<GitRepo> result = gihbuhClient.get()
                .uri("/repos/{owner}/{repo}", owner, repoName)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    return Mono.error(new IllegalStateException(
                            String.format("Failed to retrieve! %s", owner + "/" +repoName)));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    return Mono.error(new IllegalStateException(
                            String.format("Failed due to server error")));
                })
                .bodyToMono(GitRepo.class)
                .onErrorMap(throwable -> {
                    return new RuntimeException(throwable);
                });

        iGitRepo.save(result.block()).subscribe();

        return result;
    }

    @GetMapping("/allrepofromdb")
    private Flux<GitRepo> getAllRepoFromDB(){
        return  iGitRepo.findAll();
    }

    @GetMapping("/repofromdbbyfullname/{owner}/{repoName}")
    private Flux<GitRepo> getRepoFromDbByFullName(@PathVariable String owner, @PathVariable String repoName){
        GitRepo query = new GitRepo();
        query.setFull_name(owner + "/" + repoName);
        return iGitRepo.findAll(Example.of(query));
    }
}
