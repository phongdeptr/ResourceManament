/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.UserDTO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String PROCESS_REQUEST = "ProcessRequestController";
    private static final String SEARCH_REQUEST = "SearchRequestController";
    private static final String GET_RESOURCE = "GetResourceController";
    private static final String BOOK_RESOURCE = "BookResourceController";
    private static final String CREATE_ACCOUNT = "CreateAccountController";
    private static final String VIEW_HISTORY = "RequestHistoryController";
    private static final String CANCEL_REQUEST = "CancelRequest";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        String action = request.getParameter("action");
        RequestDispatcher dispatcher = null;
        String url = "";
        if (action == null) {
            url = "login.jsp";
        } else {
            switch (action) {
                case "login":
                    url = LOGIN_CONTROLLER;
                    break;
                case "reject":
                case "accept":
                    url = PROCESS_REQUEST;
                    break;
                case "bookResource":
                    url = BOOK_RESOURCE;
                    break;
                case "get-resource":
                    url = GET_RESOURCE;
                    break;
                case "search-request":
                    url = SEARCH_REQUEST;
                    break;
                case "create-account":
                    url = CREATE_ACCOUNT;
                    break;

                case "view-history":
                    url = VIEW_HISTORY;
                    break;

                case "cancel":
                    url = CANCEL_REQUEST;
                    break;
                default: {
                    url = "home.jsp";
                    break;
                }
            }
        }
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
