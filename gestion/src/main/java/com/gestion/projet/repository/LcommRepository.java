package com.gestion.projet.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestion.projet.models.Lcomm;
@Repository
public interface LcommRepository extends JpaRepository<Lcomm, Long>{

	Iterable<Lcomm> findAllByNumero(int numero);
	
	@Modifying
    @Transactional
    @Query("delete from Lcomm e where numero = ?1")
    void deleteByNumero(int numero);
}