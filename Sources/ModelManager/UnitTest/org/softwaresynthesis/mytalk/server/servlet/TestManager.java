package org.softwaresynthesis.mytalk.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
import org.softwaresynthesis.mytalk.server.authentication.AESAlgorithmTest;
import org.softwaresynthesis.mytalk.server.authentication.AuthenticationModuleTest;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAOTest;
/**
 * Servlet utilizzata per stampare la pagina contenente l'esito dei test per la parte server
 * 
 * @author Elena Zecchinato
 * @version %I%,%G%
 */
@WebServlet(description = "Servlet utilizzata per eseguire i test di unità sulla parte server dell'applicativo", urlPatterns = { "/TestManager" })
public class TestManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// insieme di classi di test da effettuare (leggermente esoterico)
	private Class<?>[] tests = {
			AESAlgorithmTest.class,
			AuthenticationModuleTest.class,
			UserDataDAOTest.class
			};
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestManager() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @author Elena Zecchinato
	 * @author Diego Beraldin
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @version %I%,%G%
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<DOCTYPE html>");
		writer.println("<html lang=\"it\">");
		writer.println("  <head>");
		writer.println("    <title>Test</title>");
		writer.println("    <meta charset=\"UTF-8\" />");
		writer.println("  </heady>");
		writer.println("  <body>");
		writer.println("<ul>");
		for (int i = 0; i < tests.length; i++) {
			Result result = JUnitCore.runClasses(tests[i]);
			if (result.wasSuccessful()) {
				writer.println("<li>Test superato!</li>");
			} else {
				writer.println("<li>Test fallito!</li>");
// TODO da completare con la stampa dei fallimenti
//				for (Failure failure: result.getFailures()) {
//					writer.println();
//				}
			}
		}
		writer.println("</ul>");
		writer.println("  </body>");
		writer.println("</html>");
	}

}
