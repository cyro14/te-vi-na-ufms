package com.example.myapplication.fragments;

import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends Fragment {

    private TextView nomeTv, toolbarNomeTv, statusTv, countSeguindoTv, countSeguidoresTv, countPostsTv;
    private CircleImageView imagemPerfil;
    private Button seguirBtn;
    private RecyclerView recyclerView;
    private LinearLayout countLayout;
    private FirebaseUser user;
    private StorageReference storageReference;
    boolean ehMeuPerfil = true;


    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init(view);

        if(ehMeuPerfil){
            seguirBtn.setVisibility(View.GONE);
            countLayout.setVisibility(View.VISIBLE);
        }else{
            seguirBtn.setVisibility(View.VISIBLE);
            countLayout.setVisibility(View.GONE);
        }

        loadBasicData();
        imagens();
    }

    private void loadBasicData() {
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users")
                .document(user.getUid());

        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    return;
                if(value.exists()){
                    String nome = value.getString("nome");
                    String status = value.getString("status");
                    int seguidores = value.getLong("seguidores").intValue();
                    int seguindo = value.getLong("seguindo").intValue();

                    String profileURL = value.getString("imagemPerfil");

                    nomeTv.setText(nome);
                    toolbarNomeTv.setText(nome);
                    statusTv.setText(status);
                    countSeguidoresTv.setText(String.valueOf(seguidores));
                    countSeguindoTv.setText(String.valueOf(seguindo));

                    Glide.with(getContext().getApplicationContext())
                            .load(profileURL)
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .timeout(6500)
                            .into(imagemPerfil);

                }
            }
        });
    }

    private void loadPostImages(){

    }

    private void init(View view) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        assert getActivity() != null;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        nomeTv = view.findViewById(R.id.nomeTV);
        statusTv = view.findViewById(R.id.statusTv);
        toolbarNomeTv = view.findViewById(R.id.toolbarNameTv);
        countSeguidoresTv = view.findViewById(R.id.countSeguidoresTV);
        countSeguindoTv = view.findViewById(R.id.countSeguindoTV);
        countPostsTv = view.findViewById(R.id.countPostsTV);
        imagemPerfil = view.findViewById(R.id.imagemPerfil);
        seguirBtn = view.findViewById(R.id.seguirBtn);
        recyclerView = view.findViewById(R.id.recyclerView);
        countLayout = view.findViewById(R.id.countLayout);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    private void imagens(){
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child(user.getUid()+"/images");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            Toast.makeText(getContext(), prefix.getName(), Toast.LENGTH_SHORT).show();
                        }

                        for (StorageReference item : listResult.getItems()) {
                            Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}