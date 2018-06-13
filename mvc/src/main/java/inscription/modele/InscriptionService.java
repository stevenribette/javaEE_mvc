package inscription.modele;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Path("/inscription")
public class InscriptionService {
	public String url = "http://www.server.net";
	public Inscription inscrire(String email, String motDePasse, String confirmationMotDePasse, boolean approbation) throws InscriptionInvalideException {
		InscriptionInvalideException ex = new InscriptionInvalideException();
		
		if (email == null || ! email.contains("@")) {
			ex.addMessage("email", "L'email est invalide !");
		}
		if (motDePasse == null || motDePasse.length() < 8) {
			ex.addMessage("motDePasse", "Le mot de passe doit contenir au moins 8 caractères !");
		}
		if (!motDePasse.equals(confirmationMotDePasse)) {
			ex.addMessage("confirmationMotDePasse", "Les deux mots de passe ne sont pas identiques !");
		}
		if (! approbation) {
			ex.addMessage("approbation", "Vous devez accepter les conditions.");
		}
		if (ex.mustBeThrown()) {
			throw ex;
		}
		
		return new Inscription(email, motDePasse);
	}
	
	@GET
	public Inscription get() {
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(url);
	    Inscription inscription = target.request().get(Inscription.class);
		return inscription;
	}

	@PUT
	public Inscription createOrUpdate() {
		return null;
	}

	@DELETE
	public void delete(Inscription inscription) {
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(url+inscription.getEmail());
		Response response = target.request().delete();
		try {
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
	        }
	        System.out.println("Successfully got result: " + response.readEntity(String.class));
	    } finally {
	        response.close();
	        client.close();
	    }
	}

	@POST
	@Path("/subscribe")
	public void subscribe(Inscription inscription) {
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(url);
		Response response = target.request().post(Entity.entity(inscription, "application/json"));
		try {
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
	        }
	        System.out.println("Successfully got result: " + response.readEntity(String.class));
	    } finally {
	        response.close();
	        client.close();
	    }
	}
}
