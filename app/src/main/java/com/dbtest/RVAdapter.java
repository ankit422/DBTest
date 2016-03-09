package com.dbtest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ankit on 09/03/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    List<Words> words;
    Context context;

    RVAdapter(List<Words> words, Context context) {
        this.context = context;
        this.words = words;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.word);
            personAge = (TextView) itemView.findViewById(R.id.meaning);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText("Word : " + words.get(i).getWord());
        personViewHolder.personAge.setText("Meaning : " + words.get(i).getMeaning());
        Picasso.with(context).load("http://www.appsculture.com/vocab/images/" + words.get(i).getId() + ".png")
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .noFade().resize(150, 150).centerCrop()
                .into(personViewHolder.personPhoto);

    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


}
