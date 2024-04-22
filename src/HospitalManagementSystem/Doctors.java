package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection conn;
    private Scanner sc;

    public Doctors(Connection conn) {
        this.conn = conn;
        this.sc = sc;
    }

    // As discuss create 3 methods Add patients , View patient, Check Patient


    public void viewDoctors() {
        String query = "SELECT * From doctors";
        try {
            PreparedStatement preparedStatemen = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatemen.executeQuery();
            System.out.println("+-----------+-------------------+------------+");
            System.out.println("| DoctorID  | Specialization    | Gender     |");
            System.out.println("+-----------+-------------------+------------+");
            while (resultSet.next()) {
                int patientID = resultSet.getInt("id");
                String doctorName = resultSet.getString("doctorName");
                String doctorSpecialization = resultSet.getString("doctorSpecialization");
                String doctorGender = resultSet.getString("doctorGender");
//                System.out.printf("|%-11s|%-18s|%-11s|");
                System.out.printf("|%-11d| %-18s| %-11s|%n", patientID, doctorSpecialization, doctorGender);
//                System.out.printf("|%-11d|%-18s|%-11s|%n", doctorSpecialization, doctorGender);
                System.out.println("+-----------+-------------------+------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorsById(int id) {
        String query = "SELECT * FROM doctors Where id = ?";
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