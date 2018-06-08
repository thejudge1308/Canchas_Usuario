package com.example.fito.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Principal extends AppCompatActivity {

    private ArrayList<Button> botonesCancha;
    private int id, cantidadCanchas, contador, total;
    private String rutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        rutAdmin = getIntent().getStringExtra("rut");
        id=1;
        //cantidad = 100;
        contador = 0;
        cantidadCanchas = 0;
        total = 0;

        Log.d("RutAdmin",rutAdmin);

        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = fecha.format(new Date());

        TextView fechaActual = (TextView) findViewById(R.id.txt1);

        fechaActual.setText(fechaString);

        botonesCancha = new ArrayList<Button>();


        int cant=0;

        CantidadCanchas();

        //CrearBotones();

        //CapturaCanchas();//captura los nombres de las canchas  en los botones

        //clickBtoBotonCancha();

        ClickBotonHistorial();

        ClickBotonAgregar();

        ClickBotonActualizar();

        ClickBotonEliminar();

    }

    public void CantidadCanchas()
    {
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("JS", response);
                try {
                    JSONObject res = new JSONObject(response);
                    boolean ok = res.getBoolean("success");

                    Log.v("Json", ok + "");

                    if (ok == true)
                    {
                        cantidadCanchas = res.getInt("cantidadcancha");
                        Log.d("CantidadCanchas",cantidadCanchas+"");

                        TotalCanchas(cantidadCanchas);

                    }
                    else
                    {

                    }
                } catch (JSONException e) {
                    Log.v("JSon", e.getMessage() + e.toString());
                }
            }
        };

        CantidadCanchaRequest r = new CantidadCanchaRequest( rutAdmin, respuesta);
        RequestQueue cola = Volley.newRequestQueue(Principal.this);
        cola.add(r);
    }

    public void TotalCanchas(int CantCancha)
    {
        Log.d("CantCancha",CantCancha+"");
        cantidadCanchas = CantCancha;
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.v("JS", response);
                try {
                    JSONObject res = new JSONObject(response);
                    Iterator<String> temp = res.keys();
                    //int i =0;
                    Log.v("CANTIDAD",cantidadCanchas+"");
                    CrearBotones(cantidadCanchas);

                    clickBotonCancha();

                    //while(temp.hasNext()){
                        //String val = temp.next();
                        //Object ob = res.get(val);
                        //CapturaCanchas(res.getInt("id"+i));
                        //Log.v("DATA",""+res.getString("nombre"+i));
                    for(int i=0;i<cantidadCanchas;i++)
                    {
                        Log.v("I", i + "");

                            Log.v("I", i + "");

                            Log.v("Nombre", "" + res.getString("nombre" + i));

                            botonesCancha.get(i).setText(res.getString("nombre" + i));
                        }


                            //botonesCancha.get(i).setText(res.getString("nombre" + i));





                    /*boolean ok = res.getBoolean("success");

                    Log.v("JsonTotal", ok + "");

                    if (ok == true)
                    {
                        total = res.getInt("id");

                        Log.d("totalCancha",total+"");

                        CrearBotones(cantidadCanchas);

                        contador = 0;

                        for(int i=0; i <15; i++)
                        {
                            try
                            {
                                Log.v("IDaca",res.getInt("id")+"");
                                CapturaCanchas((res.getInt("id")));
                            }
                            catch (Exception e)
                            {
                                Log.d("Error",e+"");
                            }
                        }

                        clickBotonCancha();


                    }
                    else
                    {*/
                        /*AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
                        alerta.setMessage("Fallo en el Registro")
                                .setNegativeButton("Reintentar", null)
                                .create().show();*/
                    //}
                } catch (JSONException e) {
                    Log.v("JSon", e.getMessage() + e.toString());
                }
            }
        };

        CapturarUltimaCanchaRequest r = new CapturarUltimaCanchaRequest(rutAdmin,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Principal.this);
        cola.add(r);

    }

    public void CrearBotones(int cantidad)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayoutbtns);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

        for (int i = 0; i < cantidad; i++)
        {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            btnTag.setText("Button " + (i + 1));
            btnTag.setId(i + 1);
            btnTag.setBackgroundColor(Color.parseColor("#94bd79"));

            /*Margen para los botones*/
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnTag.getLayoutParams();
            params.setMargins(5, 20, 10, 0); //left, top, right, bottom
            row.setLayoutParams(params);

            botonesCancha.add(btnTag);
            row.addView(btnTag);
            //}

            layout.addView(row);
        }
    }

    public void CapturaCanchas(int id)
    {
        Log.d("Entra", "entra");

        //contador =0;

        //id=1;

        //for(int i=0; i <cantidad; i++)
        //{

            Response.Listener<String> respuesta = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("JS", response);
                    try {
                        JSONObject res = new JSONObject(response);
                        boolean ok = res.getBoolean("success");

                        Log.v("Json", ok + "");

                        if (ok == true) {
                            //Intent adminPrin = new Intent(Principal.this, LoginActivity.class);
                            //Principal.this.startActivity(adminPrin);
                            String nombre = res.getString("nombre");
                            Log.d("Nombre", nombre);

                            Log.d("contador", contador+"");

                            botonesCancha.get(contador).setText(nombre);

                            contador++;
                        }
                        else
                        {
                            /*AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
                            alerta.setMessage("Fallo")
                                    .setNegativeButton("Reintentar", null)
                                    .create().show();*/
                        }
                    } catch (JSONException e) {
                        Log.v("JSon", e.getMessage() + e.toString());
                    }
                }
            };

            PrincipalRequest r = new PrincipalRequest(id+"", rutAdmin, respuesta);
            RequestQueue cola = Volley.newRequestQueue(Principal.this);
            cola.add(r);

            //id++;

            Log.d("id",id+"");
        //}
    }

    public void clickBotonCancha()
    {
        //Log.d("EntraBtn","");
        for (final Button btn:botonesCancha)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //btn.setVisibility(View.INVISIBLE);
                    Intent intent =new Intent(Principal.this, HorarioCancha.class);
                    intent.putExtra("nombreCancha",btn.getText());
                    Principal.this.startActivity(intent);
                }
            });
        }
    }

    public void ClickBotonHistorial()
    {
        Button botonHistorial = (Button) findViewById(R.id.btnHistorial);
        botonHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(Principal.this, Historial.class);
                intent.putExtra("rut",rutAdmin);
                Principal.this.startActivity(intent);
            }
        });
    }

    public void ClickBotonAgregar()
    {
        Button botonAgregar = (Button) findViewById(R.id.btnAgregar);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(Principal.this, AgregarCancha.class);
                intent.putExtra("rut",rutAdmin);
                Principal.this.startActivity(intent);
                Principal.this.finish();
            }
        });
    }

    public void ClickBotonActualizar()
    {
        Button botonActualizar = (Button) findViewById(R.id.btnActualizar);
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(Principal.this, ActualizarCancha.class);
                intent.putExtra("rut",rutAdmin);
                Principal.this.startActivity(intent);
                //Principal.this.finish();
            }
        });
    }

    public void ClickBotonEliminar()
    {
        Button botonEliminar = (Button) findViewById(R.id.btnEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(Principal.this, EliminarCancha.class);
                intent.putExtra("rut",rutAdmin);
                Principal.this.startActivity(intent);
                Principal.this.finish();
            }
        });
    }

}