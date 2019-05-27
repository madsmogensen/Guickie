package guickie;

/**
 *
 * @author Mads S. Mogensen
 */
public class Guickie extends GuickieBuilder{
    @Override
    public void build(){
        tabpane("mainTabPane");
        tab("loginTab").text("Login");
        label("loginLabelUser").text("Username");
        textfield("loginTextUser").text("Username");
        label("loginLabelPass").text("Password");
        textfield("loginTextPass").text("Password");
        button("loginButton").text("Login");
        tab("signupTab").text("SignUp");
        horizontal("signupHor");
        label("signUpLabelUser").text("Username");
        textfield("signUpTextUser").text("Username");
        label("signUpLabelPass").text("Password");
        textfield("signUpTextPass").text("Password");
        button("signUpButton").text("SignUp");
    }
}



