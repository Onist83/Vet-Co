package com.onist.animal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onist.animal.model.AnimalModel;



public interface AnimalRepository extends JpaRepository<AnimalModel, Long> {
    
    boolean existsByChipNumber(String chipNumber); 
    
    
    @Query("""
             SELECT a FROM AnimalModel a
        WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.owner.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.owner.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.secondOwner.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.secondOwner.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
            List<AnimalModel>searchByNameOrOwner(@Param("query")String query);           
}



