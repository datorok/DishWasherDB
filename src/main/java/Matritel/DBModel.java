package Matritel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//This class helps to implement the DAO (Data Access Object) design pattern

public class DBModel {

    private static final Logger logger = LoggerFactory.getLogger(DBModel.class);

    private Connection conn;
    private PreparedStatement pstmtGetTeamMember;
    private PreparedStatement pstmtGetHoliday;
    private PreparedStatement pstmtUpdateDishwasherScheduler;

    public DBModel(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstmtGetTeamMember = conn.prepareStatement("SELECT * FROM Students");
        this.pstmtGetHoliday = conn.prepareStatement("SELECT * FROM Holidays");
        this.pstmtUpdateDishwasherScheduler = conn.prepareStatement("INSERT INTO DishwasherScheduler (chargedate, emptier, filler) VALUES (?,?,?)");
    }

    //This method reads the id, firstName and lastName from the Students table. Based on the read
    //data crates TeamMember objects and puts them into a List<TeamMember> as a return. This method
    //is called in the Organiser class and the createTeamMemberList() method fills up the teamMemberList
    //of the Organiser from its return

    public List<TeamMember> getTeamMembers() {
        List<TeamMember> teamMemberList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = pstmtGetTeamMember.executeQuery();


            while (rs.next()) {

                int id = rs.getInt("id");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");

                TeamMember tempTeamMember = new TeamMember(id, firstName, lastName);
                teamMemberList.add(tempTeamMember);
                logger.info("A new TeamMember object has been created: "+tempTeamMember.toString());
            }
        } catch (SQLException e) {
            logger.error("The content of Student table can't be read.");
            e.printStackTrace();
        }
        return teamMemberList;
    }

    //This method reads holidays from the Holidays table. Based on the read data crates Date objects
    //and puts them into a List<Date> as a return. This method is called in the Organiser class
    //and the createHolidays() method fills up the holidayList of the Organiser from its return
    public List<Date> getHolidays() {
        List<Date> holidayList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = pstmtGetHoliday.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String dateString = rs.getString("holiday");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d = sdf.parse(dateString);
                    holidayList.add(d);
                    logger.info("A new Date object has been created as a holiday: "+sdf.format(d));
                } catch (ParseException e) {
                    logger.error("The content of " + id + ". line in Holidays table can't be parsed to Date.");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            logger.error("The content of Holidays table can't be read.");
            e.printStackTrace();
        }
        return holidayList;
    }

    //This method updates the DishwasherScheduler table based on the provided parameters
    //This method is called in the Organiser class by the uploadDataToDishwasherScheduleTable()
    //method that iterates the content of the organisedDateListForUpload List.
    public void UpdateDishwasherSchedule(Date date, TeamMember teamMemberemptier, TeamMember teamMemberfiller){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     String dateString = sdf.format(date);

        try {

            pstmtUpdateDishwasherScheduler.setString(1, dateString);
            pstmtUpdateDishwasherScheduler.setInt(2, teamMemberemptier.getId());
            pstmtUpdateDishwasherScheduler.setInt(3, teamMemberfiller.getId());

            int i = pstmtUpdateDishwasherScheduler.executeUpdate();

            logger.info("A new DishwasherScheduler record has been uploaded to the DB: "+dateString+", "+teamMemberemptier.getId()+", "+teamMemberfiller.getId());

        } catch (SQLException e) {
            logger.error("The DishwasherScheduler table is not available for writing.");
            e.printStackTrace();
        }
    }
}
