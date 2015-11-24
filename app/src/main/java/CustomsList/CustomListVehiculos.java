package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListVehiculos extends ArrayAdapter<String> {
    private String[] patente;
    private String[] marca;
    private String[] modelo;
    private String[] anno;
    private String[] nombre_duenno;
    private String[] kilometraje;
    private String[] revision_tecnica;
    private Activity context;

    public CustomListVehiculos(Activity context, String[] patente, String[] marca, String[] modelo, String[] anno, String[] nombre_duenno, String[] kilometraje, String[] revision_tecnica) {
        super(context, R.layout.lista_formato5, patente);
        this.context = context;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anno = anno;
        this.nombre_duenno = nombre_duenno;
        this.kilometraje = kilometraje;
        this.revision_tecnica = revision_tecnica;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato5, null, true);

        TextView textViewPatente = (TextView) layout.findViewById(R.id.text1);
        TextView textViewMarca = (TextView) layout.findViewById(R.id.text2);
        TextView textViewModelo = (TextView) layout.findViewById(R.id.text3);
        TextView textViewAnno = (TextView) layout.findViewById(R.id.text4);
        TextView textViewDuenno = (TextView) layout.findViewById(R.id.text5);
        TextView textViewKilometraje = (TextView) layout.findViewById(R.id.text6);
        TextView textViewRevision_tecnica = (TextView) layout.findViewById(R.id.text7);

        textViewPatente.setText(patente[position]);
        textViewMarca.setText(marca[position]);
        textViewModelo.setText(modelo[position]);
        textViewAnno.setText(anno[position]);
        textViewDuenno.setText(nombre_duenno[position]);
        textViewKilometraje.setText(kilometraje[position]);
        textViewRevision_tecnica.setText(revision_tecnica[position]);

        return layout;
    }
}
