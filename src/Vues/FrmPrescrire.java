package Vues;

import Controlers.*;
import Entities.Consultation;
import Entities.Medicament;
import Tools.ModelJTable;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FrmPrescrire extends JFrame
{
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JLabel lblNumero;
    private JLabel lblDate;
    private JLabel lblNomMedecin;
    private JTextField txtNumeroConsultation;
    private JComboBox cboPatients;
    private JComboBox cboMedecins;
    private JButton btnInserer;
    private JTable tblMedicaments;
    private JPanel pnlDate;
    private JLabel lblNomPatient;
    private JLabel lblMedicaments;
    private JDateChooser dcDateConsultation;
    private ModelJTable modelJTable;
    private CtrlMedicament ctrlMedicament;
    private CtrlConsultation ctrlConsultation;
    private CtrlMedecin ctrlMedecin;
    private CtrlPatient ctrlPatient;
    private CtrlPrescrire ctrlPrescrire;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmPrescrire()
    {
        this.setTitle("Prescrire");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                dcDateConsultation = new JDateChooser();
                dcDateConsultation.setDateFormatString("yyyy-MM-dd");

                pnlDate.add(dcDateConsultation);
                modelJTable = new ModelJTable();
                ctrlMedicament = new CtrlMedicament();
                ctrlConsultation = new CtrlConsultation();
                ctrlMedecin = new CtrlMedecin();
                ctrlPatient = new CtrlPatient();

                try {
                    for(String nomPatient : ctrlMedecin.getAllMedecins()){
                        cboMedecins.addItem(nomPatient);
                    }
                    for (String nomPatient : ctrlPatient.getAllPatients()){
                        cboPatients.addItem(nomPatient);
                    }
                    modelJTable.loadDataAllMedicaments(ctrlMedicament.getAllMedicaments());
                    tblMedicaments.setModel(modelJTable);
                    txtNumeroConsultation.setText(String.valueOf(ctrlConsultation.getLastNumberOfConsultation()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // A vous de jouer

            }
        });
        btnInserer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dcDateConsultation.getDate() == null){
                    JOptionPane.showMessageDialog(null,"Veuillez saisir une date");
                }
                else{
                    ctrlPrescrire = new CtrlPrescrire();
                    int idConsultation = Integer.parseInt(txtNumeroConsultation.getText());
                    String dateConsult = sdf.format(dcDateConsultation.getDate());
                    int idPatient;
                    int idMedecin;
                    try {
                        idMedecin = ctrlMedecin.getIdMedecinByName(cboMedecins.getSelectedItem().toString());
                        idPatient = ctrlPatient.getIdPatientByName(cboPatients.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        ctrlConsultation.InsertConsultation(idConsultation,dateConsult,idPatient,idMedecin);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
//                    ArrayList<Medicament> lesMedicaments = new ArrayList<>();
//                    for(int i = 0;i<tblMedicaments.getRowCount();i++){
//                        if(Integer.parseInt(tblMedicaments.getValueAt(i,3).toString()) > 0){
//                            lesMedicaments.add(new Medicament(Integer.parseInt(tblMedicaments.getValueAt(i,0).toString()),tblMedicaments.getValueAt(i,1),Integer.parseInt(tblMedicaments.getValueAt(i,2).toString()),Integer.parseInt(tblMedicaments.getValueAt(i,3).toString())));
//                        }
//                    }
                    int compteur = 0;
                    for(int i = 0;i<tblMedicaments.getRowCount();i++){
                        if(Integer.parseInt(tblMedicaments.getValueAt(i,3).toString()) > 0){
                            compteur ++;
                            try {
                                ctrlPrescrire.InsertPrescrire(idConsultation,Integer.parseInt(tblMedicaments.getValueAt(i,0).toString()),Integer.parseInt(tblMedicaments.getValueAt(i,3).toString()));
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    try {
                        txtNumeroConsultation.setText(String.valueOf(ctrlConsultation.getLastNumberOfConsultation()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
//                    if(compteur == 0){
////                        ctrlPrescrire.InsertPrescrire(idConsultation,0,0);
//                    }
                }
                // A vous de jouer


            }
        });
    }
}
