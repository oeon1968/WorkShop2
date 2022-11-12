package workshop2;

import java.sql.*;
import java.sql.Connection;

public class DbUtil {
    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        DbUtil.dbName = dbName;
    }


    //atrybuty klasy
    public static String dbName = "WorkShop2";

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPass() {
        return dbPass;
    }

    private static final String dbUser = "root";
    private static  final String dbPass = "coderslab";
    public static final String urlString = "jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";


//INSERT
    public static int insert(Connection conn, String query, String... params) {
        int newId = 0;
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
            if (!conn.isClosed()) {
                ResultSet rs= statement.executeQuery("SELECT LAST_INSERT_ID() as newId FROM users");
                while (rs.next()){
                    newId = rs.getInt("newId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

//SELECT
    public static User getUser(Connection conn, String selectStr, int userId){
       User myUser = new User();

        try (PreparedStatement statement = conn.prepareStatement(selectStr))
        {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
             myUser.setId(userId);
             myUser.setName(resultSet.getString("name"));
             myUser.setEmail(resultSet.getString("email"));
             myUser.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myUser;
    }
//DELETE
    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";
    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//UPDATE

    public static void update(Connection conn, String query, int id, String... setValues) {
        int i = 1; //od drugiego parametru będzie wstawiał dane z setValues

        try (PreparedStatement statement =
                     conn.prepareStatement(query)) {
            for (String value: setValues) {
                statement.setString(i++, value);
            }
            statement.setInt(i, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//PRINT
    public static void printData(Connection conn, String query, String... columnNames) {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                for (String columnName : columnNames) {
                    System.out.print(resultSet.getString(columnName)+"|");
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
