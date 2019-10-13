package smartdriver.smartcabtechnologies.smartcabmeter.Common;

public class User {
    String id;
    String email;
    String nombre;
    String profileImageUrl;
    private String calificacion;

    public User() {
    }

    public User(String id, String email, String nombre, String profileImageUrl) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.profileImageUrl = profileImageUrl;
    }




    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
