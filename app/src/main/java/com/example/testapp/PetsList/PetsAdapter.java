package com.example.testapp.PetsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.CatsViewHolder;
import com.example.testapp.Model.Pet;
import com.example.testapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetsAdapter extends RecyclerView.Adapter<CatsViewHolder> {

    private ArrayList<Pet> catsList;
    private Context context;

    public PetsAdapter(Context context) {
        this.catsList = new ArrayList<>();
        this.context = context;
    }

    public void setCatsList(ArrayList<Pet> catsList) {
        this.catsList = catsList;
        notifyDataSetChanged();
    }

    @Override
    public CatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewHolderCat = inflater.inflate(R.layout.view_holder_pet, parent, false);
        return new CatsViewHolder(viewHolderCat);
    }

    @Override
    public void onBindViewHolder(CatsViewHolder catsViewHolder, final int position) {
        Pet pet = catsList.get(position);
        catsViewHolder.getTextViewTitle().setText(pet.getTitle());
        Picasso.with(context).load(pet.getUrl()).error(R.drawable.ic_launcher_background).into(catsViewHolder.getImageView());
    }


    @Override
    public int getItemCount() {
        return this.catsList.size();
    }

    public Pet getItem(int position) {
        return catsList.get(position);
    }
}