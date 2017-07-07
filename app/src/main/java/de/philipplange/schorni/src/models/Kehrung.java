package de.philipplange.schorni.src.models;


import nl.qbusict.cupboard.annotation.Column;

public class Kehrung {
    private Long _id;
    private String adresse;
    private String name;
    private String fon;
    private String kuerzel;
    private String info;
    private String bemerkungen;
    private boolean kassiert;
    private Long erledigt;
    @Column("table_id")
    private long tableId; // repräsentiert die liste in der die Kehrung angezeigt werden soll

    public long getId() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public Long getErledigt() {
        return erledigt;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public void setKassiert(boolean kassiert) {
        this.kassiert = kassiert;
    }

    public void setErledigt(Long erledigt) {
        this.erledigt = erledigt;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public Kehrung(long _id, String adresse, String name, String fon, String kuerzel, String info, String bemerkungen, boolean kassiert, long erledigt) {
        this._id = _id;
        this.adresse = adresse;
        this.name = name;
        this.fon = fon;
        this.kuerzel = kuerzel;
        this.info = info;
        this.bemerkungen = bemerkungen;
        this.kassiert = kassiert;
        this.erledigt = erledigt;
    }

    public Kehrung(String adresse, String name, String fon, String kuerzel, String info, String bemerkungen, boolean kassiert) {
        this.adresse = adresse;
        this.name = name;
        this.fon = fon;
        this.kuerzel = kuerzel;
        this.info = info;
        this.bemerkungen = bemerkungen;
        this.kassiert = kassiert;
    }

    public Kehrung() {
        adresse = "";
        name = "";
        fon = "";
        kuerzel = "";
        info = "";
        bemerkungen = "";
        kassiert = false;
    }

    @Override
    public String toString() {
        return "Kehrung{" +
                "id=" + _id +
                ", Listid=" + tableId +
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
