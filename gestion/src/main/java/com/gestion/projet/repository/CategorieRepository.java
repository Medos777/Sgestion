package com.gestion.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.projet.models.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long>{

}
