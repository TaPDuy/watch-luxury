package nhom9.watchluxury;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Login extends AppCompatActivity {
    private EditText accountEdit , passEdit ;
    private Button btnlogin , btnregister ;
    private FirebaseAuth mAuth ;



    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        accountEdit = findViewById(R.id.inAccount);
        passEdit = findViewById(R.id.inPassLogin);


        btnlogin =  (Button) findViewById(R.id.btnLogin);
        btnregister = (Button)findViewById(R.id.btnRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }



    private void login() {
        String account , pass ;
        account = accountEdit.getText().toString();
        pass = passEdit.getText().toString();
        if(TextUtils.isEmpty(account)){
            Toast.makeText(this, "Please! fill account ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please! fill password to login", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(account, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override



                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Welcome to login successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this , HomePage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Sorry! Login unsuccessful", Toast.LENGTH_SHORT).show();

                    }
                }



        });
    }
}