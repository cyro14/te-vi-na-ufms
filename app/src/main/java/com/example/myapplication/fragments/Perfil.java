package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.PostImageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends Fragment {

    private TextView nomeTv, toolbarNomeTv, statusTv, countSeguindoTv, countSeguidoresTv, countPostsTv;
    private CircleImageView imagemPerfil;
    private Button seguirBtn;
    private RecyclerView recyclerView;
    private LinearLayout countLayout;
    private FirebaseUser user;
    private String uid;

    private FirestoreRecyclerAdapter<PostImageModel, PostImageHolder> adapter;
    boolean ehMeuPerfil = true;


    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        loadPostImages();
        recyclerView.setAdapter(adapter);
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
        if(ehMeuPerfil){
            uid = user.getUid();
        }else{

        }
        DocumentReference reference = FirebaseFirestore.getInstance().collection("Images").document(uid);
        Query query = reference.collection("Images");

        FirestoreRecyclerOptions<PostImageModel> options =  new FirestoreRecyclerOptions.Builder<PostImageModel>()
                .setQuery(query,PostImageModel.class)
                .build();

            adapter = new FirestoreRecyclerAdapter<PostImageModel, PostImageHolder>(options) {
            @NonNull
            @Override
            public PostImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_image_items, parent, false);

                return new PostImageHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostImageHolder holder, int position, @NonNull PostImageModel model) {
                Glide.with(holder.itemView.getContext().getApplicationContext())
                        .load(model.getImageUrl())
                        .timeout(6500)
                        .into(holder.imageView);
            }
        };

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

    private static class PostImageHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public PostImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}