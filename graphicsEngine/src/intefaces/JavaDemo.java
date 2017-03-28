package intefaces;

import components.*;
import listeners.PSEvent;
import listeners.PSListener;
import runtime.events.EventType;

/**
 * Created by user on 5/27/15.
 */
//todo add window with error message
public class JavaDemo {
    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();

        PSComponent labelField1 = new PSLabelField(220, 750, 250, 50, "Welcome!");
        PSComponent labelField2 = new PSLabelField(100, 700, 250, 50, "Fill in this form, please.");
        PSComponent labelField3 = new PSLabelField(30, 555, 250, 35, "Your name");
        PSComponent nameField = new PSTextField(175, 550, 300, 35);
        PSComponent labelField8 = new PSLabelField(30, 505, 250, 35, "Group");
        PSComponent labelField5 = new PSLabelField(30, 435, 250, 35, "Education");
        PSComponent labelField6 = new PSLabelField(30, 365, 250, 35, "Faculty");
        PSComponent labelField7 = new PSLabelField(30, 150, 250, 35, "Error checking");
        PSComponent group = new PSTextField(175, 500, 100, 35);
        PSComponent education = new PSListBox(215, 430, 100, 50,new String[]{"Full - time", "Part - time"},0);
        PSComponent faculty = new PSComboBox(200, 365, 350, 30,new String[]{"Mathematics and Mechanics","Physics", "Applied Mathematics","Chemistry"},3);
        PSComponent results = new PSCheckBox(190, 300, 20, "I want to get results");
        PSComponent errorChecking = new PSToggleButton(50, 85, 116, 50, "On", "Off");

        graphicInterface.add(labelField1);
        graphicInterface.add(labelField2);
        graphicInterface.add(labelField3);
        graphicInterface.add(nameField);
        graphicInterface.add(labelField8);
        graphicInterface.add(labelField5);
        graphicInterface.add(labelField6);
        graphicInterface.add(labelField7);
        graphicInterface.add(group);
        graphicInterface.add(education);
        graphicInterface.add(faculty);
        graphicInterface.add(results);
        graphicInterface.add(errorChecking);

        graphicInterface.setVisible(true);
    }
}