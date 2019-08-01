package dao;

import servlets.SignUpServlet;

public interface SignUpDao {

    public int insertSignUpServlet (SignUpServlet s);
    public SignUpServlet getSignUpServlet (String name, String surname, String userName);

}
