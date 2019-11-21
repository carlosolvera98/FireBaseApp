package mx.edu.utng.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText etxTema;   //Objetos de la vista
    Spinner spiCarrera, spiMateria;
    Button btnReg;
    private DatabaseReference refDiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se hace referencia a la clase modelo para los datos
        refDiario = FirebaseDatabase.getInstance().getReference("Clase");

        etxTema = (EditText)findViewById(R.id.extTema);
        spiCarrera = (Spinner)findViewById(R.id.spiCarrera);
        spiMateria = (Spinner)findViewById(R.id.spiMateria);
        btnReg = (Button)findViewById(R.id.btnRegistrar);

        //Manejo
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarClase();
            }
        });
    }// fin del OnCreate

    public void registrarClase(){
        String carrera= spiCarrera.getSelectedItem().toString();
        String materia= spiMateria.getSelectedItem().toString();
        String tema= etxTema.getText().toString();

        if(!TextUtils.isEmpty(tema)){
            //Genera id del nuevo elemento
            String id= refDiario.push().getKey();

            //Crear un objeto para ser agregado a la base de datos
            Clase leccion= new Clase(id, carrera, materia, tema);
            //Ingresar a la BD documental

            refDiario.child("Lecciones").child(id).setValue(leccion);
            Toast.makeText(this, "Clase registrada", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debes introducir un tema", Toast.LENGTH_LONG).show();
        }
    }

}
