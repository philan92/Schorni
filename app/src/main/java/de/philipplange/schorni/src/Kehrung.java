package de.philipplange.schorni.src;

import java.util.Date;


public class Kehrung {
    private int id;
    private String adresse;
    private String name;
    private String fon;
    private String kuerzel;
    private String info;
    private String bemerkungen;
    private boolean kassiert;
    private Date erledigt;

    public int getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getName() {
        return name;
    }

    public String getFon() {
        return fon;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public String getInfo() {
        return info;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public boolean isKassiert() {
        return kassiert;
    }

    public Date getErledigt() {
        return erledigt;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public void setKassiert(boolean kassiert) {
        this.kassiert = kassiert;
    }

    public void setErledigt(Date erledigt) {
        this.erledigt = erledigt;
    }

    @Override
    public String toString() {
        return "Kehrung{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", name='" + name + '\'' +
                ", fon='" + fon + '\'' +
                ", kuerzel='" + kuerzel + '\'' +
                ", info='" + info + '\'' +
                ", bemerkungen='" + bemerkungen + '\'' +
                ", kassiert=" + kassiert +
                ", erledigt=" + erledigt +
                '}';
    }
}
