package com.gestion.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.projet.models.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>{

}
