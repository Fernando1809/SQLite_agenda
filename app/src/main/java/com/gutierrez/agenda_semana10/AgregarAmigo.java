package com.gutierrez.agenda_semana10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgregarAmigo extends AppCompatActivity {
    FloatingActionButton btnAtras;
    String idAmigo, accion="nuevo";
    Button btn;
    DB miDB;
    TextView tempVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigo);
        miDB = new DB (getApplicationContext(),"",null,1);

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> {
            mostrarVistaPrincipal();
        });


        btn = findViewById(R.id.btnGuardarAmigo);
        btn.setOnClickListener(v->{
            agregarProducto();
        });
        mostrarDatosProductos();
    }


    private void agregarProducto () {

        tempVal = findViewById(R.id.txtName);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtNumber);
        String numero = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtEmail);
        String email = tempVal.getText().toString();



        String [] datos = {idAmigo,nombre, numero, email};
        miDB.admin_amigo(accion,datos);
        mostrarMsgToast("Amigo guardado con exito");
        mostrarVistaPrincipal();
        mostrarDatosProductos();

    }

    //Mostrar principal
    private void mostrarVistaPrincipal(){
        Intent iprincipal = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iprincipal);
    }

    //Mensaje Toast
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    private void mostrarDatosProductos() {
        try {
            Bundle parametros= getIntent().getExtras();
            accion = parametros.getString("accion");
            if (accion.equals("modificar")){
                String[] datos = parametros.getStringArray("datos");
                idAmigo = datos[0];
                tempVal = findViewById(R.id.txtName);
                tempVal.setText(datos[1]);
                tempVal = findViewById(R.id.txtNumber);
                tempVal.setText(datos[2]);
                tempVal = findViewById(R.id.txtEmail);


            }

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());

        }

    }

}