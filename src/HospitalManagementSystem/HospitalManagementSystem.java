package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username="root";
    private static final String password="root";

    public static void main(String[] args) {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        }

        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctors doctors = new Doctors(connection);

                while (true) {
                    System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                    System.out.println("1. Add Patient");
                    System.out.println("2. View Patient");
                    System.out.println("3. View Doctor");
                    System.out.println("4. Book Appointment");
                    System.out.println("5. EXIT");

                    System.out.print("Please enter your choice ==> ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next(); // Consume the invalid input
                        continue; // Restart the loop to ask for input again
                    }

                    int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;

                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;

//                    case 3:
//                        System.out.println();
//                        break;

                    case 3:
                        doctors.viewDoctors();
                        System.out.println();
                        break;


                    case 4:
                        //
                        bookAppointment(patient,doctors,connection,scanner);
                        System.out.println();
                        break;

                    case 5:
                        //
                        return;

                    default:
                        System.out.println("Please Enter appropriate values");
                }


            }

        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctors doctors, Connection connection, Scanner scanner){
        System.out.print("Please Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Please Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Please Enter Appointment Date: (YYYY-MM-DD)");
        String appointmentDate = scanner.next();
        if (patient.getPatientById(patientId) && doctors.getDoctorsById(doctorId)){
            if (checkDoctorAvailability(doctorId, appointmentDate , connection)) {
                String appointmentQuery = "INSERT INTO appointments (PatientID, DocId, AppDate) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement  = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0 ){
                        System.out.println("Appointment Booked");
                    }
                    else {
                        System.out.println("Failed to book appointment");
                    }
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Doctor does not available on this date!!!!");
            }
        }
        else {
            System.out.println("Either Doctor or Patient does not exists!!!");
        }

    }

    private static boolean checkDoctorAvailability(int doctorId, String appointmentDate , Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE DocId = ? AND AppDate = ?";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count==0){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
