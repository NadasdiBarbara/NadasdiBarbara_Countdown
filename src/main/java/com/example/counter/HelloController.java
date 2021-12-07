package com.example.counter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Timer;
import java.util.TimerTask;


public class HelloController {
    @FXML
    private Button indit;
    @FXML
    private TextArea datumbe;
    @FXML
    private Label hatravan;

    private LocalDateTime datetimeOra, jelenegi;
    private LocalDate jelenegiPeriod, date;
    private Timer timer;
    private Period period;


    public void indit(ActionEvent actionEvent) {
        String[] textSplit = datumbe.getText().strip().split(" ");
        String[] datum = textSplit[0].strip().split("\\.");
        String[] ido = textSplit[1].strip().split(":");

        int ev = inToInt(datum[0]);
        int honap = inToInt(datum[1]);
        int nap = inToInt(datum[2]);
        int ora = inToInt(ido[0]);
        int perc = inToInt(ido[1]);
        int masodperc = inToInt(ido[2]);

        if (ev != -1 && honap != -1 && nap != -1 && ora != -1 && perc != -1 && masodperc != -1){
            datetimeOra = LocalDateTime.of(ev,honap,nap,ora, perc,masodperc);
            date = LocalDate.of(datetimeOra.getYear(), datetimeOra.getMonth(), datetimeOra.getDayOfMonth());

            timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    jelenegi = LocalDateTime.now();
                    jelenegiPeriod = LocalDate.of(jelenegi.getYear(), jelenegi.getMonth(), jelenegi.getDayOfMonth());
                    period = Period.between(jelenegiPeriod, date);
                    Duration hatravanMeg = Duration.between(jelenegi, datetimeOra);
                    int oraKul = hatravanMeg.toHoursPart();
                    int percKul = hatravanMeg.toMinutesPart();
                    int mpKul = hatravanMeg.toSecondsPart();
                    int evKul = period.getYears();
                    int honapKul = period.getMonths();
                    int napKul = period.getDays();

                    Platform.runLater(()-> hatravan.setText(String.format( "%d év %d hó %d nap  %d:%d:%d", evKul, honapKul, napKul, oraKul, percKul, mpKul)));
                }
            };
            timer.schedule(timerTask, 1,1);

        }else{
            System.out.println("Hibás adatok");
        }
    }

    private int inToInt(String adat){
        int vissza = 0;
        try {
            vissza = Integer.parseInt(adat);
        }catch (Exception ex){
            vissza = -1;
        }
        return vissza;
    }

}