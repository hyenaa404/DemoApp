/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.StudentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import model.Student;
import util.Validation;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "DisplayStudent", urlPatterns = {"/display"})
public class DisplayServlet extends HttpServlet {

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
            throws ServletException, IOException, IllegalArgumentException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO dao = new StudentDAO();
        String name = request.getParameter("nameToSearch");
//        if(name == null){
//            HttpSession session = request.getSession();
//            name = (String) session.getAttribute("name");
//        }
        request.setAttribute("nameToSearch", name);
        List<Student> students = dao.searchStudentByName(name);
        if (students != null) {

            request.setAttribute("students", students);
            // Xóa cache của trình duyệt
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            throw new IOException("Can't get list from Database");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "update" -> {
                updateData(request, response);
            }
            case "delete" -> {
                deleteData(request, response);
            }
            case "insert" -> {
                insertData(request, response);
            }
            default -> {
                request.setAttribute("status", "Error! Can not get method.");
            }
        }
        
        request.getRequestDispatcher("home.jsp").forward(request, response);
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

    protected void deleteData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO dao = new StudentDAO();
        String[] selected = request.getParameterValues("isDelete");

        if (selected != null) {
            int index = 0;
            for (String studentId : selected) {
                int id;
                try {
                    id = Integer.parseInt(studentId);
                } catch (NumberFormatException e) {
                    throw new ServletException("invalid id");
                }
//                out.print(studentId + "  ");
                if (dao.deleteStudentById(id)) {
                    index++;
                }
            }
            request.setAttribute("status", "Successfully deleted for " + index + " students.");
        }
        
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void updateData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO dao = new StudentDAO();
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
                    } else {
                        index++;
                    }
                    request.setAttribute("status", "Successfully update for " + index + " students.");

                }
            }
        }
        
        request.getRequestDispatcher("home.jsp").forward(request, response);

    }

    protected void insertData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        StudentDAO dao = new StudentDAO();
        String name = request.getParameter("name");

        String d = request.getParameter("dob");
        Date dob;
        try {
            dob = Validation.inputDate(d);
        } catch (ParseException e1) {
            dob = null;
            request.setAttribute("status", "Error! invalid dob");
        }
        if (dob != null) {
            Boolean isGender = (request.getParameter("isMale") != null);
            Student stu = new Student(0, name, isGender, dob);
            if (dao.insertStudent(stu)) {
                request.setAttribute("status", "Added sucessfully!");
            }
        }

        
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

}
