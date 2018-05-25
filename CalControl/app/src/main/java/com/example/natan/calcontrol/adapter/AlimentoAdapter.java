package com.example.natan.calcontrol.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.natan.calcontrol.R;

public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.AlimentoAdapterViewHolder> {

    private String[] mAlimentoData;

    private final AlimentoAdapterOnClickListener mClickHandler;

    public AlimentoAdapter(AlimentoAdapterOnClickListener clickHandler) {
        this.mClickHandler = clickHandler;
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
        String[] splitData = mAlimentoData[position].split("-");
        holder.mAlimentoTextView.setText(splitData[0]);
        holder.mAlimentoCalTextView.setText(splitData[1]);
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

    public class AlimentoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mAlimentoTextView;
        public final TextView mAlimentoCalTextView;

        public AlimentoAdapterViewHolder(View itemView) {
            super(itemView);
            mAlimentoTextView = (TextView) itemView.findViewById(R.id.tv_alimento_data);
            mAlimentoCalTextView = (TextView) itemView.findViewById(R.id.tv_alimento_cal);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition= getAdapterPosition();
            String alimento = mAlimentoData[adapterPosition];
            mClickHandler.onClick(alimento);
        }
    }
}
