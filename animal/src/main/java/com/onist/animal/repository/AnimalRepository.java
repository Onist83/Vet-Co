package com.onist.animal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onist.animal.model.AnimalModel;

import feign.Param;

public interface AnimalRepository extends JpaRepository<AnimalModel, Long> {
    
    boolean existsByChipNumber(String chipNumber); 
    
    
    @Query("""
             SELECT a FROM AnimalModel a
        WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.firstNameOfOwner) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.lastNameOfOwner) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.firstNameOfSecondOwner) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.lastNameOfSecondOwner) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
            List<AnimalModel>searchByNameOrOwner(@Param("query")String query);           
}



