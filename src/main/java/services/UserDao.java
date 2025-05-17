package services;

import models.User;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class  UserDao {
    public static final String filePath = "C:\\Users\\pasin\\Desktop\\movie_rental_and_review_platform\\data\\user.txt";

    //create user id
    public static String createUserId(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyyddHHmmss");
        return "UID-"+sdf.format(new Date());
    }

    //password validation
    public static String passwordValidation(String password){
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        if(password.length() < 6){
            return "length";
        }else if(!password.matches(regex)){
            return "regex";
        }
        return "valid";
    }

    //grab all user details
    public static void getRegisterDetails(String name, String username, String email, String password) {
        String role = "user";
        String userId = createUserId();
        User user = new User(userId, name, username, email, password, role);
        registerUser(user);
    }
    //register a user
    public static void registerUser(User user) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath,true))) {
            bufferedWriter.write(user.getUserId()+","+user.getName()+","+user.getUsername()+","+user.getEmail()+","+user.getPassword()+","+user.getRole());
            bufferedWriter.newLine();
        }catch (IOException e){
            e.getMessage();
        }
    }
    //check if there are exists same users
    public static boolean isUserExist(String email){
        try(FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if(user.length == 6 && user[3].equals(email)){
                    return true;
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
        return false;
    }
    //login
    public static User loginUser(String username, String password){
        try(FileReader fileReader = new FileReader(filePath)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if(user.length == 6 && user[2].equals(username) && user[4].equals(password)){
                    return new User(user[0], user[1], username, user[3], password, user[5]);
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
        return null;
    }

    //delete user account (user side)
    public static boolean deleteUser(String email){

        List<String> users = new ArrayList<>();
        boolean isDeleted = false;

        try(FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if(user[3].equals(email)){
                    isDeleted = true;
                }else{
                    users.add(line);
                }
            }
        }catch (IOException e){
            e.getMessage();
            return false;
        }
        if(isDeleted){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String user : users) {
                    writer.write(user);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return false;
    }

    //update user details (user side)
    public static boolean updateUser(String oldEmail, String newName, String newUserName, String newPassword) {

        List<String> users = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[3].equals(oldEmail)) {
                    users.add(user[0] + "," + newName + "," + newUserName +","+oldEmail+","+newPassword + ","+user[5]);
                    updated = true;
                } else {
                    users.add(line);
                }
            }
        } catch (IOException e) {
            e.getMessage();
            return false;
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
                for (String user : users) {
                    writer.write(user);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.getMessage();
                return false;
            }
        }
        return updated;
    }
}
