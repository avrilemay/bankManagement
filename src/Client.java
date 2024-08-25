import java.util.HashSet;
import java.util.Set;

// définition de la classe Client
public class Client {
    // variables statiques et d'instance
    private static int dernierId; // compteur statique pour l'ID du dernier client créé
    private int id; // ID unique pour ce client
    private String nom; // nom du client
    private String prenom; // prénom du client
    private Set<Integer> listeComptesId; // ensemble des IDs des comptes associés à ce client

    // constructeur pour initialiser un nouveau client
    public Client(String nom, String prenom) {
        this.id = ++dernierId; // incrémente et attribue l'ID au client
        this.nom = nom; // initialise le nom
        this.prenom = prenom; // initialise le prénom
        this.listeComptesId = new HashSet<>(); // initialise l'ensemble des comptes du client
    }

    // méthode pour représenter le client sous forme de chaîne de caractères
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", listeComptesId=" + listeComptesId +
                '}';
    }

    // getters pour accéder aux propriétés du client
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Set<Integer> getComptes() {
        return listeComptesId; // obtenir la liste des IDs des comptes du client
    }

    // méthode pour ajouter un compte à la liste des comptes du client
    public void addCompte(int idCompte) {
        this.listeComptesId.add(idCompte); // ajoute l'ID du compte à la liste
    }

    // méthode pour retirer un compte de la liste des comptes du client
    public void removeCompte(Compte compte) {
        this.listeComptesId.remove(compte.getId()); // retire l'ID du compte de la liste
    }


}
