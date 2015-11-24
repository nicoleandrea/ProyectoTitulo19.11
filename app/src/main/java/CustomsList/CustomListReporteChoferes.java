package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListReporteChoferes extends ArrayAdapter<String> {
    private String[] fecha_marcacion;
    private String[] nombre;
    private String[] rut;
    private String[] tiempo_trabajado;
    private String[] tiempo_descansado;
    private Activity context;

    public CustomListReporteChoferes(Activity context, String[] fecha_marcacion, String[] nombre, String[] rut, String[] tiempo_trabajado, String[] tiempo_descansado) {
        super(context, R.layout.lista_formato6, fecha_marcacion);
        this.context = context;
        this.fecha_marcacion = fecha_marcacion;
        this.nombre = nombre;
        this.rut = rut;
        this.tiempo_trabajado = tiempo_trabajado;
        this.tiempo_descansado = tiempo_descansado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato6, null, true);
        TextView textViewFecha_marcacion = (TextView) layout.findViewById(R.id.text5);
        TextView textViewNombre = (TextView) layout.findViewById(R.id.text1);
        TextView textViewRut = (TextView) layout.findViewById(R.id.text2);
        TextView textViewTiempo_trabajado = (TextView) layout.findViewById(R.id.text3);
        TextView textViewTiempo_descansado = (TextView) layout.findViewById(R.id.text4);

        textViewFecha_marcacion.setText(fecha_marcacion[position]);
        textViewNombre.setText(nombre[position]);
        textViewRut.setText(rut[position]);
        textViewTiempo_trabajado.setText(tiempo_trabajado[position]);
        textViewTiempo_descansado.setText(tiempo_descansado[position]);

        return layout;
    }
}