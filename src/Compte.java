import java.util.HashSet;
import java.util.Set;

// définition de la classe Compte
public class Compte {
    // variables statiques et d'instance
    private static int dernierId; // ID unique pour le dernier compte créé
    private int id; // ID unique pour ce compte
    private double solde; // solde du compte
    private Set<Integer> listeIdClients; // ensemble des IDs des clients associés à ce compte

    // constructeur pour initialiser un nouveau compte
    public Compte(Client client) {
        this.id = ++dernierId; // incrémente et attribue l'ID
        this.solde = 0; // initialise le solde à zéro
        this.listeIdClients = new HashSet<>(); // initialise la liste des clients
        this.listeIdClients.add(client.getId()); // ajoute le client au compte
        client.addCompte(this.getId());  // lie le compte au client
    }

    // getters pour accéder aux propriétés du compte
    public int getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public Set<Integer> getListeClients() {
        return listeIdClients;
    }

    // getter pour obtenir les IDs des clients associés au compte
    public Set<Integer> getIdClient() {
        return new HashSet<>(listeIdClients); // retourne une copie de la liste des clients
    }

    // méthode pour représenter le compte sous forme de chaîne de caractères
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", listeIdClients=" + listeIdClients +
                '}';
    }

    // méthode pour créditer le compte
    public void crediter(double montant){
        this.solde += montant; // ajoute le montant au solde
    }

    // méthode pour débiter le compte
    public void debiter(double montant) throws IllegalOperationException {
        if (this.solde >= montant) { // vérifie si le solde est suffisant
            this.solde -= montant; // soustrait le montant du solde
        } else {  // lance une exception personnalisée si le solde est insuffisant
            throw new IllegalOperationException("Solde insuffisant.");
        }
    }


    // méthode pour transférer de l'argent vers un autre compte
    public void transfert(Compte compte, double montant) throws IllegalOperationException{
        this.debiter(montant); // débite le montant de ce compte
        compte.crediter(montant); // crédite le montant sur le compte cible
    }

    // méthode pour ajouter un client au compte
    public void addClient(Client client) throws IllegalOperationException {
        if (this.listeIdClients.size() >= 2) { // limite à 2 clients par compte
            throw new IllegalOperationException("Ce compte en banque a déjà deux clients.");
        } else { // ajoute le client à la liste du compte
            this.listeIdClients.add(client.getId()); // Ajoute le client à la liste du compte
            client.addCompte(this.id); // on ajoute le compte à la liste des comptes du client
        }
    }


    // méthode pour retirer un client du compte
    public void removeClient(Client client) {
        this.listeIdClients.remove(client.getId()); // retire le client de la liste
    }

}
