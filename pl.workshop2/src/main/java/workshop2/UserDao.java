package workshop2;
/* commit and push*/
import java.sql.*;

public class UserDao {


    public User createUser(User user){
        String insertStr = "INSERT into users(name, email, password)\n" +
                "values (?, ?, ?)";

        try( Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())){
           user.id = DbUtil.insert(userConnection, insertStr, user.name, user.email, user.password);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return user;
    }

    public User readUser(int userId){
        String selectSTR = "SELECT name, email, password FROM users WHERE id = ?";
        User user = new User();
        try( Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())){
            user = DbUtil.getUser(userConnection, selectSTR, userId);
        }
         catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return user;
    }

    public void updateUser(User user){
        String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try( Connection userConnection = DriverManager.getConnection(
                DbUtil.urlString,
                DbUtil.getDbUser(),
                DbUtil.getDbPass())){
                DbUtil.update(userConnection,query, user.getId(),
                    user.getName(),user.getEmail(), user.getPassword());
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

}
