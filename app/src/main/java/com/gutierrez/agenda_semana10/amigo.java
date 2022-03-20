package com.gutierrez.agenda_semana10;

public class amigo {
    String idAmigo;
    String Name;
    String Number;
    String Email;

    public amigo(String idAmigo, String name, String number, String email) {
        this.idAmigo = idAmigo;
        this.Name = name;
        this.Number = number;
        this.Email = email;
    }

    public String getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(String idAmigo) {
        this.idAmigo = idAmigo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
