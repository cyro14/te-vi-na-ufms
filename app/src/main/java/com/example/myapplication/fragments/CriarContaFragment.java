package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FragmentReplacerActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CriarContaFragment extends Fragment {


    private EditText nomeEDT, emailEDT, senhaEDT, confirmaSenhaEDT;
    private ProgressBar barraDeProgresso;
    private TextView loginTV;
    private Button criarContaBtn;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    public CriarContaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_criar_conta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();
    }

    private void clickListener() {
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });

        criarContaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEDT.getText().toString();
                String email = emailEDT.getText().toString();
                String senha = senhaEDT.getText().toString();
                String confirmaSenha = confirmaSenhaEDT.getText().toString();

                if(nome.isEmpty() || nome.equals(" ")){
                    nomeEDT.setError("Insira um nome válido");
                    return;
                }

                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    nomeEDT.setError("Insira um e-mail válido");
                    return;
                }

                if(senha.isEmpty() || senha.length()<6){
                    nomeEDT.setError("Insira uma senha válida");
                    return;
                }

                if(!senha.equals(confirmaSenha)){
                    nomeEDT.setError("Senhas não correspondem");
                    return;
                }

                barraDeProgresso.setVisibility(View.VISIBLE);

                criarConta(nome, email, senha);
            }
        });{

        };
    }

    private void criarConta(String nome, String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Foi enviado um link de verificação no seu e-mail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    uploadUser(user, nome, email);
                }else{
                    barraDeProgresso.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Erro"+exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadUser(FirebaseUser user, String nome, String email) {
        Map<String, Object> map = new HashMap<>();

        map.put("nome", nome);
        map.put("email", email);
        map.put("fotoDePerfil", "");
        map.put("uid", user.getUid());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            assert getActivity() != null;
                            barraDeProgresso.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            getActivity().finish();
                        }else{
                            barraDeProgresso.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Erro: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void init(View view){
        nomeEDT = view.findViewById(R.id.nomeEDT);
        emailEDT = view.findViewById(R.id.emailEDT);
        senhaEDT = view.findViewById(R.id.senhaEDT);
        confirmaSenhaEDT = view.findViewById(R.id.confirmaSenhaEDT);
        loginTV = view.findViewById(R.id.loginTV);
        criarContaBtn = view.findViewById(R.id.criarContaBtn);
        barraDeProgresso = view.findViewById(R.id.barraDeProgresso);

        auth = FirebaseAuth.getInstance();

    }
}