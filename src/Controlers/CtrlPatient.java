package Controlers;

import Entities.Consultation;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlPatient
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlPatient() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllPatients() throws SQLException {

        ArrayList<String> nom_patients = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT nomPatient FROM patient");
        rs = ps.executeQuery();
        while (rs.next()){
            nom_patients.add(rs.getString(1));
        }
        return nom_patients;
    }
    public int getIdPatientByName(String nomPat) throws SQLException {
        int idPatient = 0;
        ps = cnx.prepareStatement("SELECT idPatient FROM patient WHERE nomPatient LIKE ?");
        ps.setString(1,nomPat);
        rs = ps.executeQuery();
        while (rs.next()){
            idPatient = rs.getInt(1);
        }
        return idPatient;
    }
}
