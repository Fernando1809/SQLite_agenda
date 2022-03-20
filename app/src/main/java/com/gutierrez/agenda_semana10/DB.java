package com.gutierrez.agenda_semana10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    Context miContext;
    static String nombreDB = "db_productos";
    static String tblAmigo = "CREATE TABLE tblamigos(idAmigo integer primary key autoincrement, nombre text, numero text, email text)";


    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblAmigo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor admin_amigo (String accion, String [] datos){
        Cursor datosCursor = null;
        SQLiteDatabase sqlDataBaseW = getWritableDatabase();
        SQLiteDatabase sqlDataBaseR = getReadableDatabase();
        switch (accion){
            case "consultar":
                datosCursor = sqlDataBaseR.rawQuery("SELECT * FROM tblamigos ORDER BY nombre", null);
                break;

            case "nuevo":
                sqlDataBaseW.execSQL("INSERT INTO tblamigos(nombre, numero, email) VALUES ('"+datos[1]+"', '"+datos[2]+"', '"+datos[3]+"')");
                break;

            case "modificar":
                try{
                    sqlDataBaseW.execSQL("UPDATE tblamigos SET nombre = '"+datos[1]+"', numero = '"+datos[2]+"', email = '"+datos[3]+"' WHERE idAmigo = '"+datos[0]+"'");

            }catch (Exception e){
                    Toast.makeText(miContext.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                break;

            case "eliminar":
                sqlDataBaseW.execSQL("DELETE FROM tblamigos WHERE idamigo = '"+datos[0]+"'");

        }

        return datosCursor;
    }
}
