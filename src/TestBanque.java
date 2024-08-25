import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;



@TestMethodOrder(OrderAnnotation.class)
public class TestBanque{
	
	public Banque banque = new Banque("Test Banque");
	
	public Client toto = new Client("TOTO", "Toto"); 
	public Client titi = new Client("TITI", "Titi"); 
	public Client tutu = new Client("TUTU", "Tutu"); 
	
	public Compte c1 = new Compte(toto);
	public Compte c2 = new Compte(titi);
	public Compte c3 = new Compte(titi);
	public Compte c4 = new Compte(tutu);

	
	
	@BeforeEach
	// Méthode à exécuter avant chaque tests
	// Ou @BeforeAll pour exécuter la méthode une seule fois avant tous les tests
	public void init() {
        
        // ajout des client à la banque
        banque.addClient(toto);
		banque.addClient(titi);
		banque.addClient(tutu);
		
		// Ajout du compte dans la banque
		banque.addCompte(c1);
		banque.addCompte(c2);
		banque.addCompte(c3);
		banque.addCompte(c4);
	}

	
	// ======================= TESTS =====================================
	@Test
	@Order(1)
	public void testAddClient() throws Exception {
		int nbClient = banque.getListeClients().keySet().size();
		System.out.println("\nTEST 1 : AJOUT CLIENT");
		System.out.println("Nombre de clients dans la banque : " + (nbClient)); // pluriel
		System.out.println("Ajout d'un client tété à la banque");
		
		Client c = new Client("TETE", "tété");
		banque.addClient(c);
		
		System.out.println("Nombre de clients dans la banque : " + (banque.getListeClients().keySet().size())); // pluriel
		
		assertEquals(nbClient+1, banque.getListeClients().keySet().size()); // toto, titi, tutu + tété
	}
	
	
	@Test
	@Order(2)
	public void testAddCompte() throws Exception {
		int nbCompte = banque.getListeComptes().keySet().size();
		System.out.println("\nTEST 2 : AJOUT COMPTE");
		System.out.println("Nombre de comptes dans la banque : " + (nbCompte)); // pluriel
		System.out.println("Ajout d'un compte à la banque");
		
		Compte c5 = new Compte(titi); 
		banque.addCompte(c5);
		
		System.out.println("Nombre de comptes dans la banque : " + (banque.getListeComptes().keySet().size()));
			// on corrige "nombre de client" en "nombre de comptes" pour respecter la logique
		assertEquals(nbCompte+1, banque.getListeComptes().keySet().size()); // c1, c2, c3, c4 + c5
	}
	
	@Test
	@Order(3)
	public void testFindClient() throws Exception {
		System.out.println("\nTest 3 : Recupérer id");
		
		// On recupère l'id de TOTO
		int idCompte = toto.getId();
		System.out.println("Clients de la banque " + banque.getListeClients());
		System.out.println("TOTO ID = " + idCompte);

		// On cherche le client avec cette ID pour verifier que son prenom est bien toto
		assertEquals("Toto", banque.findClient(idCompte).getPrenom());
	}
	
	@Test
	@Order(4)
	public void testFindCompte() throws Exception {
		System.out.println("\nTest 4 : Recupérer compte");
		System.out.println("Comptes de la banque " + banque.getListeComptes());
		System.out.println("Info compte C1 = " +  banque.findCompte(c1.getId()));
		
		
		// Vérification que le compte C1 n'a qu'un propriétaire
		assertEquals(1, banque.findCompte(c1.getId()).getIdClient().size());

		// Véification que celui-ci soit bien toto
		List<Integer> listeClient = new ArrayList<Integer>(banque.findCompte(c1.getId()).getIdClient());
		int idClient = listeClient.get(0);
		System.out.println("Clients de la banque " + banque.getListeClients());
		System.out.println("Proprietaire de C1 = " + banque.findClient(idClient).getPrenom());
		
		assertEquals("Toto", banque.findClient(idClient).getPrenom());
	}
	
	@Test
	@Order(5)
	public void testCrediter() throws Exception {
		int montant = 100;
		System.out.println("\nTEST 5 : CREDITER");
		System.out.println("Solde du compte c1 avant ajout : " + c1.getSolde());
		
	    c1.crediter(montant);
	    System.out.println("Solde du compte c1 après ajout de " + (montant) + "€ -> c1 = " + c1.getSolde());
	    
	    assertEquals(montant, c1.getSolde());
	}
	
	
	@Test
	@Order(6)
	public void testDebiter() throws Exception {
		int montant = 50;
		
		c2.crediter(100);
		System.out.println("\nTEST 6 : DEBITER (NORMAL)");
		System.out.println("Solde du compte c2 avant retrait : " + c2.getSolde());
		
		
	    c2.debiter(montant);
	    
	    System.out.println("Solde du compte c2 après retrait de " + (montant) + "€ -> c2 = " + c2.getSolde());
	    
	    assertEquals(montant, c2.getSolde());
	}
	
	
	@Test
	@Order(7)
	public void testDebiterNotNormal() throws Exception {
		try {
			System.out.println("\nTEST 7 : DEBITER (ANORMAL)");
			System.out.println("Solde du compte c1 avant retrait : " + c1.getSolde());
			System.out.println("Retrait de 999€");
			c1.debiter(999);
			fail();
		}
		catch(IllegalOperationException e) {
			System.out.println(e.getMessage());
			System.out.println("Le débit d'un montant non possédé à bien été bloqué !\n");
		}
	}
	
	
	@Test
	@Order(8)
	public void testTtransfert() throws Exception {
		c3.crediter(100);
		System.out.println("\nTEST 8 : TRANSFERT (NORMAL)");
		System.out.println("Solde du compte c3 avant transfert : " + c3.getSolde());
		System.out.println("Solde du compte c4 avant transfert : " + c4.getSolde());
		System.out.println("Transfert de 50€ de c3 -> c4");

	    c3.transfert(c4, 50);
	    
	    System.out.println("Solde du compte c3 : " + c3.getSolde());
		System.out.println("Solde du compte c4 : " + c4.getSolde());
		
	    assertEquals(50, c3.getSolde());
	    assertEquals(50, c4.getSolde());

	}
	
	
	@Test
	@Order(9)
	public void testTtransfertNotNormal() throws Exception {
		try {
			System.out.println("\nTEST 9 : TRANSFERT ANORMAL");
			System.out.println("Solde du compte c1 avant transfert : " + c1.getSolde());
			System.out.println("Transfert de 999€");
			c1.transfert(c2, 999);
			fail();
		}
		catch(IllegalOperationException e) {
			System.out.println(e.getMessage());
			System.out.println("Le transfert d'un montant non possédé à bien été bloqué !\n");
		}
	}
	
	
	@Test
	@Order(10)
	public void testAddProprio() throws Exception {
		try {
			System.out.println("\nTEST 10 : AJOUT PROPRIETAIRE (NORMAL)");
			List<Integer> listeClient = new ArrayList<Integer>(c1.getIdClient());
			System.out.println("Proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"] = " + (banque.findClient(listeClient.get(0))));
			System.out.print("ajout du proprietaire titi \n");
			
			c1.addClient(titi);
			listeClient = new ArrayList<Integer>(c1.getIdClient());
			System.out.println("Proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"] = " + banque.findClient(listeClient.get(0)) + " , " + banque.findClient(listeClient.get(1)));
			
			assertEquals(2, c1.getIdClient().size());
		}
		catch(IllegalOperationException e) {
			e.printStackTrace();
			fail();
		}
	}
	

	@Test
	@Order(11)
	public void testAddProprioNotNormal() throws Exception {
		try {
			System.out.println("\nTEST 11 : AJOUT PROPRIETAIRE (ANORMAL)");
			System.out.println("Nombre de proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"]");
			System.out.println("Ajout d'un autre propriétaire");
			c1.addClient(tutu);
			System.out.println("Nombre de proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"]");
			// ajout d'une impression pour mise à jour du nombre de proprio
			c1.addClient(titi);
			fail();
			
		} catch (IllegalOperationException e) {
			System.out.println(e.getMessage());
			System.out.println("L'ajout d'un 3e beneficiare à bien été bloqué");
		}
	}


	@Test
	@Order(12)
	public void testRemoveProprio() throws Exception {
		System.out.println("\nTEST 12 : SUPPRIMER PROPRIETAIRE");
		c1.addClient(titi); // on ajoute un autre propriétaire sinon en raison de @Beforeeach
		// c'est remis à 1 seul propriétaire (toto) comme au départ et le test est sans intérêt
		System.out.println("Nombre de proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"]");
		System.out.println("Suppression du propriétaire titi");
		
		c1.removeClient(titi);
		
		System.out.println("Nombre de proprietaire du compte c1 : [" + (c1.getIdClient().size()) +"]");
		assertEquals(1, c1.getIdClient().size());
	}
	
	
	@Test
	@Order(13)
	public void testRemoveCompte() throws Exception {
		c1.addClient(titi);

		System.out.println("\nTEST 13 : SUPPRIMER COMPTE");
		System.out.println("Nombre de compte à la banque = " + (banque.getListeComptes().keySet().size()));
		System.out.println("Suppression du compte c1 ayant pour clients les IdClients :" + c1.getIdClient());
		System.out.println("Client de la banque possédant ce compte :");

		for(Integer idClient : banque.getListeClients().keySet()) {
			if(banque.getListeClients().get(idClient).getComptes().contains(c1.getId())) {
				System.out.println(banque.getListeClients().get(idClient).getPrenom());
			}
		}

		// Suppression du compte de la banque
		banque.removeCompte(c1);

		System.out.println("Nombre de compte à la banque après suppression = " + (banque.getListeComptes().keySet().size()));
		System.out.println("Client de la banque possédant ce compte :");

		for(Integer idClient : banque.getListeClients().keySet()) {
			if(banque.getListeClients().get(idClient).getComptes().contains(c1.getId())) {
				System.out.println(banque.getListeClients().get(idClient).getPrenom());
			}
		}

		// verification suppression du compte
		assertEquals(3, banque.getListeComptes().keySet().size()); // c2 + c3 + c4

		// verification suppression du compte au sein du client
		assertEquals(0, toto.getComptes().size());
		assertEquals(2, titi.getComptes().size());

	}

	
	@Test
	@Order(14)
	public void testRemoveClient() throws Exception {
		System.out.println("\n\nTEST 14 : SUPPRIMER CLIENT");
		
		// Ajout du client toto au compte 2	
		c2.addClient(toto);
		System.out.println("Nombre de client : " + (banque.getListeClients().keySet().size()));
		System.out.println("Nombre de comptes : " + (banque.getListeComptes().keySet().size()));
		System.out.print("Compte appartenant uniquement à titi: ");
		for(Integer idCompte : titi.getComptes()) {
			if(banque.getListeComptes().get(idCompte).getIdClient().size() == 1) {
				System.out.print((idCompte) + " ");
			}
		}

		System.out.println("\nSuppression de titi");
		
		banque.removeClient(titi);
		System.out.println("Nombre de client après suppression : " + (banque.getListeClients().keySet().size()));
		System.out.println("Nombre de comptes après suppression : " + (banque.getListeComptes().keySet().size()));
		

		assertEquals(2, banque.getListeClients().keySet().size()); // toto + tutu + tété
		assertEquals(3, banque.getListeComptes().keySet().size()); //c1 + c2 + c4
		assertEquals(1, c1.getIdClient().size());
	}
}
