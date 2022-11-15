package Vues;

import Controlers.CtrlConsultation;
import Controlers.CtrlMedicament;
import Tools.ModelJTable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class FrmConsulter extends JFrame
{
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JLabel lblConsultations;
    private JTable tblConsultations;
    private JLabel lblMedicaments;
    private JTable tblMedicaments;

    private ModelJTable modelJTable;
    private CtrlConsultation ctrlConsultation;
    private CtrlMedicament ctrlMedicament;
    int idConsultation;

    public FrmConsulter()
    {
        this.setTitle("Consulter");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                // A vous de jouer
                modelJTable = new ModelJTable();
                ctrlConsultation = new CtrlConsultation();

                try {
                    modelJTable.loadDataConsultations(ctrlConsultation.GetAllConsultations());
                    tblConsultations.setModel(modelJTable);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        //prix de chaque medicament et quantité
        tblConsultations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // A vous de jouer
                ctrlMedicament = new CtrlMedicament();
                try {
                    idConsultation = Integer.parseInt(tblConsultations.getValueAt(tblConsultations.getSelectedRow(),0).toString());
                    modelJTable = new ModelJTable();
                    modelJTable.loadDataMedicaments(ctrlMedicament.GetAllMedicamentsByIdConsultations(idConsultation));
                    tblMedicaments.setModel(modelJTable);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
