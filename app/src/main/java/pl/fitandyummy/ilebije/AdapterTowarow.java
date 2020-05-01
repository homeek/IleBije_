package pl.fitandyummy.ilebije;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTowarow extends RecyclerView.Adapter<AdapterTowarow.TowarViewHolder> {

    public ArrayList<ElementyKalendarza> listaTowarow;

    static class TowarViewHolder extends RecyclerView.ViewHolder {

        public ImageView ikonaa;
        public TextView nazwaTowara;
        public TextView iloscTowara;
        public TextView strzalll;
        public TextView ktoryStrzl;
        public TextView dataa;
        public TextView godzinaa;

        public TowarViewHolder(View itemView) {
            super(itemView);

            ikonaa = itemView.findViewById(R.id.ikona);
            nazwaTowara = itemView.findViewById(R.id.nazwyCwiczen);
            iloscTowara = itemView.findViewById(R.id.godzinaStartu);
            strzalll = itemView.findViewById(R.id.RestWork);
            ktoryStrzl = itemView.findViewById(R.id.ktoryET);
            dataa = itemView.findViewById(R.id.data);
            godzinaa = itemView.findViewById(R.id.godzinaZakonczenia);
        }
    }

    public AdapterTowarow(ArrayList<ElementyKalendarza> lista) {
        listaTowarow = lista;
    }

    @Override
    public TowarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_elementy_kalendarza, parent, false);
        TowarViewHolder evh = new TowarViewHolder(V);
        return evh;
    }

    @Override
    public void onBindViewHolder(TowarViewHolder holder, int position) {
        ElementyKalendarza danyElement = listaTowarow.get(position);

        holder.ikonaa.setImageResource(danyElement.getIkonaa());
        holder.nazwaTowara.setText(danyElement.getNazwaTowara());
        holder.iloscTowara.setText(danyElement.getIloscTowara());
        holder.strzalll.setText(String.valueOf(danyElement.getStrzalll()));
        holder.ktoryStrzl.setText(String.valueOf(danyElement.getKtoryStrzl()));
        holder.dataa.setText(String.valueOf(danyElement.getDataa()));
        holder.godzinaa.setText(danyElement.getGodzinaa());
    }

    @Override
    public int getItemCount() {
        return listaTowarow.size();
    }
}