package com.utbm.to52.banque.bean;


import com.utbm.to52.banque.datatable.ClientLazyDataModel;
import com.utbm.to52.banque.datatable.OperationLazyDataModel;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.service.IBanqueMetier;
import com.utbm.to52.banque.service.IClientMetier;
import com.utbm.to52.banque.service.ICompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class ClientMbean implements Serializable {

    @Autowired
    IClientMetier clientMetier;

    private ClientLazyDataModel clients;

    private  Client client;

    private  Client selectedClient;


    @PostConstruct
    public  void init(){
        this.clients = new ClientLazyDataModel(clientMetier);
        reset();
    }

    public ClientLazyDataModel getClients() {
        return clients;
    }

    public void save() {
        try {
            System.out.println("client" + client.toString());
            clientMetier.save(client);
            reset();
        } catch (Exception ex) {

        }
    }

    public void delete() {
        try {
            System.out.println("course" + client.toString());
            clientMetier.delete(client);
            reset();
        } catch (Exception ex) {

        }
    }

    public void reset(){
        client = new Client();
        selectedClient = new Client();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }
}
