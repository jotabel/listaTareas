package com.example.listatarea_03_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityCrear extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etNombre, etLugar, etDes;
    private Spinner sp;
    ArrayAdapter<CharSequence>  adapter;
    private int importancia;
    String nombre, lugar, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        cargarViews();

        sp.setOnItemSelectedListener(this);
        adapter=ArrayAdapter.createFromResource(this,R.array.arrayimp,android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);

    }


    public void guardar(View v){

        nombre=etNombre.getText().toString().trim();
        lugar=etLugar.getText().toString().trim();
        descripcion=etDes.getText().toString().trim();

        if(nombre.length()==0 || lugar.length()==0 || descripcion.length()==0){
            Toast.makeText(this, "Rellene todos los campos!!", Toast.LENGTH_SHORT).show();
            return ;
        }

        BaseDatosHelper conexion = new BaseDatosHelper(this,MainActivity.BASE, null,1);
        SQLiteDatabase base = conexion.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put(BaseDatosHelper.CAMPO1,nombre);
        registro.put(BaseDatosHelper.CAMPO2,lugar);
        registro.put(BaseDatosHelper.CAMPO3,descripcion);
        registro.put(BaseDatosHelper.CAMPO4,importancia);
        base.insert(BaseDatosHelper.TABLA,null,registro);
        base.close();
        //Intent i=new Intent();
        setResult(RESULT_OK);
        finish();

    }


    private void cargarViews() {
        etNombre=findViewById(R.id.etNombre);
        etLugar=findViewById(R.id.etLugar);
        etDes=findViewById(R.id.etDescripcion);
        sp=findViewById(R.id.spImpor);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String op=adapter.getItem(position).toString();
        switch (op){
            case "Baja":
                importancia=1;
                break;
            case "Media":
                importancia=2;
                break;
            case "Alta":
                importancia=3;
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
