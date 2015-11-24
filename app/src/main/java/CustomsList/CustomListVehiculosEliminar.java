package CustomsList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nicole.smartcoming.R;

public class CustomListVehiculosEliminar extends ArrayAdapter<String> {
    private String[] vehiculo;
    private Activity context;

    public CustomListVehiculosEliminar(Activity context, String[] vehiculo) {
        super(context, R.layout.lista_formato9, vehiculo);
        this.context = context;
        this.vehiculo = vehiculo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.lista_formato9, null, true);

        TextView textViewVehiculo = (TextView) layout.findViewById(R.id.text1);

        textViewVehiculo.setText(vehiculo[position]);

        return layout;
    }
}
