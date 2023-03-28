package co.com.iudigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends AppCompatActivity {

    private EditText document;
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        document = findViewById(R.id.txtDocument);
        name = findViewById(R.id.txtName);
        lastName = findViewById(R.id.txtLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        registerUser();

//        register.setOnClickListener(new View.OnClickListener() {
//
//            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Biblioteca", null, 1);
//            SQLiteDatabase db = admin.getWritableDatabase();
//
//            @Override
//            public void onClick(View v) {
//                Integer doc = Integer.parseInt(document.getText().toString());
//                String na = name.getText().toString();
//                String la = lastName.getText().toString();
//                String em = email.getText().toString();
//                String pw = password.getText().toString();
//
//                ContentValues data = new ContentValues();
//                data.put("document", doc);
//                data.put("name", na);
//                data.put("lastName", la);
//                data.put("email", em);
//                data.put("password", pw);
//
//                db.insert("user", null, data);
//                db.close();
//
//                document.setText("");
//                name.setText("");
//                lastName.setText("");
//                email.setText("");
//                password.setText("");
//            }
//        });


    }

    public void registerUser(){
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                boolean checkValues = verifyInputs();
                if(checkValues){
                    return;
                }

                String id = document.getText().toString();
                String nam = name.getText().toString();
                String la = lastName.getText().toString();
                String em = email.getText().toString();
                String pw = password.getText().toString();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference(User.class.getSimpleName());

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot userDb : snapshot.getChildren()){
                            if(userDb.child("document").getValue().toString().equals(id)){
                                document.setError("El documento ya ha sido registrado");
                                document.requestFocus();
                                return;
                            }
                            if(userDb.child("email").getValue().toString().equals(em)){
                                email.setError("El correo ya ha sido registrado");
                                email.requestFocus();
                                return;
                            }
                        }

                        User user = new User(id, nam, la, em, pw);
                        dbref.push().setValue(user);
                        resetValues();

                        Toast.makeText(PrincipalActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public boolean verifyInputs() {

        if(document.getText().toString().isEmpty()){
            document.setError("Campo obligatorio");
            document.requestFocus();
            return true;
        }
        if(name.getText().toString().isEmpty()){
            name.setError("Campo obligatorio");
            name.requestFocus();
            return true;
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("Campo obligatorio");
            lastName.requestFocus();
            return true;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Campo obligatorio");
            email.requestFocus();
            return true;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Campo obligatorio");
            password.requestFocus();
            return true;
        }

        return false;
    }

    public void resetValues() {
        document.setText("");
        name.setText("");
        lastName.setText("");
        email.setText("");
        password.setText("");
    }

}