package com.example.clientespotenciales;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clientespotenciales.clases.Global;
import com.example.clientespotenciales.clases.Mensaje;
import com.example.clientespotenciales.clases.Modulo;
import com.example.clientespotenciales.clases.Usuario;
import com.example.clientespotenciales.clases.Utilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnConfig,btnAcceso;
    TextView txtVFecha,txtVAgencia,txtVVersion;
    EditText edTUsuario,edTPassWord;
    Mensaje mensaje;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<Modulo> modulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PackageInfo info= new PackageInfo();

        sweetAlertDialog = new SweetAlertDialog(this);
        mensaje = new Mensaje();
        btnConfig = findViewById(R.id.btnConfig);
        btnAcceso = findViewById(R.id.btnAcceso);
        edTUsuario =  findViewById(R.id.edTUsuario);
        edTPassWord =  findViewById(R.id.edTPassWord);
        txtVFecha =  findViewById(R.id.txtVFecha);
        txtVAgencia =  findViewById(R.id.txtVMovil);
        txtVVersion =  findViewById(R.id.txtVVersion);

        try {
            info = getPackageManager( ).getPackageInfo( getPackageName( ), 0 );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtVVersion.setText("V."+info.versionName);


        btnAcceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean conectado = Utilities.isOnline(MainActivity.this);
                if (edTUsuario.length() == 0){
                    mensaje.MensajeAdvertencia(MainActivity.this,"Acceso Sistema","Debe Ingresar Usuario" +
                            " para Ingresar al Sistema");
                    return;
                }

                if (edTPassWord.length()==0){
                    mensaje.MensajeAdvertencia(MainActivity.this,"Acceso Sistema","Debe Ingresar  Contraseña " +
                            " para Ingresar al Sistema");
                    return;
                }

                if (conectado) {
                    Global.g_Usuario = edTUsuario.getText().toString();
                    ConsultarUsuario();
                }
                else{
                    mensaje.MensajeAdvertencia(MainActivity.this, "Alerta", "Movil sin conexion");
                    return;
                }
            }
        });

    }

    public void ConsultarUsuario(){
        try{

            String cadena;
            cadena = "http://192.168.1.7:65069/api/celcia/PostConsultaUsuario";

            sweetAlertDialog = mensaje.progreso(MainActivity.this, "Consultando Usuario");
            sweetAlertDialog.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, cadena,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //conduce = new Gson().fromJson(result.toString(), Conduce.class);
                                String placa = "";
                                modulos = new ArrayList<>();
                                Usuario user = new Gson().fromJson(response, Usuario.class);
                                sweetAlertDialog.dismiss();


                                if (user != null) {
                                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                } else {
                                    mensaje.MensajeAdvertencia(MainActivity.this, "Advertencia", "Usuario/Contraseña Invalida");
                                    edTUsuario.requestFocus();
                                    edTPassWord.setText("");
                                }

                            } catch (Exception e) {
                                sweetAlertDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },errorListener
            ) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:
                    params.put("login", edTUsuario.getText().toString());
                    params.put("passw", edTPassWord.getText().toString());
                    return params;
                }
            };
            try
            {
                //tiempo de espera de conexcion initialTimeout 4000 maxNumRetries = 0
                postRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                        0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(MainActivity.this).add(postRequest);
            }
            catch(Exception e){

                sweetAlertDialog.dismiss();
                mensaje.MensajeError(MainActivity.this, "9.Advertencia", e.getMessage());
                e.printStackTrace();
            }
        }
        catch(Exception ex){

            sweetAlertDialog.dismiss();
            mensaje.MensajeError(MainActivity.this, "9.Advertencia", ex.getMessage());
        }
    }

    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            NetworkResponse networkResponse = error.networkResponse;
            try {
                sweetAlertDialog.dismiss();
                //counter.cancel();
                //startCounter = false;
                if (networkResponse != null && networkResponse.statusCode == 400) {
                    error.printStackTrace();
                    sweetAlertDialog.dismiss();
                    mensaje.MensajeAdvertencia(MainActivity.this, "7.Advertencia", error.toString());
                    //startCounter = false;
                    //counter.onFinish();
                    return;
                }
                String msj = error.getMessage();
                if (msj == null) {
                    error.printStackTrace();
                    sweetAlertDialog.dismiss();
                    mensaje.MensajeAdvertencia(MainActivity.this, "8.Advertencia", "Servidor No Responde");
                    //startCounter = false;
                    //counter.onFinish();
                    return;
                } else {
                    error.printStackTrace();
                    sweetAlertDialog.dismiss();
                    mensaje.MensajeAdvertencia(MainActivity.this, "Advertencia", msj.toString());
                    //startCounter = false;
                    //counter.onFinish();
                    return;
                }
            }
            catch(WindowManager.BadTokenException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    public void  VerificaUsuarioPost()  {

        try{
            String cadena = "";
            final String[] devuelve = { "0" };
            cadena = "http://192.168.1.7:65069/api/celcia/PostPrueba"  ;

            sweetAlertDialog = mensaje.progreso(MainActivity.this,"Verificando Usuario");
            sweetAlertDialog.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, cadena,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //conduce = new Gson().fromJson(result.toString(), Conduce.class);
                                devuelve[0] = new Gson().fromJson(response.toString(),String.class);

                                sweetAlertDialog.dismiss();

                                if (devuelve[0].equals("1")) {
                                    sweetAlertDialog = mensaje.MensajeConfirmacionExitosoConUnBoton(MainActivity.this,"Confirmacion Exitosa","Acceso Correcto!!");
                                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                            sweetAlertDialog.dismiss();
                                        }
                                    });
                                    sweetAlertDialog.show();
                                }
                                else
                                    mensaje.MensajeAdvertencia(MainActivity.this, "Advertencia", "CONTRASEÑA Incorrecta / USUARIO Bloqueado");


                            } catch (Exception e) {
                                sweetAlertDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 400) {
                                error.printStackTrace();
                                sweetAlertDialog.dismiss();
                                mensaje.MensajeAdvertencia(MainActivity.this,"Titulo Ventana",error.getMessage());
                                edTUsuario.requestFocus();
                                return;
                            }
                            String msj = error.getMessage();
                            if (msj == null)
                            {
                                error.printStackTrace();
                                sweetAlertDialog.dismiss();
                                mensaje.MensajeAdvertencia(MainActivity.this,"Advertencia","Servidor No Responde");
                                return;
                            }
                        }
                    }
            ) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("user", edTUsuario.getText().toString());
                    params.put("contraseña", edTUsuario.getText().toString());
                    return params;
                }
            };
            //tiempo de espera de conexcion initialTimeout 4000 maxNumRetries = 0
            postRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(MainActivity.this).add(postRequest);

        }
        catch(Exception ex){
            mensaje.MensajeError(MainActivity.this, "Advertencia", ex.getMessage());
        }
    }



}
