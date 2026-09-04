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
            LEFT JOIN a.owner o
            LEFT JOIN a.secondOwner so
            WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(o.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(o.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(so.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(so.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
            List<AnimalModel>searchByNameOrOwner(@Param("query")String query);           
}



