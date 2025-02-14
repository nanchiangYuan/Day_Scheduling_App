Day Scheduling App  
-----------------------------------------------------------
Author: Nanchiang Yuan (Ashley)
This is an app that takes a list of tasks needed done by the user and generates a schedule of a day according to different settings and criteria the user gets to specify.

Functionality of the app:  
1. Edit Task List: (top left area of the interface)  
   - The task list is displayed in the area beneath the text "All tasks:"  
   - The text fields for "Task Name", "Length" and "Priority" is where the user can add their own tasks.  
       - Task Name: the name of the task  
       - Length: how much time the task needs in order to complete it (input format: "01:30" for 90 minutes)  
       - Priority: how urgent the task is  
   - The import button allows the user to import a file of many tasks:  
       - the file should be a csv file  
       - format of the file:  
         task1,00:30,3  
         task2,01:45,2  
   - Select a task from the task list and press the delete button to delete  
   - Remove all tasks from list by clicking the clear button  

2. Schedule Settings: (bottom left area)  
   - Users can edit the settings for this schedule.  
   - "Start Time" is the time the user wants to start doing the tasks.  
   - "End Time" is the time the user wants to stop. (If there are still more tasks to do, the rest will be omitted)  
   - "Session Length" is the length of one work session, ie how long the user wants to work on their tasks before a break.  
   - "Break length" is how long the user wants to rest between sessions.  
   - "Break Between Tasks" if checked, it means if a task ends, a break will be scheduled.  
   - If not checked, a new task begins immediately after the end of the previous one.  
   - Check the boxes next to the different meals to add them to the schedule.  
   - The "Time" and "Length" of the meals are respectively the time the user wants to eat that meal and how long they want to eat it for.  
   
3. Schedule display: (right area)  
   - The white space is the display area for the generated schedule.  
   - The top four buttons beneathe the white space generate the schedule based on different criteria:  
       - Time Ascending: tasks with shorter lengths will be scheduled first  
       - Time Descending: tasks with longer lengths will be shceduled first  
       - Priority: tasks with higher priority will be scheduled first  
       - Random: tasks are ordered randomly  
   - The save to file button lets the user save the schedule to a file.
   - The clear schedule button clears the display.
