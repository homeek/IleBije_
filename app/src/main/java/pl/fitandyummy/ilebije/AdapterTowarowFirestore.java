package pl.fitandyummy.ilebije;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterTowarowFirestore extends FirestoreRecyclerAdapter<ElementyKalendarzaFS, AdapterTowarowFirestore.TowarFirestoreViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterTowarowFirestore(@NonNull FirestoreRecyclerOptions<ElementyKalendarzaFS> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TowarFirestoreViewHolder holder, int position, @NonNull ElementyKalendarzaFS model) {
        holder.nazwaTowara.setText(model.getNazwaTowara());
        holder.iloscTowara.setText(model.getIloscTowara());
        holder.strzalll.setText(model.getStrzalll());
        holder.ktoryStrzl.setText(String.valueOf(model.getKtoryStrzl()));
        holder.dataa.setText(model.getDataa());
        holder.godzinaa.setText(model.getGodzinaa());
    }

    @NonNull
    @Override
    public TowarFirestoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_elementy_kalendarza_fs, parent, false);
        return new TowarFirestoreViewHolder(V);
    }

    class TowarFirestoreViewHolder extends RecyclerView.ViewHolder {
        public TextView nazwaTowara;
        public TextView iloscTowara;
        public TextView strzalll;
        public TextView ktoryStrzl;
        public TextView dataa;
        public TextView godzinaa;

        public TowarFirestoreViewHolder(View itemView) {
            super(itemView);
            nazwaTowara = itemView.findViewById(R.id.nazwyCwiczen);
            iloscTowara = itemView.findViewById(R.id.godzinaStartu);
            strzalll = itemView.findViewById(R.id.RestWork);
            ktoryStrzl = itemView.findViewById(R.id.ktoryET);
            dataa = itemView.findViewById(R.id.data);
            godzinaa = itemView.findViewById(R.id.godzinaZakonczenia);
        }
    }
}