package context;

//import com.sun.jdi.connect.spi.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Student;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LENOVO
 */
public class StudentDAO {

    private DBContext dbContext;
    private List<Student> studentList = new ArrayList<>();

    public StudentDAO() {
        dbContext = new DBContext();
    }

    public List<Student> getStudentList() {
        studentList.clear();
        String query = "SELECT * FROM Student";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student nv = new Student(
                        rs.getInt("StudentID"),
                        rs.getString("StudentName"),
                        rs.getBoolean("gender"),
                        rs.getDate("DOB")
                );
                studentList.add(nv);
            }
            dbContext.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public boolean insertStudent(Student student) {
        try {
            String query = "INSERT INTO Student (StudentName, gender, DOB) VALUES ( ?, ?, ?)";
            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, student.getName());
            ps.setBoolean(2, student.isGender());
            ps.setDate(3, new java.sql.Date(student.getDob().getTime())); // Assuming student.getDOB() returns a java.util.Date

            ps.executeUpdate();
            dbContext.closeConnection(conn);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean updateStudent(Student student) {
        try {
            String query = "UPDATE Student SET StudentName = ?, gender = ?, DOB = ? WHERE StudentID = ?";
            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, student.getName());
            ps.setBoolean(2, student.isGender());
            ps.setDate(3, new java.sql.Date(student.getDob().getTime())); // Assuming student.getDOB() returns a java.util.Date
            ps.setInt(4, student.getId());

            ps.executeUpdate();
            dbContext.closeConnection(conn);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Student> getStudentListByNumber(int number) {
        studentList.clear();
        String query = "SELECT * FROM Student";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student nv = new Student(
                        rs.getInt("StudentID"),
                        rs.getString("StudentName"),
                        rs.getBoolean("gender"),
                        rs.getDate("DOB")
                );
                studentList.add(nv);
                number--;
                if (number == 0) {
                    break;
                }
            }
            dbContext.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public boolean deleteStudentById(int studentId) {
        String query = "DELETE FROM Student WHERE StudentID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Student> searchStudentByName(String name) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE StudentName LIKE ?";

        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student nv = new Student(
                        rs.getInt("StudentID"),
                        rs.getString("StudentName"),
                        rs.getBoolean("gender"),
                        rs.getDate("DOB")
                );
                students.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student searchStudentById(int id) {

        String sql = "SELECT * FROM Student WHERE StudentID LIKE ?";

        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student nv = new Student(
                        rs.getInt("StudentID"),
                        rs.getString("StudentName"),
                        rs.getBoolean("gender"),
                        rs.getDate("DOB")
                );
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
