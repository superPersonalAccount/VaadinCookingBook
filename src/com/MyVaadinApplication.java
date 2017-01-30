package com;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                layout.removeAllComponents();
                System.out.println("wcisnąłeś przycisk logowania i wrociles do mainsite");
                layout.addComponent(controller);
            }
        });
        controllerRegister.registerButtonInRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if(controllerRegister.getLoginFieldRegister().isEmpty() || controllerRegister.passwordFieldRegister.isEmpty() || controllerRegister.repeatPasswordFieldRegister.isEmpty() || controllerRegister.emailFieldRegister.isEmpty()){
                }
                else if(!controllerRegister.passwordFieldRegister.getValue().equals(controllerRegister.repeatPasswordFieldRegister)){
                }
                else{
                    DbConnection dbConnection = new DbConnection();
                    dbConnection.getConnection();
                    String query = ("insert into users (login, pass, mail) values ('"+controllerRegister.getLoginFieldRegister().getValue().toString()+"', '"+controllerRegister.passwordFieldRegister.getValue().toString()+"', '"+controllerRegister.emailFieldRegister.getValue().toString()+"')");

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

