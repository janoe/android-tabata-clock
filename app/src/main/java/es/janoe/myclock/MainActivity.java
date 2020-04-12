package es.janoe.myclock;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.janoe.myclock.widgets.MyChronometer;

public class MainActivity extends AppCompatActivity {

    private MyChronometer chronometer;
    private Button button;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.chronometer = (MyChronometer) findViewById(R.id.chronometer);
        button = (Button) findViewById(R.id.buttonStart);
        info = (TextView) findViewById(R.id.InfoTextView);

        this.chronometer.setOnMyChronometerTickListener(new MyChronometer.OnMyChronometerTickListener() {
            @Override
            public void onMyChronometerTick(MyChronometer chronometer) {
                updateActivity(chronometer);
            }
        });
    }

    public void toggleChronometer(View v) {
        chronometer.toggle();
        updateActivityButtonsText(this.chronometer);
    }

    public void restartChronometer(View v) {
        chronometer.restart();
    }

    private void updateActivity(MyChronometer chronometer) {
        updateActivityBackgroundColor(chronometer);
        updateActivityButtonsText(chronometer);
        info.setText(String.format("%d/%d", chronometer.getCurrentWorkout(), chronometer.getNumWorkouts()));
    }

    private void updateActivityButtonsText(MyChronometer chronometer) {
        System.out.println("updateButton");
        System.out.println(chronometer.isRunning());

        if (chronometer.isRunning()) {
            button.setText(R.string.button_stop);
        } else if (chronometer.getCurrentStatus() == MyChronometer.Status.NOT_STARTED) {
            button.setText(R.string.button_start);
        } else {
            button.setText(R.string.button_resume);
        }
    }

    private void updateActivityBackgroundColor(MyChronometer chronometer) {
        System.out.println("BBB");
        System.out.println(this.chronometer.getCurrentStatus());
        if (this.chronometer.getCurrentStatus() == MyChronometer.Status.WORKOUT) {
            this.chronometer.getRootView().setBackgroundColor(Color.GREEN);
        } else if (this.chronometer.getCurrentStatus() == MyChronometer.Status.REST) {
            this.chronometer.getRootView().setBackgroundColor(Color.YELLOW);
        } else {
            this.chronometer.getRootView().setBackgroundColor(Color.WHITE);
        }
    }


}
