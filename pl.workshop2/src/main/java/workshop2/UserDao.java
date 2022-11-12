package workshop2;

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
}
