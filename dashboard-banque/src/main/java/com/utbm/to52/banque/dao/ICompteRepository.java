package com.utbm.to52.banque.dao;
 
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ICompteRepository extends PagingAndSortingRepository<Compte, Long>, JpaSpecificationExecutor<Compte> {   //interface qui herite d'une interface  -----  le ID de compte est de type String (2 eme parametre)

    @Query("SELECT c FROM Compte c WHERE c.client = ?1")
    public List<Compte> findCompteByClient(Client client);

    @Query("SELECT c FROM Compte c WHERE c.codeCompte = ?1")
    public Compte findCompteByCode(String codeComtpte);
}
