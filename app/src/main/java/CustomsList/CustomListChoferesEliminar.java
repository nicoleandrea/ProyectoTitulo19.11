package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListChoferesEliminar extends ArrayAdapter<String> {
    private String[] nombre;
    private Activity context;

    public CustomListChoferesEliminar(Activity context, String[] nombre) {
        super(context, R.layout.lista_formato8, nombre);
        this.context = context;
        this.nombre = nombre;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato8, null, true);
        TextView textViewNombre = (TextView) layout.findViewById(R.id.text1);

        textViewNombre.setText(nombre[position]);

        return layout;
    }
}