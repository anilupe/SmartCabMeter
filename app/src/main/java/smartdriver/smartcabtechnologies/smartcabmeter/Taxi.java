package smartdriver.smartcabtechnologies.smartcabmeter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smartdriver.smartcabtechnologies.smartcabmeter.AdapterBackend.Driver;
import smartdriver.smartcabtechnologies.smartcabmeter.AdapterBackend.Login;
import smartdriver.smartcabtechnologies.smartcabmeter.Common.Common;
import smartdriver.smartcabtechnologies.smartcabmeter.Common.User;
import smartdriver.smartcabtechnologies.smartcabmeter.Common.UserClient;

public class Taxi extends FragmentActivity implements View.OnClickListener  {
    private EditText TextEmail;
    private TextView btn1;
    private EditText TextPass;
    private Button btnIniciar;
    private CheckBox estado;
    DatabaseReference reference;
    DatabaseReference mDatabase;
    RelativeLayout activity_login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener listener;
    TextView contra_olvidada;
    String nombreUsuario;
    String deleted;
    Retrofit.Builder builder=new Retrofit.Builder()
            .baseUrl("https://smartcabback.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit =builder.build();
    UserClient userClient =retrofit.create(UserClient.class);

    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.scrollToPositionWithOffset(1,10);
        lm.isSmoothScrollbarEnabled();
        TextEmail = findViewById(R.id.edtEmail);
        TextPass = findViewById(R.id.edtPass);
        btnIniciar = findViewById(R.id.btniniciar);
        estado= findViewById(R.id.estado);
        activity_login= findViewById(R.id.rootLayout);
        progressDialog = new ProgressDialog(this);
        contra_olvidada= findViewById(R.id.txt_forgot_password);
        contra_olvidada= findViewById(R.id.txt_forgot_password);
        contra_olvidada.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showDialogContra().show();
                return false;
            }
        });
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    //no esta logeado
                    //Toast.makeText(getApplicationContext(),"Inicie sesión",Toast.LENGTH_LONG).show();
                }
                else{
                    // abrirMiCuenta();
                    // Toast.makeText(getApplicationContext(),"Sesión Activa",Toast.LENGTH_LONG).show();
                }
            }
        };
        btn1=findViewById(R.id.b_1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarsesion();
            }
        });
        CargarPreferencias();
        CargarPreferenciaMail();
        findViewById(R.id.b_1).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        TextPass.setVisibility(View.VISIBLE);
        btnIniciar.setVisibility(View.VISIBLE);
        estado.setVisibility(View.VISIBLE);
        contra_olvidada.setVisibility(View.VISIBLE);
        btn1.setVisibility(View.GONE);

    }

    private AlertDialog showDialogContra() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_contrasena_olvidada, null);
        final MaterialEditText edtEmail= v.findViewById(R.id.edtEmail);
        final Button btnFire = v.findViewById(R.id.confirmar);
        Button btnCancel = v.findViewById(R.id.cancelar);
        builder.setView(v);
        alertDialog = builder.create();
        btnFire.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog waitingDialog=new SpotsDialog(Taxi.this);
                        waitingDialog.show();
                        firebaseAuth.sendPasswordResetEmail(edtEmail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        waitingDialog.dismiss();
                                        Snackbar.make(activity_login, "Please check your e-mail", Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(activity_login, ""+e.getMessage(),Snackbar.LENGTH_LONG).show();
                            }
                        });
                        alertDialog.dismiss();
                    }
                }
        );
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                }
        );
        return alertDialog;
    }
    private void CargarPreferencias() {
        SharedPreferences mispreferencias=getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE);
        estado.setChecked(mispreferencias.getBoolean("checked",false));
        TextPass.setText(mispreferencias.getString("pass",""));
    }
    private void CargarPreferenciaMail(){
        SharedPreferences preferences=getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        estado.setChecked(preferences.getBoolean("checked", false));
        TextEmail.setText(preferences.getString("email",""));
    }
    private void GuardarPreferenciasMail(){
        SharedPreferences preferences=getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1=preferences.edit();
        boolean valor1=estado.isChecked();
        String mail=TextEmail.getText().toString();
        editor1.putBoolean("checked",valor1);
        editor1.putString("email",mail);
        editor1.commit();
    }
    private void GuardarPreferencias() {
        SharedPreferences mispreferencias=getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=mispreferencias.edit();
        boolean valor=estado.isChecked();
        String pass=TextPass.getText().toString();
        editor.putBoolean("checked",valor);
        editor.putString("pass",pass);
        editor.commit();

    }
    public void iniciarsesion(){
        //obtenemos el email y la contraseña
        final String email = TextEmail.getText().toString();
        String pass = TextPass.getText().toString();
        //verificamos que las cajas de texto no esten vacias
        if(!email.isEmpty()&& !pass.isEmpty()){
            closeTecladoMovil();
            progressDialog.setMessage("Performing online consultation ...");
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference("Usuarios").child("Conductores")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Common.currentUser=dataSnapshot.getValue(User.class);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                        String userid=firebaseUser.getUid();
                        //traer el nombre de usuairio para comparar
                        mDatabase=FirebaseDatabase.getInstance().getReference("Usuarios").child("Conductores").child(userid);
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User conductor=dataSnapshot.getValue(User.class);
                                deleted=conductor.getDeleted();


                                if (deleted.equals("N")) {
                                    nombreUsuario = conductor.getUsername();
                                    generarTokenBackend(nombreUsuario);
                                    GuardarPreferencias();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),getString(R.string.Credenciales),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        GuardarPreferencias();
        GuardarPreferenciasMail();
    }

    String token;
    private void generarTokenBackend(String nombreUsuario) {
        Login login =new Login(nombreUsuario, TextPass.getText().toString());
        Call<Driver> call=userClient.login(login);
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if (response.isSuccessful()){
                    token=response.body().getToken();
                    }

                    if (token!=null){

                        abrirMiCuenta();
                    }
                    else{
                        Toast.makeText(Taxi.this,"error ",Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Toast.makeText(Taxi.this, "error ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void closeTecladoMovil() {
        View view=this.getCurrentFocus();
        if (view !=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0 );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user){
        if(user!=null){

            Intent intent=new Intent(Taxi.this,Inicio.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(listener !=null){
            firebaseAuth.removeAuthStateListener(listener);

        }
    }
    private void abrirMiCuenta(){
        startActivity(new Intent(getApplicationContext(),Inicio.class));
        overridePendingTransition(R.anim.slide_rigth_to_left_enter, R.anim.slide_rigth_to_left_exit);
        finish();
    }
}