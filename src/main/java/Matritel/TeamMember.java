package Matritel;

import java.text.SimpleDateFormat;
import java.util.*;

public class TeamMember {

    //Each TeamMember object has firstName, and two Lists:
    //one List for storing the off-duty days (the days when the given team member won't be available)
    //and another List storing the data of the duty-days: the date and the part of the day (mor/aft)

    private int id;
    private String firstName;
    private String lastName;
    private List<Map<Date, String>> dutyDaysList = new ArrayList<>();

    public TeamMember(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addTodutyDaysList (Map<Date, String> dutyDay){

        dutyDaysList.add(dutyDay);
    }

    //This method creates a String containing the date of a given day and the part of it (morning/afternoon)
    //This method uses the Maps of the dutyDaysList formatting and concatenating it's key and value
    //the created String will be saved into the .csv file by the csvCreator() method.
    //THIS METHOD IS OUT OF DATE DUE TO DATABASE USAGE!!!
    public String createString(){

        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd");
        String ret="";

        for (int i = 0; i < dutyDaysList.size(); i++) {

            Set tempset = dutyDaysList.get(i).keySet();
            Iterator tempsetIterator = tempset.iterator();
            String tempString= sdf0.format((Date) tempsetIterator.next());

            Collection col = dutyDaysList.get(i).values();
            Iterator colIterator= col.iterator();
            String partOfTheDay = (String)colIterator.next();
            ret = ret.concat(tempString+" - ").concat(partOfTheDay+";");
        }
    return ret;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
