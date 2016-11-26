package com.abjlab.swipelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joseba on 25/11/2016.
 */

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> implements View.OnClickListener{

    private ArrayList<Beer> data;
    private View.OnClickListener listener;

    public BeerAdapter(ArrayList<Beer> data) {
        this.data = data;
    }

    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_item, parent, false);
        itemView.setOnClickListener(this);
        BeerViewHolder viewHolder = new BeerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = data.get(position);
        holder.bindBeer(beer);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void onClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void removeItem(int pos){
        data.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, data.size());
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder{

        private TextView lblTitle;
        private TextView lblDesc;
        private ImageView iBeer;
        private View mainView;

        public BeerViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            iBeer = (ImageView) itemView.findViewById(R.id.imageBeer);
            lblTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            lblDesc = (TextView) itemView.findViewById(R.id.itemDesc);

        }

        public void bindBeer(Beer beer){
            iBeer.setImageDrawable(mainView.getContext().getResources().getDrawable(beer.getBeerIcon()));
            lblTitle.setText(beer.getName());
            lblDesc.setText(beer.getDescription());

        }
    }
}
