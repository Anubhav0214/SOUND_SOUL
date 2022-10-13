package com.example.sound_soul;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SIGNUP extends Fragment
{
    private FirebaseAuth fire;
    EditText mail;
    EditText pass;
    EditText pass1;
    Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fire.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_s_i_g_n_u_p, container, false);
        fire = FirebaseAuth.getInstance();
        mail = v.findViewById(R.id.Mail);
        pass = v.findViewById(R.id.sigpass1);
        btn = v.findViewById(R.id.SignButton);
        pass1 = v.findViewById(R.id.sigpass2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        return v;
    }
    private void createUser()
    {
        String MailId = mail.getText().toString();
        String password = pass.getText().toString();
        String password2 = pass1.getText().toString();
        if(password.equals(password2)) {
            if (!MailId.isEmpty()) {
                if (!password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(MailId).matches()) {
                    fire.createUserWithEmailAndPassword(MailId, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Signup failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    pass.setError("Empty Fields are not allowed");
                }
            } else if (!Patterns.EMAIL_ADDRESS.matcher(MailId).matches()) {
                mail.setError("Enter correct mail ID");

            } else {
                mail.setError("Empty fields are not allowed");
            }
        }
        else
        {
            pass.setError("Password does not match");
        }
    }
}