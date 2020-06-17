package com.utbm.to52.banque.bean;


import com.utbm.to52.banque.datatable.OperationLazyDataModel;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Operation;
import com.utbm.to52.banque.service.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Component
@ManagedBean
@ViewScoped
public class DashboardMbean implements Serializable {

    @Autowired
    IBanqueMetier banqueMetier;

    private OperationLazyDataModel operations;
    //métriques attributs
    private  int countClient;
    private  int countRetrait;
    private  int countDepot;


    @PostConstruct
    public  void init(){
        System.out.println(operations+"operations");
        this.operations = new OperationLazyDataModel(banqueMetier);
        countClient = banqueMetier.countClients().intValue();
        countDepot = banqueMetier.countDepot().intValue();
        countRetrait = banqueMetier.countRetrait().intValue();
        System.out.println(operations+"operations");
    }

    public OperationLazyDataModel getOperations() {
        return operations;
    }


    public int getCountClient() {
        return countClient;
    }

    public int getCountRetrait() {
        return countRetrait;
    }

    public int getCountDepot() {
        return countDepot;
    }
}
