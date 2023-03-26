package co.com.iudigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
                        User user = new User(id, nam, la, em, pw);
                        dbref.push().setValue(user);
                        Toast.makeText(PrincipalActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        resetValues();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public boolean verifyInputs() {
        boolean result = false;

        if(document.getText().toString().isEmpty()){
            document.setError("Campo obligatorio");
            result = true;
        }
        if(name.getText().toString().isEmpty()){
            name.setError("Campo obligatorio");
            result = true;
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("Campo obligatorio");
            result = true;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Campo obligatorio");
            result = true;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Campo obligatorio");
            result = true;
        }

        return result;
    }

    public void resetValues() {
        document.setText("");
        name.setText("");
        lastName.setText("");
        email.setText("");
        password.setText("");
    }
}