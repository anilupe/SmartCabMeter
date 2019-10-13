package smartdriver.smartcabtechnologies.smartcabmeter.Common;

public class Solicitudes {
    String destino;
    String origen;
    String nombre;
    String estado;

    String distancia_recorrida;
    String duracion_viaje;

    Long tarifa_base;
    String valor_carrera;


    public Solicitudes() {
    }

    public Solicitudes(String destino, String origen, String nombre, String estado, String distancia_recorrida, String duracion_viaje, Long tarifa_base, String valor_carrera) {
        this.destino = destino;
        this.origen = origen;
        this.nombre = nombre;
        this.estado = estado;
        this.distancia_recorrida = distancia_recorrida;
        this.duracion_viaje = duracion_viaje;
        this.tarifa_base = tarifa_base;
        this.valor_carrera = valor_carrera;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDistancia_recorrida() {
        return distancia_recorrida;
    }

    public void setDistancia_recorrida(String distancia_recorrida) {
        this.distancia_recorrida = distancia_recorrida;
    }

    public String getDuracion_viaje() {
        return duracion_viaje;
    }

    public void setDuracion_viaje(String duracion_viaje) {
        this.duracion_viaje = duracion_viaje;
    }

    public Long getTarifa_base() {
        return tarifa_base;
    }

    public void setTarifa_base(Long tarifa_base) {
        this.tarifa_base = tarifa_base;
    }

    public String getValor_carrera() {
        return valor_carrera;
    }

    public void setValor_carrera(String valor_carrera) {
        this.valor_carrera = valor_carrera;
    }
}
