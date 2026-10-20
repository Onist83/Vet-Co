package com.onist.animal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign client interface for interacting with the commentary service
// Interface de client Feign pour interagir avec le service de commentaires
@FeignClient(name = "commentary")
public interface CommentaryFeign {

    // Method to delete all commentaries associated with a specific animal ID
    // Méthode pour supprimer tous les commentaires associés à un ID d'animal
    // spécifique
    @DeleteMapping("/api/v1/commentary/animal/{animalId}")
    public void deleteAllCommentaryByAnimalId(@PathVariable("animalId") Long animalId);
}
