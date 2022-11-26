package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.HomeModel;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private List<HomeModel> list;
    Context context;

    public HomeAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.userNameTv.setText(list.get(position).getUserName());
        holder.timeTv.setText(list.get(position).getTimestamp());

        int count = list.get(position).getLikeCount();

        if(count==0){
            holder.likeCountTv.setVisibility(View.INVISIBLE);
        }
        else if(count==1){
            holder.likeCountTv.setText(count+" like");
        }
        else{
            holder.likeCountTv.setText(count+" likes");

        }



        Random random =  new Random();

        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_baseline_person_24)
                .timeout(6500)
                .into(holder.imagemPerfil);

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getPostImage())
                .placeholder(new ColorDrawable(color))
                .timeout(7000)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HomeHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagemPerfil;
        private TextView userNameTv, timeTv, likeCountTv;
        private ImageView imageView;
        private ImageButton likeBtn, commentBtn;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            imagemPerfil = itemView.findViewById(R.id.imagemPerfil);
            imageView = itemView.findViewById(R.id.imageView);
            userNameTv = itemView.findViewById(R.id.nomeTV);
            timeTv = itemView.findViewById(R.id.timeTV);
            likeCountTv = itemView.findViewById(R.id.likeCountTV);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);

        }
    }
}
