package com.utbm.to52.banque.service;


import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICompteMetier {

	public Page<Compte> findByFilter(Map<String, String> filters, Pageable pageable);
	public List<Compte> findCompteByClient(Client client);
	public void save(Compte compte);
	public  String generateCode(String type);
	public Compte findCompteByCode(String codeComtpte);
}
