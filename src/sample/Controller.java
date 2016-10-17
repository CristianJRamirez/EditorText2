package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import javafx.stage.Stage;
import sample.Main.*;

public class Controller extends Component {
    //region Componentes
    public MenuItem btExit;
    public MenuItem btCopy;
    public MenuItem btDelete;
    public MenuItem btCut;
    public MenuItem btPaste;
    public MenuItem btDeshacer;
    public MenuItem btInfo;
    public TextArea txtArea;
    public MenuItem btBig;
    public MenuItem btSmall;
    public Button btCopiar;
    public Button btCortar;
    public Button btPegar;
    public MenuItem btT12;
    public MenuItem btTimes;
    public MenuItem btComics;
    public MenuItem btArial;
    public Menu menuEdit;
    public RadioButton chkArial;
    public RadioButton chkComic;
    public RadioButton chkTimes;
    public RadioButton chkBig;
    public RadioButton chkSmall;
    public RadioButton chk12;
    //endregion
    public String PATH;


    public void Exit() {
        System.out.println("Exit");
        Platform.exit();
    }

    public void Copiar(ActionEvent actionEvent) {
        System.out.println("Copiar");
        txtArea.copy();

    }

    public void Delete(ActionEvent actionEvent) {
        System.out.println("Delete");
        txtArea.deletePreviousChar();
    }

    public void Cut(ActionEvent actionEvent) {

        System.out.println("Cut");
        txtArea.cut();
    }

    public void Paste(ActionEvent actionEvent) {
        System.out.println("Paste");
        txtArea.paste();
    }

    public void Deshacer(ActionEvent actionEvent) {
        System.out.println("Deshacer");
        txtArea.undo();
    }


    public void Info(ActionEvent actionEvent) {
        System.out.println("Info");
    }


//menu Font
    public void setArial(ActionEvent actionEvent) {

        txtArea.setFont(new Font("Arial", 12));
        System.out.println("Arial");
        chkComic.setSelected(false);
        chkArial.setSelected(true);
        chkTimes.setSelected(false);


    }

    public void SetComicSans(ActionEvent actionEvent) {

        txtArea.setFont(new Font("Comic Sans ", 12));
        System.out.println("Comic Sans");
        chkComic.setSelected(true);
        chkArial.setSelected(false);
        chkTimes.setSelected(false);
    }

    public void setTimes(ActionEvent actionEvent) {

        txtArea.setFont(new Font("times new roman", 12));
        System.out.println("Times new roman");
        chkComic.setSelected(false);
        chkArial.setSelected(false);
        chkTimes.setSelected(true);
    }

    //Menu Tamany
    public void setBig(ActionEvent actionEvent) {
        txtArea.setFont(new Font(txtArea.getFont().getName(), txtArea.getFont().getSize() + 2));
        System.out.println("Big");
        chkSmall.setSelected(false);
        chk12.setSelected(false);
        chkBig.setSelected(true);
    }

    public void setSmall(ActionEvent actionEvent) {
        txtArea.setFont(new Font(txtArea.getFont().getName(), txtArea.getFont().getSize() - 2));
        System.out.println("Small");
        chkBig.setSelected(false);
        chk12.setSelected(false);
        chkSmall.setSelected(true);
    }

    public void setT12(ActionEvent actionEvent) {
        txtArea.setFont(new Font(12));
        System.out.println("12");
        chkSmall.setSelected(false);
        chkBig.setSelected(false);
        chk12.setSelected(true);
    }


    private void btDisable() {
        if (txtArea.getSelectedText().isEmpty()) {
            btCopy.setDisable(true);
            btCut.setDisable(true);
            btCopiar.setDisable(true);
            btCortar.setDisable(true);
        } else {
            btCopy.setDisable(false);
            btCut.setDisable(false);
            btCopiar.setDisable(false);
            btCortar.setDisable(false);
        }
    }

    public void setEdit(ActionEvent actionEvent) {
        btDisable();

    }

    public void Abrir(ActionEvent actionEvent) {
        String aux = "";
        String texto = "";

        try {
            JFileChooser file = new JFileChooser();
            //file.getName();
            file.showOpenDialog(this);
            /*abrimos el archivo seleccionado*/
            File archivo = file.getSelectedFile();
            Stage stage= (Stage) txtArea.getScene().getWindow();
            stage.setTitle(archivo.getName());
            if (archivo != null) {
                FileReader archivos = new FileReader(archivo);
                BufferedReader lee = new BufferedReader(archivos);
                while ((aux = lee.readLine()) != null) {
                    texto += aux + "\n";
                }
                lee.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + "" +"\nNo se ha encontrado el archivo", "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }
        txtArea.setText(texto);
    }

    public void Guardar(ActionEvent actionEvent) {
        String texto = txtArea.getText();//variable para comparacion

        if (texto.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
            JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
        }else{

            JFileChooser fileChooser = new JFileChooser();

            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION)
                {//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    PrintWriter printwriter = new PrintWriter(JFC);

                    if(new File(PATH).exists())//si existe el fichero
                    {
                        if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this,"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION));
                        printwriter.print(txtArea.getText());//escribe en el archivo el contenido del txtArea
                        printwriter.close();//cierra el archivo
                    }

                    JOptionPane.showMessageDialog(null,"Guardado exitoso!", "Fichero guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);

                }
            }catch (Exception e){//por alguna excepcion salta un mensaje de error
                JOptionPane.showMessageDialog(null,"Error al guardar el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
