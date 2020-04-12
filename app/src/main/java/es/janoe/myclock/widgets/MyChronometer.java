package es.janoe.myclock.widgets;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

import androidx.annotation.RequiresApi;

public class MyChronometer extends Chronometer {

    private long elapsed = 0L;
    private boolean isRunning;
    private long milliseconds;

    private int currentWorkout = 0;
    private int numWorkouts = 2;
    private final int secondsPerWorkout = 5;
    private final int secondsPerRest = 3;

    private OnMyChronometerTickListener mOnMyChronometerTickListener;

    public enum Status {
        NOT_STARTED,
        WORKOUT,
        REST;
    }

    Status currentStatus = Status.NOT_STARTED;

    {
        super.setOnChronometerTickListener(new OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                milliseconds = SystemClock.elapsedRealtime() - chronometer.getBase();
                System.out.println("AAA");
                System.out.println(milliseconds);
                updateStatuses();
                dispatchChronometerTick();
            }
        });
    }

    public MyChronometer(Context context) {
        super(context);
    }

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChronometer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyChronometer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void stop() {
        this.isRunning = false;
        super.stop();
        elapsed = SystemClock.elapsedRealtime() - this.getBase();
    }

    @Override
    public void start() {
        if (currentStatus == Status.NOT_STARTED) {
            currentStatus = Status.WORKOUT;
            this.currentWorkout++;
        }
        this.setBase(SystemClock.elapsedRealtime() - elapsed);
        this.isRunning = true;
        super.start();
    }

    public void reset() {
        this.setBase(SystemClock.elapsedRealtime());
        elapsed = 0;
        milliseconds = 1L;
    }

    public void restart() {
        this.currentWorkout = 0;
        currentStatus = Status.NOT_STARTED;
        this.stop();
        this.reset();
    }

    public void toggle() {
        if (isRunning) {
            this.stop();
        } else {
            this.start();
        }
    }

    private void updateStatuses() {
        if (isRunning) {
            if (currentStatus == Status.WORKOUT) {
                if (getSeconds() == secondsPerWorkout) {
                    currentStatus = Status.REST;
                    this.reset();
                }
            } else if (currentStatus == Status.REST) {
                if (getSeconds() == secondsPerRest) {
                    currentStatus = Status.WORKOUT;
                    this.currentWorkout++;
                    this.reset();
                }
            }
            if (currentWorkout > numWorkouts) {
                System.out.println("ZZZZ: " + currentWorkout + "/" + numWorkouts);
                this.restart();
            }
        }
    }

    void dispatchChronometerTick() {
        if (mOnMyChronometerTickListener != null) {
            mOnMyChronometerTickListener.onMyChronometerTick(this);
        }
    }

    public interface OnMyChronometerTickListener {
        void onMyChronometerTick(MyChronometer chronometer);

    }

    public void setOnMyChronometerTickListener(OnMyChronometerTickListener listener) {
        mOnMyChronometerTickListener = listener;
    }

    public int getSeconds() {
        return (int) (milliseconds / 1000);
    }

    public int getCurrentWorkout() {
        return currentWorkout;
    }

    public int getNumWorkouts() {
        return numWorkouts;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public boolean isRunning() {
        return isRunning;
    }
}



