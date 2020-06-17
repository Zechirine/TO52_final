package com.utbm.to52.banque.service;


import com.utbm.to52.banque.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface IClientMetier {

	public Page<Client> findByFilter(Map<String, String> filters, Pageable pageable);

	public  Long countClients();
	public  void save(Client client);
	public  void delete(Client client);
	public Client finfClientByName(@Param("name")String name);


}
