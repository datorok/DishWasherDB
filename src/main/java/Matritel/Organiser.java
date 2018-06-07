package Matritel;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Organiser {
    private static final Logger logger = LoggerFactory.getLogger(Organiser.class);
    //The Organiser object orders the tasks to a given TeamMember. The ordering method is the following:
    //the program reads 3 different Lists as a parameter:
    //dishwasherTeamMembers.txt contains the names of the TeamMembers
    //dishwasherHolidays.txt contains the holidays, when no task is delegated
    //dishwasherOffDuty.txt contains the data regarding the TeamMembers absence.
    //The organiser puts the names of the TeamMembers into a list (teamMembersPool)
    //and chooses 2 names (one for morning and one for afternoon) per day randomly
    //Once the size of the teamMembersPool List reduced to 2, the List will be refilled and the left
    //2 TeamMembers will be added to the new list.


    private String name;
    private String endOfTrainingString;
    private List<TeamMember> teamMemberList;
    private List<Map<Date, String>> organisedDateList;
    private List<Map<Date, Map<TeamMember, TeamMember>>> organisedDateListForUpload;
    private List<Date> holidayList;

    public Organiser(String name, String endOfTraining) {
        this.name = name;
        this.teamMemberList = new LinkedList<>();
        this.holidayList = new ArrayList<>();
        this.organisedDateList = new ArrayList<>();
        this.organisedDateListForUpload = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public List<Date> getHolidayList() {
        return holidayList;
    }

    public String getEndOfTrainingString() {
        return endOfTrainingString;
    }

    public void setEndOfTrainingString(String endOfTrainingString) {
        this.endOfTrainingString = endOfTrainingString;
    }

    public List<Map<Date, String>> getOrganisedDateList() {
        return organisedDateList;
    }

    public void addToTeamMemberList(TeamMember tm) {

        if (tm != null) {
            teamMemberList.add(tm);
        }
    }

    //This method adds a date as a holiday to the Organiser's holidayList

    public void addToHolidayList(Date d) {

        holidayList.add(d);
    }

    //This boolean method helps to check if a String can be parsed to a Date or not.

    public static boolean stringParseToDateBoolean(String courseEndDate) {

        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd");

        try {
            Date endDate = sdf0.parse(courseEndDate);

        } catch (ParseException e) {

            return false;
        }

        return true;
    }

    //This method reads the end date of the given course and parses the read String to Date

    public static String stringParseToDate() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Provide the end date of the current course in yyyy.mm.dd format: ");
        String courseEndDate = sc.nextLine();
        while (!stringParseToDateBoolean(courseEndDate)) {

            System.out.println("False data format! Please provide the data again (yyyy.mm.dd): ");
            courseEndDate = sc.nextLine();
        }
        return courseEndDate;
    }

    //This boolean method helps to check if a String can be parsed to a int or not.

    public static boolean intParseChecker(String days) {

        try {
            Integer.parseInt(days);

        } catch (NumberFormatException ex) {

            return false;
        }
        return true;
    }

    //This boolean method helps to check if a given quantity of days is less than the number of
    //days till the end of the course or not.

    public boolean differenceChecker(String dayString) {

        LocalDate act = LocalDate.now();
        String eots = endOfTrainingString;

        String yString = String.valueOf(eots.charAt(0)).concat(String.valueOf(eots.charAt(1)).concat(String.valueOf(eots.charAt(2))).concat(String.valueOf(eots.charAt(3))));
        String mString = String.valueOf(eots.charAt(5)).concat(String.valueOf(eots.charAt(6)));
        String dString = String.valueOf(eots.charAt(8)).concat(String.valueOf(eots.charAt(9)));

        int yearint = Integer.parseInt(yString);
        int monthint = Integer.parseInt(mString);
        int dayint = Integer.parseInt(dString);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.LL.dd");
        LocalDate endDate = LocalDate.of(yearint, monthint, dayint);
        //System.out.println("enddate: "+endDate.format(formatter));
        long differenceBetweenDates = ChronoUnit.DAYS.between(act, endDate);
        //System.out.println("Difference in days: "+differenceBetweenDates);

        if (differenceBetweenDates + 1 < Long.parseLong(dayString)) {
            System.out.println(differenceBetweenDates + 1 + " days left till the end of the course...");
            return false;
        } else {
            return true;
        }
    }

    //This method reads the quantity of planned days from standard input as a String, and parse that
    //to an int. The method can handle the wrong data input (input that can't be parsed to int).

    public int intervallChecker() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Provide the length of the requested interval: ");
        String dayString = sc.nextLine();

        while (!intParseChecker(dayString)) {

            System.out.println("False data format! Please provide the data again (int): ");
            dayString = sc.nextLine();
        }
        while (!differenceChecker(dayString)) {

            System.out.println("Wrong data! The length of the interval must be shorter " +
                    "than the difference between today and the end date of the course counted in days");
            System.out.println("Please provide the data again (int): ");
            dayString = sc.nextLine();
        }

        return Integer.parseInt(dayString);
    }

    //This method is able to read .txt files and creates a String from the content.
    //THIS METHOD IS OUT OF DATE DUE TO DATABASE USAGE!!!

    public String readFromFile(String FilePath) {

        FileReader fr1 = null;
        String readData = "";

        try {

            fr1 = new FileReader(FilePath);

            while (fr1.ready()) {

                readData += (char) fr1.read();
            }

        } catch (FileNotFoundException e) {
            System.out.println("The following file can't be found: " + FilePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The following file can't be read: " + FilePath);
            e.printStackTrace();
        } finally {
            try {
                fr1.close();
            } catch (IOException e) {
                System.out.println("The FileReader can't be closed");
                e.printStackTrace();
            }
        }

        return readData;
    }

    //This method creates a connection with the DB and reads the id, firstName and lastName from
    //the Students table. Based on the read data crates TeamMember objects and using the
    //addToTeamMemberList() method adds them to the teamMemberList of the Organiser.

    public void createTeamMemberList() {

        List<TeamMember> DBTeamMemberList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DishwasherSchedule?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                            "useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "1234");

            DBModel dbm = new DBModel(conn);

            DBTeamMemberList = dbm.getTeamMembers();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        for (int i = 0; i < DBTeamMemberList.size(); i++) {
            addToTeamMemberList(DBTeamMemberList.get(i));
        }

    }

    //This method crates Dates from the content of the dishwasherHolidays.txt, and adds them
    //one by one to the holidayList of the Organiser using the addToHolidayList() method

    public void createHolidays() {

        List<Date> DBholidayList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DishwasherSchedule?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                            "useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "1234");

            DBModel dbm = new DBModel(conn);

            DBholidayList = dbm.getHolidays();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        for (int i = 0; i < DBholidayList.size(); i++) {
            addToHolidayList(DBholidayList.get(i));
        }
    }


    //This boolean method helps to check if a given Date is the element of the holidayList of
    //the Organiser or not

    public boolean dateChecker(Date d1) {

        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd");
        boolean contain = false;
        for (int i = 0; i < holidayList.size(); i++) {
            if (sdf0.format(holidayList.get(i)).equals(sdf0.format(d1))) {
                contain = true;
            }
        }
        return contain;
    }

    //This method returns with a List of the TeamMembers. The return value is constant, it contains
    //all the TeamMembers without any modification

    public List<TeamMember> getTeamMembersPool() {

        List<TeamMember> teamMembersPool = new LinkedList<>();

        for (int i = 0; i < teamMemberList.size(); i++) {
            teamMembersPool.add(teamMemberList.get(i));
        }

        return teamMembersPool;
    }

    //This method returns a TeamMember who is available on the given day (the given day is not the
    //element of his/her offDutyDayList) or returns null.
    //Way of working:
    //Using the Random class the method generates an integer (0 <= integer <=teamMembersPool.size())
    //called chosenForService. The method picks the chosenForService.th element of the actual
    //teamMembersPool. If this TeamMember is available on the given day (so the given day isn't
    //element of his/her offDutyDaysList) the method returns him/her (the chosen TeamMember) otherwise
    //the method returns null. If the method returns a TeamMember, the given TeamMember will be removed
    //from the teamMembersPool.
    //If the size of the teamMembersPool is 2 or less, this method calls the getTeamMembersPool() in
    //order to get a new teamMembersPool. In this case the rest (unchosen)

    public TeamMember getFreeTeamMember(List<TeamMember> teamMembersPool) {

        TeamMember teamMemberChosedForService = null;
        Random ran = new Random();

        if (teamMembersPool.size() == 0) {
            teamMembersPool = getTeamMembersPool();
        }

        int chosenForService = ran.nextInt(teamMembersPool.size());

        teamMemberChosedForService = teamMembersPool.get(chosenForService);
        teamMembersPool.remove(teamMembersPool.get(chosenForService));

        return teamMemberChosedForService;

    }

    //This method orders duty days and it's part of the days to the members of the team
    //holidays provided in holidaylist will be skipped.

    public void choseWhoIsInDuty(int days) {

        Date actDate = new Date();
        Calendar cal = Calendar.getInstance();

        List<TeamMember> teamMembersPool = getTeamMembersPool();

        for (int i = 1; i <= days; i++) {

            if (!dateChecker(actDate)) {

                Map<Date, String> tempMorningMap = new LinkedHashMap<>();
                Map<Date, String> tempAfternoonMap = new LinkedHashMap<>();

                tempMorningMap.put(actDate, "Morning");
                tempAfternoonMap.put(actDate, "Afternoon");

                if (teamMembersPool.size() == 0) {
                    teamMembersPool = getTeamMembersPool();
                }

                TeamMember teamMemberInMorningService = null;

                while (teamMemberInMorningService == null) {

                    teamMemberInMorningService = getFreeTeamMember(teamMembersPool);
                }

                if (teamMembersPool.size() == 0) {
                    teamMembersPool = getTeamMembersPool();
                }

                TeamMember teamMemberInAfternoonService = null;

                while (teamMemberInAfternoonService == null) {

                    teamMemberInAfternoonService = getFreeTeamMember(teamMembersPool);

                }

                teamMemberInMorningService.addTodutyDaysList(tempMorningMap);
                teamMemberInAfternoonService.addTodutyDaysList(tempAfternoonMap);
                //private List<Map<Date, Map<TeamMember, TeamMember>>> organisedDateListForUpload;
                Map<TeamMember, TeamMember> teamMemberMap = new LinkedHashMap<>();

                teamMemberMap.put(teamMemberInMorningService, teamMemberInAfternoonService);
                Map<Date, Map<TeamMember, TeamMember>> actDayTeamMemberMap = new LinkedHashMap<>();
                actDayTeamMemberMap.put(actDate, teamMemberMap);
                organisedDateListForUpload.add(actDayTeamMemberMap);
                String organisedDateListString = teamMemberInMorningService + ";" + teamMemberInAfternoonService;

                Map<Date, String> tempOrganisedDateListMap = new LinkedHashMap<>();

                tempOrganisedDateListMap.put(actDate, organisedDateListString);

                organisedDateList.add(tempOrganisedDateListMap);

                cal.setTime(actDate);
                cal.add(Calendar.DAY_OF_WEEK, 1);
                actDate = cal.getTime();

            } else {

                cal.setTime(actDate);
                cal.add(Calendar.DAY_OF_WEEK, 1);
                actDate = cal.getTime();
            }
        }
    }

    //this method creates the output as a .csv file. Each line of the .csv file starts with the
    //name of the person in duty and continues with the duty days (date) and the part of the days
    //(Morning / Afternoon). The records are separated with ";" mark.
    //EXAMPLE:
    //Kliszki Balint;2018.05.09 - Morning;2018.05.25 - Afternoon;2018.06.05 - Morning;
    //THIS METHOD IS OUT OF DATE DUE TO DATABASE USAGE!!!

    public void csvCreator(String csvNameDestinationString) {
        File file = new File(csvNameDestinationString);
        Writer pw = null;
        String result = "";
        for (int i = 0; i < teamMemberList.size(); i++) {
            result += teamMemberList.get(i).getFirstName() + "; " + teamMemberList.get(i).createString() + "\n";
        }
        try {

            pw = new FileWriter(file);

            pw.write(result);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found ");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("file can't be read");
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //This method creates a String containing the date of a given day and the part of it
    //(morning/afternoon) and concatenates to this the name of the TeamMember who is in charge
    //on the given in the given part of the day. Each records will be created in a new line of the
    //String. For this reason the data of a given day will be saved in 2 different lines. EG.:
    //2018.07.20 morning;Kiss Mate
    //2018.07.20 afternoon;Mandi Bence
    //THIS METHOD IS OUT OF DATE DUE TO DATABASE USAGE!!!

    public String csvDateStringCreator() {
        String ret = "";

        for (int i = 0; i < organisedDateList.size(); i++) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
            Map<Date, String> tempmap = organisedDateList.get(i);

            Set tempset = tempmap.keySet();
            Iterator tempsetIterator = tempset.iterator();
            String actDay = (sdf1.format(tempsetIterator.next()));
            String actDayMorning = actDay + " morning";
            String actDayAfternoon = actDay + " afternoon";

            Collection tempMapValues = tempmap.values();
            Iterator tempMapValuesIterator = tempMapValues.iterator();
            String teamMembersInCharge = (String) tempMapValuesIterator.next();
            String[] teamMembersInChargeArr = teamMembersInCharge.split(";");
            ret = ret + actDayMorning + ";" + teamMembersInChargeArr[0] + "\n";
            ret = ret + actDayAfternoon + ";" + teamMembersInChargeArr[1] + "\n";
        }

        return ret;
    }

    //this method creates the output as a .csv file. Each line of the .csv file starts with a
    //date and the part of it (morning / afternoon) then continues with the name of the TeamMember
    //who is in charge on the given day in the given part of the day (morning / afternoon)
    //The two parts of the records are separated with ";" mark.
    //EXAMPLE:
    //2018.07.20 morning;Kiss Mate
    //2018.07.20 afternoon;Mandi Bence
    //THIS METHOD IS OUT OF DATE DUE TO DATABASE USAGE!!!

    public void csvDateCreator(String csvDateDestinationString) {
        File file = new File(csvDateDestinationString);
        Writer pw = null;
        String result = csvDateStringCreator();

        try {

            pw = new FileWriter(file);

            pw.write(result);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found ");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("file can't be read");
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //This method uses the UpdateDishwasherSchedule() method to upload the content of the
    //List<Map<Date, Map<TeamMember, TeamMember>>>organisedDateListForUpload to the DB.
    public void uploadDataToDishwasherScheduleTable() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DishwasherSchedule?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                            "useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "1234");

            DBModel dbm = new DBModel(conn);

            for (int i = 0; i < organisedDateListForUpload.size(); i++) {

                Map<Date, Map<TeamMember, TeamMember>> tempmap = organisedDateListForUpload.get(i);

                Set tempset = tempmap.keySet();
                Iterator tempsetIterator = tempset.iterator();
                Date actDay = (Date) tempsetIterator.next();

                Collection tempMapValues = tempmap.values();
                Iterator tempMapValuesIterator = tempMapValues.iterator();
                Map<TeamMember, TeamMember> tempTeamMemberMap = (Map<TeamMember, TeamMember>) tempMapValuesIterator.next();
                Set tempEmptierSet = tempTeamMemberMap.keySet();
                Collection tempFillerCollection = tempTeamMemberMap.values();

                Iterator tempEmptierSetIterator = tempEmptierSet.iterator();
                TeamMember emptier = (TeamMember) tempEmptierSetIterator.next();

                Iterator tempFillerCollectionIterator = tempFillerCollection.iterator();
                TeamMember filler = (TeamMember) tempFillerCollectionIterator.next();
                dbm.UpdateDishwasherSchedule(actDay, emptier, filler);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}