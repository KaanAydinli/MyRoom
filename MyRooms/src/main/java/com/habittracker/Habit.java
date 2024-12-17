package com.habittracker;

import com.example.myrooms.Frequence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.stream.Collectors;



public class Habit {
    private  String name;
    private String description;
    private boolean isActive;
    private final int id;
    private final int habitNo;
    private int currentProgress,
                wantedProgress,
                progressCoefficient;

    LocalDate startDate;
    LocalDate currentDate;

    private final String DATABASE = "jdbc:sqlite:MyRooms/src/main/resources/TemporaryUserDB/habit_tracker.sqlite";

    private ArrayList<LocalDate> activeDates = new ArrayList<>();
    //1 done, 0 not done 2 date not yet come
    private ArrayList<Integer> doneDates;

    private final HabitType habitType;
    private final HabitProgressType habitProgressType;
    private final Frequence frequence;

    private static final Object DB_LOCK = new Object();

    Gson gson = new Gson();
    Connection connection;

    public Habit(int id, int habitNo, String name, String description, HabitType habitType, HabitProgressType habitProgressType, Frequence frequence, LocalDate startDate, int currentProgress,Connection connection)
    {
        this.connection = connection;
        this.id = id;
        this.habitNo = habitNo;
        this.name = name;
        this.description = description;
        this.habitType = habitType;
        this.habitProgressType = habitProgressType;
        this.frequence = frequence;
        this.startDate = startDate;
        this.currentDate = LocalDate.now();
        this.currentProgress = currentProgress;

        this.setUpActiveAndDoneDatesData();
        if(createActiveDatesAndDoneDatesFYear())
        {
            System.out.println("Habit created");
            setUpActiveAndDoneDatesData();
        }





        setActivity();
        setUpPreviousDoneDates();
        this.wantedProgress = habitProgressType.getProgress();
        this.progressCoefficient = habitProgressType.getProgressCoefficient();


    }



    public Connection connect()
    {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }

    private void setUpActiveAndDoneDatesData()
    {
        String sql = "SELECT activeDates, doneDates FROM HabitDB WHERE userId = ? AND habitNo = ?";
        String activeDatesJSON,
                doneDatesJSON;
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        Type listIntegerType = new TypeToken<ArrayList<Integer>>() {}.getType();

        ArrayList<String> activeDatesS;

        synchronized (DB_LOCK) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1,this.id);
                pstmt.setInt(2, this.habitNo);
                ResultSet rs = pstmt.executeQuery();

                // Process the result
                while (rs.next()) {
                    activeDatesJSON = rs.getString("activeDates");
                    doneDatesJSON = rs.getString("doneDates");

                    activeDatesS = gson.fromJson(activeDatesJSON, listType);
                    doneDates = gson.fromJson(doneDatesJSON, listIntegerType);
                    activeDates = stringToDate(activeDatesS);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ArrayList<LocalDate> stringToDate(ArrayList<String> datesString)
    {
        int size;
        LocalDate date;
        ArrayList<LocalDate> dates = new ArrayList<>();
        if(datesString == null)
        {
            return null;
        }
        size = datesString.size();
        for(int i = 0; i < size; i++)
        {
            date = LocalDate.parse(datesString.get(i));
            dates.add(date);
        }

        return dates;
    }

    public int getDatesDoneValue(LocalDate date)
    {
        int index,
            value;
        index = activeDates.indexOf(date);
        if(index == -1)
            return -1;
        else
            value = doneDates.get(index);

        return value;
    }

    private boolean createActiveDatesAndDoneDatesFYear()
    {

        String updateSQL = "UPDATE habitDb SET activeDates = ?, doneDates = ? WHERE userId = ? and habitNo = ? ";
        String yearActiveDates,
                yearDoneDates;
        if(checkYearActive())
        {
            return false;
        }
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {

            pstmt.setInt(3, id);
            pstmt.setInt(4, habitNo);


            yearActiveDates = createActiveDatesStringFYear();
            yearDoneDates = createDoneDatesStringFYear();

            pstmt.setString(1, yearActiveDates);
            pstmt.setString(2, yearDoneDates);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String createActiveDatesStringFYear()
    {
        int size,
             currentYear;
        String activeDatesJSON;
        currentYear = currentDate.getYear();
        ArrayList<String> datesString = new ArrayList<>();
       ArrayList<LocalDate> dates =  getDatesForMonthsAndDays(currentYear, frequence.getMonthlyOccurrence()
               , frequence.getWeeklyOccurrence());


       size = dates.size();
       System.out.println(size);
       for(int i = 0; i < size; i++)
       {
           datesString.add(dates.get(i).toString());
       }
       activeDatesJSON = gson.toJson(datesString);

       return activeDatesJSON;
    }

    private String createDoneDatesStringFYear()
    {
        int size,
                currentYear;
        String doneDatesJSON;
        currentYear = currentDate.getYear();
        ArrayList<Integer> datesInt = new ArrayList<>();
        ArrayList<LocalDate> dates =  getDatesForMonthsAndDays(currentYear, frequence.getMonthlyOccurrence()
                , frequence.getWeeklyOccurrence());

        size = dates.size();
        for(int i = 0; i < size; i++)
        {
            datesInt.add(3);
        }
        doneDatesJSON = gson.toJson(datesInt);

        return doneDatesJSON;
    }

    public boolean checkYearActive()
    {
        int size,
            currentYear;
        boolean isIn;

        isIn = false;

        currentYear = currentDate.getYear();
        if(activeDates == null)
            return false;
        size = activeDates.size();
        for(int i = 0; i < size; i++)
        {
            if (activeDates.get(i).getYear() == currentYear)
            {
                isIn = true;
                break;
            }
        }

        return isIn;

    }



    private void setActivity()
    {
        int index;
        if(activeDates == null)
        {
            return;
        }
        index = activeDates.indexOf(currentDate);
        isActive = index != -1;
    }

    private void setUpPreviousDoneDates()
    {

        int size,
            val;
        LocalDate cDate;
        if(activeDates == null)
        {
            return;
        }


        size = activeDates.size();
        for(int i = 0; i < size; i++)
        {
            cDate = activeDates.get(i);
            if(currentDate.isAfter(cDate) || currentDate.isEqual(cDate))
            {


                val = doneDates.get(i);
                if(val == 3)
                {
                    if(currentDate.isEqual(cDate))
                    {
                        updateProgressDB(0);
                    }
                    doneDates.set(i,0);
                }
            }
            else
            {

                break;
            }
        }
        updateDoneDatesDB();
    }

    //1-->done 0-->not done 2 --> date has not yet reached
    public void setSpecificDoneDate(LocalDate d,int val)
    {
        int index;
        index = activeDates.indexOf(d);
        if(index == -1)
        {
            return;
        }
        doneDates.set(index,val);
        // change a specific dates case
        // use this first then setUpDoneDate so that it would be written to file;
        updateDoneDatesDB();
    }


    private void updateDoneDatesDB()
    {
        String doneDatesJSON;
        String updateSQL = "UPDATE habitDB SET doneDates = ? WHERE userId = ? AND habitNo = ?";

        synchronized (DB_LOCK) {

            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {

                pstmt.setInt(2, id);
                pstmt.setInt(3, habitNo);

                doneDatesJSON = gson.toJson(doneDates);
                pstmt.setString(1, doneDatesJSON);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    // will also be used to reset the active progress
    private void updateProgressDB(int progress)
    {

        String updateSQL = "UPDATE habitDB SET activeProgress = ? WHERE userId = ? AND habitNo = ?";

        synchronized (DB_LOCK) {

            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {

                pstmt.setInt(2, id);
                pstmt.setInt(3, habitNo);


                pstmt.setInt(1,progress);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void makeProgress()
    {
        currentProgress += progressCoefficient;
        updateProgressDB(currentProgress);
        if(currentProgress >= wantedProgress)
        {
            setSpecificDoneDate(currentDate,1);
            updateDoneDatesDB();
        }
    }


    public void undoDate(LocalDate date)
    {
        setSpecificDoneDate(date,0);
        updateDoneDatesDB();
        if(date.isEqual(currentDate))
        {
            currentProgress = 0;
            updateProgressDB(currentProgress);
        }

    }

    public void doDate(LocalDate date)
    {
        setSpecificDoneDate(date,1);
        updateDoneDatesDB();
        if(date.isEqual(currentDate))
        {
            currentProgress = wantedProgress;
            updateProgressDB(currentProgress);
        }

    }

    private boolean isHabitDone()
    {
        // checks whether the habit is done or not
        return false;
    }


    public String[] createActiveDatesAndDoneDatesStringFYear() {
        String[] output = new String[2];
        String activeDates;
        StringBuilder doneDates;
        int size;
        doneDates = new StringBuilder();

        ArrayList<LocalDate> dates = new ArrayList<>();

        int year = currentDate.getYear();


        // Suppose you want dates in September and October
        int[] targetMonths = frequence.getMonthlyOccurrence();
        int[] targetDays = frequence.getWeeklyOccurrence();

        dates = getDatesForMonthsAndDays(year, targetMonths, targetDays);

        activeDates = dates.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining("/"));

        size = dates.size();
        doneDates.append("3/".repeat(size));

        output[0] = activeDates;
        output[1] = doneDates.toString();

        return output;
    }

    public  ArrayList<LocalDate> getDatesForMonthsAndDays(int year, int[] monthActive, int[] weekActive)
    {
        ArrayList<LocalDate> result = new ArrayList<>();
        for (int m = 0; m < 12; m++)
        {
            if (monthActive[m] == 1) {
                Month month = Month.of(m + 1);
                LocalDate start = LocalDate.of(year, month, 1);
                LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

                for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                    DayOfWeek dow = date.getDayOfWeek();

                    int dayIndex = dow.getValue() - 1;

                    if (weekActive[dayIndex] == 1) {
                        if (currentDate.isBefore(date)||currentDate.equals(date))
                        {
                            result.add(date);
                        }
                    }
                }
            }
        }
        return result;
    }

    public void changeHabit(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }

    public int getHabitNo()
    {
        return habitNo;
    }

    public int getCurrentProgress()
    {
        return currentProgress;
    }

    public int getWantedProgress()
    {
        return wantedProgress;
    }

    public boolean getIsActive()
    {
        return isActive;
    }

    public HabitType getHabitType()
    {
        return habitType;
    }

    public Frequence getFrequence()
    {
        return frequence;
    }

    public HabitProgressType getHabitProgressType()
    {
        return habitProgressType;
    }

    public ArrayList<LocalDate> getActiveDates()
    {
        return activeDates;
    }

    public ArrayList<Integer> getDoneDates()
    {
        return doneDates;
    }

}
