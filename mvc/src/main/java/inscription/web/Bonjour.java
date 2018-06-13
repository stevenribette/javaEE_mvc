package inscription.web;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/bonjour")
public class Bonjour {
	
	@GET
	public String direBonjour() {
		return "bonjour";
	}
}
