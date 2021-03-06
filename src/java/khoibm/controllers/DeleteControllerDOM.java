package khoibm.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import khoibm.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@WebServlet(name = "DeleteControllerDOM", urlPatterns = {"/DeleteControllerDOM"})
public class DeleteControllerDOM extends HttpServlet {

    private static final String XML_FILE = "/WEB-INF/studentAccounts.xml";
    private static final String SUCCESS_PAGE = "SearchControllerDOM";
    private static final String ERROR_PAGE = "index.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            String id = request.getParameter("id");
            String txtAddress = request.getParameter("txtAddress");

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XML_FILE;

            Document doc = XMLUtils.parseFileToDOM(filePath);
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();
                String exp = "//student[@id='" + id + "']";

                Node student = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);

                if (student != null) {
                    Node parent = student.getParentNode();

                    parent.removeChild(student);
                    boolean result = XMLUtils.transformDomToStreamResult(doc, filePath);

                    if (result) {
                        url = SUCCESS_PAGE + "?txtAddress=" + txtAddress;
                    }

                }

            }

        } catch (Exception e) {
            System.out.println("Delete Controller: " + e.getMessage());

        } finally {
            response.sendRedirect(url);
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
