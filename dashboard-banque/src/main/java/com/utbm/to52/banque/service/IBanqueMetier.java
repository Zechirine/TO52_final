package com.utbm.to52.banque.service;

  
import java.util.List;
import java.util.Map;

import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import com.utbm.to52.banque.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBanqueMetier {

	 public Compte getCompte(String codeCompte);
	 public void versement(String codeCompte, double montant );

	public void retrait(String codeCompte, double montant);
	 public void virement(String codeCompteRetrait,String codeCompteVersement,double montant);
	public Page<Operation> findByFilter(Map<String, String> filters, Pageable pageable);

	public  Long countClients();
	public  Long countDepot();
	public Long countRetrait();
	public  Long countVersement();

}
