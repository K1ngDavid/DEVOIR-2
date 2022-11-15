package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedecin
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedecin() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllMedecins() throws SQLException {
        ArrayList<String> nom_medecins = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT nomMedecin FROM medecin");
        rs = ps.executeQuery();
        while (rs.next()){
            nom_medecins.add(rs.getString(1));
        }
        return nom_medecins;
    }

    public int getIdMedecinByName(String nomMed) throws SQLException {
        int idMedecin = 0;
        ps = cnx.prepareStatement("SELECT idMedecin FROM medecin WHERE nomMedecin LIKE ?");
        ps.setString(1,nomMed);
        rs = ps.executeQuery();
        while (rs.next()){
            idMedecin = rs.getInt(1);
        }
        return idMedecin;

    }
}
