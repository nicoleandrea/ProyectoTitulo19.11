package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListReporteVehiculos extends ArrayAdapter<String> {
    private String[] fecha;
    private String[] nombre;
    private String[] kilometraje;
    //private String[] velocidad_maxima;
   // private String[] velocidad_minima;
    //private String[] velocidad_promedio;
    private Activity context;

    public CustomListReporteVehiculos(Activity context, String[] fecha, String[] nombre, String[] kilometraje/*, String[] velocidad_maxima, String[] velocidad_minima, String[] velocidad_promedio*/) {
        super(context, R.layout.lista_formato7, fecha);
        this.context = context;
        this.fecha = fecha;
        this.nombre = nombre;
        this.kilometraje = kilometraje;
        /*this.velocidad_maxima = velocidad_maxima;
        this.velocidad_minima = velocidad_minima;
        this.velocidad_promedio = velocidad_promedio;*/
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato7, null, true);
        TextView textViewFecha = (TextView) layout.findViewById(R.id.text2);
        TextView textViewNombre = (TextView) layout.findViewById(R.id.text1);
        TextView textViewKilometraje = (TextView) layout.findViewById(R.id.text3);
        /*TextView textViewVelocidad_maxima = (TextView) layout.findViewById(R.id.text4);
        TextView textViewVelocidad_minima = (TextView) layout.findViewById(R.id.text5);
        TextView textViewVelocidad_promedio = (TextView) layout.findViewById(R.id.text6);*/

        textViewFecha.setText(fecha[position]);
        textViewNombre.setText(nombre[position]);
        textViewKilometraje.setText(kilometraje[position]);
        /*textViewVelocidad_maxima.setText(velocidad_maxima[position]);
        textViewVelocidad_minima.setText(velocidad_minima[position]);
        textViewVelocidad_promedio.setText(velocidad_promedio[position]);*/

        return layout;
    }
}