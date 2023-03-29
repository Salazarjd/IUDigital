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

public class MenuActivity extends AppCompatActivity {

    private EditText document;
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private Button btnUpdate;
    private Button btnDelete;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        document = findViewById(R.id.updateDocument);
        document.setEnabled(false);
        name = findViewById(R.id.updateName);
        lastName = findViewById(R.id.updateLastName);
        email = findViewById(R.id.updateEmail);
        email.setEnabled(false);
        password = findViewById(R.id.updatePassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        document.setText(user.getDocument());
        name.setText(user.getName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());

        updateUser();
    }

    public void updateUser(){

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

                        Intent intent = new Intent(MenuActivity.this, RegisterActivity.class);
                        startActivity(intent);

                        Toast.makeText(MenuActivity.this, "Usuario actualizado", Toast.LENGTH_SHORT).show();

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
}