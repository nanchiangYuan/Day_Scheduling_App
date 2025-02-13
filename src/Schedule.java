import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/*
 * Includes an ordered list of tasks and the times that they should start at.
 */
public class Schedule {
    private ArrayList<Task> scheduledTasks;     // ordered list of tasks depending on criteria
    private ArrayList<Task> taskList;           // unordered tasks
    private ArrayList<Time> timePoints;         // the times when the tasks should start at

    private Time sessionDur;        // duration of every session
    private Task breakTask;         // break between sessions
    private Time breakDur;

    private boolean hasBreakfast;
    private boolean hasLunch;
    private boolean hasDinner;
    // no matter what, a new session starts after meal times
    private Task breakfast;
    private Task lunch;
    private Task dinner;
    // time when the meals start
    private Time breakfastTime;
    private Time lunchTime;
    private Time dinnerTime;

    // start and end time for tasks
    // always starts with first task, but will cut short at end time if the task list is longer
    private Time startTime;
    private Time endTime;

    // to note if break between tasks
    // a new session starts if so
    private boolean breakBetweenTasks;

    /*
     * Constructor, just creates this object, need to call generate to actually generate the schedule
     * the values are all default, can be changed with methods
     */
    public Schedule(ArrayList<Task> taskList) {

        // deep copy of task list
        this.taskList = new ArrayList<>();
        for(Task task: taskList) {
            Time timeNew = new Time(task.getEstTime().getHour(), task.getEstTime().getMinute());
            Task taskNew = new Task(task.getName(), timeNew, task.getPriority());

            this.taskList.add(taskNew);
        }

        this.scheduledTasks = new ArrayList<>();
        this.timePoints = new ArrayList<>();

        // default info
        sessionDur = new Time(0, 50);
        breakDur = new Time(0, 10);
        breakTask = new Task("break", breakDur, 0);

        hasBreakfast = true;
        hasLunch = true;
        hasDinner = true;
        breakfast = new Task("breakfast", new Time(1, 0), 0);
        lunch = new Task("lunch", new Time(1, 0), 0);
        dinner = new Task("dinner", new Time(1, 0), 0);
        breakfastTime = new Time(8, 0);
        lunchTime = new Time(12, 0);
        dinnerTime = new Time(18, 0);

        startTime = new Time(8, 0);
        endTime = new Time(17, 0);

        breakBetweenTasks = true;
    }

    public void setBreakDur(Time breakDur) {
        this.breakDur = breakDur;
        this.breakTask.setEstTime(breakDur);
    }
    public void setBreakfastTime(Time breakfastTime) {
        this.breakfastTime = breakfastTime;
    }
    public void setLunchTime(Time lunchTime) {
        this.lunchTime = lunchTime;
    }
    public void setDinnerTime(Time dinnerTime) {
        this.dinnerTime = dinnerTime;
    }
    public void setBreakfastDur(Time dur) {
        breakfast.setEstTime(dur);
    }
    public void setLunchDur(Time dur) {
        lunch.setEstTime(dur);
    }
    public void setDinnerDur(Time dur) {
        dinner.setEstTime(dur);
    }


    public void setHasBreakfast(boolean set) {
        this.hasBreakfast = set;
    }
    public void setHasLunch(boolean set) {
        this.hasLunch = set;
    }
    public void setHasDinner(boolean set) {
        this.hasDinner = set;
    }
    public boolean getHasBreakfast() {
        return this.hasBreakfast;
    }
    public boolean getHasLunch() {
        return this.hasLunch;
    }
    public boolean getHasDinner() {
        return this.hasDinner;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    public void setSessionDur(Time sessionDur) {
        this.sessionDur = sessionDur;
    }
    public void setBreakBetweenTasks(boolean set) {
        this.breakBetweenTasks = set;
    }

    /*
     * Generates an ordered list of tasks based on priority
     */
    public void generatePriority() {
        taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getPriority() - t2.getPriority();
            }
        });
        orderTasks();

    }

    /*
     * Generates an ordered list of tasks, shortest time first
     */
    public void generateTimeAscending() {
        taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getEstTime().compareTo(t2.getEstTime());
            }
        });
        orderTasks();
    }

    /*
     * Generates an ordered list of tasks, longest time first
     */
    public void generateTimeDescending() {
        taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t2.getEstTime().compareTo(t1.getEstTime());
            }
        });
        orderTasks();

    }

    /*
     * Generates a randomly-ordered list of tasks
     */
    public void generateRandom() {
        Collections.shuffle(taskList);
        orderTasks();
    }

    /*
     * Creates ordered list of tasks split by session duration, meal times and breaks
     * Also records the time stamps
     */
    private void orderTasks() {

        Iterator<Task> taskIt = taskList.iterator();
        // timeLeftOver: the remaining time of the session, should be 0 if there is break between tasks
        Time timeLeftOver = new Time(0, 0);
        Time currentTime = Time.cpy(startTime);
        // to track if meal is already added
        boolean breakfastAdded = !hasBreakfast;
        boolean lunchAdded = !hasLunch;
        boolean dinnerAdded = !hasDinner;

        // loops until no more tasks or reached endTime
        while (taskIt.hasNext() && currentTime.compareTo(endTime) < 0) {
            Task curTask = taskIt.next();
            Time timeLeft = curTask.getEstTime();

            // loop is to make sure the first iteration check for timeLeftOver from previous session
            int loop = 0;

            // loop until est time of task is 0
            while (!timeLeft.isZero()) {

                // check if meals should be added then add them
                Time interval;
                if(timeLeftOver.isZero())
                    interval = sessionDur;
                else
                    interval = timeLeftOver;
                if (!breakfastAdded) {
                    breakfastAdded = addMeal(0, currentTime, timeLeft, timeLeftOver, curTask, interval);
                    if (breakfastAdded) {
                        loop++;
                        continue;
                    }
                }
                if (!lunchAdded) {
                    lunchAdded = addMeal(1, currentTime, timeLeft, timeLeftOver, curTask, interval);
                    if (lunchAdded) {
                        loop++;
                        continue;
                    }
                }
                if (!dinnerAdded) {
                    dinnerAdded = addMeal(2, currentTime, timeLeft, timeLeftOver, curTask, interval);
                    if (dinnerAdded) {
                        loop++;
                        continue;
                    }
                }

                // first add current time (indicates start of task
                timePoints.add(Time.cpy(currentTime));

                // first loop to fill up previous session time if no break between tasks
                if (loop == 0 && !breakBetweenTasks && !timeLeftOver.isZero()) {

                    // if timeLeftOver is shorted than task length
                    if(timeLeftOver.compareTo(timeLeft) <= 0) {
                        // add task
                        currentTime.add(timeLeftOver);

                        scheduledTasks.add(new Task(curTask.getName(), Time.cpy(timeLeftOver), curTask.getPriority()));

                        // add break
                        // check if meals happen during break, if so , add them
                        if (!breakfastAdded) {
                            breakfastAdded = addMeal(0, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!breakfastAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else if (!lunchAdded) {
                            lunchAdded = addMeal(1, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!lunchAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else if (!dinnerAdded) {
                            dinnerAdded = addMeal(2, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!dinnerAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else {
                            timePoints.add(Time.cpy(currentTime));
                            currentTime.add(breakDur);
                            scheduledTasks.add(breakTask);
                        }

                        // subtract used time and reset timeLeftOver
                        timeLeft.subtract(timeLeftOver);
                        timeLeftOver.setHour(0);
                        timeLeftOver.setMinute(0);
                        continue;
                    }
                    // if timeLeftOver is longer than task length, update timeLeftOver
                    else {
                        currentTime.add(timeLeft);
                        scheduledTasks.add(new Task(curTask.getName(), Time.cpy(timeLeft), curTask.getPriority()));
                        timeLeftOver.subtract(timeLeft);
                        timeLeft.subtract(timeLeft);
                        continue;
                    }
                }

                // check if session duration is longer than time left for the task
                // 1: session is shorter than or equal to time left
                if (sessionDur.compareTo(timeLeft) <= 0) {
                    // add task
                    currentTime.add(sessionDur);
                    scheduledTasks.add(new Task(curTask.getName(), Time.cpy(sessionDur), curTask.getPriority()));

                    // add break
                    // check if meals should be added then add them
                    if (!breakfastAdded) {
                        breakfastAdded = addMeal(0, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                        if(!breakfastAdded) {
                            timePoints.add(Time.cpy(currentTime));
                            currentTime.add(breakDur);
                            scheduledTasks.add(breakTask);
                        }
                    }
                    else if (!lunchAdded) {
                        lunchAdded = addMeal(1, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                        if(!lunchAdded) {
                            timePoints.add(Time.cpy(currentTime));
                            currentTime.add(breakDur);
                            scheduledTasks.add(breakTask);
                        }
                    }
                    else if (!dinnerAdded) {
                        dinnerAdded = addMeal(2, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                        if(!dinnerAdded) {
                            timePoints.add(Time.cpy(currentTime));
                            currentTime.add(breakDur);
                            scheduledTasks.add(breakTask);
                        }
                    }
                    else {
                        timePoints.add(Time.cpy(currentTime));
                        currentTime.add(breakDur);
                        scheduledTasks.add(breakTask);
                    }

                }
                // 2. session is longer than time left
                // if break between tasks is on, a new session starts after the previous task is done
                // if not, the new task starts in the same session as the previous task ended
                else if (sessionDur.compareTo(timeLeft) > 0) {
                    scheduledTasks.add(new Task(curTask.getName(), Time.cpy(timeLeft), curTask.getPriority()));

                    if (breakBetweenTasks) {
                        currentTime.add(timeLeft);

                        //add break
                        // check if meals should be added then add them

                        if (!breakfastAdded) {
                            breakfastAdded = addMeal(0, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!breakfastAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else if (!lunchAdded) {
                            lunchAdded = addMeal(1, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!lunchAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else if (!dinnerAdded) {
                            dinnerAdded = addMeal(2, currentTime, timeLeft, timeLeftOver, breakTask, breakDur);
                            if(!dinnerAdded) {
                                timePoints.add(Time.cpy(currentTime));
                                currentTime.add(breakDur);
                                scheduledTasks.add(breakTask);
                            }
                        }
                        else {
                            timePoints.add(Time.cpy(currentTime));
                            currentTime.add(breakDur);
                            scheduledTasks.add(breakTask);
                        }

                    } else {
                        timeLeftOver = Time.subtraction(sessionDur, timeLeft);
                        currentTime.add(timeLeft);
                    }
                }
                // subtract session duration from time left
                // if session is longer than time left, time left is set to 0
                timeLeft.subtract(sessionDur);
                if (timeLeft.getHour() == -1) {
                    timeLeft.setHour(0);
                    timeLeft.setMinute(0);
                }
                loop++;
            }

        }
        // get rid of ordered tasks that went over endTime
        while (timePoints.size() != 0 && timePoints.get(timePoints.size() - 1).compareTo(endTime) >= 0) {
            timePoints.remove(timePoints.size() - 1);
            scheduledTasks.remove(scheduledTasks.size() - 1);
        }
        // adjust the last task time
        Task lastTask = scheduledTasks.getLast();
        Time lastTime = timePoints.getLast();

        if (Time.addition(lastTime, lastTask.getEstTime()).compareTo(endTime) > 0) {
            Time adjusted = Time.subtraction(endTime, lastTime);
            lastTask.setEstTime(adjusted);
        }

        // add end time to the list
        timePoints.add(Time.addition(lastTime, lastTask.getEstTime()));
    }

    /*
     * Insert meal into schedule and changes the parameters of Time object passed in
     * mealMode: 0 for breakfast, 1 for lunch, 2 for dinner
     */
    private boolean addMeal(int mealMode, Time currentTime, Time timeLeft, Time timeLeftOver, Task curTask, Time interval) {

        // check meal times
        // 3 scenarios:
        //      |------timeLeft------|---------timeLeftOver----------|
        // currentTime                                          + sessionDur
        //      1       2
        //      |--------------------timeLeft+ ----------------------|
        // currentTime                                          + sessionDur
        //                              3

        // scenario 1:
        //  just add breakfast
        // scenario 2 & 3:
        //  check if meal time is within timeLeft
        //  add task up till meal time, add meal time
        //  update timeLeft & timeLeftOver, continue

        // first check mode
        Time mealTime;
        Task mealTask;

        if(mealMode == 0) {
            mealTime = breakfastTime;
            mealTask = breakfast;
        }
        else if(mealMode == 1) {
            mealTime = lunchTime;
            mealTask = lunch;
        }
        else {
            mealTime = dinnerTime;
            mealTask = dinner;
        }

        // scenario 1
        if (mealTime.compareTo(currentTime) == 0) {
            timePoints.add(Time.cpy(currentTime));
            scheduledTasks.add(mealTask);
            currentTime.add(mealTask.getEstTime());
            return true;
        }

        // end is to see if the end of session comes before or after end of task
        // inserts meal into the current task in orderTasks() while loop, so any meal that is not within
        // the current task timeframe should wait until a task that is
        Time end;
        if (timeLeft.compareTo(interval) > 0)
            end = Time.cpy(Time.addition(currentTime, interval));
        else
            end = Time.cpy(Time.addition(currentTime, timeLeft));

        // scenario 2 & 3
        if (mealTime.compareTo(currentTime) > 0 && mealTime.compareTo(end) < 0) {

            // meal time and task time would be break up to: "task - meal - task"
            // where the second segment of task is the start of a new session regardless of whether the first segment
            // is a full session or not
            // part1 is the time of the first task segment
            timePoints.add(Time.cpy(currentTime));
            Time part1 = Time.subtraction(mealTime, currentTime);
            // add the first segment of task
            scheduledTasks.add(new Task(curTask.getName(), part1, curTask.getPriority()));
            timeLeft.subtract(part1);
            currentTime.add(part1);

            // add meal
            timePoints.add(Time.cpy(currentTime));
            scheduledTasks.add(mealTask);
            currentTime.add(mealTask.getEstTime());

            // since inserting a meal means the next task starts in a new session,
            // there is no need to track time left over from previous session
            if (!timeLeftOver.isZero()) {
                timeLeftOver.setHour(0);
                timeLeftOver.setMinute(0);
            }
            return true;
        }
        // false if meals are not in the current task timeframe
        return false;
    }

    // turns the schedule into a readable timetable
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("===================================================\n");
        str.append(String.format("%-10s", "Time"));
        str.append(String.format("%-30s", "Task"));
        str.append(String.format("%-10s\n", "Duration"));
        str.append("---------------------------------------------------\n");

        Iterator<Time> timeIt = timePoints.iterator();
        Iterator<Task> taskIt = scheduledTasks.iterator();
        while(timeIt.hasNext()) {
            str.append(String.format("%-10s", timeIt.next()));
            if(taskIt.hasNext()) {
                Task cur = taskIt.next();
                str.append(String.format("%-30s", cur.getName()));
                str.append(String.format("%-10s\n", cur.getEstTime()));
            }
        }
        str.append("\n");
        str.append("===================================================\n");

        return str.toString();
    }

}
