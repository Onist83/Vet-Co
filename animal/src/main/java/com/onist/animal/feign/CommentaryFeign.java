package com.onist.animal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "commentary", url = "http://localhost8030")
public interface CommentaryFeign {
    @DeleteMapping("/api/v1/commentary/animal/{animalId}")
    public void deleteAllCommentaryByAnimalId(@PathVariable("animalId") long animalId);
}
