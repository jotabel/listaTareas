package com.example.listatarea_03_02;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String ROWID="rowid";
    public final static int REQ_CODE=123;
    private RecyclerView recView;
    private ArrayList<ListaItem> miLista;
    ListaAdapter adapter;
    public final static String BASE ="bdTarea1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this, ActivityCrear.class);
                startActivityForResult(i,REQ_CODE);
            }
        });

        //-------------------------------------------------
        recView=findViewById(R.id.recView);

        obtenerRegistros();
        ponerGestos();

    }

    private void obtenerRegistros() {

        miLista=new ArrayList<>();

        //ponemos el layout en recView
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new ListaAdapter(miLista);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListaItem li=miLista.get(recView.getChildAdapterPosition(v));
                Intent i= new Intent(MainActivity.this, ActivityDetalle.class);
                i.putExtra(ROWID,li.getRowid());
                startActivity(i);
            }
        });

        recView.setAdapter(adapter);
        //ahora leo todos los registros y los añado
        BaseDatosHelper conexion = new BaseDatosHelper(this,BASE,null,1);
        SQLiteDatabase base=conexion.getReadableDatabase();

        Cursor cursor = base.rawQuery("select rowid, nombre, lugar, descripcion, importancia " +
                " from "+BaseDatosHelper.TABLA+" order by importancia desc",null);
        ListaItem item=null;
        while(cursor.moveToNext()){
            item=new ListaItem();
            item.setRowid(cursor.getInt(0));
            item.setNombre(cursor.getString(1));
            item.setLugar(cursor.getString(2));
            item.setDescripcion(cursor.getString(3));
            item.setImportancia(cursor.getInt(4));
            miLista.add(item);
        }
        base.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE && resultCode==RESULT_OK){
            obtenerRegistros();
            Toast.makeText(this,"Tarea Guardada con Exito", Toast.LENGTH_SHORT).show();
        }

    }

    private void ponerGestos(){

        ItemTouchHelper.SimpleCallback ith = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //cojo la posicion del elemento que he pulsado
                int pos=viewHolder.getAdapterPosition();
                ListaItem elemento = miLista.get(pos);
                if(direction==ItemTouchHelper.LEFT){
                    BaseDatosHelper conexion = new BaseDatosHelper(MainActivity.this,MainActivity.BASE, null,1);
                    SQLiteDatabase base = conexion.getWritableDatabase();
                    base.delete(BaseDatosHelper.TABLA,"ROWID"+elemento.getRowid(),null);
                    base.close();
                    //notifico para actualizar recycler
                    miLista.remove(pos);
                    adapter.notifyItemRemoved(pos);
                }


                if (direction==ItemTouchHelper.RIGHT){

                    Intent i= new Intent(MainActivity.this, ActivityCrear.class);
                    i.putExtra(ROWID,elemento.getRowid());
                    startActivity(i);

                }


            }
        };


    }


}
