package smartdriver.smartcabtechnologies.smartcabmeter.Common;

public class Taximetro_pojo {
    String destino_final;
    String origen;
    String estado;
    String distancia_recorrida;
    String duracion_viaje;
    Double tarifa_base;
    String valor_carrera;

    public Taximetro_pojo() {
    }

    public Taximetro_pojo(String destino_final, String origen, String estado, String distancia_recorrida, String duracion_viaje, Double tarifa_base, String valor_carrera) {
        this.destino_final = destino_final;
        this.origen = origen;
        this.estado = estado;
        this.distancia_recorrida = distancia_recorrida;
        this.duracion_viaje = duracion_viaje;
        this.tarifa_base = tarifa_base;
        this.valor_carrera = valor_carrera;
    }

    public String getDestino_final() {
        return destino_final;
    }

    public void setDestino_final(String destino_final) {
        this.destino_final = destino_final;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
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

    public Double getTarifa_base() {
        return tarifa_base;
    }

    public void setTarifa_base(Double tarifa_base) {
        this.tarifa_base = tarifa_base;
    }

    public String getValor_carrera() {
        return valor_carrera;
    }

    public void setValor_carrera(String valor_carrera) {
        this.valor_carrera = valor_carrera;
    }
}
