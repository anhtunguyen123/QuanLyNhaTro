    package com.example.quanlynhatro;

    import androidx.appcompat.app.AppCompatActivity;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import Database.DatabaseHelperLogin;

    public class Login extends AppCompatActivity {
        private EditText txtUser;
        private EditText txtPass;
        private Button btLogin;
        DatabaseHelperLogin dbHelper = new DatabaseHelperLogin(this);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            long result = dbHelper.addUser("admin", "123123");
            txtUser = findViewById(R.id.txtUser);
            txtPass = findViewById(R.id.txtPass);
            btLogin = findViewById(R.id.btLogin);

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Loginn();
                }
            });
        }

        private void Loginn() {
            String User = txtUser.getText().toString().trim();
            String Pass = txtPass.getText().toString().trim();
            if (User.isEmpty() || Pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.checkUser(User, Pass)) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
