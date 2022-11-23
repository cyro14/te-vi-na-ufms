package com.example.myapplication.fragments;

import static com.example.myapplication.fragments.CriarContaFragment.EMAIL_REGEX;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private EditText emailEDT, senhaEDT;
    private TextView cadastrarTV, esqueceuSenhaTV;
    private Button loginBtn;
            //googleLoginBtn;
    private ProgressBar barraDeProgresso;
    GoogleSignInClient  mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth auth;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);




        clickListener();
    }

    private void clickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEDT.getText().toString();
                String senha = senhaEDT.getText().toString();

                if (email.isEmpty()||!email.matches(EMAIL_REGEX)){
                    emailEDT.setError("Insira um e-mail válido");
                    return;
                }
                if(senha.isEmpty()|| senha.length()<6){
                    senhaEDT.setError("Insira uma senha válida com mais de 6 dígitos");
                    return;
                }
                barraDeProgresso.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    if(!user.isEmailVerified()){
                                        Toast.makeText(getContext(), "Verifique seu e-mail", Toast.LENGTH_SHORT).show();
                                    }

                                    sendUserToMainActivity();
                                }else {
                                    String exception = "Erro: "+task.getException().getMessage();
                                    Toast.makeText(getContext(), exception, Toast.LENGTH_SHORT).show();
                                    barraDeProgresso.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
/*
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

        });*/
    }

    private void sendUserToMainActivity() {

        if(getActivity()==null){
            return;
        }

        barraDeProgresso.setVisibility(View.GONE);
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }

    private void init(View view) {

        emailEDT =  view.findViewById(R.id.emailEDT);
        senhaEDT =  view.findViewById(R.id.senhaEDT);
        loginBtn =  view.findViewById(R.id.loginBtn);
        //googleLoginBtn =  view.findViewById(R.id.googleLoginBtn);
        cadastrarTV =  view.findViewById(R.id.cadastroTV);
        esqueceuSenhaTV =  view.findViewById(R.id.esqueceuSenhaTV);
        barraDeProgresso =  view.findViewById(R.id.barraDeProgresso);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    /*private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                fireBaseAuthWithGoogle(account.getIdToken());
            }catch(ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void fireBaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w("TAG", "signInWithCredential: failure", task.getException());
                        }
                    }
                });
    }
*/


    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        Map<String, Object> map = new HashMap<>();

        map.put("nome", account.getDisplayName());
        map.put("email", account.getEmail());
        map.put("fotoDePerfil", String.valueOf(account.getPhotoUrl()));
        map.put("uid", user.getUid());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            assert getActivity() != null;
                            barraDeProgresso.setVisibility(View.GONE);
                            sendUserToMainActivity();
                        }else{
                            barraDeProgresso.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Erro: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

