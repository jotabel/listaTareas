package com.example.listatarea_03_02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.MiVistaHolder> implements View.OnClickListener{

    private ArrayList<ListaItem> miLista;
    private final int [] colores={R.drawable.ic_green, R.drawable.ic_yellow, R.drawable.ic_red};//añadir aqui las imagenes
    private View.OnClickListener listener;

    public ListaAdapter(ArrayList<ListaItem>al){
        miLista=al;
    }

    public class MiVistaHolder extends RecyclerView.ViewHolder {

        TextView tvNombre,tvLugar;
        ImageView iv;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvLugar=itemView.findViewById(R.id.tvLugar);
            iv=itemView.findViewById(R.id.ivImpor);
        }
    }

    @NonNull
    @Override
    public ListaAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.lista_layout,parent,false);
        v.setOnClickListener(this);

        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAdapter.MiVistaHolder holder, int position) {

        holder.tvNombre.setText(miLista.get(position).getNombre());
        holder.tvLugar.setText(miLista.get(position).getLugar());

        int imp=miLista.get(position).getImportancia();
        switch(imp){
            case 1:
                holder.iv.setImageResource(colores[0]);
                break;
            case 2:
                holder.iv.setImageResource(colores[1]);
                break;
            case 3:
                holder.iv.setImageResource(colores[2]);
        }

    }

    public void setOnClickListener(View.OnClickListener lis){
        listener=lis;
    }

    public void onClick(View v ){
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public int getItemCount() {
        return miLista.size();
    }

}
