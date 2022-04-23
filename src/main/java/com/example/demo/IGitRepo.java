package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface IGitRepo extends ReactiveMongoRepository<GitRepo, String> {}
public interface IGitRepo extends ReactiveMongoRepository<GitRepo, String> {}
