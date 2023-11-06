package prova;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Path("/test")
public class Esempio {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "test";
	}
}
