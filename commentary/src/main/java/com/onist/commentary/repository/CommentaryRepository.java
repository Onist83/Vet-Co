package com.onist.commentary.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.onist.commentary.model.CommentaryModel;

public interface CommentaryRepository extends MongoRepository<CommentaryModel, String> {
    List<CommentaryModel> findAllByOrderByDateOfPublicationAsc();
    List<CommentaryModel> findByAnimalId(Long animalId);
}
