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

    private static String dbUser = "root";
    private static  String dbPass = "coderslab";
    public static final String urlString = "jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";

    //metody klasy
    private static Connection myConnection(){
        try (Connection dbConnection = DriverManager.getConnection(urlString, dbUser, dbPass)) {
            return dbConnection;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }

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
    public static ResultSet getUser(Connection conn, String selectStr, int userId){
       ResultSet resultSet;

        try (PreparedStatement statement = conn.prepareStatement(selectStr))
        {
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//DELETE
    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";
    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//UPDATE
    private static final String UPDATE_QUERY = "UPDATE tableName SET colName = ? WHERE id = ?";
    public static void updateOneRecord(Connection conn, String tableName, String colName, String colValue, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(UPDATE_QUERY.replace("tableName", tableName)
                             .replace("colName", colName))) {
            statement.setString(1, colValue);
            statement.setInt(2, id);
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//PRINT
    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
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
