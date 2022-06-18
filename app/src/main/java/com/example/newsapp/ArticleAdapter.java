package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<ArticleModel> localDataSet;

    public static OnItemClickListener itemClickListener;


    public ArticleAdapter(List<ArticleModel> localDataSet, OnItemClickListener onItemClickListener) {
        this.localDataSet = localDataSet;
        itemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder viewHolder, final int position) {
        viewHolder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null)
            return 0;
        return localDataSet.size();
    }


    public void filterList(ArrayList<ArticleModel> filterList) {
        localDataSet = filterList;
        notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView source;
        private final TextView title;
        private final TextView description;

        private final ConstraintLayout layout;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            source = itemView.findViewById(R.id.source);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            layout = itemView.findViewById(R.id.container);
        }

        public void bind(ArticleModel item) {
            source.setText(item.getSource().getName());
            title.setText(item.getTitle());
            description.setText(item.getDescription());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
        }

    }
}