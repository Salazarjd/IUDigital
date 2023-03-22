package co.com.iudigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PrincipalActivity extends AppCompatActivity {

    private EditText document;
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        document = findViewById(R.id.txtDocument);
        name = findViewById(R.id.txtName);
        lastName = findViewById(R.id.txtLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {

            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Biblioteca", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();

            @Override
            public void onClick(View v) {
                Integer doc = Integer.parseInt(document.getText().toString());
                String na = name.getText().toString();
                String la = lastName.getText().toString();
                String em = email.getText().toString();
                String pw = password.getText().toString();

                ContentValues data = new ContentValues();
                data.put("document", doc);
                data.put("name", na);
                data.put("lastName", la);
                data.put("email", em);
                data.put("password", pw);

                db.insert("user", null, data);
                db.close();

                document.setText("");
                name.setText("");
                lastName.setText("");
                email.setText("");
                password.setText("");
            }
        });


    }
}