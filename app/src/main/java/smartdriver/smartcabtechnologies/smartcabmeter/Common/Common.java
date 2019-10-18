package smartdriver.smartcabtechnologies.smartcabmeter.Common;

import android.location.Location;

public class Common {
    public static final String conductor_tbl = "Conductores_activos";
    public static final String conductores = "Conductores";
    public static final String token_tbl = "Tokens";
    public static final int PICK_IMAGE_REQUEST = 9999;
    public static final String rate_detal_tbl="Calificacion_cliente";


    public static User currentUser;

    public static Location mLastLocation=null;

   public static double base_fare=8.74;
   private static double time_rate=1.43;
   private static double distance_rate=6.0;

   public static double getPrice(double km, double min){
       return (base_fare+(time_rate*min)+(distance_rate*km));
   }


    public static final String baseURL="https://maps.googleapis.com";
    public static final String fcmURL="https://fcm.googleapis.com/";
    public static final String googleAPIUrl="https://maps.googleapis.com";

}
