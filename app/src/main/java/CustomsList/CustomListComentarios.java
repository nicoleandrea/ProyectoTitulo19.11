package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListComentarios extends ArrayAdapter<String> {
    private String[] nombre;
    private String[] texto;
    private String[] fecha;
    private String[] fechaHora;
    private Activity context;

    public CustomListComentarios(Activity context, String[] nombre, String[] texto, String[] fecha, String[] fechaHora) {
        super(context, R.layout.lista_formato, nombre);
        this.context = context;
        this.nombre = nombre;
        this.texto = texto;
        this.fecha = fecha;
        this.fechaHora = fechaHora;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista_formato, null, true);
        TextView textViewNombre = (TextView) listViewItem.findViewById(R.id.text1);
        TextView textViewTexto = (TextView) listViewItem.findViewById(R.id.text3);
        TextView textViewFecha = (TextView) listViewItem.findViewById(R.id.text2);
        TextView textViewFechaHora = (TextView) listViewItem.findViewById(R.id.text4);

        textViewNombre.setText(nombre[position]);
        textViewTexto.setText(texto[position]);
        textViewFecha.setText(fecha[position]);
        textViewFechaHora.setText(fechaHora[position]);

        return listViewItem;
    }
}