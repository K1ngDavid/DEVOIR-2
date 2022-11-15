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

public class CtrlConsultation
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlConsultation() {
        cnx = ConnexionBDD.getCnx();
    }
    //SELECT DISTINCT (idPatient) , dateConsult,nomPatient,nomMedecin,quantite * (prixMedoc * tauxRemb%100/100) AS montant FROM patient,consultation,medecin,prescrire,medicament,vignette WHERE idPatient = consultation.numPatient AND numMedecin = medecin.idMedecin AND idConsult = prescrire.numConsult AND idMedoc = idVignette GROUP BY idConsult
    public ArrayList<Consultation> GetAllConsultations() throws SQLException {
        ArrayList<Consultation> lesConsultations = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT idConsult,dateConsult,nomPatient,nomMedecin,quantite*prixMedoc*(tauxRemb%100/100) FROM consultation,patient,prescrire,medicament,vignette,medecin WHERE idPatient = consultation.numPatient AND numMedecin = medecin.idMedecin AND idConsult = prescrire.numConsult AND numMedoc = idMedoc AND numVignette = vignette.idVignette GROUP BY idConsult");
        rs = ps.executeQuery();
        while (rs.next()){
            lesConsultations.add(new Consultation(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5)));
        }
        return lesConsultations;
    }
    public int getLastNumberOfConsultation() throws SQLException {
        int last_consultation = 0;
        ps = cnx.prepareStatement("SELECT MAX(idConsult) FROM consultation");
        rs = ps.executeQuery();
        while (rs.next()){
            last_consultation = rs.getInt(1) + 1;
        }
        return last_consultation;
    }
    public void InsertConsultation(int idConsult, String dateConsultation, int numPatient,int numMedecin) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO consultation VALUES(?,?,?,?)");
        System.out.println("INSERT INTO consultation VALUES("+ idConsult+",'"+dateConsultation+"',"+numPatient+","+numMedecin+")");
        ps.setInt(1,idConsult);
        ps.setString(2,dateConsultation);
        ps.setInt(3,numPatient);
        ps.setInt(4,numMedecin);
        ps.executeUpdate();
        ps.close();
    }
}
