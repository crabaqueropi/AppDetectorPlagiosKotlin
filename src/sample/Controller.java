package sample;

import com.sun.glass.ui.CommonDialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Controller {

    public FileChooser fc = new FileChooser();
    public File file1;
    public File file2;
    @FXML
    public ImageView iconVarios;
    @FXML
    public ImageView iconDos;
    @FXML
    public WebView webViewId1;
    @FXML
    public WebView webViewId2;

    @FXML
    private AnchorPane panelDosArchivos;
    @FXML
    private AnchorPane panelVariosArchivos;

    @FXML
    private Label labArchivo1;
    @FXML
    private Label labArchivo2;

    private final String editingTemplate =
            "<!doctype html>" +
                    "<html>" +
                    "<head>" +
                    "  <link rel=\"stylesheet\" href=\"http://codemirror.net/lib/codemirror.css\">" +
                    "  <script src=\"http://codemirror.net/lib/codemirror.js\"></script>" +
                    "  <script src=\"http://codemirror.net/mode/clike/clike.js\"></script>" +
                    "  <style>" +
                    "       body{" +
                    "           height: 100%;" +
                    "           width: 100%;" +
                    "       }" +
                    "       form{" +
                    "           height: 100%;" +
                    "           width: 100%;" +
                    "       }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +

                    "<h3>Coincidencia 1</h3>" +
                    "<form><textarea id=\"code\" name=\"code\">\n" +
                    "${code}" +
                    "</textarea></form>" +
                    "<script>" +
                    "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" +
                    "    lineNumbers: true," +
                    "    firstLineNumber: 50," +
                    "    readOnly: true," +
                    "    matchBrackets: true," +
                    "    mode: \"text/x-kotlin\"" +
                    "  });" +
                    "</script>" +

                    "<h3>Coincidencia 2</h3>" +
                    "<form><textarea id=\"code2\" name=\"code2\">\n" +
                    "${code}" +
                    "</textarea></form>" +
                    "<script>" +
                    "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code2\"), {" +
                    "    lineNumbers: true," +
                    "    firstLineNumber: 30," +
                    "    readOnly: true," +
                    "    matchBrackets: true," +
                    "    mode: \"text/x-kotlin\"" +
                    "  });" +
                    "</script>" +
                    "</body>" +
                    "</html>";

    private String editingCode;

    private String applyEditingTemplate() {
        return editingTemplate.replace("${code}", editingCode);
    }

    public void setCodeUno(String newCode) {
        this.editingCode = newCode;
        webViewId1.getEngine().loadContent(applyEditingTemplate());
    }

    public void setCodeDos(String newCode) {
        this.editingCode = newCode;
        webViewId2.getEngine().loadContent(applyEditingTemplate());
    }

    @FXML
    void btnDosArchivos(MouseEvent event) {
        panelDosArchivos.setVisible(true);
        panelVariosArchivos.setVisible(false);
    }

    @FXML
    void btnVariosArchivos(MouseEvent event) {
        panelDosArchivos.setVisible(false);
        panelVariosArchivos.setVisible(true);
    }

    @FXML
    void btnSeleccionarMultiplesArchivos(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        List<File> f = fc.showOpenMultipleDialog(null);
        for (File file : f) {
            System.out.println(file.getAbsolutePath());
        }
    }

    @FXML
    void btnseleccionarArchivoUno(MouseEvent event) throws IOException {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        file1 = fc.showOpenDialog(null);
        if (file1 != null) {
            labArchivo1.setText("Archivo seleccionado: " + file1.getName());

            BufferedReader br = new BufferedReader(new FileReader(file1));

            String codigoCompleto = "";
            String st;
            while ((st = br.readLine()) != null)
                codigoCompleto += st + "\n";

            setCodeUno(codigoCompleto);
        }
    }

    @FXML
    void btnseleccionarArchivoDos(MouseEvent event) throws IOException {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        file2 = fc.showOpenDialog(null);
        if (file2 != null) {
            labArchivo2.setText("Archivo seleccionado: " + file2.getName());

            BufferedReader br = new BufferedReader(new FileReader(file2));

            String codigoCompleto = "";
            String st;
            while ((st = br.readLine()) != null)
                codigoCompleto += st + "\n";

            setCodeDos(codigoCompleto);
        }
    }

    @FXML
    void btnVerificarPlagio(MouseEvent event) throws IOException {
        if (file1 == null || file2 == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archivos faltantes");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar los dos archivos a verificar.");
            alert.showAndWait();
        }else {

        }
    }
}

