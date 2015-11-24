package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListUsuarios extends ArrayAdapter<String> {
    private String[] nombre;
    private String[] provincia;
    private Activity context;

    public CustomListUsuarios(Activity context, String[] nombre, String[] provincia) {
        super(context, R.layout.lista_formato2, nombre);
        this.context = context;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista_formato2, null, true);
        TextView textViewNombre = (TextView) listViewItem.findViewById(R.id.text1);
        TextView textViewProvincia = (TextView) listViewItem.findViewById(R.id.text2);

        textViewNombre.setText(nombre[position]);
        textViewProvincia.setText(provincia[position]);

        return listViewItem;
    }
}