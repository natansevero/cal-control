package com.example.natan.calcontrol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.AlimentoAdapterViewHolder> {

    private String[] mAlimentoData;

    public AlimentoAdapter() {

    }

    @NonNull
    @Override
    public AlimentoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int alimentoDiaListItem = R.layout.alimento_dia_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(alimentoDiaListItem, parent, false);
        AlimentoAdapterViewHolder viewHolder = new AlimentoAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentoAdapterViewHolder holder, int position) {
        holder.mAlimentoTextView.setText(mAlimentoData[position]);
    }

    public void setmAlimentoData(String[] alimentoData) {
        this.mAlimentoData = alimentoData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAlimentoData == null) return 0;
        return mAlimentoData.length;
    }

    public class AlimentoAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mAlimentoTextView;

        public AlimentoAdapterViewHolder(View itemView) {
            super(itemView);
            mAlimentoTextView = (TextView) itemView.findViewById(R.id.tv_alimento_data);
        }
    }
}
