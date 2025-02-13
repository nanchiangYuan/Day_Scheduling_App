/*
 * Format is AA:BB where AA is hour 00 to 23 and BB is minute 00 to 59.
 * Can be used to represent time of day in 24-hour format, or length of time, for example,
 * 01:00 represents 60 minutes, 03:30 represents 210 minutes.
 */
public class Time implements Comparable{
    private int hour;
    private int minute;

    /*
     * Constructor
     */
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        if(!checkTime()) {
            this.hour = -1;
            this.minute = -1;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    // adds to the time object
    public void add(Time added) {
        this.minute += added.minute;
        this.hour += added.hour;
        if(this.minute > 59) {
            this.hour += 1;
            this.minute -= 60;
        }

        if(this.hour > 23) {
            this.hour = -1;
            this.minute = -1;
        }
    }

    // creates a new object
    public static Time addition(Time time, Time add) {
        int minute = time.minute + add.minute;
        int hour = time.hour;
        if(minute > 59) {
            minute -= 60;
            hour += (add.hour + 1);
        }
        else
            hour += add.hour;

        if(hour > 23) {
            hour = -1;
            minute = -1;
        }
        return new Time(hour, minute);


    }

    public void subtract(Time added) {
        this.minute -= added.minute;
        this.hour -= added.hour;
        if(this.minute < 0) {
            this.hour -= 1;
            this.minute += 60;
        }

        if(this.hour < 0) {
            this.hour = -1;
            this.minute = -1;
        }
    }

    public static Time subtraction(Time time, Time sub) {
        int minute = time.minute - sub.minute;
        int hour = time.hour;
        if(minute < 0) {
            minute += 60;
            hour -= (sub.hour + 1);
        }
        else
            hour -= sub.hour;

        if(hour < 0 || (hour == 0 && minute < 0)) {
            hour = -1;
            minute = -1;
        }

        return new Time(hour, minute);
    }

    // check if time is legal
    private boolean checkTime() {
        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }

    public boolean isZero() {
        return (hour == 0) && (minute == 0);
    }

    // switch from int to Time
    private Time toTime(int intTime) {
        if (intTime < 0)
            return null;
        int hour = intTime / 60;
        if(hour > 23)
            return null;
        int minute = intTime % 60;
        return new Time(hour, minute);
    }

    // switch from Time to int
    public int toInt() {
        return hour * 60 + minute;
    }

    // returns a new object of the same time
    public static Time cpy(Time time) {
        return new Time(time.getHour(), time.getMinute());
    }

    public String toString() {
        String hourStr;
        String minuteStr;
        if(hour < 10)
            hourStr = "0" + Integer.toString(hour);
        else
            hourStr = Integer.toString(hour);
        if(minute < 10)
            minuteStr = "0" + Integer.toString(minute);
        else
            minuteStr = Integer.toString(minute);

        return hourStr + ":" + minuteStr;
    }

    @Override
    public int compareTo(Object o) {
        Time time = (Time) o;
        int totalTime = this.toInt();
        int totalTimeComp = time.toInt();
        return totalTime - totalTimeComp;
    }
}
