package com.example.country;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{

    private List<Country> CountryList;

    public interface mClickListener {
        public void mClick(View v, int position);
    }

    public CountryAdapter(List<Country> movieList) {
        this.CountryList = movieList;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new CountryViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(final CountryViewHolder holder, int position) {



        holder.linearLayout.setId(CountryList.get(position).getId());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context =v.getContext();
                if (CountryList.get(position).getCan_click()){

                    Intent intent_send = new Intent("message_subject_intent");
                    intent_send.putExtra("the_id" , position+"");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent_send);
                }else{
                    Toast.makeText(context, "cant Click here!",Toast.LENGTH_SHORT).show();
                }



            }
        });



        holder.textViewName.setText(CountryList.get(position).getName());
        holder.textViewNative.setText(CountryList.get(position).getNativeName());
        holder.textViewArea.setText(CountryList.get(position).getArea()+"");



    }




    @Override
    public int getItemCount() {
        return CountryList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewNative;
        public TextView textViewArea;
        public LinearLayout linearLayout;


        public CountryViewHolder(View view) {
            super(view);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewNative = (TextView) itemView.findViewById(R.id.textViewNative);
            this.textViewArea = (TextView) itemView.findViewById(R.id.textViewArea);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);



        }
    }
}

