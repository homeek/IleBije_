package pl.fitandyummy.ilebije;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterET extends RecyclerView.Adapter<AdapterET.ETViewHolder> {

    public ArrayList<ElementyTreningu> listaET;

    public static class ETViewHolder extends RecyclerView.ViewHolder {



        public TextView restWork;
        public TextView ktoryET;
        public TextView czas;



        public ETViewHolder(View itemView) {
            super(itemView);


            restWork = itemView.findViewById(R.id.RestWork);
            ktoryET = itemView.findViewById(R.id.ktoryET);
            czas = itemView.findViewById(R.id.data);



        }

    }

    public AdapterET(ArrayList<ElementyTreningu> lista){
        listaET = lista;

    }

    @Override
    public ETViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_elementy_treningu,parent,false);
        ETViewHolder evh = new ETViewHolder(V);
        return evh;

    }


    @Override
    public void onBindViewHolder(ETViewHolder holder, int position) {



        ElementyTreningu danyElement = listaET.get(position);



        holder.restWork.setText(danyElement.getElementt());
        holder.ktoryET.setText(String.valueOf (danyElement.getNumerr()));
        holder.czas.setText( danyElement.getCzass());



    }

    @Override
    public int getItemCount() {
        return listaET.size();

    }

  /*  @Override
    public int getItemCount() {
        return listaETWork.size() ;
    }*/
}
