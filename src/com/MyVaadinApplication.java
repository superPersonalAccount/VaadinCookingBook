package com;

import com.google.gwt.thirdparty.guava.common.hash.Hashing;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by rKlonos on 25.01.2017.
 */
public class MyVaadinApplication extends UI{
    public static VerticalLayout layout = new VerticalLayout();
    @Override
    public void init(VaadinRequest request) {
        //VerticalLayout layout = new VerticalLayout();

        setContent(layout);
        //layout.addComponent(new Label("Hello, world!"));
        life();
        //layout.addComponent(new VaadinLogin());
    }

    public void life(){
        //layout.removeAllComponents();
        layout.removeAllComponents();
        Controller controller = new Controller();
        ControllerLogin controllerLogin = new ControllerLogin();
        ControllerRegister controllerRegister = new ControllerRegister();
        layout.addComponent(controller);
        controller.loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                layout.removeAllComponents();
                System.out.println("wcisnąłeś przycisk logowania");
                layout.addComponent(controllerLogin);
            }
        });
        controller.registerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                layout.removeAllComponents();
                System.out.println("wcisnąłeś przycisk rejestracji");
                layout.addComponent(controllerRegister);
            }
        });
        controllerLogin.loginButtonInLogin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if(controllerLogin.loginField.getValue().isEmpty() || controllerLogin.passwordField.getValue().isEmpty()){
                    System.out.println("Należy wypełnić wszystkie pola");
                }
                else{
                    DbConnection dbConnection = new DbConnection();
                    dbConnection.getConnection();
                    String query = ("Select login, pass from users where login='"+controllerLogin.loginField.getValue()+"' and pass=sha('"+controllerLogin.passwordField.getValue()+"')");

                    try {
                        Statement statement = dbConnection.conn.createStatement();
                        statement.executeQuery(query);
                        ResultSet resultSet = statement.getResultSet();
                        if (resultSet.next()){
                            Cookie cookie = new Cookie("SESSIONID",""+getSession());
                            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
                            VaadinService.getCurrentResponse().addCookie(cookie);
                            getSession();//sukces
                            Notification.show("Sesja: "+getSession()+"");
                            layout.removeAllComponents();
                            System.out.println("wcisnąłeś przycisk logowania i wrociles do mainsite");
                            layout.addComponent(controller);
                        }
                        else{
                            Notification.show("Błędne dane logowania");

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }


            }
        });
        controllerRegister.registerButtonInRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if(controllerRegister.getLoginFieldRegister().isEmpty() || controllerRegister.passwordFieldRegister.isEmpty() || controllerRegister.repeatPasswordFieldRegister.isEmpty() || controllerRegister.emailFieldRegister.isEmpty()){
                    Notification.show("Niewypełnione wszystkie pola");
                }
                else if(!controllerRegister.passwordFieldRegister.getValue().equals(controllerRegister.repeatPasswordFieldRegister.getValue())){
                    Notification.show("Hasła muszą być takie same");
                }
                else{
                    DbConnection dbConnection = new DbConnection();
                    dbConnection.getConnection();
                    String query = ("insert into users (login, pass, mail) values ('"+controllerRegister.getLoginFieldRegister().getValue().toString()+"', SHA('"+controllerRegister.passwordFieldRegister.getValue().toString()+"'), '"+controllerRegister.emailFieldRegister.getValue().toString()+"')");

                    System.out.println(query+"");
                    try {

                        PreparedStatement preparedStatement = dbConnection.conn.prepareStatement(query);
                        preparedStatement.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        dbConnection.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    layout.removeAllComponents();
                    System.out.println("wcisnąłeś przycisk rejestracji i wrociłes do mainsite");

                    layout.addComponent(controller);
                }


            }
        });

    }
}

