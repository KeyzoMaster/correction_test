import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.Client;
import org.example.Moto;
import org.example.Vehicule;
import org.example.Voiture;

import static org.junit.Assert.*;

public class LocationSteps {

    private Vehicule vehicule;
    private Client client;
    private Client autreClient;
    private Exception vehiculeException;
    private double coutTotal;
    private int jours;

    @Given("Une voiture {string} {string} immatriculée {string} avec un tarif de {int} F\\/j")
    public void creerVoiture(String marque, String modele, String matricule, double tarif) {
        vehicule = new Voiture(marque, modele, matricule, tarif);
    }

    @Given("Une moto {string} {string} immatriculée {string} avec un tarif de {int} F\\/j et une cylindrée de {int} cm³")
    public void creerMotoGrosCylindre(String marque, String modele, String matricule, double tarif, int cylindre){
        vehicule = new Moto(marque, modele, matricule, tarif, cylindre);
    }

    @Given("Un client {string} {string}")
    public void creerClient(String prenom, String nom){
        client = new Client(1, prenom, nom);
    }

    @Given("Une voiture {string} {string} immatriculée {string} louée par {string} {string}")
    public void voitureLoueeParClient(String marque, String modele, String matricule, String prenom, String nom) throws Exception{
        vehicule = new Voiture(marque, modele, matricule, 10000);
        client = new Client(1,prenom, nom);
        client.ajouterLocation(vehicule, 1);
    }

    @When("Un autre client {string} {string} tente de la louer pour {int} jour")
    public void creerAutreClient(String prenom, String nom, int joursLocations) throws Exception{
       try{
           autreClient = new Client(2, prenom, nom);
           jours = joursLocations;
           autreClient.ajouterLocation(vehicule, jours);
       }catch (Exception e){
           vehiculeException = e;
       }
    }

    @When("Le client loue la voiture pour {int} jours")
    public void louerVoiture(int joursLocations) throws Exception{
        jours = joursLocations;
        client.ajouterLocation(vehicule, jours);
    }

    @When("Le client loue la moto pour {int} jours")
    public void louerMoto(int joursLocation) throws Exception{
        jours = joursLocation;
        client.ajouterLocation(vehicule,jours);
    }

    @When("Le client retourne la voiture")
    public void retournerVoiture(){
        client.retournerVehicule(vehicule);
    }

    @Then("La voiture ne doit plus être disponible")
    public void verifierVoiturePlusDisponible(){
        assertFalse(vehicule.isDisponible());
    }

    @Then("Le coût total de la location doit être de {double} F")
    public void verifiercoutLocation(double coutTotalAttendu){
        coutTotal = vehicule.calculerCout(jours);
        assertEquals(((int) coutTotal), ((int) coutTotalAttendu));
    }

    @Then("Une exception {string} doit être levée")
    public void leverException(String message) {
        assertEquals(message, vehiculeException.getMessage());
    }

    @Then("La voiture doit être disponible à nouveau")
    public void verifierVoitureDisponible(){
        assertTrue(vehicule.isDisponible());
    }

    @Then("Le coût total de la location doit être de {int} F \\(réduction appliquée)")
    public void verifierCoutLocationReduit(double coutTotalAttendu){
        coutTotal = vehicule.calculerCout(jours);
        assertEquals(((int) coutTotal), ((int) coutTotalAttendu));
    }
}
