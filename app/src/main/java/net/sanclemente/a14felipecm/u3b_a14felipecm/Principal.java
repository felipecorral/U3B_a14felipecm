package net.sanclemente.a14felipecm.u3b_a14felipecm;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Principal extends FragmentActivity {

    //Constante para la peticion del 2º activity, no hace falta porque solo regresaremos de un activity
    private static final int COD_PETICION_SECUNDARIA = 200;
    //Constante para el dialog, no hace falta tampoco pero es interesante
    private static final int DIALOGO_ACCION = 1;
    //TextViews Ocultos para guardar la información al volver del 2º activity
    //Al usar textview con ids y girar la pantalla, estos guardaran su valor, lo cual no es lo que pide exactamente
    //Pero sin embargo sabremos que estamos guardando bien el estado de la aplicacion, porque si salimos del activity
    //a otro y luego volvemos a este, los datos persisten.
    private TextView tv_oculto_buscar;
    private TextView tv_oculto_telefono;
    //Variable para crear los dialogs
    AlertDialog.Builder dialogo;

    private U3B_Dialog_Fragment dialogo_fragmentado;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Inicializamos los TextView ocultos
        tv_oculto_buscar = (TextView) findViewById(R.id.textv_texto_buscar_invisible);
        tv_oculto_telefono = (TextView) findViewById(R.id.textv_telefono_oculto);

        dialogo_fragmentado = new U3B_Dialog_Fragment();
        //Listener para la pulsacion larga del boton, en el que llamaremos a showDialog
        //Pasandole el valor correspondiente al dialog que vamos a querer mostrar(en este caso solo hay uno que sería este)
        Button boton = (Button) findViewById(R.id.boton_dtb);
        boton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fm = getSupportFragmentManager();
                //Creo el bundle con los datos necesarios para llamar al fragment
                Bundle args =new Bundle();
                args.putString("search",tv_oculto_buscar.getText().toString());
                args.putString("telefone",tv_oculto_telefono.getText().toString());
                dialogo_fragmentado.setArguments(args);
                dialogo_fragmentado.show(fm,"ty8dy");
                return true;
            }
        });//Fin Listener
    }//Fin onCreate




    //En este metodo mostraremos el toast al pulsar en el boton datos, con los datos que recogemos de los Textview ocultos
    public void mostrarToastStrings(View v){
        //Intente recuperar el bundle desde aquí
        //Bundle extras=getIntent().getExtras();
        Toast.makeText(Principal.this,getResources().getString(R.string.alert_dialog_texto)+":\n"+ tv_oculto_buscar.getText().toString() +"\n"+getResources().getString(R.string.alert_dialog_texto_telefono)+":\n"+ tv_oculto_telefono.getText().toString(), Toast.LENGTH_SHORT).show();
    }


    /*
    * Metodo para llamar al 2º activity con startActivityForResult y el codigo de retorno
     */
    public void lanzarSegunda(View v){
        Intent intent = new Intent(this, Secundaria.class);
        startActivityForResult(intent, COD_PETICION_SECUNDARIA);
    }

    /*
    * Metodo al que volveremos del 2º activity
    * Guardamos los valores del Intent data en los textview ocultos *en este caso
    * tambien comprobamos el codigo, el resultcode y si el intent contiene los datos que queremos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COD_PETICION_SECUNDARIA){
            if (resultCode == RESULT_OK) {
                if(data.hasExtra("TELEFONO")||data.hasExtra("TEXTO")) {
                    //Inserto en los TextView la informacion del Intent data
                    tv_oculto_buscar.setText(data.getExtras().getString("TEXTO"));
                    tv_oculto_telefono.setText(data.getExtras().getString("TELEFONO"));
                    //Intente crear un bundle y meter ahi los datos recibidos
                    //Bundle estado =data.getExtras();
                    //estado.putString("TEXTO", data.getExtras().getString("TEXTO"));
                    //estado.putString("TELEFONO", data.getExtras().getString("TELEFONO"));
                    //Toast.makeText(Principal.this, data.getExtras().getString("TELEFONO"), Toast.LENGTH_SHORT).show();
                    //this.onRestoreInstanceState(estado);
                    //this.onSaveInstanceState(estado);
                    //this.onCreate(estado);
                }else{
                    Toast.makeText(Principal.this, "No hay nada", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*
    * Cogemos el bundle con los datos de la aplicacion y le insertamos los datos de
    * los textview ocultos, para que cuando salgamos de la actividad se guarden los datos
     */
    @Override
    protected void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        estado.putString("TELEFONO", tv_oculto_telefono.getText().toString());
        estado.putString("TEXTO", tv_oculto_buscar.getText().toString());
        //Destruyo el dialog
        this.removeDialog(1);
        //Toast.makeText(Principal.this, estado.getString("TELEFONO")+"saveinstance",Toast.LENGTH_SHORT).show();
    }//Fin

    /*
    * Recuperamos los datos del bundle antes guardado y los ponemos en los textview ocultos
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        tv_oculto_buscar.setText(savedInstanceState.getString("TEXTO"));
        tv_oculto_telefono.setText(savedInstanceState.getString("TELEFONO"));
        //Toast.makeText(Principal.this, savedInstanceState.getString("TELEFONO")+"restoreinstace", Toast.LENGTH_SHORT).show();
    }//Fin



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
}
