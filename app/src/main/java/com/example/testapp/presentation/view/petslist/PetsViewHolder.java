package com.example.testapp.presentation.view.petslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.R;

public class PetsViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTitle;
    private ImageView imageView;

    public PetsViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        imageView = itemView.findViewById(R.id.image_view);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }
}
