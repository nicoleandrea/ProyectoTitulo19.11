package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListHorarios extends ArrayAdapter<String> {
    private String[] hora;
    private Activity context;

    public CustomListHorarios(Activity context, String[] hora) {
        super(context, R.layout.lista_formato3, hora);
        this.context = context;
        this.hora = hora;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista_formato3, null, true);
        TextView textViewHora = (TextView) listViewItem.findViewById(R.id.text1);

        textViewHora.setText(hora[position]);

        return listViewItem;
    }
}