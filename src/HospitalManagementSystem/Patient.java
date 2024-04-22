package HospitalManagementSystem;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection conn;
    private Scanner sc;

    public Patient(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    // As discuss create 3 methods Add patients , View patient, Check Patient
    public void addPatient() {
        System.out.print("Enter Patient Name ===>   ");
        String name = sc.next();
        System.out.print("Enter Patient Age");
        int age = sc.nextInt();
        System.out.print("Enter Patient/Father CNIC");
        String cnic = sc.next();
        System.out.print("Enter Patient Gender");
        String gender = sc.next();

        try {
            String query = "INSERT into patients(patientName, patientAge, patientCNIC, patientGender)VALUES(?,?,?,?) ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, cnic);
            preparedStatement.setString(4, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient inserted successfully " + affectedRows + " Rows affected in Database");
            } else System.out.println("Faild to add patient");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "SELECT * From patients";
        try {
                PreparedStatement preparedStatemen = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatemen.executeQuery();
                System.out.println("+------------+-------------------+----------+------------------+------------+");
                System.out.println("| PatientID  | Name              | Age      | CNIC             | Gender     |");
                System.out.println("+------------+-------------------+----------+------------------+------------+");
                while (resultSet.next()) {
                int patientID = resultSet.getInt("id");
                String patientName = resultSet.getString("patientName");
                int patientAge = resultSet.getInt("patientAge");
                String patientCnic = resultSet.getString("patientCNIC");
                String patientGender = resultSet.getString("patientGender");
                    System.out.printf("| %-11d| %-18s| %-9d| %-17s| %-11s|%n", patientID, patientName, patientAge, patientCnic, patientGender);
                System.out.println("+------------+-------------------+----------+------------------+-------------");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients Where id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
