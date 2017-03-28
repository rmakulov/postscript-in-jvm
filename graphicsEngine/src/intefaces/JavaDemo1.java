package intefaces;

import components.*;

/**
 * Created by user on 5/27/15.
 */
public class JavaDemo1 extends GraphicInterface{
    public JavaDemo1() {
        PSComponent labelField1 = new PSLabelField(220, 750, 250, 50, "Welcome!");
        PSComponent labelField2 = new PSLabelField(100, 700, 250, 50, "Fill in this form, please.");
        PSComponent labelField3 = new PSLabelField(30, 555, 250, 35, "Your name");
        PSComponent nameField = new PSTextField(175, 550, 300, 35);
        PSComponent labelField8 = new PSLabelField(30, 505, 250, 35, "Group");
        PSComponent labelField5 = new PSLabelField(30, 435, 250, 35, "Education");
        PSComponent labelField6 = new PSLabelField(30, 365, 250, 35, "Faculty");
        PSComponent labelField7 = new PSLabelField(30, 220, 250, 35, "Error checking");
        PSComponent group = new PSTextField(175, 500, 100, 35);
        PSComponent education = new PSListBox(215, 430, 120, 50,new String[]{"Full - time", "Part - time"},0);
        PSComponent faculty = new PSComboBox(200, 365, 370, 30,new String[]{"Mathematics and Mechanics","Physics", "Applied Mathematics","Chemistry"},3);
        PSComponent results = new PSCheckBox(190, 300, 20, "I want to get results");
        PSComponent errorChecking = new PSToggleButton(50, 155, 116, 50, "On", "Off");

        add(labelField1);
        add(labelField2);
        add(labelField3);
        add(nameField);
        add(labelField8);
        add(labelField5);
        add(labelField6);
        add(labelField7);
        add(group);
        add(education);
        add(faculty);
        add(results);
        add(errorChecking);

        setVisible(true);
    }

    public static void main(String[] args) {
        new JavaDemo1();
    }
}