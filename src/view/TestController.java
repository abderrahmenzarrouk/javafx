/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import entities.question;
import entities.test;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.questioncrud;
import utils.database;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * FXML Controller class
 *
 * @author Zarrouk
 */
public class TestController implements Initializable {

    @FXML
    private Label test_enonce_id;
    @FXML
    private Button btn_next_question;
    private List<question> questions;
    private int currentQuestionIndex;
    private Connection connect;
    private Statement statement;
    private ResultSet result;
    @FXML
    private Button start_test;
    private Map<Integer, String> answers;
    @FXML
    private RadioButton premiere_reponse;
    @FXML
    private RadioButton deuxieme_reponse;
    @FXML
    private ToggleGroup reponses;
    private Map<Integer, String> lesreponses = new HashMap<>();
    private int score=0;
    private Double total=0.0;
    @FXML
    private AnchorPane bienvenu_form;
    @FXML
    private AnchorPane passer_form;
    @FXML
    private AnchorPane validation_form;
    @FXML
    private TextField user_email;
    private Map<String, String> question_signification = new HashMap<>();
    @FXML
    private VBox cardContainer;
    @FXML
    private Label score_label;
    @FXML
    private Button details_btn;
    @FXML
    private PieChart result_chart;
    @FXML
    private Button lire_enonce;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public ObservableList<question> questionListData() throws SQLException {

        return new questioncrud().read();
    }
    private ObservableList<question> questionList;
    
    @FXML
  private void startTest() {

        Alert alert ;
        if(user_email.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Votre email est obligatoire pour passer notre test!");
                alert.showAndWait();

        }else{
                questions = new ArrayList<>();
                try {
                    questions = questionListData();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                currentQuestionIndex = 0;
                displayQuestion(questions.get(currentQuestionIndex)); 
                passer_form.setVisible(true);
                bienvenu_form.setVisible(false);
        }
    }
    private void displayQuestion(question question) {
        test_enonce_id.setText(question.getEnonce());
        premiere_reponse.setText(question.getReponse1());
        premiere_reponse.setId("reponse1_" + question.getId());
        deuxieme_reponse.setText(question.getReponse2());
        deuxieme_reponse.setId("reponse2_" + question.getId());
        lire_enonce.setOnAction(e -> {
            String enon = questions.get(currentQuestionIndex).getEnonce();
            readenonce(enon);
        });
        
    }
    @FXML
     private void nextQuestion() throws SQLException {

        boolean isAnswer1Selected = premiere_reponse.isSelected();
        boolean isAnswer2Selected = deuxieme_reponse.isSelected();
        String userAnswer = null;
        if (isAnswer1Selected) {
        userAnswer = premiere_reponse.getText();
    } else if (isAnswer2Selected) {
        userAnswer = deuxieme_reponse.getText();
    }
        
    if (userAnswer != null) {
        int currentQuestionId = questions.get(currentQuestionIndex).getId();
        System.out.println(currentQuestionId);
        String reponseuser = questions.get(currentQuestionIndex).getReponse_ideal();
        System.out.println(userAnswer);
        System.out.println(reponseuser);
        if(!userAnswer.equals(reponseuser)){
            score ++;
            question_signification.put(questions.get(currentQuestionIndex).getEnonce(), questions.get(currentQuestionIndex).getSignification());
            System.out.println("score =" + score);
        }
       lesreponses.put(currentQuestionId, userAnswer);
    }
        reponses.selectToggle(null);
        // 
        if (currentQuestionIndex < questions.size() - 1) {
            System.out.println(premiere_reponse.getId());
            System.out.println(deuxieme_reponse.getId());
            currentQuestionIndex++;
            displayQuestion(questions.get(currentQuestionIndex));
            
        } else {

            LocalDate currentDate = LocalDate.now();
            String dateString = currentDate.toString();
             System.out.println(question_signification);
             total = (double) (score * 100) / lesreponses.size();
             Double roundedTotal = Math.round(total * 100.0) / 100.0;
             String email = user_email.getText();
             test t = new test(email,roundedTotal, dateString);
             questioncrud pc = new questioncrud();
             pc.addtest(t);
             passer_form.setVisible(false);
             bienvenu_form.setVisible(false);
             generateCards();
             grapheresult(roundedTotal);
             score_label.setText(roundedTotal.toString()+"%");
             validation_form.setVisible(true);
        }
    }
private void generateCards() {
    for (Map.Entry<String, String> entry : question_signification.entrySet()) {
        String question = entry.getKey();
        String signification = entry.getValue();


        GridPane card = new GridPane();
        
        card.getStyleClass().add("card");
      Label questionLabel = new Label(question);
        questionLabel.getStyleClass().add("question-label");


        Label significationLabel = new Label(signification);
        significationLabel.getStyleClass().add("signification-label");


        card.add(questionLabel, 0, 0);
        card.add(significationLabel, 0, 1);


        cardContainer.getChildren().add(card);
    }
       ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(validation_form);
    scrollPane.setFitToWidth(true);


    Scene scene = validation_form.getScene();
    scene.setRoot(scrollPane);
}
    @FXML
     public void detailsTest(ActionEvent event) {
        cardContainer.setVisible(true);
    }
     public void grapheresult(double s ){
         Double sc = 100 - s;
         ObservableList<PieChart.Data> chart = FXCollections.observableArrayList();
         chart.add(new PieChart.Data("Positive", s));
         chart.add(new PieChart.Data("Negative", sc));
         result_chart.setData(chart);
     }
     public void readenonce(String enon){
         
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(enon);
            voice.deallocate();
        } else {
            System.err.println("Unable to find voice.");
        }
         
     }



    
}
