package khoibm.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import khoibm.utils.XMLUtils;

@WebServlet(name = "LoginControllerStAX", urlPatterns = {"/LoginControllerStAX"})
public class LoginControllerStAX extends HttpServlet {

   private static final String XML_FILE = "/WEB-INF/studentAccounts.xml";
    private static final String SUCCESS = "show.jsp";
    private static final String ERROR = "index.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        try {
            String txtUsername = request.getParameter("txtUsername");
            String txtPassword = request.getParameter("txtPassword");

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XML_FILE;

            String fullname = "";
            boolean found = false;

            XMLStreamReader reader = XMLUtils.createStAXCursorReadFromFile(filePath);
            while (reader.hasNext()) {

                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();

                    if (tagName.equals("student")) {
                        String id = reader.getAttributeValue("", "id");
                        if (id.equals(txtUsername)) {
                            fullname = XMLUtils.getTextContext(reader, "lastname").trim();
                            fullname = XMLUtils.getTextContext(reader, "middlename").trim() + " " + fullname;
                            fullname = XMLUtils.getTextContext(reader, "firstname").trim() + " " + fullname;

                            System.out.println(fullname);

                            String pass = XMLUtils.getTextContext(reader, "password").trim();
                            if (pass.equals(txtPassword)) {
                                String status = XMLUtils.getTextContext(reader, "status").trim();

                                if (!status.equals("Dropout")) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }

            }

            if (found) {
                url = SUCCESS;
                HttpSession session = request.getSession();
                session.setAttribute("FULL_NAME", fullname);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
