import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://HOME-PC;databaseName=example;trustServerCertificate=true";
        String user = "qwe";
        String password = "qwer";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            insertData(url, user, password, "John");
            selectData(url, user, password);
            updateData(url, user, password, 1, "UpdatedName");
            selectData(url, user, password);
            deleteData(url, user, password, 1);
            selectData(url, user, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver not found");
        }
    }

    private static void insertData(String url, String user, String password, String name) {
        String sqlInsert = "INSERT INTO example (Name) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            System.out.println("Data added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Data insert failed");
        }
    }
    private static void selectData(String url, String user, String password) {
        String sqlSelect = "SELECT Id, Name FROM example";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                System.out.println("Id: " + id + " Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Data select failed");
        }
    }
    private static void updateData(String url, String user, String password, int id, String newName) {
        String sqlUpdate = "UPDATE example SET Name = ? WHERE Id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Data updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Data update failed");
        }
    }
    private static void deleteData(String url, String user, String password, int id) {
        String sqlDelete = "DELETE FROM example WHERE Id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Data deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Data delete failed");
        }
    }
}
