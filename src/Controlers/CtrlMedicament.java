package Controlers;

import Entities.Consultation;
import Entities.Medicament;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedicament
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedicament() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Medicament> GetAllMedicamentsByIdConsultations(int idConsultation) throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT idMedoc,nomMedoc,prixMedoc AS prix , quantite FROM medicament ,prescrire,consultation WHERE idMedoc = prescrire.numMedoc AND numConsult = consultation.idConsult AND idConsult = ?");
        ps.setInt(1,idConsultation);
        rs = ps.executeQuery();
        while (rs.next()){
            lesMedicaments.add(new Medicament(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)));
        }
        return lesMedicaments;
    }
    public ArrayList<Medicament> getAllMedicaments() throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT idMedoc,nomMedoc,prixMedoc FROM medicament");
        rs = ps.executeQuery();
        while (rs.next()) {
            lesMedicaments.add(new Medicament(rs.getInt(1), rs.getString(2), rs.getDouble(3), 0));
        }
        return lesMedicaments;
    }
}
