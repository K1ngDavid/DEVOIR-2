package Tools;



import Entities.Consultation;
import Entities.Medicament;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTable extends AbstractTableModel {
    private String[] colonnes;
    private Object[][] lignes;

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    @Override
    public int getRowCount() {
        return lignes.length;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return lignes[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        lignes[row][column] = value;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 3 ;
    }

    public void loadDataConsultations(ArrayList<Consultation> lesConsultations){
        colonnes = new String[]{"Numéro","Date","Nom du patient","Nom du médecin","Montant"};
        lignes = new Object[lesConsultations.size()][5];
        for(int i = 0;i<lesConsultations.size();i++){
            lignes[i][0] = lesConsultations.get(i).getNumero();
            lignes[i][1] = lesConsultations.get(i).getDate();
            lignes[i][2] = lesConsultations.get(i).getNomPatient();
            lignes[i][3] = lesConsultations.get(i).getNomMedecin();
            lignes[i][4] = lesConsultations.get(i).getMontant();
        }
        fireTableChanged(null);
    }

    public void loadDataMedicaments(ArrayList<Medicament> lesMedicaments){
        colonnes = new String[]{"Numéro","Nom","Prix"};
        lignes = new Object[lesMedicaments.size()][3];
        for(int i = 0;i<lesMedicaments.size();i++){
            lignes[i][0] = lesMedicaments.get(i).getNumero();
            lignes[i][1] = lesMedicaments.get(i).getNom();
            lignes[i][2] = lesMedicaments.get(i).getPrix();
        }
        fireTableChanged(null);
    }
    public void loadDataAllMedicaments(ArrayList<Medicament> lesMedicaments){
        colonnes = new String[]{"Numéro","Nom","Prix","Quantité"};
        lignes = new Object[lesMedicaments.size()][4];
        for(int i = 0;i<lesMedicaments.size();i++){
            lignes[i][0] = lesMedicaments.get(i).getNumero();
            lignes[i][1] = lesMedicaments.get(i).getNom();
            lignes[i][2] = lesMedicaments.get(i).getPrix();
            lignes[i][3] = lesMedicaments.get(i).getQuantite();

        }
        fireTableChanged(null);
    }

}
