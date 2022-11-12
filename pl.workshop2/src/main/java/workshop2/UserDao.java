package workshop2;
/* commit and push*/
import java.sql.*;
import java.util.Arrays;

public class UserDao {
    public User createUser(User user) {
        String insertStr = "INSERT into users(name, email, password)\n" +
                "values (?, ?, ?)";

        try (Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())) {
            user.id = DbUtil.insert(userConnection, insertStr, user.name, user.email, user.password);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }

    public User readUser(int userId) {
        String selectSTR = "SELECT name, email, password FROM users WHERE id = ?";
        User user = new User();
        try (Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())) {
            user = DbUtil.getUser(userConnection, selectSTR, userId);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return user;
    }

    public void updateUser(User user) {
        String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())) {
            DbUtil.update(userConnection, query, user.getId(),
                    user.getName(), user.getEmail(), user.getPassword());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static User[] findAll() {
        User[] users = new User[0];
        ResultSet rsUsers;
        String selectSTR = "SELECT id, name, email, password FROM users";

        try (Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())) {
            PreparedStatement stmt = userConnection.prepareStatement(selectSTR,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rsUsers = stmt.executeQuery();

            while (rsUsers.next()) {
                users = expandArray(users);
                User tmpUser = new User();
                tmpUser.setId(rsUsers.getInt("id"));
                tmpUser.name = rsUsers.getString("name");
                tmpUser.email = rsUsers.getString("email");
                tmpUser.password = rsUsers.getString("password");
                users[users.length-1] = tmpUser;
                System.out.println(users[users.length -1].getName());

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    private static User[] expandArray (User[] sourceArr){
        return Arrays.copyOf(sourceArr, sourceArr.length+1);
    }
}
