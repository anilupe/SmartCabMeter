package smartdriver.smartcabtechnologies.smartcabmeter.Common;

public class User {
    String id;
    String email;
    String nombre;
    String profileImageUrl;
    private String calificacion;
    String username;
    String tipo_vehiculo;
    String deleted;

    public User() {
    }

    public User(String id, String email, String nombre, String profileImageUrl, String calificacion, String username, String tipo_vehiculo, String deleted) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.profileImageUrl = profileImageUrl;
        this.calificacion = calificacion;
        this.username = username;
        this.tipo_vehiculo = tipo_vehiculo;
        this.deleted = deleted;
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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
