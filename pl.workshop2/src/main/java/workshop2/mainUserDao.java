package workshop2;
/* commit and push*/
public class mainUserDao {

    public static void main(String[] args) {

        String tmpName = "Artur 2";

        UserDao myUserDao = new UserDao();
        User myUser = new User();
        /*myUser.setName(tmpName);
        myUser.setEmail(tmpName+"@email.pl");
        myUser.setPassword(tmpName);
        myUser = myUserDao.createUser(myUser);*/

// Można wykorzystać metodę inicjującą obiekt user:
        //user() - create user
        //user(id) - select user

        myUser = myUserDao.readUser(12);
        myUser.setName("Dariusz");
        myUser.setEmail("dariusz@email.pl");

        myUserDao.updateUser(myUser);


    }
}
