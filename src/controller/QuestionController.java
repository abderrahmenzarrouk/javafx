/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entities.question;
import entities.test;
import java.awt.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.questioncrud;
import utils.database;

/**
 * FXML Controller class
 *
 * @author Zarrouk
 */
public class QuestionController implements Initializable {
  
    @FXML
    private javafx.scene.control.Button question_ajouter_id;
    @FXML
    private TableView<question> addquestion_tableView;
    @FXML
    private TableColumn<question, String> tab_enonce_id;

    @FXML
    private TableColumn<question, String> tab_premierereponse_id;

    @FXML
    private TableColumn<question, String> tab_deuxièmereponse_id;

    @FXML
    private TableColumn<question, String> tab_reponseideal_id;

    @FXML
    private TableColumn<question, String> tab_signification_id;
    @FXML
    private TextArea add_enonce_id;

    @FXML
    private TextField add_premierereponse_id;

    @FXML
    private TextField add_deuxiemereponse_id;

    @FXML
    private TextField add_reponseideal_id;
    
    @FXML
    private TextArea add_signification_id;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private javafx.scene.control.Button question_clear_id;
    @FXML
    private javafx.scene.control.Button question_supprimer_id;
    @FXML
    private javafx.scene.control.Button question_modifier_id;
    @FXML
    private TextField addquestion_search;
    @FXML
    private TextField ques_id;
    @FXML
    private TableColumn<?, ?> teb_quesid;
    @FXML
    private AnchorPane main_form;
    @FXML
    private javafx.scene.control.Button btn_question;
    @FXML
    private javafx.scene.control.Button btn_test;
    @FXML
    private AnchorPane question_form;
    @FXML
    private AnchorPane test_form;
    @FXML
    private Label total_tests;
    @FXML
    private Label positive_test;
    @FXML
    private Label negative_test;
    @FXML
    private PieChart piechart;
    @FXML
    private javafx.scene.control.Button btn_listtest;
    @FXML
    private AnchorPane test_tab_form;
    @FXML
    private AnchorPane list_test_form;
    @FXML
    private TableView<test> listdestests_id;
    @FXML
    private TableColumn<?, ?> test_email_id;
    @FXML
    private TableColumn<?, ?> test_date_id;
    @FXML
    private TableColumn<?, ?> test_resultat_id;
    @FXML
    private TableColumn<?, ?> test_col_id;
    @FXML
    private TextField t_id;
    @FXML
    private TextField min_result_id;
    @FXML
    private TextField max_result_id;
    @FXML
    private javafx.scene.control.Button filter_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            TestShowListData();
            ghraphe();
            Totaltestnegative();
            Totaltestpositive();
            TotalTests();
            QuestionShowListData();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    
    public ObservableList<question> questionListData() throws SQLException {

        return new questioncrud().read();
    }
    private ObservableList<question> questionList;
        public void QuestionShowListData() throws SQLException {
        questionList = questionListData();
        teb_quesid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tab_enonce_id.setCellValueFactory(new PropertyValueFactory<>("enonce"));
        tab_premierereponse_id.setCellValueFactory(new PropertyValueFactory<>("reponse1"));
        tab_deuxièmereponse_id.setCellValueFactory(new PropertyValueFactory<>("reponse2"));
        tab_reponseideal_id.setCellValueFactory(new PropertyValueFactory<>("reponse_ideal"));
        tab_signification_id.setCellValueFactory(new PropertyValueFactory<>("signification"));
        
        

        addquestion_tableView.setItems(questionList);

    }
            @FXML
    private void ajouterquestion(ActionEvent event) {
        
        
            question m = new question(
                    add_enonce_id.getText(),
                    add_premierereponse_id.getText(),
                    
                    add_deuxiemereponse_id.getText(),
                    add_reponseideal_id.getText(),
                    add_signification_id.getText()
            );
            questioncrud pc = new questioncrud();
                try {
              if (add_signification_id.getText().isEmpty()
                   || add_enonce_id.getText().isEmpty()
                    || add_premierereponse_id.getText().isEmpty()
                    || add_deuxiemereponse_id.getText().isEmpty()
                 
                    || add_reponseideal_id.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez d'abord sélectionner tous les champs");
                alert.showAndWait();
            }else if (add_signification_id.getText().length()<10
                   || add_enonce_id.getText().length()<10){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("la lengeur des champs enonce et signification doit être supérieur a 10");
                alert.showAndWait();
            }else if(!add_enonce_id.getText().endsWith("?")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("l'enonce doit terminé avec ?");
                alert.showAndWait();
            }
              
              else{
                    pc.add(m);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("question");
                    alert.setHeaderText(null);
                    alert.setContentText("question ajouté avec succés!");
                    alert.show();
                    QuestionShowListData();
                    questionReset();
              }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());;
                }
            
    }
    @FXML
        public void addQuestionSelect() {
        question q = addquestion_tableView.getSelectionModel().getSelectedItem();
        int num = addquestion_tableView.getSelectionModel().getSelectedIndex();


       ques_id.setText(q.getId().toString());
        add_enonce_id.setText(q.getEnonce());
        add_premierereponse_id.setText(q.getReponse1());
        add_deuxiemereponse_id.setText(q.getReponse2());
        add_reponseideal_id.setText(q.getReponse_ideal());
        add_signification_id.setText(q.getSignification()); 
    }
    @FXML
    public void questionReset() {
        add_enonce_id.setText("");
        add_premierereponse_id.setText("");
        add_deuxiemereponse_id.setText("");
        add_reponseideal_id.setText("");
        add_signification_id.setText("");
    }
    
    @FXML
    public void questionUpdate() {
        question q = new question(Integer.parseInt(ques_id.getText()), add_enonce_id.getText(), add_premierereponse_id.getText(), add_deuxiemereponse_id.getText(), add_reponseideal_id.getText(), add_signification_id.getText());
       questioncrud qc = new questioncrud();

        try {
            Alert alert ;

            if (add_signification_id.getText().isEmpty()
                   || add_enonce_id.getText().isEmpty()
                    || add_premierereponse_id.getText().isEmpty()
                    || add_deuxiemereponse_id.getText().isEmpty()
                 
                    || add_reponseideal_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez d'abord sélectionner tous les champs");
                alert.showAndWait();
            }else if (add_signification_id.getText().length()<10
                   || add_enonce_id.getText().length()<10){
                  alert = new Alert(Alert.AlertType.ERROR); 
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("la lengeur des champs enonce et signification doit être supérieur a 10");
                alert.showAndWait();
            }else if(!add_enonce_id.getText().endsWith("?")){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("l'enonce doit terminé avec ?");
                alert.showAndWait();
            } 
            
            else {
                   qc.update(q);
                   alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("succes");
                    alert.setHeaderText(null);
                    alert.setContentText("question modifiée avec succés!");
                    alert.show();
                    QuestionShowListData();

              
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addQuestionSearch() {

        FilteredList<question> filter = new FilteredList<>(questionList, e -> true);

        addquestion_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicatequestionData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicatequestionData.getEnonce().toString().contains(searchKey)) {
                    return true;
                } 
                else {
                    return false;
                }
            });
        });

        SortedList<question> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addquestion_tableView.comparatorProperty());
        addquestion_tableView.setItems(sortList);
    }
    @FXML
        public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void questionDelete() {

        String sql = "DELETE FROM question WHERE id = '"
                + Integer.parseInt(ques_id.getText()) + "'";

        connect = database.getInstance().getConnection();

        try {

            Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Êtes-vous sûr de vouloir SUPPRIMER la question : ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM question WHERE id = '"
                            + Integer.parseInt(ques_id.getText()) + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("supprimer aves succées");
                    alert.showAndWait();

                    QuestionShowListData();
                    questionReset();
                    
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
     public void switchform(ActionEvent event) {
        if (event.getSource() == btn_question) {
            question_form.setVisible(true);
            test_form.setVisible(false);
            test_tab_form.setVisible(false);
            btn_question.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            btn_test.setStyle("-fx-background-color:transparent");
            btn_listtest.setStyle("-fx-background-color:transparent");
            

        } else if (event.getSource() == btn_test) {
            question_form.setVisible(false);
            test_form.setVisible(true);
            test_tab_form.setVisible(false);
            btn_test.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            btn_question.setStyle("-fx-background-color:transparent");
            btn_listtest.setStyle("-fx-background-color:transparent");

        }else if (event.getSource()== btn_listtest){
            test_tab_form.setVisible(true);
            question_form.setVisible(false);
            test_form.setVisible(false);
            btn_listtest.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            btn_question.setStyle("-fx-background-color:transparent");
            btn_test.setStyle("-fx-background-color:transparent");
        }
    }
     public void TotalTests() {

        String sql = "SELECT COUNT(id) FROM test";

        connect = database.getInstance().getConnection();
        int countData = 0;
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            System.out.println(countData);
            total_tests.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
         public void Totaltestpositive() {

        String sql = "SELECT COUNT(id) FROM test WHERE resultat > 30";


        connect = database.getInstance().getConnection();
        int countData = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            negative_test.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
         
     public void Totaltestnegative() {

        String sql = "SELECT COUNT(id) FROM test WHERE resultat < 30";


        connect = database.getInstance().getConnection();
        int countData = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            positive_test.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
     public void ghraphe(){
         String sql = "SELECT COUNT(id) FROM test WHERE resultat < 30";


        connect = database.getInstance().getConnection();
        int negative = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                negative = result.getInt("COUNT(id)");
            }
            positive_test.setText(String.valueOf(negative));

        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql2 = "SELECT COUNT(id) FROM test WHERE resultat > 30";


        connect = database.getInstance().getConnection();
        int positive = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql2);

            while (result.next()) {
                positive = result.getInt("COUNT(id)");
            }
            negative_test.setText(String.valueOf(positive));

        } catch (Exception e) {
            e.printStackTrace();
        }

         ObservableList<PieChart.Data> chart = FXCollections.observableArrayList();
         chart.add(new PieChart.Data("Positive", positive));
         chart.add(new PieChart.Data("Negative", negative));
         piechart.setData(chart);
     }

     
     public ObservableList<test> testListData() throws SQLException {

        return new questioncrud().readtest();
    }
        private ObservableList<test> testList;
    public void TestShowListData() throws SQLException {
        testList = testListData();
        test_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        test_email_id.setCellValueFactory(new PropertyValueFactory<>("email"));
        test_date_id.setCellValueFactory(new PropertyValueFactory<>("date"));
        test_resultat_id.setCellValueFactory(new PropertyValueFactory<>("resultat"));

        listdestests_id.setItems(testList);

    }
    
        @FXML
    public void testDelete() {

        String sql = "DELETE FROM test WHERE id = '"
                + Integer.parseInt(t_id.getText()) + "'";

        connect = database.getInstance().getConnection();

        try {

            Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Êtes-vous sûr de vouloir SUPPRIMER ce test : ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM test WHERE id = '"
                            + Integer.parseInt(t_id.getText()) + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("supprimer aves succées");
                    alert.showAndWait();
                    ghraphe();
                    TotalTests();
                    TestShowListData();
                    
                    
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @FXML
        public void addTestSelect() {
        test t = listdestests_id.getSelectionModel().getSelectedItem();
        int num = listdestests_id.getSelectionModel().getSelectedIndex();



        t_id.setText(t.getId().toString());
        
    }
  public void filterByResult( ) {
    FilteredList<test> filter = new FilteredList<>(testList);
    
    filter.setPredicate(q -> {
        double min = Double.parseDouble(min_result_id.getText());
        double max = Double.parseDouble(max_result_id.getText());
        return q.getResultat() >= min && q.getResultat() <= max;
    });

    SortedList<test> sortedList = new SortedList<>(filter);
    sortedList.comparatorProperty().bind(listdestests_id.comparatorProperty());
    listdestests_id.setItems(sortedList);
}
        

}
