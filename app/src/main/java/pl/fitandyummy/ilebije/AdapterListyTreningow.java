package pl.fitandyummy.ilebije;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListyTreningow extends RecyclerView.Adapter<AdapterListyTreningow.ETViewHolderTR> {

    public ArrayList<ElementyListyTreningow> listaLTR;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ETViewHolderTR extends RecyclerView.ViewHolder {



        public TextView komentarzTreningu;
        public TextView nazwyCwiczen;
        public TextView godzinaStartu;
        public TextView godzinaZakonczenia;
        public TextView data;



        public ETViewHolderTR(View itemView) {
            super(itemView);


            komentarzTreningu = itemView.findViewById(R.id.komentarzTreningu);
            nazwyCwiczen = itemView.findViewById(R.id.nazwyCwiczen);
            godzinaStartu = itemView.findViewById(R.id.godzinaStartu);
            godzinaZakonczenia = itemView.findViewById(R.id.godzinaZakonczenia);
            data = itemView.findViewById(R.id.data);



        }

    }

    public AdapterListyTreningow(ArrayList<ElementyListyTreningow> lista){

        listaLTR = lista;

    }

    @Override
    public ETViewHolderTR onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_elementy_listy_treningow,parent,false);
        ETViewHolderTR evh = new ETViewHolderTR(V);
        return evh;

    }


    @Override
    public void onBindViewHolder(ETViewHolderTR holder, int position) {



       // ElementyListyTreningow danyElementListyTreningow = listaLTR.get(position);
        ElementyListyTreningow danyElementListyTreningow = listaLTR.get(position);




        holder.komentarzTreningu.setText(danyElementListyTreningow.getKomentarzCwiczenn());
        holder.nazwyCwiczen.setText(danyElementListyTreningow.getNazwaCwiczenienn());
        holder.godzinaStartu.setText(danyElementListyTreningow.getGodzinaStartuu());
        holder.godzinaZakonczenia.setText( danyElementListyTreningow.getGodzinaZakonczeniaa());
        holder.data.setText(danyElementListyTreningow.getDataaa());


    }




    @Override
    public int getItemCount() {
        return listaLTR.size();
    }

    public void removeItem (int position){

        listaLTR.remove(position);
        notifyItemRemoved(position);
    }







  /*  @Override
    public int getItemCount() {

        return 0;
    }*/


    /* @Override
    public int getItemCount(){
        return listaLTR.size();

    }*/



   /* @Override
    public int getItemCount() {
        return listaLTR.size();

       // return listaLTR.size();

    }*/

  /*  @Override
    public int getItemCount() {
        return listaETWork.size() ;
    }*/
}
