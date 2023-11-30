/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package karyawan;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author user
 */
import java.sql.*;
import java.util.Scanner;

public class Karyawan {
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        connection = DatabaseManager.getConnection();

        if (connection != null) {
            boolean exit = false;
            while (!exit) {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer

                switch (choice) {
                    case 1:
                        tambahKaryawan();
                        break;
                    case 2:
                        editKaryawan();
                        break;
                    case 3:
                        hapusKaryawan();
                        break;
                    case 4:
                        tampilkanKaryawan();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            }

            try {
                connection.close();
                System.out.println("Terima Kasih!");
            } catch (SQLException e) {
                System.out.println("Tidak dapat menutup koneksi! Error: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("============================");
        System.out.println("==== PENGELOLA KARYAWAN ====");
        System.out.println("============================");
        System.out.println("1. Tambah Karyawan");
        System.out.println("2. Edit Karyawan");
        System.out.println("3. Hapus Karyawan");
        System.out.println("4. Tampilkan Karyawan");
        System.out.println("5. Keluar Program");
        System.out.println("============================");
        System.out.print("Pilih Menu : ");
    }

    private static void tambahKaryawan() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO karyawan (nik, nama, jabatan, alamat, email, no_telp) VALUES (?, ?, ?, ?, ?, ?)"
            );

            System.out.println("============================");
            System.out.println("====== TAMBAH KARYAWAN =====");
            System.out.println("============================");
            System.out.print("NIK : ");
            String nik = scanner.nextLine();
            System.out.print("Nama : ");
            String nama = scanner.nextLine();
            System.out.print("Jabatan : ");
            String jabatan = scanner.nextLine();
            System.out.print("Alamat : ");
            String alamat = scanner.nextLine();
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Telepon : ");
            String telepon = scanner.nextLine();

            statement.setString(1, nik);
            statement.setString(2, nama);
            statement.setString(3, jabatan);
            statement.setString(4, alamat);
            statement.setString(5, email);
            statement.setString(6, telepon);
            
            System.out.println("============================");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data berhasil ditambahkan!");
            } else {
                System.out.println("Gagal menambahkan data.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editKaryawan() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nik, nama, jabatan FROM karyawan");

            System.out.println("============================");
            System.out.println("======= EDIT KARYAWAN =======");
            System.out.println("============================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String jabatan = resultSet.getString("jabatan");
                System.out.println(id + ". " + nik + " - " + nama + " - " + jabatan);
            }
            System.out.println("============================");

            System.out.print("Masukkan id yang ingin diubah : ");
            int idToEdit = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            ResultSet rs = statement.executeQuery("SELECT * FROM karyawan WHERE id = " + idToEdit);
            if (rs.next()) {
                PreparedStatement ps = connection.prepareStatement(
                    "UPDATE karyawan SET nik = ?, nama = ?, jabatan = ?, alamat = ?, email = ?, no_telp = ? WHERE id = ?"
                );

                System.out.println("============================");
                System.out.println("======= UBAH KARYAWAN ======");
                System.out.println("============================");
                System.out.print("NIK : ");
                String nik = scanner.nextLine();
                System.out.print("Nama : ");
                String nama = scanner.nextLine();
                System.out.print("Jabatan : ");
                String jabatan = scanner.nextLine();
                System.out.print("Alamat : ");
                String alamat = scanner.nextLine();
                System.out.print("Email : ");
                String email = scanner.nextLine();
                System.out.print("Telepon : ");
                String telepon = scanner.nextLine();

                ps.setString(1, nik);
                ps.setString(2, nama);
                ps.setString(3, jabatan);
                ps.setString(4, alamat);
                ps.setString(5, email);
                ps.setString(6, telepon);
                ps.setInt(7, idToEdit);
                
                System.out.println("============================");

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Data berhasil diubah!");
                } else {
                    System.out.println("Gagal mengubah data.");
                }
            } else {
                System.out.println("Id tidak tersedia!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void hapusKaryawan() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nik, nama, jabatan FROM karyawan");

            System.out.println("============================");
            System.out.println("====== HAPUS KARYAWAN ======");
            System.out.println("============================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String jabatan = resultSet.getString("jabatan");
                System.out.println(id + ". " + nik + " - " + nama + " - " + jabatan);
            }
            System.out.println("============================");

            System.out.print("Masukkan id yang ingin dihapus : ");
            int idToDelete = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            int rowsDeleted = statement.executeUpdate("DELETE FROM karyawan WHERE id = " + idToDelete);
            if (rowsDeleted > 0) {
                System.out.println("Data karyawan dengan ID " + idToDelete + " berhasil dihapus!");
            } else {
                System.out.println("Id tidak tersedia!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void tampilkanKaryawan() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nik, nama, jabatan FROM karyawan");

            System.out.println("============================");
            System.out.println("====== DAFTAR KARYAWAN =====");
            System.out.println("============================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String jabatan = resultSet.getString("jabatan");
                System.out.println(id + ". " + nik + " - " + nama + " - " + jabatan);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
