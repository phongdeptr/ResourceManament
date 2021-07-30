/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Dao;
import dto.error.AccountError;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CreateAccountController", urlPatterns = {"/CreateAccountController"})
public class CreateAccountController extends HttpServlet {

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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Dao dao = new Dao();
        String phoneError = null;
        String passwordError = null;
        String emailError = null;
        boolean isNotValid = false;
        if (phone.length() < 10 || phone.length() > 11 || !phone.matches("[0-9]{10,11}")) {
            phoneError = "Phone lenght from 8 to 11 digit";
            isNotValid = true;
        }
        if (password.length() < 8 || password.length() > 32) {
            passwordError = "Password length from 8 to 12 character";
            isNotValid = true;

        }
        if (!email.matches("^[a-z][a-z0-9_\\.]{1,}@[a-z0-9]{1,}(\\.[a-z0-9]{2,4}){1,2}$")) {
            emailError = "email is not valid";
            isNotValid = true;

        }
        if (dao.checkDuplicateEmail(email)) {
            isNotValid = true;
            emailError = "Email is use by another account";
        }

        if (!isNotValid) {
            // Get properties object
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", 465);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.port", 465);

            // get Session
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("hoangthanhphong123@gmail.com", "phongkhongdeptrai123");
                }
            });

            // compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Testing Subject");
                message.setText("http://localhost:8080/J3P0016/VerifyAccountController?userID="+email +"&code="+dao.createVerification(email));
                // send message
                Transport.send(message);
                dao.creatAccount(email, password, fullName, phone, address);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                System.out.println("Message sent successfully");
            } catch (IOException | MessagingException | ServletException e) {
                e.printStackTrace();
            }
        } else {
            AccountError error = new AccountError();
            error.setEmailError(emailError);
            error.setPasswordError(passwordError);
            error.setPhoneError(phoneError);
            request.setAttribute("ERROR_REGISTER", error);
            request.getRequestDispatcher("register.jsp").forward(request, response);
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
