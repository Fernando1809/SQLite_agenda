package com.gutierrez.agenda_semana10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Cursor datosAmigosCursor = null;
    amigo misAmigos;
    DB miDB;
    FloatingActionButton btn;
    ListView ltsAmigos;
    ArrayList<amigo> amigosArrayList = new ArrayList<amigo>();
    ArrayList<amigo> amigosrrayListCopy = new ArrayList<amigo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarAmigo);
        try {
            comprobarDatos();
        } catch (Exception e) {
            mostrarMsgToast(e.getMessage());
        }

        btn.setOnClickListener(v -> {
            agregarAmigo("nuevo", new String[]{});
        });

        buscarAmigo();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater myMenu = getMenuInflater();
        myMenu.inflate(R.menu.menu, menu);
        AdapterView.AdapterContextMenuInfo mymenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        datosAmigosCursor.moveToPosition(mymenuInfo.position);
        menu.setHeaderTitle(datosAmigosCursor.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxagregar:
                    agregarAmigo("nuevo", new String[]{});
                    break;

                case R.id.mnxmodificar:
                    String[] datos = {
                            datosAmigosCursor.getString(0),//idAmigo
                            datosAmigosCursor.getString(1),//Nombre
                            datosAmigosCursor.getString(2),//Numero
                            datosAmigosCursor.getString(3),//Correo

                    };
                    agregarAmigo("modificar", datos);
                    break;

                case R.id.mnxeliminar:
                    eliminarAmigo();
                    break;

            }
        } catch (Exception e) {
            mostrarMsgToast(e.getMessage());
        }
        return super.onContextItemSelected(item);
    }

    private void mostrarMsgToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void eliminarAmigo() {
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("¿Seguro desea eliminar?");
            confirmacion.setMessage(datosAmigosCursor.getString(2));
            confirmacion.setPositiveButton("SI", ((dialog, which) -> {
                miDB = new DB(getApplicationContext(), "", null, 1);
                datosAmigosCursor = miDB.admin_amigo("eliminar", new String[]{datosAmigosCursor.getString(0)});
                comprobarDatos();
                mostrarMsgToast("Eliminado correcto");
                dialog.dismiss();
            }));
            confirmacion.setNegativeButton("NO", (dialog, which) -> {
                mostrarMsgToast("Se cancelo eliminar");
                dialog.dismiss();
            });
            confirmacion.create().show();

        } catch (Exception e) {
            mostrarMsgToast(e.getMessage());

        }

    }

    private void buscarAmigo() {
        TextView temp = findViewById(R.id.txtBuscarAmigos);
        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amigosArrayList.clear();
                if (temp.getText().toString().length() < 1) {
                    amigosArrayList.addAll(amigosrrayListCopy);
                } else {
                    for (amigo AO : amigosrrayListCopy) {
                        String Nombre = AO.getName();
                        String Numero = AO.getNumber();
                        String Correo = AO.getEmail();
                        String buscando = temp.getText().toString().trim().toLowerCase();
                        if (Nombre.toLowerCase().contains(buscando) ||
                                Numero.toLowerCase().contains(buscando) ||
                                Correo.toLowerCase().contains(buscando)) {
                            amigosArrayList.add(AO);
                        }
                    }
                }
                AdaptadorImagenes amigoEncontrado = new AdaptadorImagenes(getApplicationContext(), amigosArrayList);
                ltsAmigos.setAdapter(amigoEncontrado);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void agregarAmigo(String accion, String[] datos) {
        try {
            Bundle parametroAmigo = new Bundle();
            parametroAmigo.putString("accion", accion);
            parametroAmigo.putStringArray("datos", datos);
            Intent nuevoProducto = new Intent(getApplicationContext(), AgregarAmigo.class);
            nuevoProducto.putExtras(parametroAmigo);
            startActivity(nuevoProducto);
        } catch (Exception e) {
            mostrarMsgToast(e.getMessage());
        }

    }

    private void comprobarDatos() {
        miDB = new DB(getApplicationContext(), "", null, 1);
        datosAmigosCursor = miDB.admin_amigo("consultar", null);

        if (datosAmigosCursor.moveToFirst()) {
            mostrarAmigo();


        } else {
            mostrarMsgToast("Porfavor agregar datos");
            agregarAmigo("nuevo", new String[]{});
        }

    }

    private void mostrarAmigo() {
        ltsAmigos = findViewById(R.id.ltsamigos);
        amigosArrayList.clear();
        amigosrrayListCopy.clear();

        do {
            misAmigos = new amigo(
                    datosAmigosCursor.getString(0),//idAmigo
                    datosAmigosCursor.getString(1),//Nombre
                    datosAmigosCursor.getString(2),//Numero
                    datosAmigosCursor.getString(3)//Correo
            );
            amigosArrayList.add(misAmigos);
        } while (datosAmigosCursor.moveToNext());

        try {
            AdaptadorImagenes adaptadorImagenes = new AdaptadorImagenes(getApplicationContext(), amigosArrayList);
            ltsAmigos.setAdapter(adaptadorImagenes);

            registerForContextMenu(ltsAmigos);
            amigosrrayListCopy.addAll(amigosArrayList);

        } catch (Exception e) {
            mostrarMsgToast(e.getMessage());
        }

    };
}



