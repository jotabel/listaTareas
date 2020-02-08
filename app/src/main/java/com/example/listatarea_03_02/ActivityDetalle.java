package com.example.listatarea_03_02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDetalle extends AppCompatActivity {

    private TextView tvNombre,tvLugar,tvTitulo,tvDesc,tvImpo;
    private ImageView iv;
    private int [] dibujar={R.drawable.ic_green,R.drawable.ic_yellow,R.drawable.ic_red};
    private int rowid,importancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        cargarViews();

        //cargo datos del intent
        Bundle datos=new Bundle();
        datos=getIntent().getExtras();
        rowid=datos.getInt(MainActivity.ROWID);
        rellenarDatos();

    }

    private void rellenarDatos() {
        BaseDatosHelper conexion = new BaseDatosHelper(this,MainActivity.BASE, null,1);
        SQLiteDatabase base = conexion.getReadableDatabase();

        Cursor c=base.rawQuery("select rowid , nombre , lugar , descripcion , importancia from " + BaseDatosHelper.TABLA + " where rowid = "+rowid,null);
        if(c.moveToFirst()){
            tvNombre.setText(c.getString(1));
            tvLugar.setText(c.getString(2));
            tvDesc.setText(c.getString(3));
            importancia=c.getInt(4);
            tvTitulo.setText(String.format(getResources().getString(R.string.titulo),rowid));

        }
        base.close();

        switch(importancia){
            case 1:
                tvImpo.setText("Baja");
                iv.setImageResource(dibujar[0]);
                break;
            case 2:
                tvImpo.setText("Normal");
                iv.setImageResource(dibujar[1]);
                break;
            case 3:
                tvImpo.setText("Alta");
                iv.setImageResource(dibujar[2]);
                break;
        }
    }

    public void volver(View v){
        finish();
    }

    private void cargarViews() {
        tvNombre=findViewById(R.id.tvDNombre);
        tvLugar=findViewById(R.id.tvDlugar);
        tvTitulo=findViewById(R.id.tvDTitulo);
        tvDesc=findViewById(R.id.tvDDesc);
        tvImpo=findViewById(R.id.tvDImp);
        iv=findViewById(R.id.imageView);


    }
}
