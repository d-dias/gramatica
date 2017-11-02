package com.example.dilkidias.gramatica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
* Created by DILKI DIAS on 21-Jul-17.
*/

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.viewHolder>{

    private ClickListener clickListener = null;

    @Override
    public DetailsAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_list_items, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DetailsAdapter.viewHolder holder, final int position) {
//        holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.txt1.setText(horizontalList.get(position).txt1);
        holder.txt2.setText(horizontalList.get(position).txt2);
        holder.txt3.setText(horizontalList.get(position).txt3);
        holder.txt4.setText(horizontalList.get(position).txt4);
        holder.txt5.setText(horizontalList.get(position).txt5);
        holder.txt6.setText(horizontalList.get(position).txt6);

        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.ItemClicked(v, position);
                }
            }
        });
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface ClickListener {
        public void ItemClicked(View v, int position);
    }

    @Override
    public int getItemCount() {
        if (horizontalList != null){
            return horizontalList.size();
        }else {
            return 0;
        }

    }

    List<DetailsData> horizontalList = Collections.emptyList();
    Context context;

    public DetailsAdapter(List<DetailsData> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;

        public viewHolder(View view) {
            super(view);
            txt1 = (TextView) view.findViewById(R.id.text1);
            txt2 = (TextView) view.findViewById(R.id.text2);
            txt3 = (TextView) view.findViewById(R.id.text3);
            txt4 = (TextView) view.findViewById(R.id.text4);
            txt5 = (TextView) view.findViewById(R.id.text5);
            txt6 = (TextView) view.findViewById(R.id.text6);
        }
    }

}
