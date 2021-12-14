package com.example.demo1;

import java.net.URL;
import java.util.ResourceBundle;
// подключаем необходимые модули и зависимости для нашей программы
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage; // подключаем необходимые модули и зависимости для нашей программы

public class HelloController implements Initializable{

    @FXML
    private WebView webView; // инициализируем наши элементы
    @FXML
    private TextField textField;
    @FXML // инициализируем наши элементы
    private ProgressBar progress;
    @FXML // инициализируем наши элементы
    private WebView webview;
    private WebEngine engine;
    private WebHistory history;


    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
    private void loadUrl() { // метод loadUrl - загрузчик и обработчик наших ссылок
        engine = webview.getEngine();
        engine.load("https://" + textField.getText()); // загружаем наш url получая ссылку из textField с помощью метода getText

        progress.progressProperty().bind(engine.getLoadWorker().progressProperty()); // Прогресс-бар

        engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                System.out.println("Страница успешно загружена"); // Если страница загружена, то выводим в консоль сообщение об успешном выполнении

                history = webview.getEngine().getHistory(); // Записываем в историю наши посещения
                ObservableList<WebHistory.Entry> entries = history.getEntries();
                textField.setText(entries.get(history.getCurrentIndex()).getUrl());

                Stage stage = (Stage) textField.getScene().getWindow();
                stage.setTitle(entries.get(history.getCurrentIndex()).getTitle()); // изменяем title на title текущего сайта

            } else if (newValue == Worker.State.FAILED) {
                System.out.println("Не удалось загрузить страницу"); // Если не удалось загрузить страницу - выводим сообщение в консоль
            }
        });
    }

    @FXML
    private void back(ActionEvent event) {
        history = webview.getEngine().getHistory();
        history.go(-1);
        ObservableList<WebHistory.Entry> entries = history.getEntries(); // Функционал кнопки "назад", движемся к предыдущей странице
        textField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    @FXML
    private void reload(ActionEvent event) {
        engine.reload(); // перезагрузка страницы по нажатию кнопки reload
    }

    @FXML
    private void forward(ActionEvent event) {
        history = webview.getEngine().getHistory();
        history.go(1);
        ObservableList<WebHistory.Entry> entries = history.getEntries(); // вперед к следующей странице
        textField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    @FXML
    private void txtEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loadUrl(); // загрузка страницы по нажатию клавиши enter
        }
    }

    @FXML
    private void zoomIn(ActionEvent event) {
        webview.setZoom(webview.getZoom() + 0.10); // Увеличение масштаба на странице с помощью setZoom
    }

    @FXML
    private void zoomOut(ActionEvent event) {
        webview.setZoom(webview.getZoom() - 0.10); // Уменьшение масштаба страницы
    }
    @FXML
    public void executeJS(){
        engine.executeScript("window.location = \"https://www.youtube.com\";"); // Можно исполнять javascript код
    }
    @FXML
    public void displayHistory(){ // Показ истории, вывод ее в консоль
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        System.out.println("===========History=========");
        for(WebHistory.Entry entry : entries){
            System.out.println(entry.getUrl() + " " + entry.getLastVisitedDate()); // выводим в цикле нашу историю
        }
    }
}
