package com.gae.sync;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ChromeGaeSyncServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
