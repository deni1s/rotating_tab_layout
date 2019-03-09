package com.example.testapp.presentation.view.petdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.presentation.utils.BaseFragment;
import com.example.testapp.model.Pet;
import com.example.testapp.R;
import com.squareup.picasso.Picasso;

public class PetDetailsFragment extends BaseFragment {

    private static final String TITLE_PET = "petdetail";

    public static PetDetailsFragment newInstance(Pet pet) {
        PetDetailsFragment fragment = new PetDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(TITLE_PET, pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_pet_details, container, false);
        prepareViews(rootView);
        return rootView;
    }

    private void prepareViews(View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TITLE_PET)) {
            Pet pet = bundle.getParcelable(TITLE_PET);
            if (pet != null) {
                setTitle(pet.getTitle());
                TextView textViewTitle = rootView.findViewById(R.id.text_view_title);
                textViewTitle.setText(pet.getTitle());
                ImageView imageView = rootView.findViewById(R.id.image_view);
                Picasso.with(getContext()).load(pet.getUrl()).error(R.drawable.ic_launcher_background).into(imageView);
            }
        }
    }
}
