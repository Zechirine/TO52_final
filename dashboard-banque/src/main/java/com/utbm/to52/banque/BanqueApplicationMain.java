/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utbm.to52.banque;

import com.utbm.to52.banque.dao.IClientRepository;
import com.utbm.to52.banque.dao.ICompteRepository;
import com.utbm.to52.banque.dao.IOperationRepository;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import com.utbm.to52.banque.entities.CompteCourant;
import com.utbm.to52.banque.entities.CompteEpargne;
import com.utbm.to52.banque.entities.Retrait;
import com.utbm.to52.banque.entities.Versement;
import com.utbm.to52.banque.service.IBanqueMetier;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;

 /**
 * @author rmpestano
 */
//@EnableAutoConfiguration
	 @ComponentScan("com.utbm.to52.banque.bean")
	 @ComponentScan("com.utbm.to52.banque.service")
	 @ComponentScan("com.utbm.to52.banque.dao")
	 @EntityScan("com.utbm.to52.banque.entities")
 @SpringBootApplication
public class BanqueApplicationMain {   //cette implementation de cette interface permet de tester dans la meme class et obligne de redefinir la methode run de cette interface


	public static void  main(String[] args) { 
		
		  ApplicationContext ctx= SpringApplication.run(BanqueApplicationMain.class, args);
	
	 
	}	
	
	
}
