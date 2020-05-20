package com.example.clientespotenciales.clases;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {
    public static boolean isOnline(Activity activity) {
        //Obtiene el servicio de conectividad que se encarga de las conexiones a internet.
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo( );
        if( networkInfo != null && networkInfo.isConnected( ) )
            return true;
        return false;
    }

    public static String getEncrypt(String plaintext)  {

        int  j = 0, valor = 0;
        char []dato;
        String retorno;
        try {
            dato = plaintext.toCharArray();
            valor = 0;
            for (int i = 0; i < dato.length; i++) {

                valor  += (int) dato[i];
            }
            retorno =  ""+(5000 - valor + dato.length);
        }catch (Exception e){
            retorno = e.getMessage();
        }

        return retorno;

    }

    /**
     * =============================================================================
     * Encripta el dato que se recibe como parámetro.
     * Parámetro: Dato a encriptar.
     * Return: Dato encriptado.
     * =============================================================================
     */
    public static String encriptarDato(String cadenaOrg  )
    {
        String cadenaMay,cadenaEnc;
        Integer i;
        cadenaMay = cadenaOrg.toUpperCase();
        cadenaEnc = "";
        for (i = 0;i< cadenaMay.length();i++) {
            cadenaEnc = cadenaEnc + "0" + cadenaMay.codePointAt(i);
        }

        return (cadenaEnc);
    }

    /**
     * =============================================================================
     * Desencripta el dato que se recibe como parámetro.
     * Parámetro: Dato a desencriptar.
     * Return: Dato desencriptado.
     * =============================================================================
     */
    public static String desencriptarDato(String cadenaOrg  )
    {


        return (cadenaOrg);
    }

}
