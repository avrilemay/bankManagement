import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// définition de la classe Banque
public class Banque {
    private static int dernierId; // ID unique pour la dernière banque créée
    private int id; // ID unique de cette banque
    private String nom; // nom de la banque
    private Map<Integer, Client> clientsParId; // dictionnaire associant ID de client et Client
    private Map<Integer, Compte> comptesParId; // dictionnaire associant ID de compte et Compte

    // constructeur pour initialiser une nouvelle banque
    public Banque(String nom) {
        this.id=++dernierId;
        this.nom = nom;
        this.clientsParId = new HashMap<>(); // initialisation du dictionnaire des clients
        this.comptesParId = new HashMap<>(); // initialisation du dictionnaire des comptes
    }

    // méthodes getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Map<Integer, Client> getListeClients() {
        return new HashMap<>(clientsParId);
    }

    public Map<Integer, Compte> getListeComptes() {
        return new HashMap<>(comptesParId);
    }

    // crée et ajoute un nouveau client à la banque
    public Client createClient(String nom, String prenom) {
        Client nouveauClient = new Client(nom, prenom); // création d'un nouveau client
        clientsParId.put(nouveauClient.getId(), nouveauClient); // ajout du client au dictionnaire
        return nouveauClient;
    }

    // ajoute un client existant à la banque
    public void addClient(Client client) {
        clientsParId.put(client.getId(), client);
    }

    // supprime un client et ses comptes associés si nécessaire
    public void removeClient(Client client) {
        clientsParId.remove(client.getId()); // supprime le client du dictionnaire
        // parcourt tous les IDs de comptes associés au client
        for (Integer compteId : client.getComptes()) {
            Compte compte = comptesParId.get(compteId); // récupère l'objet compte
            Set<Integer> clientsDuCompte = compte.getListeClients();
            // récupère tous les clients associés au compte
            if (clientsDuCompte.size() > 1) { // si le compte a plus d'un client
                clientsDuCompte.remove(client.getId()); // on supprime seulement la réf à notre client
            } else { // si le compte n'a qu'un seul client, on supprime le compte
                comptesParId.remove(compteId);
            }
        }
    }

    // trouve un client par son ID
    public Client findClient(int id) {
        return clientsParId.get(id);
    }

    // crée et ajoute un nouveau compte pour un client donné à la banque
    public Compte createCompte(Client client) {
        Compte nouveauCompte = new Compte(client); // création d'un nouveau compte
        comptesParId.put(nouveauCompte.getId(), nouveauCompte); // ajout du compte au dictionnaire
        client.addCompte(nouveauCompte.getId()); // associé le compte au client
        return nouveauCompte;
    }


    // ajoute un compte à la banque
    public void addCompte(Compte comptePourAjouter) {
        comptesParId.put(comptePourAjouter.getId(), comptePourAjouter);
    }

    // supprime un compte de la banque et des clients associés
    public void removeCompte(Compte comptePourSupprimer) {
        comptesParId.remove(comptePourSupprimer.getId());
        for (Client client : clientsParId.values()) { // pour chaque client de la banque
            if (client.getComptes().contains(comptePourSupprimer.getId())) {
                // si le client possède le compte à supprimer
                client.removeCompte(comptePourSupprimer); // on le supprime de sa liste des comptes
            }
        }
    }

    // trouve un compte par son ID
    public Compte findCompte(int id) {
        return comptesParId.get(id);
    }

    // affiche la liste des comptes de la banque
    public void afficherListeCompte() {
        for (Compte compte : comptesParId.values()) {
            System.out.printf("%s\n", compte.toString());
        }
    }

    // affiche la liste des clients de la banque
    public void afficherListeClient() {
        for (Client client : clientsParId.values()) {
            System.out.printf("%s\n", client.toString());
        }
    }

    // affiche la liste des comptes d'un client spécifique
    public void afficherListeCompteClient(Client client) {
        for (Integer compteId : client.getComptes()) {
            Compte compte = comptesParId.get(compteId);
            System.out.printf("%s\n", compte.toString());
        }
    }

}
