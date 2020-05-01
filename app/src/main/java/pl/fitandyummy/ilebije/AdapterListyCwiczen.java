package pl.fitandyummy.ilebije;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListyCwiczen extends RecyclerView.Adapter<AdapterListyCwiczen.ETViewHolder> {

    public ArrayList<ElementyListyCwiczen> listaCW;

    public static class ETViewHolder extends RecyclerView.ViewHolder {

        public TextView nazwaCwiczenia;
        public TextView czasWork;
        public TextView czasRest;
        public TextView godzina;
        public TextView data;

        public ETViewHolder(View itemView) {
            super(itemView);

            nazwaCwiczenia = itemView.findViewById(R.id.nazwaCwiczenia);
            czasWork = itemView.findViewById(R.id.nazwyCwiczen);
            czasRest = itemView.findViewById(R.id.godzinaStartu);
            godzina = itemView.findViewById(R.id.godzinaZakonczenia);
            data = itemView.findViewById(R.id.data);
        }
    }

    public AdapterListyCwiczen(ArrayList<ElementyListyCwiczen> lista) {
        listaCW = lista;
    }

    @Override
    public ETViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_elementy_listy_cwiczen, parent, false);
        ETViewHolder evh = new ETViewHolder(V);
        return evh;
    }

    @Override
    public void onBindViewHolder(ETViewHolder holder, int position) {
        ElementyListyCwiczen danyElement = listaCW.get(position);

        holder.nazwaCwiczenia.setText(danyElement.getNazwaCwiczeniaa());
        holder.czasWork.setText(String.valueOf(danyElement.getCzasWorkk()));
        holder.czasRest.setText(String.valueOf(danyElement.getCzasRestt()));
        holder.data.setText(danyElement.getDataa());
        holder.godzina.setText(danyElement.getGodzinaa());
    }

    @Override
    public int getItemCount() {
        return listaCW.size();
    }
}
