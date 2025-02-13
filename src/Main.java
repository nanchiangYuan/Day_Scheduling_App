import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

/*
 * No UI, console loop for the app
 */
public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean toExit = false;

        ArrayList<Task> allTasks = new ArrayList<>();
        System.out.println(" ===============================");
        System.out.println(" ========= Day Planner =========");
        System.out.println(" ===============================");

        int schedType = -1;
        Schedule schedP = null;
        Schedule schedST = null;
        Schedule schedLT = null;
        Schedule schedR = null;

        while (!toExit) {

            System.out.println();
            System.out.println("Please enter the corresponding number for action: ");
            System.out.println("1. Create task");
            System.out.println("2. Delete task");
            System.out.println("3. Import tasks (csv file)");
            System.out.println("4. List Current Tasks");
            System.out.println("5. Generate Schedule (priority)");
            System.out.println("6. Generate Schedule (shortest time first)");
            System.out.println("7. Generate Schedule (longest time first)");
            System.out.println("8. Generate Schedule (random)");
            System.out.println("9. Save to file");
            System.out.println("10. Clear and Restart");
            System.out.println("11. Exit");

            if(!input.hasNextInt()) {
                System.out.println("Invalid action");
                input.nextLine();
                continue;
            }
            int inputOp = input.nextInt();
            input.nextLine();

            // temp value
            String taskName = "example";
            Time time = new Time(0, 0);
            int priority = 0;

            switch(inputOp) {

                // create task
                case 1:
                    boolean done = false;
                    while(!done) {
                        System.out.println("Enter task name: ");
                        if(!input.hasNextLine()) {
                            System.out.println("Format error");
                        }
                        else {
                            taskName = input.nextLine();
                            done = true;
                        }
                    }

                    done = false;
                    while(!done) {
                        System.out.println("Enter time: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
                        if(!input.hasNextLine()) {
                            System.out.println("Format error");
                            continue;
                        }
                        time = parseEstTime(input.nextLine());
                        if(time != null) {
                            done = true;
                        }
                    }

                    done = false;
                    while(!done) {
                        System.out.println("Enter priority: (format: any number above and includes 1)");
                        if(!input.hasNextInt()) {
                            System.out.println("Format error");
                        }
                        else {
                            priority = input.nextInt();
                            if(priority < 1)
                                System.out.println("Format error");
                            else
                                done = true;
                        }
                    }

                    Task newTask = new Task(taskName, time, priority);
                    allTasks.add(newTask);
                    break;

                // delete task
                case 2:
                    done = false;
                    while(!done) {
                        System.out.println("Enter task name to delete: ");
                        if(!input.hasNextLine())
                            System.out.println("Format error");
                        else {
                            taskName = input.nextLine();
                            done = true;
                        }
                    }

                    Iterator<Task> taskIt = allTasks.iterator();
                    while(taskIt.hasNext()) {
                        Task temp = taskIt.next();
                        if(taskName.equals(temp.getName())) {
                            allTasks.remove(temp);
                            break;
                        }
                    }
                    break;

                // import tasks
                case 3:
                    done = false;
                    while(!done) {
                        System.out.println("Enter file name: ");
                        if(!input.hasNextLine())
                            System.out.println("format error");
                        else {
                            if(parseFile(allTasks,input.nextLine())) {
                                done = true;
                            }
                            else
                                System.out.println("file error");
                        }
                    }
                    break;

                // list current tasks
                case 4:
                    listTasks(allTasks);
                    break;

                // Generate Schedule (priority)
                case 5:
                    schedP = new Schedule(allTasks);
                    enterScheduleInfo(input, schedP);
                    schedP.generatePriority();
                    System.out.println(schedP);
                    schedType = 0;
                    break;

                // Generate Schedule (shortest time first)
                case 6:
                    schedST = new Schedule(allTasks);
                    enterScheduleInfo(input, schedST);
                    System.out.println(schedST.getHasBreakfast());
                    schedST.generateTimeAscending();
                    System.out.println(schedST);
                    schedType = 1;
                    break;

                // Generate Schedule (longest time first)
                case 7:
                    schedLT = new Schedule(allTasks);
                    enterScheduleInfo(input, schedLT);
                    System.out.println(schedLT.getHasBreakfast());
                    schedLT.generateTimeDescending();
                    System.out.println(schedLT);
                    schedType = 2;
                    break;

                // Generate Schedule (random)
                case 8:
                    schedR = new Schedule(allTasks);
                    enterScheduleInfo(input, schedR);
                    schedR.generateRandom();
                    System.out.println(schedR);
                    schedType = 3;
                    break;

                // save to file
                case 9:
                    Schedule toSave = null;
                    if(schedType == -1) {
                        System.out.println("no schedule to save");
                        break;
                    }
                    else if(schedType == 0 && schedP != null) {
                        toSave = schedP;
                    }
                    else if(schedType == 1 && schedST != null) {
                        toSave = schedST;
                    }
                    else if(schedType == 2 && schedLT != null) {
                        toSave = schedLT;
                    }
                    else if(schedType == 3 && schedR != null) {
                        toSave = schedR;
                    }

                    boolean correctIn = false;
                    String filename = "test.txt";
                    while(!correctIn) {
                        System.out.println("Enter file name: ");
                        if(!input.hasNextLine())
                            System.out.println("format error");
                        else {
                            filename = input.nextLine();
                            correctIn = true;
                        }
                    }

                    File saveFile = new File(filename);
                    try{
                        if(!saveFile.createNewFile()) {
                            System.out.println("save file error");
                            break;
                        }

                    } catch (IOException e) {
                        System.out.println("save file error");
                        break;
                    }

                    try{
                        FileWriter fileOut = new FileWriter(saveFile);
                        fileOut.write(toSave.toString());
                        fileOut.close();
                        System.out.println("Saved file: " + filename);
                    } catch (IOException e) {
                        System.out.println("write file error");
                        break;
                    }
                    break;

                // clear and restart
                case 10:
                    allTasks = new ArrayList<>();
                    break;

                // exit
                case 11:
                    toExit = true;
                    break;

                // wrong number for action
                default:
                    System.out.println("Invalid action");
                    break;
            }

        }
    }

    /*
     * Print list of tasks to screen
     */
    public static void listTasks(ArrayList<Task> tasks) {
        if(tasks.isEmpty()) {
            System.out.println("No tasks currently");
            return;
        }

        Iterator<Task> taskIt = tasks.iterator();
        while(taskIt.hasNext()) {
            System.out.println(taskIt.next());
        }
    }

    /*
     * Turn string of time into Time object
     */
    public static Time parseEstTime(String input) {
        String delim = ":";
        String[] tokens = input.split(delim);

        // check if both hour and minute are present
        if(tokens.length != 2) {
            System.out.println("Format error");
            return null;
        }

        // parse hour and check if legal
        int hour = Integer.parseInt(tokens[0]);
        if(hour < 0 || hour > 23) {
            System.out.println("Format error");
            return null;
        }

        // parse minute and check if legal
        int minute = Integer.parseInt(tokens[1]);
        if(minute < 0 || minute > 59) {
            System.out.println("Format error");
            return null;
        }

        return new Time(hour, minute);

    }

    /*
     * parse a csv file into an arraylist of tasks
     * file format: taskName,estTime,priority
     */
    public static boolean parseFile(ArrayList<Task> allTasks, String fname) {
        File file = new File(fname);
        if(!file.exists())
            return false;

        try {
            Scanner fileInput = new Scanner(file);
            String delim = ",";
            while(fileInput.hasNextLine()) {
                String[] tokens = fileInput.nextLine().split(delim);
                Time time = parseEstTime(tokens[1]);
                int priority = Integer.parseInt(tokens[2]);
                allTasks.add(new Task(tokens[0], time, priority));
            }
        }
        catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    /*
     * loops that ask for time and duration of meals, sessions and breaks
     */
    public static void enterScheduleInfo(Scanner input, Schedule sched) {
        boolean done = false;

        // get session info
        while(!done) {
            System.out.println("Session Duration: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                Time sessDur = parseEstTime(input.nextLine());
                if(sessDur != null) {
                    sched.setSessionDur(sessDur);
                    done = true;
                }
            }
        }

        done = false;
        // get break info
        while(!done) {
            System.out.println("Break Duration: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                Time breakDur = parseEstTime(input.nextLine());
                if(breakDur != null) {
                    sched.setBreakDur(breakDur);
                    done = true;
                }
            }
        }

        done = false;
        // get break between task
        while(!done) {
            System.out.println("Break between tasks? Y/N");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                String ans = input.nextLine();
                if(ans.equals("Y") || ans.equals("y")) {
                    sched.setBreakBetweenTasks(true);
                    done = true;
                }
                else if(ans.equals("N") || ans.equals("n")) {
                    sched.setBreakBetweenTasks(false);
                    done = true;
                } else
                    System.out.println("Format error");

            }
        }

        done = false;
        // get breakfast
        while(!done) {
            System.out.println("Breakfast? Y/N");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                String ans = input.nextLine();
                if(ans.equals("Y") || ans.equals("y")) {
                    sched.setHasBreakfast(true);
                    done = true;
                }
                else if(ans.equals("N") || ans.equals("n")) {
                    sched.setHasBreakfast(false);
                    done = true;
                } else
                    System.out.println("Format error");

            }
        }

        if(sched.getHasBreakfast()) {
            done = false;
            // get breakfast time
            while(!done) {
                System.out.println("Breakfast time: (format: \"00:00\"  example: type \"13:00\" for 1 pm)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealTime = parseEstTime(input.nextLine());
                    if(mealTime != null) {
                        sched.setBreakfastTime(mealTime);
                        done = true;
                    }
                }
            }

            done = false;
            // get breakfast duration
            while(!done) {
                System.out.println("Breakfast duration: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealDur = parseEstTime(input.nextLine());
                    if(mealDur != null) {
                        sched.setBreakfastDur(mealDur);
                        done = true;
                    }

                }
            }
        }

        done = false;
        // get lunch
        while(!done) {
            System.out.println("Lunch? Y/N");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                String ans = input.nextLine();
                if(ans.equals("Y") || ans.equals("y")) {
                    sched.setHasLunch(true);
                    done = true;
                }
                else if(ans.equals("N") || ans.equals("n")) {
                    sched.setHasLunch(false);
                    done = true;
                } else
                    System.out.println("Format error");

            }
        }

        if(sched.getHasLunch()) {
            done = false;
            // get lunchtime
            while(!done) {
                System.out.println("Lunch time: (format: \"00:00\"  example: type \"13:00\" for 1 pm)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealTime = parseEstTime(input.nextLine());
                    if(mealTime != null) {
                        sched.setLunchTime(mealTime);
                        done = true;
                    }
                }
            }

            done = false;
            // get lunch duration
            while(!done) {
                System.out.println("Lunch duration: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealDur = parseEstTime(input.nextLine());
                    if(mealDur != null) {
                        sched.setLunchDur(mealDur);
                        done = true;
                    }

                }
            }
        }

        done = false;
        // get dinner
        while(!done) {
            System.out.println("Dinner? Y/N");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                String ans = input.nextLine();
                if(ans.equals("Y") || ans.equals("y")) {
                    sched.setHasDinner(true);
                    done = true;
                }
                else if(ans.equals("N") || ans.equals("n")) {
                    sched.setHasDinner(false);
                    done = true;
                } else
                    System.out.println("Format error");

            }
        }

        if(sched.getHasDinner()) {
            done = false;
            // get dinner time
            while(!done) {
                System.out.println("Dinner time: (format: \"00:00\"  example: type \"13:00\" for 1 pm)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealTime = parseEstTime(input.nextLine());
                    if(mealTime != null) {
                        sched.setDinnerTime(mealTime);
                        done = true;
                    }

                }
            }

            done = false;
            // get dinner duration
            while(!done) {
                System.out.println("Dinner duration: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
                if(!input.hasNextLine())
                    System.out.println("Format error");
                else {
                    Time mealDur = parseEstTime(input.nextLine());
                    if(mealDur != null) {
                        sched.setDinnerDur(mealDur);
                        done = true;
                    }

                }
            }

        }

        done = false;
        // set start time
        while(!done) {
            System.out.println("Start time: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                Time start = parseEstTime(input.nextLine());
                if(start != null) {
                    sched.setStartTime(start);
                    done = true;
                }

            }
        }

        done = false;
        // set end time
        while(!done) {
            System.out.println("End time: (format: \"00:00\"  example: type \"01:30\" for 90 minutes)");
            if(!input.hasNextLine())
                System.out.println("Format error");
            else {
                Time end = parseEstTime(input.nextLine());
                if(end != null) {
                    sched.setEndTime(end);
                    done = true;
                }
            }
        }
    }
}