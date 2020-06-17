package com.utbm.to52.banque.dao;
//on est dans la couche dao --> donc on ne met que les fonctionalit�s de base (select, add, delete,update) 
// --> ��d que : ajout , consulter, supprimer , mettre � jour --> afin de distinguer la couche dao de la couche metier
// --> et dans la couche metier , on met ce qui est sp�cifications fonctionnels

 
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import com.utbm.to52.banque.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
 
//utilisation de spring data
//cette interface permet de gérer l'entite client 

public interface IClientRepository  extends PagingAndSortingRepository<Client, Long>, JpaSpecificationExecutor<Client> {  //interface JPA/repository (utilisation de spring data necessite la creation de l'interface jpaRepository)  ---- le Id de Client est de type long

    @Query("SELECT DISTINCT(c) FROM Client c WHERE c.nomClient LIKE CONCAT('%',:name,'%')")
    public List<Client> findClientByName(@Param("name")String name);


}
