package smartdriver.smartcabtechnologies.smartcabmeter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import smartdriver.smartcabtechnologies.smartcabmeter.Common.Common;
import smartdriver.smartcabtechnologies.smartcabmeter.Common.Solicitudes;


public class Inicio extends AppCompatActivity {

TextView total, tbase, drecorrida, dcarrera, vtotal;
TextView origen, destino;
Button cerrar;
        TextView fechaCompleta;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, d-' 'yyyy  hh:mm a", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);

                //no se apague pantalla
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                setContentView(R.layout.activity_inicio);

                fechaCompleta=findViewById(R.id.fecha);


                fechaCompleta.setText(fecha);
               total=findViewById(R.id.total);
               tbase=findViewById(R.id.tarifabase);
               drecorrida=findViewById(R.id.distanciar);
               dcarrera=findViewById(R.id.duracionc);
               vtotal=findViewById(R.id.costot);
               origen=findViewById(R.id.txtFrom);
               destino=findViewById(R.id.txtTo);
               cerrar=findViewById(R.id.cerrar_sesion);
               cerrar.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       signOut();
                   }
               });


                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.d("Identificacion", uid);
                Query query=FirebaseDatabase.getInstance().getReference().child("Solicitudes").orderByChild("conductor_acepta").equalTo(uid);
                query.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        Solicitudes chat=snapshot.getValue(Solicitudes.class);
                                        if (chat.getEstado().equals("Cobrando")){
                                                total.setText(chat.getValor_carrera());
                                                tbase.setText(String.format("$ %.2f", Common.base_fare));
                                                drecorrida.setText(chat.getDistancia_recorrida()+ " km");
                                                dcarrera.setText(chat.getDuracion_viaje());
                                                vtotal.setText(chat.getValor_carrera());
                                                origen.setText(chat.getOrigen());
                                                destino.setText(chat.getDestino());
                                        }
                                        else {
                                                total.setText("$ 0.00");
                                                tbase.setText(String.format("$ %.2f", Common.base_fare));
                                                drecorrida.setText("0 km");
                                                dcarrera.setText("0 ");
                                                vtotal.setText("$0.0");
                                                origen.setText("");
                                                destino.setText("");
                                        }
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });




        }

    private void signOut() {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder=new android.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder=new android.app.AlertDialog.Builder(this);

        builder.setMessage("Do you want to log out?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Inicio.this, Taxi.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}