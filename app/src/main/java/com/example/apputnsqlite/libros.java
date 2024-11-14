package com.example.apputnsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class libros {

    private SqlLiteAdmin sqlAdmin;
    private SQLiteDatabase db;

    public libros(Context ctx, String nombreBdd, int version){
        sqlAdmin = new SqlLiteAdmin(ctx, nombreBdd, null, version);
        db = sqlAdmin.getWritableDatabase();
    }

    public Libro Create(int id, String titulo, int idAutor, String isbn, int anioPublicacion, int revision, int nroHojas){
        ContentValues datos = new ContentValues();
        datos.put("id", id);
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("isbn", isbn);
        datos.put("anioPublicacion", anioPublicacion);
        datos.put("revision", revision);
        datos.put("nroHojas", nroHojas);
        long r = db.insert("libros", null, datos);
        if (r <= 0)
            return null;
        else {
            Libro libro = new Libro();
            libro.Id = id;
            libro.Titulo = titulo;
            libro.IdAutor = idAutor;
            libro.ISBN = isbn;
            libro.AnioPublicacion = anioPublicacion;
            libro.Revision = revision;
            libro.NroHojas = nroHojas;
            return libro;
        }
    }

    public Libro Update(int id, String titulo, int idAutor, String isbn, int anioPublicacion, int revision, int nroHojas){
        ContentValues datos = new ContentValues();
        datos.put("id", id);
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("isbn", isbn);
        datos.put("anioPublicacion", anioPublicacion);
        datos.put("revision", revision);
        datos.put("nroHojas", nroHojas);
        int r = db.update("libros", datos, "id = " + id, null);
        if (r == 0)
            return null;
        else {
            Libro libro = new Libro();
            libro.Id = id;
            libro.Titulo = titulo;
            libro.IdAutor = idAutor;
            libro.ISBN = isbn;
            libro.AnioPublicacion = anioPublicacion;
            libro.Revision = revision;
            libro.NroHojas = nroHojas;
            return libro;
        }
    }

    public Libro Read_ById(int id){
        Libro registro = null;
        Cursor cursor;
        cursor = db.rawQuery("SELECT id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas FROM libros WHERE id=" + id, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            registro = new Libro();
            registro.Id = cursor.getInt(0);
            registro.Titulo = cursor.getString(1);
            registro.IdAutor = cursor.getInt(2);
            registro.ISBN = cursor.getString(3);
            registro.AnioPublicacion = cursor.getInt(4);
            registro.Revision = cursor.getInt(5);
            registro.NroHojas = cursor.getInt(6);
        }
        cursor.close();
        return registro;
    }

    public Libro[] Read_All(){
        Libro registro;
        Libro[] datos;
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas FROM libros ORDER BY titulo", null);
        datos = new Libro[cursor.getCount()];
        while(cursor.moveToNext()){
            registro = new Libro();
            registro.Id = cursor.getInt(0);
            registro.Titulo = cursor.getString(1);
            registro.IdAutor = cursor.getInt(2);
            registro.ISBN = cursor.getString(3);
            registro.AnioPublicacion = cursor.getInt(4);
            registro.Revision = cursor.getInt(5);
            registro.NroHojas = cursor.getInt(6);
            datos[i++] = registro;
        }
        cursor.close();
        return datos;
    }

    public boolean Delete(int id){
        int r = db.delete("libros", "id=" + id, null);
        return r > 0;
    }
}
