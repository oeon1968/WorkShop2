package workshop2;

public class MainUserDao {

    public static void main(String[] args) {

        String tmpName = "3 Dariusz";

        UserDao myUserDao = new UserDao();
        User myUser = new User();
        myUser.setName(tmpName);
        myUser.setEmail(tmpName+"@emial.pl");
        myUser.setPassword(tmpName);
        myUser = myUserDao.createUser(myUser);
        System.out.println(myUser.id);

    }
}
