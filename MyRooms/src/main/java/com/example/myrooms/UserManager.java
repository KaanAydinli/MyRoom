package com.example.myrooms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserManager {


    public static String USER_FILE = "";

    public static void saveUser(User user){

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static User loadUser(){

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE))){

            User user = (User)in.readObject();
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
