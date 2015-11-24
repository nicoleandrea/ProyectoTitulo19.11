package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.nicole.smartcoming.R;

public class CustomListSelectVehiculos extends ArrayAdapter<String> {
    private String[] patente;
    private String[] marca;
    private String[] modelo;
    private String[] anno;
    private String[] nombre_duenno;
    private String[] kilometraje;
    private String[] revision_tecnica;
    private Activity context;

    public CustomListSelectVehiculos(Activity context, String[] patente, String[] marca, String[] modelo, String[] anno, String[] nombre_duenno, String[] kilometraje, String[] revision_tecnica) {
        super(context, R.layout.activity_modificar_vehiculo, patente);
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
        View layout = inflater.inflate(R.layout.activity_modificar_vehiculo, null, true);

        EditText textViewPatente = (EditText) layout.findViewById(R.id.txt_patenteVehiculoMod);
        EditText textViewMarca = (EditText) layout.findViewById(R.id.txt_marcaVehiculoMod);
        EditText textViewModelo = (EditText) layout.findViewById(R.id.txt_modeloVehiculoMod);
        EditText textViewAnno = (EditText) layout.findViewById(R.id.txt_annoVehiculoMod);
        EditText textViewDuenno = (EditText) layout.findViewById(R.id.txt_duennoVehiculoMod);
        EditText textViewKilometraje = (EditText) layout.findViewById(R.id.txt_kilometrajeVehiculoMod);
        EditText textViewRevision_tecnica = (EditText) layout.findViewById(R.id.txt_RevisionTecnicaMod);

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
