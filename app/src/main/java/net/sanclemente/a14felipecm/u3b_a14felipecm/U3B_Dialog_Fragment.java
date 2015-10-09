package net.sanclemente.a14felipecm.u3b_a14felipecm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by felipe on 9/10/15.
 */
public class U3B_Dialog_Fragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle estado){
        AlertDialog.Builder dialogo;
        //Intento de coger el context
        //Context ctx = this.getApplicationContext();

        //Cojo los argumentos que les paso en el bundle
        final String texto_buscar = getArguments().getString("search");
        final String texto_telefono = getArguments().getString("telefone");
        dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle(getResources().getString(R.string.alert_dialog_titulo));
        dialogo.setMessage(getResources().getString(R.string.alert_dialog_texto) +":\n"+texto_buscar+"\n"+getResources().getString(R.string.alert_dialog_texto_telefono)+":\n"+texto_telefono);
        dialogo.setIcon(android.R.mipmap.sym_def_app_icon);
        //Esto impediria poder cerrar el cuadro de dialogo
        //dialogo.setCancelable(false);
        dialogo.setPositiveButton(getResources().getString(R.string.alert_boton_buscar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intente recuperar el estado del activity pero sin exito
                //Bundle estado = getIntent().getExtras();
                //Inicializo el string que se va a buscar por defecto si el valor que hay guardado es nulo
                String terminoBuscar= getResources().getString(R.string.string_default_busqueda);
                if(texto_buscar !=null & !texto_buscar.equals("")){
                    //Asigno el valor que hay en el textView oculto
                    terminoBuscar=texto_buscar;
                }
                //Creamos un intent para la accion de buscar
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, terminoBuscar);
                startActivity(intent);
            }
        });
        dialogo.setNegativeButton(getResources().getString(R.string.alert_boton_llamar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Bundle estado = getIntent().getExtras();
                if (!texto_telefono.equals("") & texto_telefono!=null) {
                    //Creamos el intent para la accion de abrir el dialogo de llamada
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + texto_telefono.toString()));
                    startActivity(intent);
                } else {
                    //Si la cadena del telefono es nula, muestra el error
                    Toast.makeText(getActivity(), getResources().getString(R.string.toast_aviso), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return dialogo.create();
    }//FIN onCreateDialog



}
