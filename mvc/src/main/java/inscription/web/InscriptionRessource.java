package inscription.web;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import inscription.modele.InscriptionInvalideException;
import inscription.modele.InscriptionService;

@Path("/inscription")
public class InscriptionRessource {
	
	@POST
	public void creerInscription(
		@FormParam("mail") String mail,@FormParam("mdp") String mdp, 
		@FormParam("confirm") String confirm, @FormParam("cgu") boolean cgu) 
		throws InscriptionInvalideException {
		
		InscriptionService service = new InscriptionService();
		service.inscrire(mail, mdp, confirm, cgu);
		
	}
}
