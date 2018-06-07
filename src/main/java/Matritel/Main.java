package Matritel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static Matritel.Organiser.stringParseToDate;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        // Create a schedule for the dishwasher consisting of a list of people
        // who empty the dishwasher in the morning and a list of people who fill it up
        // after the course!
        // Your input is the names in our group!
        // Outputs:a
        // 1. A map with Integer keys (value = number of the day) and a value containing two names!
        // Do not worry about dates, 10th day for example does not mean march 10, or tenth working day
        // from now on, it is just the tenth day when someone has to empty and someone
        // has to fill up the dishwasher!
        // 2. A map with name keys containing a value defining what days that person
        //    will be assigned to something!
        // 3. Print out a readable list of the schedule!
        // 4. Print out an importable format (CSV)!

        // Re-implement dishwasher scheduler!
        // Use dates, consider weekends, define exceptions for holidays and the course break!
        // From a file read days for users where they cannot do dishwasher emptying filling!
        // Create a list of people who are emptying and filling until the end of the course!
        // Preferred output format is CSV!

        //- Use Mysql Workbench or H2 database or https://hub.docker.com/r/mysql/mysql-server/ !
        //- Using JDBC or Hibernate fill up the DishwasherSchedule table!

        Scanner sc = new Scanner(System.in);
        String holidayFilePath="/home/dtorok/IdeaProjects/0intermediate/dishwasherAndDB/files/dishwasherHolidays.txt";
        System.out.println("Provide the name of the current course: ");
        String coursename=sc.nextLine();
        String courseEndDate=stringParseToDate();
        Organiser org = new Organiser(coursename, courseEndDate);
        org.setEndOfTrainingString(courseEndDate);
        org.createTeamMemberList();
        /*
        for (int i = 0; i < org.getTeamMemberList().size(); i++) {
            System.out.println(org.getTeamMemberList().get(i).getId() +" "+org.getTeamMemberList().get(i).getFirstName()+" "+org.getTeamMemberList().get(i).getLastName());
        }
        */
        org.createHolidays();
        int days = org.intervallChecker();
        org.choseWhoIsInDuty(days);
        org.uploadDataToDishwasherScheduleTable();
        /*
        org.csvCreator("/home/dtorok/resultName.csv");
        org.csvDateCreator("/home/dtorok/resultDate.csv");
        System.out.println("The created files can be found here:");
        System.out.println("/home/dtorok/resultName.csv");
        System.out.println("/home/dtorok/resultDate.csv");
        */
    }

}

