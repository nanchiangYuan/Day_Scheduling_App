import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class DaySchedulingApp extends Application{

    // basic settings for color and font
    int CONTENT_SIZE = 16;
    String CONTENT_COLOR = "0x000000";
    String FONT = "Calibri";
    String DISPLAY_FONT = "Consolas";
    String DARK_COLOR = "0xAAAAAA";
    String BG_COLOR = "0xEEEEEE";
    String GREYED_COLOR = "0xBBBBBB";

    // default values for Schedule object
    String TASK_NAME_DEFAULT = "Read a Book";
    String TASK_TIME_DEFAULT = "01:15";
    String TASK_PRIORITY_DEFAULT = "1";
    String START_TIME_DEFAULT = "08:00";
    String END_TIME_DEFAULT = "17:00";
    String SESSION_DEFAULT = "00:50";
    String BREAK_DEFAULT = "00:10";
    String BREAKFAST_TIME_DEFAULT = "08:00";
    String BREAKFAST_LENGTH_DEFAULT = "01:00";
    String LUNCH_TIME_DEFAULT = "12:00";
    String LUNCH_LENGTH_DEFAULT = "01:00";
    String DINNER_TIME_DEFAULT = "18:00";
    String DINNER_LENGTH_DEFAULT = "01:00";

    ArrayList<Task> taskListArr;            // stores all tasks entered
    Schedule curSchedule;                   // the schedule generated, old ones are discarded

    StackPane base;                         // base of everything
    Rectangle titleBG;                      // title text BG
    Text title;                             // title text
    HBox allTasks;
    Text taskListTitle;
    ListView<Task> taskList;
    VBox allTasksBase;
    VBox allTasksButtons;
    Button fileButton;
    Button deleteButton;
    Button clearTasksButton;

    Rectangle taskRec;
    Text addTaskTitle;
    Rectangle addTaskTitleBG;
    Rectangle optionsRec;
    Text optionsTitle;
    Rectangle optionsTitleBG;
    Rectangle mealLine;
    Text mealsTitle;
    Rectangle mealsTitleBG;

    GridPane createTaskBase;
    Text taskNameTitle;
    TextField taskNameIn;
    Text taskTimeTitle;
    TextField taskTimeIn;
    Text taskPriorityTitle;
    TextField taskPriorityIn;
    Button createButton;

    GridPane timeSettingsBase;
    GridPane breakBBase;
    Text startTimeTitle;
    TextField startTimeIn;
    Text endTimeTitle;
    TextField endTimeIn;

    Text sessionTitle;
    TextField sessionIn;
    Text breakTitle;
    TextField breakIn;

    Text breakBetweenTitle;
    CheckBox breakBetweenCheck;

    GridPane breakfastBase;
    Text breakfastTitle;
    CheckBox breakfastCheck;
    Text breakfastTimeTitle;
    TextField breakfastTimeIn;
    Text breakfastLengthTitle;
    TextField breakfastLengthIn;

    GridPane lunchBase;
    Text lunchTitle;
    CheckBox lunchCheck;
    Text lunchTimeTitle;
    TextField lunchTimeIn;
    Text lunchLengthTitle;
    TextField lunchLengthIn;

    GridPane dinnerBase;
    Text dinnerTitle;
    CheckBox dinnerCheck;
    Text dinnerTimeTitle;
    TextField dinnerTimeIn;
    Text dinnerLengthTitle;
    TextField dinnerLengthIn;

    VBox scheduleBase;
    Text scheduleTitle;
    StackPane displayBase;
    TextArea displayText;
    GridPane generateOp;

    Button generateTimeAsc;
    Button generateTimeDes;
    Button generatePriority;
    Button generateRandom;
    Button clearSButton;
    Button toFileButton;
    TextArea warningDisplay;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        // initialize tasks list and schedule object
        taskListArr = new ArrayList<>();
        curSchedule = null;

        stage.setTitle("Day Scheduling App");

        // base of everything
        base = new StackPane();
        base.setAlignment(Pos.TOP_LEFT);
        base.setStyle("-fx-background-color: #EEEEEE;");

        // title
        titleBG = new Rectangle(1080, 40, Color.web(DARK_COLOR));
        title = new Text("Day Schedule");
        title.setTranslateY(8);
        title.setTranslateX(15);
        title.setFill(Color.web(CONTENT_COLOR));
        title.setFont(Font.font(FONT, 28));
        base.getChildren().add(titleBG);
        base.getChildren().add(title);

        // lines surrounding edit task list section
        taskRec = new Rectangle();
        taskRec.setTranslateX(15);
        taskRec.setTranslateY(55);
        taskRec.setWidth(540);
        taskRec.setHeight(300);
        taskRec.setArcWidth(20);
        taskRec.setArcHeight(20);
        taskRec.setFill(Color.TRANSPARENT);
        taskRec.setStroke(Color.web(DARK_COLOR));
        taskRec.setStrokeWidth(1);

        addTaskTitle = new Text("Edit Task List");
        addTaskTitle.setFill(Color.web(DARK_COLOR));
        addTaskTitle.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        addTaskTitle.setTranslateX(25);
        addTaskTitle.setTranslateY(49);
        addTaskTitleBG = new Rectangle();
        addTaskTitleBG.setFill(Color.web(BG_COLOR));
        addTaskTitleBG.setTranslateX(23);
        addTaskTitleBG.setTranslateY(49);
        addTaskTitleBG.setHeight(CONTENT_SIZE - 2);
        addTaskTitleBG.setWidth(77);
        base.getChildren().add(taskRec);
        base.getChildren().add(addTaskTitleBG);
        base.getChildren().add(addTaskTitle);

        // lines surrounding schedule settings section
        optionsRec = new Rectangle();
        optionsRec.setTranslateX(15);
        optionsRec.setTranslateY(375);
        optionsRec.setWidth(540);
        optionsRec.setHeight(335);
        optionsRec.setArcWidth(20);
        optionsRec.setArcHeight(20);
        optionsRec.setFill(Color.TRANSPARENT);
        optionsRec.setStroke(Color.web(DARK_COLOR));
        optionsRec.setStrokeWidth(1);

        optionsTitle = new Text("Schedule Settings");
        optionsTitle.setFill(Color.web(DARK_COLOR));
        optionsTitle.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        optionsTitle.setTranslateX(25);
        optionsTitle.setTranslateY(369);
        optionsTitleBG = new Rectangle();
        optionsTitleBG.setFill(Color.web(BG_COLOR));
        optionsTitleBG.setTranslateX(23);
        optionsTitleBG.setTranslateY(369);
        optionsTitleBG.setHeight(CONTENT_SIZE - 2);
        optionsTitleBG.setWidth(104);
        base.getChildren().add(optionsRec);
        base.getChildren().add(optionsTitleBG);
        base.getChildren().add(optionsTitle);

        // line that separates the schedule settings section
        mealLine = new Rectangle();
        mealLine.setHeight(1);
        mealLine.setWidth(528);
        mealLine.setTranslateX(21);
        mealLine.setTranslateY(510);
        mealLine.setFill(Color.web(DARK_COLOR));

        mealsTitle = new Text("Add Meals");
        mealsTitle.setFill(Color.web(DARK_COLOR));
        mealsTitle.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        mealsTitle.setTranslateX(32);
        mealsTitle.setTranslateY(504);
        mealsTitleBG = new Rectangle();
        mealsTitleBG.setFill(Color.web(BG_COLOR));
        mealsTitleBG.setTranslateX(29);
        mealsTitleBG.setTranslateY(505);
        mealsTitleBG.setHeight(CONTENT_SIZE - 2);
        mealsTitleBG.setWidth(68);
        base.getChildren().add(mealLine);
        base.getChildren().add(mealsTitleBG);
        base.getChildren().add(mealsTitle);

        // setup for edit task list section
        allTasks = new HBox(10);
        allTasks.setMaxHeight(220);
        allTasks.setMinWidth(540);
        allTasksBase = new VBox(2);
        allTasksBase.setMinWidth(410);
        // edit task list section title
        taskListTitle = new Text("All tasks: ");
        taskListTitle.setFill(Color.web(CONTENT_COLOR));
        taskListTitle.setFont(Font.font(FONT, CONTENT_SIZE + 2));
        taskList = new ListView<>();
        taskList.setMaxHeight(200);
        allTasksBase.getChildren().add(taskListTitle);
        allTasksBase.getChildren().add(taskList);
        allTasks.getChildren().add(allTasksBase);
        allTasks.setTranslateX(40);
        allTasks.setTranslateY(70);

        // edit task list section buttons
        fileButton = new Button("Import File");
        fileButton.setPrefWidth(80);
        fileButton.setOnAction(eventHandler -> {
            taskListArr = new ArrayList<>();
            taskList.getItems().clear();
            importFile(stage);
        });

        deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(80);
        deleteButton.setOnAction(eventHandler -> {
            int index = taskList.getSelectionModel().getSelectedIndex();
            if(index >= 0) {
                taskListArr.remove(index);
                taskList.getItems().clear();
                taskList.getItems().addAll(taskListArr);
            }
        });

        clearTasksButton = new Button("Clear Tasks");
        clearTasksButton.setPrefWidth(80);
        clearTasksButton.setOnAction(eventHandler -> {
            taskListArr = new ArrayList<>();
            taskList.getItems().clear();
        });

        // edit task list section buttons setup
        allTasksButtons = new VBox(5);
        allTasksButtons.setAlignment(Pos.BOTTOM_CENTER);
        allTasksButtons.getChildren().addAll(fileButton, deleteButton, clearTasksButton);
        allTasks.getChildren().add(allTasksButtons);

        // create new task area in edit task list section
        // creates the objects
        createTaskBase = new GridPane();
        createTaskBase.setHgap(10);
        taskNameTitle = new Text("Task Name");
        taskNameTitle.setFill(Color.web(CONTENT_COLOR));
        taskNameTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        taskNameIn = new TextField();
        taskNameIn.setPromptText(TASK_NAME_DEFAULT);
        taskTimeTitle = new Text("Length*");
        taskTimeTitle.setFill(Color.web(CONTENT_COLOR));
        taskTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        taskTimeIn = new TextField();
        taskTimeIn.setPromptText(TASK_TIME_DEFAULT);
        taskPriorityTitle = new Text("Priority*");
        taskPriorityTitle.setFill(Color.web(CONTENT_COLOR));
        taskPriorityTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        taskPriorityIn = new TextField();
        taskPriorityIn.setPromptText(TASK_PRIORITY_DEFAULT);
        // create new task area button
        createButton = new Button("Add");
        createButton.setPrefWidth(80);
        createButton.setOnAction(eventHandler -> {
            String defaultName = TASK_NAME_DEFAULT;
            String defaultTime = TASK_TIME_DEFAULT;
            String defaultPriority = TASK_PRIORITY_DEFAULT;
            if(!taskNameIn.getText().isEmpty())
                defaultName = taskNameIn.getText();
            if(!taskTimeIn.getText().isEmpty())
                defaultTime = taskTimeIn.getText();
            if(!taskPriorityIn.getText().isEmpty())
                defaultPriority = taskPriorityIn.getText();
            addTask(defaultName, defaultTime, defaultPriority);
            taskList.getItems().clear();
            taskList.getItems().addAll(taskListArr);
        });
        // create new task area setup in a gridPane
        ColumnConstraints tCol0 = new ColumnConstraints();
        tCol0.setPercentWidth(60);
        ColumnConstraints tCol1 = new ColumnConstraints();
        tCol1.setPercentWidth(20);
        ColumnConstraints tCol2 = new ColumnConstraints();
        tCol2.setPercentWidth(20);
        createTaskBase.getColumnConstraints().addAll(tCol0, tCol1, tCol2);
        createTaskBase.add(taskNameTitle, 0, 0);
        createTaskBase.add(taskTimeTitle, 1, 0);
        createTaskBase.add(taskPriorityTitle, 2, 0);
        createTaskBase.add(taskNameIn, 0, 1);
        createTaskBase.add(taskTimeIn, 1, 1);
        createTaskBase.add(taskPriorityIn, 2, 1);
        createButton.setTranslateX(460);
        createButton.setTranslateY(318);
        base.getChildren().add(createButton);
        createTaskBase.setTranslateX(40);
        createTaskBase.setTranslateY(300);
        createTaskBase.setMaxWidth(410);

        base.getChildren().add(createTaskBase);
        base.getChildren().add(allTasks);

        // schedule settings section setup
        startTimeTitle = new Text("Start Time*");
        startTimeTitle.setFill(Color.web(CONTENT_COLOR));
        startTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        startTimeIn = new TextField();
        startTimeIn.setPromptText("08:00");
        endTimeTitle = new Text("End Time");
        endTimeTitle.setFill(Color.web(CONTENT_COLOR));
        endTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        endTimeIn = new TextField();
        endTimeIn.setPromptText("17:00");

        sessionTitle = new Text("Session Length");
        sessionIn = new TextField();
        sessionIn.setPromptText("00:50");
        sessionTitle.setFill(Color.web(CONTENT_COLOR));
        sessionTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        breakTitle = new Text("Break Length");
        breakTitle.setFill(Color.web(CONTENT_COLOR));
        breakTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        breakIn = new TextField();
        breakIn.setPromptText("00:10");

        // schedule settings section checkbox
        breakBBase = new GridPane();
        breakBBase.setHgap(5);
        breakBBase.setAlignment(Pos.CENTER);
        breakBetweenTitle = new Text("Break Between Tasks");
        breakBetweenTitle.setFill(Color.web(CONTENT_COLOR));
        breakBetweenTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        breakBetweenCheck = new CheckBox();
        breakBBase.add(breakBetweenTitle, 1, 0);
        breakBBase.add(breakBetweenCheck, 0, 0);

        timeSettingsBase = new GridPane();
        timeSettingsBase.setHgap(10);
        ColumnConstraints tiCol0 = new ColumnConstraints();
        tiCol0.setPercentWidth(33);
        ColumnConstraints tiCol1 = new ColumnConstraints();
        tiCol1.setPercentWidth(33);
        ColumnConstraints tiCol2 = new ColumnConstraints();
        tiCol2.setPercentWidth(33);
        timeSettingsBase.getColumnConstraints().addAll(tiCol0, tiCol1, tiCol2);
        timeSettingsBase.setMaxWidth(505);
        timeSettingsBase.add(startTimeTitle, 0, 0);
        timeSettingsBase.add(startTimeIn, 0, 1);
        timeSettingsBase.add(endTimeTitle, 1, 0);
        timeSettingsBase.add(endTimeIn, 1, 1);
        Text temp = new Text();
        timeSettingsBase.add(temp, 0, 2);

        timeSettingsBase.add(sessionTitle, 0, 3);
        timeSettingsBase.add(sessionIn, 0, 4);
        timeSettingsBase.add(breakTitle, 1, 3);
        timeSettingsBase.add(breakIn, 1, 4);
        timeSettingsBase.setTranslateX(40);
        timeSettingsBase.setTranslateY(390);
        timeSettingsBase.add(breakBBase, 2, 4);

        base.getChildren().add(timeSettingsBase);

        // schedule settings section meal times
        breakfastTitle = new Text("Breakfast");
        breakfastTitle.setFill(Color.web(GREYED_COLOR));
        breakfastTitle.setFont(Font.font(FONT, CONTENT_SIZE));

        // when checkbox is checked, the words turn black and text fields become editable
        breakfastCheck = new CheckBox();
        breakfastCheck.setOnAction(eventHandler -> {
            if(breakfastCheck.isSelected()) {
                breakfastTitle.setFill(Color.web(CONTENT_COLOR));
                breakfastTimeTitle.setFill(Color.web(CONTENT_COLOR));
                breakfastLengthTitle.setFill(Color.web(CONTENT_COLOR));
                breakfastTimeIn.setEditable(true);
                breakfastLengthIn.setEditable(true);
            } else {
                breakfastTitle.setFill(Color.web(GREYED_COLOR));
                breakfastTimeTitle.setFill(Color.web(GREYED_COLOR));
                breakfastLengthTitle.setFill(Color.web(GREYED_COLOR));
                breakfastTimeIn.setEditable(false);
                breakfastLengthIn.setEditable(false);
            }
        });

        breakfastTimeTitle = new Text("Time");
        breakfastTimeTitle.setFill(Color.web(GREYED_COLOR));
        breakfastTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        breakfastTimeIn = new TextField();
        breakfastTimeIn.setPromptText("08:00");
        breakfastLengthTitle = new Text("Length");
        breakfastLengthTitle.setFill(Color.web(GREYED_COLOR));
        breakfastLengthTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        breakfastLengthIn = new TextField();
        breakfastLengthIn.setPromptText("01:00");

        // initially text fields are not editable, until the checkbox is checked
        breakfastTimeIn.setEditable(false);
        breakfastLengthIn.setEditable(false);

        // GridPane for setup for every meal time
        breakfastBase = new GridPane();
        breakfastBase.setHgap(5);
        breakfastBase.setTranslateX(65);
        breakfastBase.setTranslateY(530);
        GridPane breakfastCheckBase = new GridPane();
        breakfastCheckBase.setHgap(5);
        breakfastCheckBase.add(breakfastCheck, 0, 0);
        breakfastCheckBase.add(breakfastTitle, 1, 0);
        breakfastBase.add(breakfastCheckBase, 0, 0);
        GridPane.setHalignment(breakfastTimeTitle, HPos.RIGHT);
        GridPane.setHalignment(breakfastLengthTitle, HPos.RIGHT);

        breakfastBase.add(breakfastTimeTitle, 0, 1);
        breakfastBase.add(breakfastTimeIn, 1, 1);
        breakfastBase.add(breakfastLengthTitle, 2, 1);
        breakfastBase.add(breakfastLengthIn, 3, 1);
        ColumnConstraints mCol0 = new ColumnConstraints();
        mCol0.setPercentWidth(20);
        ColumnConstraints mCol1 = new ColumnConstraints();
        mCol1.setPercentWidth(30);
        ColumnConstraints mCol2 = new ColumnConstraints();
        mCol2.setPercentWidth(20);
        ColumnConstraints mCol3 = new ColumnConstraints();
        mCol3.setPercentWidth(30);
        breakfastBase.setMaxWidth(420);
        breakfastBase.getColumnConstraints().addAll(mCol0, mCol1, mCol2, mCol3);

        base.getChildren().add(breakfastBase);

        // lunch
        lunchTitle = new Text("Lunch");
        lunchTitle.setFill(Color.web(GREYED_COLOR));
        lunchTitle.setFont(Font.font(FONT, CONTENT_SIZE));

        lunchCheck = new CheckBox();
        lunchCheck.setOnAction(eventHandler -> {
            if(lunchCheck.isSelected()) {
                lunchTitle.setFill(Color.web(CONTENT_COLOR));
                lunchTimeTitle.setFill(Color.web(CONTENT_COLOR));
                lunchLengthTitle.setFill(Color.web(CONTENT_COLOR));
                lunchTimeIn.setEditable(true);
                lunchLengthIn.setEditable(true);
            } else {
                lunchTitle.setFill(Color.web(GREYED_COLOR));
                lunchTimeTitle.setFill(Color.web(GREYED_COLOR));
                lunchLengthTitle.setFill(Color.web(GREYED_COLOR));
                lunchTimeIn.setEditable(false);
                lunchLengthIn.setEditable(false);
            }
        });

        lunchTimeTitle = new Text("Time");
        lunchTimeTitle.setFill(Color.web(GREYED_COLOR));
        lunchTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        lunchTimeIn = new TextField();
        lunchTimeIn.setPromptText("12:00");
        lunchLengthTitle = new Text("Length");
        lunchLengthTitle.setFill(Color.web(GREYED_COLOR));
        lunchLengthTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        lunchLengthIn = new TextField();
        lunchLengthIn.setPromptText("01:00");

        lunchTimeIn.setEditable(false);
        lunchLengthIn.setEditable(false);

        lunchBase = new GridPane();
        lunchBase.setHgap(5);
        lunchBase.setTranslateX(65);
        lunchBase.setTranslateY(590);
        GridPane lunchCheckBase = new GridPane();
        lunchCheckBase.setHgap(5);
        lunchCheckBase.add(lunchCheck, 0, 0);
        lunchCheckBase.add(lunchTitle, 1, 0);
        lunchBase.add(lunchCheckBase, 0, 0);
        GridPane.setHalignment(lunchTimeTitle, HPos.RIGHT);
        GridPane.setHalignment(lunchLengthTitle, HPos.RIGHT);

        lunchBase.add(lunchTimeTitle, 0, 1);
        lunchBase.add(lunchTimeIn, 1, 1);
        lunchBase.add(lunchLengthTitle, 2, 1);
        lunchBase.add(lunchLengthIn, 3, 1);
        lunchBase.setMaxWidth(420);
        lunchBase.getColumnConstraints().addAll(mCol0, mCol1, mCol2, mCol3);

        base.getChildren().add(lunchBase);

        // dinner
        dinnerTitle = new Text("Dinner");
        dinnerTitle.setFill(Color.web(GREYED_COLOR));
        dinnerTitle.setFont(Font.font(FONT, CONTENT_SIZE));

        dinnerCheck = new CheckBox();
        dinnerCheck.setOnAction(eventHandler -> {
            if(dinnerCheck.isSelected()) {
                dinnerTitle.setFill(Color.web(CONTENT_COLOR));
                dinnerTimeTitle.setFill(Color.web(CONTENT_COLOR));
                dinnerLengthTitle.setFill(Color.web(CONTENT_COLOR));
                dinnerTimeIn.setEditable(true);
                dinnerLengthIn.setEditable(true);
            } else {
                dinnerTitle.setFill(Color.web(GREYED_COLOR));
                dinnerTimeTitle.setFill(Color.web(GREYED_COLOR));
                dinnerLengthTitle.setFill(Color.web(GREYED_COLOR));
                dinnerTimeIn.setEditable(false);
                dinnerLengthIn.setEditable(false);
            }
        });

        dinnerTimeTitle = new Text("Time");
        dinnerTimeTitle.setFill(Color.web(GREYED_COLOR));
        dinnerTimeTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        dinnerTimeIn = new TextField();
        dinnerTimeIn.setPromptText("18:00");
        dinnerLengthTitle = new Text("Length");
        dinnerLengthTitle.setFill(Color.web(GREYED_COLOR));
        dinnerLengthTitle.setFont(Font.font(FONT, CONTENT_SIZE));
        dinnerLengthIn = new TextField();
        dinnerLengthIn.setPromptText("01:00");

        dinnerTimeIn.setEditable(false);
        dinnerLengthIn.setEditable(false);

        dinnerBase = new GridPane();
        dinnerBase.setHgap(5);
        dinnerBase.setTranslateX(65);
        dinnerBase.setTranslateY(650);
        GridPane dinnerCheckBase = new GridPane();
        dinnerCheckBase.setHgap(5);
        dinnerCheckBase.add(dinnerCheck, 0, 0);
        dinnerCheckBase.add(dinnerTitle, 1, 0);
        dinnerBase.add(dinnerCheckBase, 0, 0);
        GridPane.setHalignment(dinnerTimeTitle, HPos.RIGHT);
        GridPane.setHalignment(dinnerLengthTitle, HPos.RIGHT);

        dinnerBase.add(dinnerTimeTitle, 0, 1);
        dinnerBase.add(dinnerTimeIn, 1, 1);
        dinnerBase.add(dinnerLengthTitle, 2, 1);
        dinnerBase.add(dinnerLengthIn, 3, 1);
        dinnerBase.setMaxWidth(420);
        dinnerBase.getColumnConstraints().addAll(mCol0, mCol1, mCol2, mCol3);

        base.getChildren().add(dinnerBase);

        // schedule display area on the right
        scheduleBase = new VBox(8);
        scheduleBase.setMaxWidth(450);
        scheduleTitle = new Text("Schedule: ");
        scheduleTitle.setFill(Color.web(CONTENT_COLOR));
        scheduleTitle.setFont(Font.font(FONT, CONTENT_SIZE + 2));
        scheduleBase.getChildren().add(scheduleTitle);

        // where the schedule is displayed
        displayBase = new StackPane();
        displayBase.setAlignment(Pos.TOP_LEFT);
        displayBase.setMinWidth(450);
        displayBase.setMinHeight(455);
        displayText = new TextArea();
        displayText.setEditable(false);
        displayBase.getChildren().add(displayText);
        scheduleBase.getChildren().add(displayBase);
        base.getChildren().add(scheduleBase);
        scheduleBase.setTranslateX(592);
        scheduleBase.setTranslateY(70);

        // buttons to generate schedules based on different criteria
        generateTimeAsc = new Button("Generate (Time Ascending)");
        generateTimeAsc.setPrefWidth(200);
        generateTimeAsc.setOnAction(eventHandler -> {
            clearSchedule();
            generateSchedule(0);
            displaySchedule();
        });

        generateTimeDes = new Button("Generate (Time Descending)");
        generateTimeDes.setPrefWidth(200);
        generateTimeDes.setOnAction(eventHandler -> {
            clearSchedule();
            generateSchedule(1);
            displaySchedule();
        });

        generatePriority = new Button("Generate (Priority)");
        generatePriority.setPrefWidth(200);
        generatePriority.setOnAction(eventHandler -> {
            clearSchedule();
            generateSchedule(2);
            displaySchedule();
        });

        generateRandom = new Button("Generate (Random)");
        generateRandom.setPrefWidth(200);
        generateRandom.setOnAction(eventHandler -> {
            clearSchedule();
            generateSchedule(3);
            displaySchedule();
        });

        // button to save current schedule to file
        toFileButton = new Button("Save to File");
        toFileButton.setPrefWidth(200);
        toFileButton.setOnAction(eventHandler -> {
            exportFile(stage);
        });

        // clear out the schedule display area
        clearSButton = new Button("Clear Schedule");
        clearSButton.setPrefWidth(200);
        clearSButton.setOnAction(eventHandler -> {
            clearSchedule();
        });

        // the buttons setup
        generateOp = new GridPane();
        generateOp.setHgap(10);
        generateOp.setVgap(10);
        generateOp.setAlignment(Pos.CENTER);
        generateOp.add(generateTimeAsc, 0, 0);
        generateOp.add(generateTimeDes, 1, 0);
        generateOp.add(generatePriority, 0, 1);
        generateOp.add(generateRandom, 1, 1);
        generateOp.add(toFileButton , 0, 2);
        generateOp.add(clearSButton, 1, 2);

        scheduleBase.getChildren().add(generateOp);

        // text field that displays warning messages
        warningDisplay = new TextArea();
        warningDisplay.setEditable(false);
        warningDisplay.setMaxHeight(10);
        scheduleBase.getChildren().add(warningDisplay);

        // Popup tips for inputting time length format
        Popup lengthTipPopup = new Popup();
        StackPane lengthTipBase = new StackPane();
        Rectangle lengthTipBG = new Rectangle();
        lengthTipBG.setHeight(15);
        lengthTipBG.setWidth(320);
        lengthTipBG.setFill(Color.web("0xFFF0CB"));
        lengthTipBG.setStroke(Color.web("0xCCCCCC"));
        lengthTipBG.setStrokeWidth(1);
        Text lengthTipText = new Text("format: \"00:00\"  example: type \"01:30\" for 90 minutes");
        lengthTipText.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        lengthTipText.setFill(Color.web("0x000000"));
        lengthTipBase.getChildren().add(lengthTipBG);
        lengthTipBase.getChildren().add(lengthTipText);
        lengthTipPopup.getContent().add(lengthTipBase);
        lengthTipPopup.setAutoHide(true);
        // popup shows up when hovered over text
        taskTimeTitle.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                lengthTipPopup.setAnchorX(stage.getX() + 320);
                lengthTipPopup.setAnchorY(stage.getY() + 310);
                lengthTipPopup.show(stage);
            } else {
                lengthTipPopup.hide();
            }
        });

        // Popup tips for inputting priority format
        Popup priorityTipPopup = new Popup();
        StackPane priorityTipBase = new StackPane();
        Rectangle priorityTipBG = new Rectangle();
        priorityTipBG.setHeight(15);
        priorityTipBG.setWidth(210);
        priorityTipBG.setFill(Color.web("0xFFF0CB"));
        priorityTipBG.setStroke(Color.web("0xCCCCCC"));
        priorityTipBG.setStrokeWidth(1);
        Text priorityTipText = new Text("1 for highest priority, 10 for lowest");
        priorityTipText.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        priorityTipText.setFill(Color.web("0x000000"));
        priorityTipBase.getChildren().add(priorityTipBG);
        priorityTipBase.getChildren().add(priorityTipText);
        priorityTipPopup.getContent().add(priorityTipBase);
        priorityTipPopup.setAutoHide(true);

        taskPriorityTitle.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                priorityTipPopup.setAnchorX(stage.getX() + 400);
                priorityTipPopup.setAnchorY(stage.getY() + 310);
                priorityTipPopup.show(stage);
            } else {
                priorityTipPopup.hide();
            }
        });

        // Popup tips for inputting time of day format
        Popup timeTipPopup = new Popup();
        StackPane timeTipBase = new StackPane();
        Rectangle timeTipBG = new Rectangle();
        timeTipBG.setHeight(15);
        timeTipBG.setWidth(286);
        timeTipBG.setFill(Color.web("0xFFF0CB"));
        timeTipBG.setStroke(Color.web("0xCCCCCC"));
        timeTipBG.setStrokeWidth(1);
        Text timeTipText = new Text("format: \"00:00\"  example: type \"13:00\" for 1 pm");
        timeTipText.setFont(Font.font(FONT, CONTENT_SIZE - 2));
        timeTipText.setFill(Color.web("0x000000"));
        timeTipBase.getChildren().add(timeTipBG);
        timeTipBase.getChildren().add(timeTipText);
        timeTipPopup.getContent().add(timeTipBase);
        timeTipPopup.setAutoHide(true);

        startTimeTitle.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                timeTipPopup.setAnchorX(stage.getX() + 70);
                timeTipPopup.setAnchorY(stage.getY() + 400);
                timeTipPopup.show(stage);
            } else {
                timeTipPopup.hide();
            }
        });

        Scene bg = new Scene(base, 1080, 720);
        stage.setScene(bg);
        stage.show();
    }

    /*
     * Imports a csv file of tasks with elements separated by "," and one task on one line
     * file format example:
     * Laundry,01:00,3
     */
    public void importFile(Stage stage) {
        FileChooser openFileWin = new FileChooser();
        openFileWin.setTitle("Import");
        openFileWin.setInitialDirectory(new File(System.getProperty("user.dir")));
        File importFile = openFileWin.showOpenDialog(stage);
        Scanner input;
        try{
            input = new Scanner(importFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        parseFile(input);
        taskList.getItems().addAll(taskListArr);

    }

    /*
     * parse the inputted file
     */
    public void parseFile(Scanner input) {

        String delim = ",";
        while(input.hasNextLine()) {
            String[] tokens = input.nextLine().split(delim);
            Time time = parseEstTime(tokens[1]);
            int priority = Integer.parseInt(tokens[2]);
            taskListArr.add(new Task(tokens[0], time, priority));
        }

    }

    /*
     * Adds a task into the list. If time or priority format is wrong, no task will be added and a warning will
     * be displayed.
     */
    public void addTask(String taskName, String estTime, String priority) {

        warningDisplay.clear();
        Time added = parseEstTime(estTime);
        if(added == null) {
            warningDisplay.setText("Wrong time format when adding task");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }

        int priorityInt;
        try {
            priorityInt = Integer.parseInt(priority);
        } catch (NumberFormatException e) {
            warningDisplay.setText("Wrong priority format when adding task");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }

        taskListArr.add(new Task(taskName, added, priorityInt));
    }

    /*
     * Parses a string into a Time object. Returns null if string is in a wrong format.
     * Only accepts "00:00" to "23:59"
     */
    public static Time parseEstTime(String input) {
        String delim = ":";
        String[] tokens = input.split(delim);

        // check if both hour and minute are present
        if(tokens.length != 2)
            return null;

        // parse hour and check if legal
        int hour = Integer.parseInt(tokens[0]);
        if(hour < 0 || hour > 23)
            return null;

        // parse minute and check if legal
        int minute = Integer.parseInt(tokens[1]);
        if(minute < 0 || minute > 59)
            return null;

        return new Time(hour, minute);

    }

    /*
     * Generates a schedule based on inputted information.
     * Mode 0 for ascending length of task, mode 1 for descending length of time, mode 2 for priority, mode 3 for
     * random. Will not generate schedule if any of the information entered is in a wrong format, a warning message is
     * displayed instead.
     */
    public void generateSchedule(int mode) {

        warningDisplay.clear();

        curSchedule = new Schedule(taskListArr);
        if(taskListArr.isEmpty())
            return;

        String startTime = START_TIME_DEFAULT;
        String endTime = END_TIME_DEFAULT;
        String sessionLength = SESSION_DEFAULT;
        String breakLength = BREAK_DEFAULT;

        String breakfastTime = BREAKFAST_TIME_DEFAULT;
        String lunchTime = LUNCH_TIME_DEFAULT;
        String dinnerTime = DINNER_TIME_DEFAULT;
        String breakfastLength = BREAKFAST_LENGTH_DEFAULT;
        String lunchLength = LUNCH_LENGTH_DEFAULT;
        String dinnerLength = DINNER_LENGTH_DEFAULT;

        if(!startTimeIn.getText().isEmpty())
            startTime = startTimeIn.getText();
        if(!endTimeIn.getText().isEmpty())
            endTime = endTimeIn.getText();
        if(!sessionIn.getText().isEmpty())
            sessionLength = sessionIn.getText();
        if(!breakIn.getText().isEmpty())
            breakLength = breakIn.getText();

        if(!breakfastTimeIn.getText().isEmpty())
            breakfastTime = breakfastTimeIn.getText();
        if(!breakfastLengthIn.getText().isEmpty())
            breakfastLength = breakfastLengthIn.getText();
        if(!lunchTimeIn.getText().isEmpty())
            lunchTime = lunchTimeIn.getText();
        if(!lunchLengthIn.getText().isEmpty())
            lunchLength = lunchLengthIn.getText();
        if(!dinnerTimeIn.getText().isEmpty())
            dinnerTime = dinnerTimeIn.getText();
        if(!dinnerLengthIn.getText().isEmpty())
            dinnerLength = dinnerLengthIn.getText();

        Time startTimeT = parseEstTime(startTime);
        if(startTimeT == null) {
            warningDisplay.setText("Wrong start time format");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }
        curSchedule.setStartTime(startTimeT);

        Time endTimeT = parseEstTime(endTime);
        if(endTimeT == null) {
            warningDisplay.setText("Wrong end time format");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }
        curSchedule.setEndTime(endTimeT);

        Time sessionLengthT = parseEstTime(sessionLength);
        if(sessionLengthT == null) {
            warningDisplay.setText("Wrong session length format");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }
        curSchedule.setSessionDur(sessionLengthT);

        Time breakLengthT = parseEstTime(breakLength);
        if(breakLengthT == null) {
            warningDisplay.setText("Wrong break length format");
            warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
            return;
        }
        curSchedule.setBreakDur(breakLengthT);

        curSchedule.setBreakBetweenTasks(breakBetweenCheck.isSelected());

        curSchedule.setHasBreakfast(breakfastCheck.isSelected());
        curSchedule.setHasLunch(lunchCheck.isSelected());
        curSchedule.setHasDinner(dinnerCheck.isSelected());

        if(curSchedule.getHasBreakfast()) {
            Time breakfastTimeT = parseEstTime(breakfastTime);
            if(breakfastTimeT == null) {
                warningDisplay.setText("Wrong breakfast time format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setBreakfastTime(breakfastTimeT);

            Time breakfastLengthT = parseEstTime(breakfastLength);
            if(breakfastLengthT == null) {
                warningDisplay.setText("Wrong breakfast length format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setBreakfastDur(breakfastLengthT);
        }

        if(curSchedule.getHasLunch()) {
            Time lunchTimeT = parseEstTime(lunchTime);
            if(lunchTimeT == null) {
                warningDisplay.setText("Wrong lunch time format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setLunchTime(lunchTimeT);

            Time lunchLengthT = parseEstTime(lunchLength);
            if(lunchLengthT == null) {
                warningDisplay.setText("Wrong lunch length format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setLunchDur(lunchLengthT);
        }

        if(curSchedule.getHasDinner()) {
            Time dinnerTimeT = parseEstTime(dinnerTime);
            if(dinnerTimeT == null) {
                warningDisplay.setText("Wrong dinner time format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setDinnerTime(dinnerTimeT);

            Time dinnerLengthT = parseEstTime(dinnerLength);
            if(dinnerLengthT == null) {
                warningDisplay.setText("Wrong dinner length format");
                warningDisplay.setFont(Font.font(FONT, CONTENT_SIZE - 2));
                return;
            }
            curSchedule.setDinnerDur(dinnerLengthT);
        }

        switch(mode) {
            case 0:
                curSchedule.generateTimeAscending();
                break;
            case 1:
                curSchedule.generateTimeDescending();
                break;
            case 2:
                curSchedule.generatePriority();
                break;
            case 3:
            default:
                curSchedule.generateRandom();
                break;
        }
    }

    /*
     * Displays the result schedule in the display text field
     */
    public void displaySchedule() {
        if(curSchedule != null) {
            displayText.setText(curSchedule.toString());
            displayText.setFont(Font.font(DISPLAY_FONT, CONTENT_SIZE));
        }
    }

    /*
     * Clears the display text field
     */
    public void clearSchedule() {
        displayText.clear();
    }

    /*
     * Save the schedule to a file
     */
    public void exportFile(Stage stage) {

        if(curSchedule == null || taskListArr.isEmpty())
            return;

        FileChooser saveFileWin = new FileChooser();
        saveFileWin.setTitle("Save");
        saveFileWin.setInitialDirectory(new File(System.getProperty("user.dir")));

        File saveFile = saveFileWin.showSaveDialog(stage);

        try{
            if(!saveFile.createNewFile()) {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileWriter writer = new FileWriter(saveFile);
            writer.write(curSchedule.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}