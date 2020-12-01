import com.sun.glass.ui.CommonDialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.*;

import javax.swing.*;

public class Controller {

    public FileChooser fc = new FileChooser();
    public File file1;
    public File file2;
    public String codigoCompletoUno = "";
    public String codigoCompletoDos = "";
    @FXML
    public ImageView iconVarios;
    @FXML
    public ImageView iconDos;
    @FXML
    public WebView webViewId1;
    @FXML
    public WebView webViewId2;
    @FXML
    public ImageView imagenLista1;
    @FXML
    public ImageView imagenLista2;
    @FXML
    public ImageView imagenPrincipal;
    @FXML
    public Text TextoPrincipal;
    @FXML
    public RadioButton radioBtnBaja;
    public RadioButton radioBtnMedia;
    public RadioButton radioBtnAlta;

    @FXML
    private Label nombreArchivoUno;
    @FXML
    private Label nombreArchivoDos;

    @FXML
    private AnchorPane panelDosArchivos;
    @FXML
    private AnchorPane panelVariosArchivos;

    @FXML
    private Label labArchivo1;
    @FXML
    private Label labArchivo2;

    private final String plantillaInicio =
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
                    "       } " +
                    "       .CodeMirror{" +
                    "           height: auto;" +
                    "       }" +
                    "  </style>" +
                    "</head>" +
                    "<body>";

    private final String plantillaCoincidencia =
            "<h3>Coincidencia ${numCoincidencia}</h3>" +
                    "<form><textarea id=\"${codeID}\" name=\"${codeID}\">\n" +
                    "${code}" +
                    "</textarea></form>" +
                    "<script>" +
                    "  var editor = CodeMirror.fromTextArea(document.getElementById(\"${codeID}\"), {" +
                    "    lineNumbers: true," +
                    "    firstLineNumber: ${primerNumLinea}," +
                    "    readOnly: true," +
                    "    matchBrackets: true," +
                    "    mode: \"text/x-kotlin\"" +
                    "  });" +
                    "</script>";

    private final String plantillaFinal =
            "</body>" +
                    "</html>";


    public void setCodeUno(String newHtmlCode) {
        webViewId1.getEngine().loadContent(newHtmlCode);
    }

    public void setCodeDos(String newHtmlCode) {
        webViewId2.getEngine().loadContent(newHtmlCode);
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
            webViewId1.setVisible(false);
            webViewId2.setVisible(false);
            nombreArchivoUno.setVisible(false);
            nombreArchivoDos.setVisible(false);
            imagenPrincipal.setVisible(true);
            TextoPrincipal.setVisible(true);

            labArchivo1.setText("Archivo seleccionado: " + file1.getName());
            nombreArchivoUno.setText(file1.getName());

            imagenLista1.setVisible(true);

            BufferedReader br = new BufferedReader(new FileReader(file1));

            String st;
            while ((st = br.readLine()) != null)
                codigoCompletoUno += st + "\n";


        }
    }

    @FXML
    void btnseleccionarArchivoDos(MouseEvent event) throws IOException {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        file2 = fc.showOpenDialog(null);
        if (file2 != null) {
            webViewId1.setVisible(false);
            webViewId2.setVisible(false);
            nombreArchivoUno.setVisible(false);
            nombreArchivoDos.setVisible(false);
            imagenPrincipal.setVisible(true);
            TextoPrincipal.setVisible(true);


            labArchivo2.setText("Archivo seleccionado: " + file2.getName());
            nombreArchivoDos.setText(file2.getName());

            imagenLista2.setVisible(true);

            BufferedReader br = new BufferedReader(new FileReader(file2));

            String st;
            while ((st = br.readLine()) != null)
                codigoCompletoDos += st + "\n";

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
        } else {
            try {
                KotlinLexer lexer1;
                KotlinLexer lexer2;

                lexer1 = new KotlinLexer(CharStreams.fromFileName(file1.getAbsolutePath()));
                lexer2 = new KotlinLexer(CharStreams.fromFileName(file2.getAbsolutePath()));

                List<Integer> listaTipoTokens1 = new ArrayList<>();
                List<Token> listaTokens1 = new ArrayList<>();
                for (Token token = lexer1.nextToken(); token.getType() != Token.EOF; token = lexer1.nextToken()) {
                    if (token.getType() != 3 && token.getType() != 5) {
                        listaTipoTokens1.add(token.getType());
                        listaTokens1.add(token);
                    }
                    //System.out.println(token.getType() + "--" + token.getText());
                }

                List<Integer> listaTipoTokens2 = new ArrayList<>();
                List<Token> listaTokens2 = new ArrayList<>();
                for (Token token = lexer2.nextToken(); token.getType() != Token.EOF; token = lexer2.nextToken()) {
                    if (token.getType() != 3 && token.getType() != 5) {
                        listaTipoTokens2.add(token.getType());
                        listaTokens2.add(token);
                    }
                    //System.out.println(token.getType() + "--" + token.getText());
                }

                int rigurosidad = 40;

                String[] lineasCodigUno = codigoCompletoUno.split("\n");
                String[] lineasCodigDos = codigoCompletoDos.split("\n");

                String htmlCodigoUno = plantillaInicio;
                String htmlCodigoDos = plantillaInicio;

                String auxCoincidencia = "";
                String auxCodigo = "";
                int coincidencias = 0;

                List<Integer> listaAux = new ArrayList<>();
                int contador = 0;

                for (int i = 0; i < listaTipoTokens2.size(); i++) {
                    if (contador < rigurosidad) {
                        listaAux.add(listaTipoTokens2.get(i));
                        contador++;
                    } else {
                        contador = 0;
                        listaAux.clear();
                    }

                    if (contador == rigurosidad) {
                        int resp = Collections.indexOfSubList(listaTipoTokens1, listaAux);
                        if (resp != -1) {
                            coincidencias++;

                            auxCoincidencia = plantillaCoincidencia;
                            auxCoincidencia = auxCoincidencia.replace("${numCoincidencia}", Integer.toString(coincidencias));
                            auxCoincidencia = auxCoincidencia.replace("${codeID}", ("code" + coincidencias));
                            for (int j = listaTokens1.get(resp).getLine() - 1; j < listaTokens1.get(resp + rigurosidad).getLine(); j++) {
                                auxCodigo += lineasCodigUno[j] + "\n";
                            }
                            auxCoincidencia = auxCoincidencia.replace("${code}", auxCodigo);
                            auxCoincidencia = auxCoincidencia.replace("${primerNumLinea}", Integer.toString(listaTokens1.get(resp).getLine()));
                            htmlCodigoUno += auxCoincidencia;
                            auxCodigo = "";

                            auxCoincidencia = plantillaCoincidencia;
                            auxCoincidencia = auxCoincidencia.replace("${numCoincidencia}", Integer.toString(coincidencias));
                            auxCoincidencia = auxCoincidencia.replace("${codeID}", ("code" + coincidencias));
                            for (int k = listaTokens2.get(i - rigurosidad + 1).getLine() - 1; k < listaTokens2.get(i).getLine(); k++) {
                                auxCodigo += lineasCodigDos[k] + "\n";
                            }
                            auxCoincidencia = auxCoincidencia.replace("${code}", auxCodigo);
                            auxCoincidencia = auxCoincidencia.replace("${primerNumLinea}", Integer.toString(listaTokens2.get(i - rigurosidad + 1).getLine()));
                            htmlCodigoDos += auxCoincidencia;
                            auxCodigo = "";

//                            System.out.println("Entrada 1");
//                            System.out.println("Desde: Fila " + listaTokens1.get(resp).getLine() + "\tColumna " + listaTokens1.get(resp).getCharPositionInLine());
//                            System.out.println("Hasta: Fila " + listaTokens1.get(resp + rigurosidad).getLine() + "\tColumna " + listaTokens1.get(resp + rigurosidad).getCharPositionInLine());
//
//                            System.out.println("Entrada 2");
//                            System.out.println("Desde: Fila " + listaTokens2.get(i - rigurosidad + 1).getLine() + "\tColumna " + listaTokens1.get(i - rigurosidad + 1).getCharPositionInLine());
//                            System.out.println("Hasta: Fila " + listaTokens2.get(i).getLine() + "\tColumna " + listaTokens1.get(i).getCharPositionInLine());
//
//                            System.out.println("*************************************************");
                        }
                    }
                }

                htmlCodigoUno += plantillaFinal;
                htmlCodigoDos += plantillaFinal;

                setCodeUno(htmlCodigoUno);
                setCodeDos(htmlCodigoDos);

                webViewId1.setVisible(true);
                webViewId2.setVisible(true);
                nombreArchivoUno.setVisible(true);
                nombreArchivoDos.setVisible(true);
                imagenPrincipal.setVisible(false);
                TextoPrincipal.setVisible(false);

            } catch (
                    Exception e) {
                System.err.println("Error (Test): " + e);
            }

        }
    }

    @FXML
    void onScrollUno(ScrollEvent event) {

    }
}

