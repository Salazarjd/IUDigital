package co.com.iudigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkValues = verifyInputs();
                if(checkValues){
                    return;
                }

                String em = email.getText().toString();
                String pass = password.getText().toString();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference(User.class.getSimpleName());

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot userDb : snapshot.getChildren()){
                            if(userDb.child("email").getValue().toString().equals(em) && userDb.child("password").getValue().toString().equals(pass)){
                                String doc = userDb.child("document").getValue().toString();
                                String nam = userDb.child("name").getValue().toString();
                                String lastNam = userDb.child("lastName").getValue().toString();
                                String em = userDb.child("email").getValue().toString();
                                String pass = userDb.child("password").getValue().toString();
                                User user = new User(doc, nam, lastNam, em, pass);
                                Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
//                                Toast.makeText(RegisterActivity.this, "Usuario Logueado", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        Toast.makeText(RegisterActivity.this, "Usuario y/o contraseña incorrectosº", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean verifyInputs() {

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
}