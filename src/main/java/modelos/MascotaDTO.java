package modelos;

import java.time.LocalDate;

public class MascotaDTO {

    private int id;                  // PK
    private String chip;            // único
    private String nombre;
    private double peso;
    private LocalDate fechaNacimiento;
    private String tipo;            // perro, gato, otros

    private Integer veterinarioId;      // FK -> Veterinario

    public MascotaDTO() {
    }

    public MascotaDTO(int id, String chip, String nombre, double peso,
            LocalDate fechaNacimiento, String tipo, Integer veterinarioId) {
        this.id = id;
        this.chip = chip;
        this.nombre = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
        this.veterinarioId = veterinarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    @Override
    public String toString() {
        return "MascotaDTO [id=" + id + ", chip=" + chip + ", nombre=" + nombre + ", peso=" + peso
                + ", fechaNacimiento=" + fechaNacimiento + ", tipo=" + tipo + ", veterinarioId=" + veterinarioId + "]";
    }

}
