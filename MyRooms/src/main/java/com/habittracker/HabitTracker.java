package com.habittracker;
import com.example.myrooms.Frequence;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class HabitTracker {
    private int id;
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<HabitType> habitTypes = new ArrayList<>();
    private ArrayList<HabitProgressType> habitProgressTypes = new ArrayList<>();
    private ArrayList<Frequence> frequenceTypes = new ArrayList<>();


    private final String DATABASE = "jdbc:sqlite:MyRooms/src/main/resources/TemporaryUserDB/habit_tracker.sqlite";

    private final Gson gson = new Gson();
    private  Connection connection;

    //change methods will be in this
    public HabitTracker(int id)
    {
        connection = connect();
        this.id = id;
        //the order matters because setting habit type data uses the arraylist of frequences and habit progresses
        setFrequencies();
        setHabitProgressTypes();
        setHabitTypes();
        setHabits();
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

    public void addFrequency(String name,int[]weeklyOcc,int[]monthlyOcc)
    {
        int frequenceNo;
        String weeklyOccurenceJSON,
                monthlyOccurenceJSON;


        String query = "INSERT INTO FrequencesDB(userId, frequenceNo, name,weeklyOccurrence,monthlyOccurrence) VALUES (?,?,?,?,?)";


        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {
            frequenceNo = getMaxFrequenceNo() + 1;
            weeklyOccurenceJSON = gson.toJson(weeklyOcc);
            monthlyOccurenceJSON = gson.toJson(monthlyOcc);
            // Set the values for the query parameters
            pstmt.setInt(1, this.id);
            pstmt.setInt(2, frequenceNo);
            pstmt.setString(3, name);
            pstmt.setString(4,weeklyOccurenceJSON);
            pstmt.setString(5,monthlyOccurenceJSON);

            pstmt.executeUpdate();
            Frequence frequence = new Frequence(this.id,frequenceNo,name,weeklyOcc,monthlyOcc);
            frequenceTypes.add(frequence);
        }
        catch (Exception e)
        {
            System.out.println("Error adding frequency: " + e.getMessage());
        }
    }

    public void addHabitProgress(String name,int progress,int progressCoefficient)
    {
        int progressTypeNo;

        String query = "INSERT INTO main.HabitProgressTypeDB(userId, progressTypeNo,name,progress,progressCoef) VALUES (?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {
            progressTypeNo = getMaxHabitProgressNo() + 1;
            // Set the values for the query parameters
            pstmt.setInt(1, this.id);
            pstmt.setInt(2, progressTypeNo);
            pstmt.setString(3, name);
            pstmt.setInt(4,progress);
            pstmt.setInt(5,progressCoefficient);

            pstmt.executeUpdate();
            HabitProgressType progressType = new HabitProgressType(name,progressTypeNo,progress,progressCoefficient);
            habitProgressTypes.add(progressType);
        }
        catch (Exception e)
        {
            System.out.println("Error adding habit progress: " + e.getMessage());
        }
    }

    public void addHabitType(String name, String description, String progressTypeFID, String frequenceFID)
    {
        int habitTypeNo,
            frequenceNo,
            progressTypeNo;

        String query = "INSERT INTO main.HabitTypeDB(userId, habitTypeNo, name, description, frequenceNo, progressTypeNo) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {
            habitTypeNo = getMaxHabitTypeNo() + 1;


            progressTypeFID =  progressTypeFID.substring(0,1);
            frequenceFID = frequenceFID.substring(0,1);

            frequenceNo = Integer.parseInt(frequenceFID);
            progressTypeNo = Integer.parseInt(progressTypeFID);


            // Set the values for the query parameters
            pstmt.setInt(1, this.id);
            pstmt.setInt(2, habitTypeNo);
            pstmt.setString(3, name);
            pstmt.setString(4, description);
            pstmt.setInt(5,frequenceNo);
            pstmt.setInt(6,progressTypeNo);
            pstmt.executeUpdate();

            Frequence frequence = getSpecificFrequence(frequenceNo);
            HabitProgressType progressType = getSpecificHabitProgressType(progressTypeNo);

            HabitType habitType = new HabitType(id,habitTypeNo,name,description,frequence,progressType);
            habitTypes.add(habitType);
        }
        catch (Exception e)
        {
            System.out.println("Error adding habit type: " + e.getMessage());
        }

    }


    public void addHabit(String name, String description, HabitType type, HabitProgressType progressType, Frequence frequence)
    {
        int habitTypeNo,
                frequenceNo,
                progressTypeNo;
        int habitNo;
        String todayS;

        String query = "INSERT INTO main.HabitDB(userId, HabitNo, name, description, habitTypeNo, frequencyNo, progressTypeNo, activeProgress, startDate,activeDates,doneDates) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {

            habitNo = getMaxHabitNo() + 1;


            habitTypeNo = type.getHabitTypeNo();
            frequenceNo = frequence.getFrequenceNo();
            progressTypeNo = progressType.getProgressNo();
            LocalDate today = LocalDate.now();
            todayS = today.toString();

            // Set the values for the query parameters
            pstmt.setInt(1, this.id);
            pstmt.setInt(2, habitNo);
            pstmt.setString(3, name);
            pstmt.setString(4, description);
            pstmt.setInt(5,habitTypeNo);
            pstmt.setInt(6,frequenceNo);
            pstmt.setInt(7,progressTypeNo);
            pstmt.setInt(8,0);
            pstmt.setString(9,todayS);
            pstmt.setString(10,"");
            pstmt.setString(11,"");
            pstmt.executeUpdate();
            //active dates AND DONE DATES are set up in the constructor

            Habit habit = new Habit(id,habitTypeNo,name,description,type,progressType,frequence,today,0,connection);
            habits.add(habit);
        }
        catch (Exception e)
        {
            System.out.println("Error adding habit type: " + e.getMessage());
        }

    }

    public void setFrequencies()
    {
        String sql = "SELECT userid, frequenceNo, name, weeklyOccurrence,monthlyOccurrence FROM FrequencesDB WHERE userId = ?";
        String name,
              weeklyOccurrencesJSON,
              monthlyOccurrencesJSON;
        int frequenceNo;

        int[] weeklyOccurrences,
              monthlyOccurrences;

        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {

            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                frequenceNo = rs.getInt(2);
                name = rs.getString("name");
                weeklyOccurrencesJSON = rs.getString("weeklyOccurrence");
                monthlyOccurrencesJSON = rs.getString("monthlyOccurrence");

                weeklyOccurrences = gson.fromJson(weeklyOccurrencesJSON, int[].class);
                monthlyOccurrences = gson.fromJson(monthlyOccurrencesJSON, int[].class);

                Frequence frequence = new Frequence(id,frequenceNo,name,weeklyOccurrences,monthlyOccurrences);
                frequenceTypes.add(frequence);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void setHabitProgressTypes()
    {
        String sql = "SELECT userid, progressTypeNo, name, progress, progressCoef FROM HabitProgressTypeDB WHERE userId = ?";
        String name;
        int progressTypeNo,
            progress,
            progressCoef;
        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {

            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                progressTypeNo = rs.getInt(2);
                name = rs.getString("name");
                progress = rs.getInt("progress");
                progressCoef = rs.getInt("progressCoef");


                HabitProgressType habitProgressType = new HabitProgressType(name,progressTypeNo,progress,progressCoef);
                habitProgressTypes.add(habitProgressType);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void setHabitTypes()
    {
        String sql = "SELECT userId, habitTypeNo, name, description, frequenceNo, progressTypeNo  FROM HabitTypeDB WHERE userId = ?";
        String name,
                description;

        int habitTypeNo,
                frequenceNo,
            progressTypeNo;

        Frequence frequence;
        HabitProgressType progressType;

        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {

            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                habitTypeNo = rs.getInt(2);
                name = rs.getString("name");
                description = rs.getString("description");
                frequenceNo = rs.getInt("frequenceNo");
                progressTypeNo = rs.getInt("progressTypeNo");

                frequence = getSpecificFrequence(frequenceNo);
                progressType = getSpecificHabitProgressType(progressTypeNo);

                HabitType habitType = new HabitType(id,habitTypeNo,name,description,frequence,progressType);
                habitTypes.add(habitType);

            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void setHabits()
    {
        String sql = "SELECT userid, habitNo, name, description, habittypeno, frequencyno, progresstypeno, activeprogress, startdate FROM HabitDB WHERE userId = ?";
        String name,
                description,
                startDateS;

        int     habitNo,
                habitTypeNo,
                frequenceNo,
                progressTypeNo,
                activeProgress;
        Frequence frequence;
        HabitProgressType progressType;
        HabitType habitType;
        LocalDate startDate;


        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {

            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                habitNo = rs.getInt(2);
                name = rs.getString("name");
                description = rs.getString("description");
                habitTypeNo = rs.getInt("habitTypeNo");
                frequenceNo = rs.getInt("frequencyNo");
                progressTypeNo = rs.getInt("progressTypeNo");
                activeProgress = rs.getInt("activeProgress");
                startDateS = rs.getString("startDate");
                startDate = LocalDate.parse(startDateS);

                frequence = getSpecificFrequence(frequenceNo);
                progressType = getSpecificHabitProgressType(progressTypeNo);
                habitType = getSpecificHabitType(habitTypeNo);

                Habit habit = new Habit(id,habitNo,name,description,habitType,progressType,frequence,startDate,activeProgress,connection);
                habits.add(habit);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public int getMaxHabitNo()
    {
        int max,
                current;
        if(habits.isEmpty())
        {
            return 0;
        }
        max = habits.get(0).getHabitNo();
        for(Habit habit: habits)
        {
            current = habit.getHabitNo();
            if(current>max)
            {
                max = current;
            }
        }
        return max;
    }

    public int getMaxFrequenceNo()
    {
        int max,
                current;
        if(frequenceTypes.isEmpty())
        {
            return 0;
        }
        max = frequenceTypes.get(0).getFrequenceNo();
        for(Frequence frequence: frequenceTypes)
        {
            current = frequence.getFrequenceNo();
            if(current>max)
            {
                max = current;
            }
        }
        return max;
    }

    public int getMaxHabitTypeNo()
    {
        int max,
                current;
        if(habitTypes.isEmpty())
        {
            return 0;
        }
        max = habitTypes.get(0).getHabitTypeNo();
        for(HabitType habitType: habitTypes)
        {
            current = habitType.getHabitTypeNo();
            if(current>max)
            {
                max = current;
            }
        }
        return max;
    }

    public int getMaxHabitProgressNo()
    {
        int max,
                current;
        if(habitProgressTypes.isEmpty())
        {
            return 0;
        }
        max = habitProgressTypes.get(0).getProgressNo();
        for(HabitProgressType habitProgress: habitProgressTypes)
        {
            current = habitProgress.getProgressNo();
            if(current>max)
            {
                max = current;
            }
        }
        return max;
    }

    public void changeHabitProgressType(String name,int progress,int progressCoefficient, HabitProgressType type)
    {
        int no;
        no = type.getProgressNo();

        String updateSQL = "UPDATE HabitProgressTypeDB SET name = ?, progress = ?, progressCoef = ? WHERE userId = ? and progressTypeNo = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(4, id);
            pstmt.setInt(5, no);

            pstmt.setString(1, name);
            pstmt.setInt(2, progress);
            pstmt.setInt(3, progressCoefficient);

            type.changeProgressType(name,progress,progressCoefficient);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing habit progress type");;
        }
    }

    public void changeFrequenceType(String name, int[] weeklyOcc, int[] monthlyOcc, Frequence frequence)
    {
        int no;
        String weeklyOccS,
                monthlyOccS;


        String updateSQL = "UPDATE FrequencesDB SET name = ?, weeklyOccurrence = ?, monthlyOccurrence = ? WHERE userId = ? and frequenceNo = ? ";
        no = frequence.getFrequenceNo();

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(4, id);
            pstmt.setInt(5, no);

            weeklyOccS = gson.toJson(weeklyOcc);
            monthlyOccS = gson.toJson(monthlyOcc);

            pstmt.setString(1, name);
            pstmt.setString(2, weeklyOccS);
            pstmt.setString(3, monthlyOccS);

            frequence.changeFrequency(name,weeklyOcc,monthlyOcc);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing frequency");
        }
    }

    public void changeHabitType(String name,String description,int frequenceNo, int progressTypeNo, HabitType habitType)
    {
        int no;
        String updateSQL = "UPDATE HabitTypeDB SET name = ?, description = ?, frequenceNo = ?,progressTypeNo = ?  WHERE userId = ? and habitTypeNo = ? ";
        no = habitType.getHabitTypeNo();

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(5, id);
            pstmt.setInt(6, no);

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, frequenceNo);
            pstmt.setInt(4, progressTypeNo);

            Frequence frequence = getSpecificFrequence(frequenceNo);
            HabitProgressType progressType = getSpecificHabitProgressType(progressTypeNo);

            habitType.changeHabitType(name,description,frequence,progressType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing habit type");
        }
    }


    public void changeHabit(String name,String description,Habit habit)
    {
        int no;
        String updateSQL = "UPDATE HabitDB SET name = ?, description = ? WHERE userId = ? and habitNo = ? ";
        no = habit.getHabitNo();

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(3, id);
            pstmt.setInt(4, no);

            pstmt.setString(1, name);
            pstmt.setString(2, description);

            habit.changeHabit(name, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing habit type");
        }
    }

    public void deleteHabitProgressType(HabitProgressType habitProgressType)
    {
        String deleteSQL = "DELETE FROM HabitProgressTypeDB WHERE userId = ? AND progressTypeNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2,habitProgressType.getProgressNo() );;
            pstmt.executeUpdate();
            habitProgressTypes.remove(habitProgressType);

        } catch (SQLException e) {
            System.out.println("Problem in deleting habit progress type");
        }
    }

    public void deleteFrequenceType(Frequence frequence)
    {
        String deleteSQL = "DELETE FROM FrequencesDB WHERE userId = ? and frequenceNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)){
            pstmt.setInt(1, id);
            pstmt.setInt(2, frequence.getFrequenceNo());
            pstmt.executeUpdate();
            frequenceTypes.remove(frequence);

        } catch (SQLException e) {
            System.out.println("Problem in deleting frequence type");
        }
    }

    public void deleteHabitType(HabitType habitType)
    {
        String deleteSQL = "DELETE FROM HabitTypeDB WHERE userId = ? and habitTypeNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)){
            pstmt.setInt(1, id);
            pstmt.setInt(2, habitType.getHabitTypeNo());
            pstmt.executeUpdate();
            habitTypes.remove(habitType);

        } catch (SQLException e) {
            System.out.println("Problem in deleting habit type");
        }
    }

    public void deleteHabit(Habit habit)
    {
        String deleteSQL = "DELETE FROM HabitDB WHERE userId = ? and HabitNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)){
            pstmt.setInt(1, id);
            pstmt.setInt(2, habit.getHabitNo());
            pstmt.executeUpdate();
            habits.remove(habit);


        } catch (SQLException e) {
            System.out.println("Problem in deleting habits");
        }
    }

    public HabitType getSpecificHabitType(int id)
    {
        HabitType habitType;
        for(HabitType type : habitTypes)
        {
            if(type.getHabitTypeNo() == id)
            {
                habitType = type;
                return habitType;
            }
        }
        return null;
    }
    public HabitProgressType getSpecificHabitProgressType(int id)
    {
        HabitProgressType habitProgressType;
        for(HabitProgressType type : habitProgressTypes)
        {
            if(type.getProgressNo() == id)
            {
                habitProgressType = type;
                return habitProgressType;
            }
        }
        return null;
    }
    public Frequence getSpecificFrequence(int id)
    {
        Frequence frequence;
        for(Frequence type : frequenceTypes)
        {
            if(type.getFrequenceNo() == id)
            {
                frequence = type;
                return frequence;
            }
        }
        return null;
    }

    public ArrayList<Habit> getHabits()
    {
        return habits;
    }
    public ArrayList<HabitType> getHabitTypes()
    {
        return habitTypes;
    }
    public ArrayList<HabitProgressType> getHabitProgressTypes()
    {
        return habitProgressTypes;
    }
    public ArrayList<Frequence> getFrequenceTypes()
    {
        return frequenceTypes;
    }




}
