package com.ilmale.doodlejump.domain;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.ilmale.doodlejump.RegisterActivity;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;

import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LoginUser {

    private static final LoginUser ourInstance = new LoginUser();

    public static LoginUser getInstance() {
        return ourInstance;
    }

    private LoginUser() {}

    Context context;
    public static OurDatabase db;

    private String username = null;
    private String password = null;
    private String email = null;
    private int money = 0;
    private int punteggio = 0;
    private double lat = 0.0;
    private double longi = 0.0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney(){return money;}

    public void setMoney(int money){this.money=money;}

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }


    public void initializeLoginUser(Context context) {
        this.context=context;
        db = Room.databaseBuilder(context, OurDatabase.class,"userdb").allowMainThreadQueries().build();
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", MODE_PRIVATE);
        String email = pref.getString("email", null);
        if(email!=null){
            setLoginUser(findUserByEmail(email));
        }

    }

    public void login() {
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putString("email", email);
        editorPref.commit();
    }

    public void logout() {
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putString("email", null);
        editorPref.commit();
        resetLoginUser();
    }

    public void setLoginUser(User us) {
        setEmail(us.getEmail());
        setUsername(us.getUsername());
        setPassword(us.getPassword());
        setMoney(us.getMoney());
        setPunteggio(us.getPunteggio());
        setLat(us.getLat());
        setLongi(us.getLongi());
        login();
    }

    public void resetLoginUser() {
        setEmail(null);
        setUsername(null);
        setPassword(null);
        setMoney(0);
        setPunteggio(0);
        setLat(0.0);
        setLongi(0.0);
    }


    public User findUserByEmail(String email) {
        List<User> users= db.ourDao().getUsers();
        for(User us:users){
            if(email.equalsIgnoreCase(us.getEmail())){
                return us;
            }
        }
        return null;
    }
}
