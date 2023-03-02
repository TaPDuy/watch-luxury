package nhom9.watchluxury;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText emailEdit , passEdit ;
//    nameEdit , rePassEdit ;
    private Button btnRegister;
    private FirebaseAuth mAuth;




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.inName);
        passEdit = findViewById(R.id.inEmailRes);
//        nameEdit = findViewById(R.id.inPassRes);
//        rePassEdit = findViewById(R.id.inRePass);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }
    private void register(){
        String email , pass ;
        email = emailEdit.getText().toString();
        pass = passEdit.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please! fill email ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please! fill password ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Create account successfull",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(Register.this, "Not success to create an account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}