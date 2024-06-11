/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import model.Student;
import util.Validation;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/update"})
public class UpdateServlet extends HttpServlet {

    StudentDAO dao = new StudentDAO();

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
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        String[] updated = request.getParameterValues("id");

        if (updated != null) {
            int index = 0;
            for (String studentId : updated) {
                int id;
                try {
                    id = Integer.parseInt(studentId);
                } catch (NumberFormatException e) {
                    throw new ServletException("invalid id");
                }
                Student st = dao.searchStudentById(id);
                if (st != null) {
                    String name = request.getParameter("name_" + studentId);

                    String d = request.getParameter("dob_" + studentId);
                    Date dob;
                    try {
                        dob = Validation.inputDate(d);
                    } catch (ParseException e1) {
                        dob = st.getDob();
                    }

                    Boolean isGender = (request.getParameter("isMale_" + studentId) != null);
                    Student stu = new Student(id, name, isGender, dob);
                    if (!dao.updateStudent(stu)) {
                        throw new IOException("Can't update to Database");
                    }else{
                        index++;
                    }
                    request.setAttribute("status", "Successfully update for "+index+ " students.");

                }
            }
        }
        request.setAttribute("name", "");
        request.getRequestDispatcher("/display").forward(request, response);

        

    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
