package computomovil.fmat.lalo.integratingproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import computomovil.fmat.lalo.integratingproject.database.general.UserDataSource;
import computomovil.fmat.lalo.integratingproject.model.User;

public class MainActivity extends AppCompatActivity {

    UserDataSource userDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getPreferences(Context.MODE_PRIVATE).getString("username", "NA").equals("NA")) {
            startStudentListActivity();
        }
        userDataSource = new UserDataSource(getApplicationContext());
        try {
            //Crear base de datos
            userDataSource.open();
            userDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login(View view) {
        SharedPreferences.Editor ed = getPreferences(Context.MODE_PRIVATE).edit();
        String username = ((TextView) findViewById(R.id.txtUser)).getText().toString();
        String password = ((TextView) findViewById(R.id.txtPassword)).getText().toString();


        User newUser = userDataSource.getUserByUsername(username);
        if (!newUser.getUsername().equals("empty") && password.equals(newUser.getPassword())) {
            ed.putString("username", newUser.getUsername());
            ed.putString("password", newUser.getPassword());
            ed.apply();
            startStudentListActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña inválidos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startStudentListActivity(){
        Intent intent = new Intent(getApplicationContext(), StudentList.class);
        startActivity(intent);
    }

    public void registerNewStudent(View view) {
        Intent intent = new Intent(getApplicationContext(), FormUser.class);
        startActivity(intent);
    }
}
