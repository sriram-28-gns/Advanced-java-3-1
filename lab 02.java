import java.sql.*;

public class Prepare{

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb?";
        String user = "testuser";
        String password = "testpass"; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            String createTable = "CREATE TABLE IF NOT EXISTS student ("
                               + "RollNo INT PRIMARY KEY, "
                               + "Name VARCHAR(50), "
                               + "Address VARCHAR(100))";
            con.createStatement().executeUpdate(createTable);
            System.out.println("Table created successfully.");

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (1, 'ravi', 'Hyderabad')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (2, 'sita', 'Chennai')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (3, 'kiran', 'Bangalore')");
            System.out.println("Initial records inserted.");
          
            System.out.println("\nInitial Records:");
            displayRecords(con);

            String insertSQL = "INSERT INTO student (RollNo, Name, Address) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertSQL);

            insertStmt.setInt(1, 4);
            insertStmt.setString(2, "meena");
            insertStmt.setString(3, "Pune");
            insertStmt.executeUpdate();

            insertStmt.setInt(1, 5);
            insertStmt.setString(2, "ramesh");
            insertStmt.setString(3, "Mumbai");
            insertStmt.executeUpdate();

            System.out.println("Two new records inserted.");

            String updateSQL = "UPDATE student SET Address = ? WHERE RollNo = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateSQL);
            updateStmt.setString(1, "Delhi");
            updateStmt.setInt(2, 2);
            updateStmt.executeUpdate();
            System.out.println("One record updated.");

            String deleteSQL = "DELETE FROM student WHERE RollNo = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, 3);
            deleteStmt.executeUpdate();
            System.out.println("One record deleted.");

            System.out.println("\nFinal Records:");
            displayRecords(con);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayRecords(Connection con) throws SQLException {
        String selectSQL = "SELECT * FROM student";
        PreparedStatement selectStmt = con.prepareStatement(selectSQL);
        ResultSet rs = selectStmt.executeQuery();

        System.out.println("RollNo\tName\tAddress");
        while (rs.next()) {
            int roll = rs.getInt("RollNo");
            String name = rs.getString("Name");
            String address = rs.getString("Address");
            System.out.println(roll + "\t" + name + "\t" + address);
        }
    }
}


 // OUTPUT:
 // Table created successfully.
 // Initial records inserted.

 // Initial Records:
 // RollNo  Name    Address
 // 1       ravi    Hyderabad
 // 2       sita    Chennai
 // 3       kiran   Bangalore
   
 // Two new records inserted.
 // One record updated.
 // One record deleted.

 // Final Records:
 // RollNo  Name     Address
 // 1       ravi     Hyderabad
 // 2       sita     Delhi
 // 4       meena    Pune
 // 5       ramesh   Mumbai
