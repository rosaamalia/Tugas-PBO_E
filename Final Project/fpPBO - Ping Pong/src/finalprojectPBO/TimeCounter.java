package finalprojectPBO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeCounter implements ActionListener {

    private Timer clock;
    public int hours = 0;
    public int min = 0;
    public int sec = 0;

    public TimeCounter(int hours, int min, int sec) {
        this.hours = hours;
        this.min = min;
        this.sec = sec;

        clock = new Timer(1000, this);
        clock.start();
    }

    public String result()
    {
        return hours + ":" + min + ":" + sec;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        {
            if(e.getSource() == clock) {
                sec++;
            }
            if(sec == 60)
            {
                min++;
                sec = 0;
            }
            if(min == 60)
            {
                hours++;
                min = 0;
            }
        }
    }

    public void actionPerformed() {

    }

	public void stop() {
		clock.stop();
		// TODO Auto-generated method stub
		
	}

	public void start() {
		// TODO Auto-generated method stub
		clock.start();
	}

	
}
