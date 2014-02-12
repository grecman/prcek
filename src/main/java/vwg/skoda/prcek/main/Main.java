package vwg.skoda.prcek.main;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Main...");
		
		EntityManager em  = Persistence.createEntityManagerFactory("PrcekPersistence").createEntityManager();
		System.out.println("EntityManager: " + em);

	}

}
