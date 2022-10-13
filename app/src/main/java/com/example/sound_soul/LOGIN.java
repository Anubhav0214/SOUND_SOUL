package com.example.sound_soul;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LOGIN extends Fragment
{
    EditText Email;
    EditText password;
    Button btn;
    FirebaseAuth fire;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment_l_o_g_i_n, container, false);
        Email = v.findViewById(R.id.Mail);
        fire = FirebaseAuth.getInstance();
        password = v.findViewById(R.id.password);
        btn = v.findViewById(R.id.LoginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        return v;
    }
    private void login()
    {
        String MailId = Email.getText().toString();
        String pass = password.getText().toString();
        if (!MailId.isEmpty()) {
            if (!pass.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(MailId).matches()) {
                fire.signInWithEmailAndPassword(MailId,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent in = new Intent(getActivity(),homepage.class);
                        startActivity(in);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"LoginFailed",Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                password.setError("Empty Fields are not allowed");
            }
        } else if (!Patterns.EMAIL_ADDRESS.matcher(MailId).matches()) {
            Email.setError("Enter correct mail ID");

        } else {
            Email.setError("Empty fields are not allowed");
        }
    }
}
