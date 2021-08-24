package com.example.taller_1;

public class Pais {
    String capital;
    String nobmrePais;
    String nombreInt;
    String sigla;

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getNobmrePais() {
        return nobmrePais;
    }

    public void setNobmrePais(String nobmrePais) {
        this.nobmrePais = nobmrePais;
    }

    public String getNombreInt() {
        return nombreInt;
    }

    public void setNombreInt(String nombreInt) {
        this.nombreInt = nombreInt;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return  this.nobmrePais;
   }
}
