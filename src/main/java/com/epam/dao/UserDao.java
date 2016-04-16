package com.epam.dao;

import com.epam.entities.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Almas_Doskozhin
 * on 4/2/2016.
 */
public class UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);
    private String file = UserDao.class.getClassLoader().getResource("userDirectory.csv").getFile();

    public void addUser(User user) {
        List<User> users = parseCSVToBeanList();
        users.add(user);
        LOG.info("User added.");
        writeCSVData(users);
    }

    public int updateUser(User updatedUser) {
        List<User> users = parseCSVToBeanList();
        for (User user : users) {
            if (user.getLogin().equals(updatedUser.getLogin())) {
                LOG.info(user.getLogin()+ "" + updatedUser.getLogin());
                users.remove(user);
                users.add(updatedUser);
                writeCSVData(users);
                LOG.info("User updated.");
                return 1;
            }
        }
        LOG.info("User was not updated.");
        return 0;
    }

    public int deleteUser(String login) {
        List<User> users = parseCSVToBeanList();
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                users.remove(user);
                LOG.info("User deleted.");
                return 1;
            }
        }
        LOG.info("User was not deleted.");
        return 0;
    }

    public User readUser(String login) {
        List<User> users = parseCSVToBeanList();
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    private List<User> parseCSVToBeanList() {
        HeaderColumnNameTranslateMappingStrategy<User> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
        beanStrategy.setType(User.class);

        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("Name", "firstName");
        columnMapping.put("Surname", "lastName");
        columnMapping.put("Login", "login");
        columnMapping.put("Email", "email");

        beanStrategy.setColumnMapping(columnMapping);

        CsvToBean<User> csvToBean = new CsvToBean<>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            LOG.error("File not found for CSVReader: ", e);
        }
        List<User> users = csvToBean.parse(beanStrategy, reader);
        LOG.info(users.toString());
        return users;
    }

    private void writeCSVData(List<User> users) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);

            CSVWriter csvWriter = new CSVWriter(writer);
            List<String[]> data = toStringArray(users);
            csvWriter.writeAll(data);
            csvWriter.close();
        } catch (IOException e) {
            LOG.error("writeCSVData failed: ", e);
        }
    }

    private static List<String[]> toStringArray(List<User> emps) {
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"Name", "Surname", "Login", "Email"});
        for (User user : emps) {
            records.add(new String[]{user.getFirstName(), user.getLastName(), user.getLogin(), user.getEmail()});
        }
        return records;
    }
}
