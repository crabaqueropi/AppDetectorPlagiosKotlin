import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {

    public FileChooser fc = new FileChooser();
    public File file1;
    public File file2;
    public List<File> files;
    static public List<String> codigosCompletos = new ArrayList<>();

    public String codigoCompletoUno = "";
    public String codigoCompletoDos = "";
    public int rigurosidadBaja = 200;
    public int rigurosidadMedia = 100;
    public int rigurosidadAlta = 50;
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
    @FXML
    public RadioButton radioBtnMedia;
    @FXML
    public RadioButton radioBtnAlta;
    @FXML
    public Line linea1;
    @FXML
    public Line linea2;
    @FXML
    public ProgressBar barraProgreso;
    @FXML
    public ProgressBar barraProgreso2;
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

    // segundo panel
    @FXML
    public WebView webViewId11;
    @FXML
    public WebView webViewId21;
    @FXML
    private Label nombreArchivoUno1;
    @FXML
    private Label nombreArchivosSeleccionados;
    @FXML
    public ImageView imagenPrincipal1;
    @FXML
    public Text TextoPrincipal1;
    @FXML
    private Label labVariosArchivos;
    @FXML
    public ImageView imagenLista11;
    @FXML
    public ImageView imagenLista21;
    @FXML
    private Label labArchivo12;
    @FXML
    public RadioButton radioBtnBaja1;
    @FXML
    public RadioButton radioBtnMedia1;
    @FXML
    public RadioButton radioBtnAlta1;

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

    private final String plantillaCoincidenciaPanel2 =
            "<h3>${nombreArchivo} - Coincidencia ${numCoincidencia}</h3>" +
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
        ponerBarraProgreso(webViewId1.getEngine());
    }

    public void setCodeDos(String newHtmlCode) {
        webViewId2.getEngine().loadContent(newHtmlCode);
    }

    public void setCodeUnoPanel2(String newHtmlCode) {
        webViewId11.getEngine().loadContent(newHtmlCode);
        ponerBarraProgresoPanel2(webViewId11.getEngine());
    }

    public void setCodeDosPanel2(String newHtmlCode) {
        webViewId21.getEngine().loadContent(newHtmlCode);
    }


    @FXML
    void btnDosArchivos(MouseEvent event) {
        panelDosArchivos.setVisible(true);
        panelVariosArchivos.setVisible(false);
        linea1.setVisible(true);
        linea2.setVisible(false);
    }

    @FXML
    void btnVariosArchivos(MouseEvent event) {
        panelDosArchivos.setVisible(false);
        panelVariosArchivos.setVisible(true);
        linea1.setVisible(false);
        linea2.setVisible(true);
    }

    @FXML
    void btnSeleccionarMultiplesArchivos(MouseEvent event) throws IOException {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        files = fc.showOpenMultipleDialog(null);
        for (File file : files) {
            //System.out.println(file.getAbsolutePath());
            webViewId11.setVisible(false);
            webViewId21.setVisible(false);
            nombreArchivoUno1.setVisible(false);
            nombreArchivosSeleccionados.setVisible(false);
            imagenPrincipal1.setVisible(true);
            TextoPrincipal1.setVisible(true);

            labVariosArchivos.setText("Archivos seleccionados: " + files.size());

            imagenLista21.setVisible(true);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            String codigoCompleto = "";
            while ((st = br.readLine()) != null)
                codigoCompleto += st + "\n";

            codigosCompletos.add(codigoCompleto);
        }
    }

    @FXML
    void btnseleccionarArchivoUnoPanel2(MouseEvent event) throws IOException {
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Kotlin", "*.kt"));
        file1 = fc.showOpenDialog(null);
        if (file1 != null) {
            webViewId11.setVisible(false);
            webViewId21.setVisible(false);
            nombreArchivoUno1.setVisible(false);
            nombreArchivosSeleccionados.setVisible(false);
            imagenPrincipal1.setVisible(true);
            TextoPrincipal1.setVisible(true);

            labArchivo12.setText("Archivo seleccionado: " + file1.getName());
            nombreArchivoUno1.setText(file1.getName());

            imagenLista11.setVisible(true);

            BufferedReader br = new BufferedReader(new FileReader(file1));

            String st;
            codigoCompletoUno = "";
            while ((st = br.readLine()) != null)
                codigoCompletoUno += st + "\n";
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
            codigoCompletoUno = "";
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
            codigoCompletoDos = "";
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
                webViewId1.getEngine().loadContent(""); //limpiar webview
                webViewId2.getEngine().loadContent("");

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

                int rigurosidad = rigurosidadBaja;

                if (radioBtnBaja.isSelected()) {
                    rigurosidad = rigurosidadBaja;
                } else if (radioBtnMedia.isSelected()) {
                    rigurosidad = rigurosidadMedia;
                } else if (radioBtnAlta.isSelected()) {
                    rigurosidad = rigurosidadAlta;
                }



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

                if (coincidencias == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No hay plagio!");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró plagio en los documentos (con la rigurosidad escogida)");
                    alert.showAndWait();
                }else{
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
                }

            } catch (
                    Exception e) {
                System.err.println("Error (Test): " + e);
            }

        }
    }

    private void ponerBarraProgreso(WebEngine engine){
        barraProgreso.setVisible(true);
        barraProgreso.progressProperty().bind(engine.getLoadWorker().progressProperty());

        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> ov, State oldValue, State newValue) {
                        if (newValue == State.SUCCEEDED) {
                            // hide progress bar then page is ready
                            barraProgreso.setVisible(false);
                        }
                    }
                });

    }

    private void ponerBarraProgresoPanel2(WebEngine engine){
        barraProgreso2.setVisible(true);
        barraProgreso2.progressProperty().bind(engine.getLoadWorker().progressProperty());

        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> ov, State oldValue, State newValue) {
                        if (newValue == State.SUCCEEDED) {
                            // hide progress bar then page is ready
                            barraProgreso2.setVisible(false);
                        }
                    }
                });

    }

    @FXML
    void btnVerificarPlagioPanel2(MouseEvent event) throws IOException {
        if (file1 == null || files == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archivos faltantes");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar los archivos a verificar.");
            alert.showAndWait();
        } else {
            try {
                webViewId11.getEngine().loadContent(""); //limpiar webview
                webViewId21.getEngine().loadContent("");

                String htmlCodigoUno = plantillaInicio;
                String htmlCodigoDos = plantillaInicio;
                int contadorFiles = 0;

                int coincidenciasTotales = 0;

                for (File file : files){
                    //----------

                    KotlinLexer lexer1;
                    KotlinLexer lexer2;

                    lexer1 = new KotlinLexer(CharStreams.fromFileName(file1.getAbsolutePath()));
                    lexer2 = new KotlinLexer(CharStreams.fromFileName(file.getAbsolutePath()));

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

                    int rigurosidad = rigurosidadBaja;

                    if (radioBtnBaja1.isSelected()) {
                        rigurosidad = rigurosidadBaja;
                    } else if (radioBtnMedia1.isSelected()) {
                        rigurosidad = rigurosidadMedia;
                    } else if (radioBtnAlta1.isSelected()) {
                        rigurosidad = rigurosidadAlta;
                    }



                    String[] lineasCodigUno = codigoCompletoUno.split("\n");
                    String[] lineasCodigDos = codigosCompletos.get(contadorFiles).split("\n");

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
                                coincidenciasTotales++;

                                auxCoincidencia = plantillaCoincidenciaPanel2;
                                auxCoincidencia = auxCoincidencia.replace("${nombreArchivo}", "Con: " + file.getName());
                                auxCoincidencia = auxCoincidencia.replace("${numCoincidencia}", Integer.toString(coincidencias));
                                auxCoincidencia = auxCoincidencia.replace("${codeID}", ("code" + contadorFiles + coincidencias));
                                for (int j = listaTokens1.get(resp).getLine() - 1; j < listaTokens1.get(resp + rigurosidad).getLine(); j++) {
                                    auxCodigo += lineasCodigUno[j] + "\n";
                                }
                                auxCoincidencia = auxCoincidencia.replace("${code}", auxCodigo);
                                auxCoincidencia = auxCoincidencia.replace("${primerNumLinea}", Integer.toString(listaTokens1.get(resp).getLine()));
                                htmlCodigoUno += auxCoincidencia;
                                auxCodigo = "";

                                auxCoincidencia = plantillaCoincidenciaPanel2;
                                auxCoincidencia = auxCoincidencia.replace("${nombreArchivo}", file.getName());
                                auxCoincidencia = auxCoincidencia.replace("${numCoincidencia}", Integer.toString(coincidencias));
                                auxCoincidencia = auxCoincidencia.replace("${codeID}", ("code" + contadorFiles + coincidencias));
                                for (int k = listaTokens2.get(i - rigurosidad + 1).getLine() - 1; k < listaTokens2.get(i).getLine(); k++) {
                                    auxCodigo += lineasCodigDos[k] + "\n";
                                }
                                auxCoincidencia = auxCoincidencia.replace("${code}", auxCodigo);
                                auxCoincidencia = auxCoincidencia.replace("${primerNumLinea}", Integer.toString(listaTokens2.get(i - rigurosidad + 1).getLine()));
                                htmlCodigoDos += auxCoincidencia;
                                auxCodigo = "";
                            }
                        }
                    }
                    contadorFiles++;
                    //--------------
                }

                if (coincidenciasTotales == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No hay plagio!");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró plagio en los documentos (con la rigurosidad escogida)");
                    alert.showAndWait();
                }else{
                    htmlCodigoUno += plantillaFinal;
                    htmlCodigoDos += plantillaFinal;

                    setCodeUnoPanel2(htmlCodigoUno);
                    setCodeDosPanel2(htmlCodigoDos);

                    webViewId11.setVisible(true);
                    webViewId21.setVisible(true);
                    nombreArchivoUno1.setVisible(true);
                    nombreArchivosSeleccionados.setVisible(true);
                    imagenPrincipal1.setVisible(false);
                    TextoPrincipal1.setVisible(false);
                }

            } catch (
                    Exception e) {
                System.err.println("Error (Test): " + e);
            }

        }
    }
}

