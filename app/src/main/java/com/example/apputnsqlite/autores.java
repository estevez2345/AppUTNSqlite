package com.example.apputnsqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class autores {

    private SqlLiteAdmin sqlAdmin;
    private SQLiteDatabase db;

    public autores(Context ctx, String nombreBdd, int version){
        sqlAdmin = new SqlLiteAdmin(ctx, nombreBdd,null,version);
        db = sqlAdmin.getWritableDatabase();
    }

    public Autor Create (int id, String nombres, String apellidos, String isoPais, int edad){
        ContentValues datos;
        datos = new ContentValues();
        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad",edad);
        long r = db.insert("autores", "id,nombres,apellidos,isoPais,edad",datos);
        if (r<=0)
            return null;
        else{
            Autor autor = new Autor();
            autor.Id = id;
            autor.Nombres = nombres;
            autor.Apellidos = apellidos;
            autor.IsoPais = isoPais;
            autor.Edad = edad;
            return autor;
        }
    }

    public Autor Update(int id, String nombres, String apellidos, String isoPais, int edad){
        ContentValues datos;
        datos = new ContentValues();
        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad",edad);
        int r = db.update("autores",datos,"id = " + id, null);
        if (r==0)
            return null;
        else{
            Autor autor = new Autor();
            autor.Id = id;
            autor.Nombres = nombres;
            autor.Apellidos = apellidos;
            autor.IsoPais = isoPais;
            autor.Edad = edad;
            return autor;
        }
    }

    public Autor Read_ById(int id){
        Autor registro = null;
        Cursor cursor;
        cursor = db.rawQuery("SELECT id, nombres, apellidos, isoPais, edad FROM autores WHERE id="+id,null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            registro = new Autor();
            registro.Id = cursor.getInt(0);
            registro.Nombres = cursor.getString(1);
            registro.Apellidos = cursor.getString(2);
            registro.IsoPais = cursor.getString(3);
            registro.Edad = cursor.getInt(4);
        }
        cursor.close();
        return registro;
    }

    public Autor[] Read_All(){
        Autor registro;
        Autor[] datos;
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT id, nombres, apellidos, isoPais, edad FROM autores ORDER BY apellidos, nombres",null);
        datos = new Autor[cursor.getCount()];
        while(cursor.moveToNext()){
            registro = new Autor();
            registro.Id = cursor.getInt(0);
            registro.Nombres = cursor.getString(1);
            registro.Apellidos = cursor.getString(2);
            registro.IsoPais = cursor.getString(3);
            registro.Edad = cursor.getInt(4);
            datos[i++] = registro;
        }
        cursor.close();
        return datos;
    }

    public boolean Delete(int id){
        int r = db.delete("autores","id=" + id,null);
        return r > 0;
    }
}
