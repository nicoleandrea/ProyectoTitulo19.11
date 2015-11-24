package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListChoferes extends ArrayAdapter<String> {
    private String[] nombre;
    private String[] rut;
    private String[] fecha_nacimiento;
    private String[] direccion;
    private String[] telefono;
    private String[] licencia;
    private Activity context;

    public CustomListChoferes(Activity context, String[] nombre, String[] rut, String[] fecha_nacimiento, String[] direccion, String[] telefono, String[] licencia) {
        super(context, R.layout.lista_formato4, nombre);
        this.context = context;
        this.nombre = nombre;
        this.rut = rut;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.licencia = licencia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato4, null, true);
        TextView textViewNombre = (TextView) layout.findViewById(R.id.text1);
        TextView textViewRut = (TextView) layout.findViewById(R.id.text2);
        TextView textViewFechaNacimiento = (TextView) layout.findViewById(R.id.text3);
        TextView textViewDireccion = (TextView) layout.findViewById(R.id.text4);
        TextView textViewTelefono = (TextView) layout.findViewById(R.id.text5);
        TextView textViewLicencia = (TextView) layout.findViewById(R.id.text6);

        textViewNombre.setText(nombre[position]);
        textViewRut.setText(rut[position]);
        textViewFechaNacimiento.setText(fecha_nacimiento[position]);
        textViewDireccion.setText(direccion[position]);
        textViewTelefono.setText(telefono[position]);
        textViewLicencia.setText(licencia[position]);

        return layout;
    }
}