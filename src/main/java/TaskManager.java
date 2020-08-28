import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        List<String> data = initProgram();
        chooseAnOption(data);
    }

    private static void chooseAnOption(List<String> data) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Please select the option:");
            System.out.println(ConsoleColors.RESET + "add | remove | list | exit");
            String temp = scan.nextLine();
            switch (temp.toLowerCase().trim()) {
                case "add":
                    data = add(data);
                    break;
                case "remove":
                    data = remove(data);
                    break;
                case "list":
                    for (String element : data) {
                        System.out.println(data.indexOf(element) + " : " + element);
                    }
                    break;
                case "exit":
                    exit(data);
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "Not valid input, please tap again: "
                            + ConsoleColors.RESET);
                    continue;
            }
        }

    }

    private static List<String> add(List<String> data) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please, type task description:");
        String title = validateTitle();
        System.out.println("Please type task due date in format YYYY-MM-DD");
        String date = validateDate();
        System.out.println("Is this task important? true/false");
        String isImportant = validateIsImp();
        String finalConcat = title + " " + date + "  " + isImportant;
        data.add(finalConcat);
        return data;
    }

    private static void exit(List<String> data) {
        System.out.println(ConsoleColors.BLUE + "See you next time, bye!" + ConsoleColors.RESET);
        Path path = Paths.get("tasks.scv");
        try {
            Files.write(path, data);
        } catch (IOException e) {
            System.out.println("Save data failed. Sorry for that :(");
        }
    }

    private static List<String> remove(List<String> data) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Chose index of task you want to remove: ");
        while (true) {
            if (!scan.hasNextInt()) {
                scan.next();
                System.out.println(ConsoleColors.RED + "Input data not a number, please retype index again: "
                        + ConsoleColors.RESET);
                continue;
            }
            int index = scan.nextInt();
            if (index < 0 || index > (data.size() - 1)) {
                System.out.println(ConsoleColors.RED + "Sorry, that index is incorrect, please choose next one: "
                        + ConsoleColors.RESET);
                continue;
            }
            data.remove(index);
            return data;
        }

    }

    private static List<String> initProgram() {
        Path path = Paths.get("tasks.scv");
        File file = new File(String.valueOf(path));
        if (!file.exists()) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED+"Failed fail creation"+ConsoleColors.RESET);
            }
        }
        List<String> dataList = new ArrayList<>();
        try {
            for (String listElement : Files.readAllLines(path)) {
                dataList.add(listElement);
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED+"Attempt read data file failed"+ConsoleColors.RESET);
        }
        return dataList;
    }

    private static String validateDate() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String date = scan.nextLine();
            String[] splitedData = date.split("-");
            boolean isNumbers = true;
            for (int i = 0; i < splitedData.length; i++) {
                if (!NumberUtils.isParsable(splitedData[i])) isNumbers = false;
            }
            if (date.length() == 10 && splitedData.length == 3 && isNumbers) {
                if (Integer.parseInt(splitedData[0]) > 2000 && Integer.parseInt(splitedData[0]) < 2100
                        && Integer.parseInt(splitedData[1]) > 0 && Integer.parseInt(splitedData[1]) < 13
                        && Integer.parseInt(splitedData[2]) > 0 && Integer.parseInt(splitedData[2]) < 32) {
                    return date;
                }
            }
            System.out.println(ConsoleColors.RED+"Input have invalid format, please retype date in format YYYY-MM-DD: "+ConsoleColors.RESET);
        }
    }

    private static String validateTitle() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String title = scan.nextLine();
            if (title.length() >= 29) {
                System.out.println(ConsoleColors.RED+"Type shorter task description, please: "+ConsoleColors.RESET);
                continue;
            }
            while (title.length() != 30) {
                title += " ";
            }
            return title;
        }
    }

    private static String validateIsImp() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String isImportant = scan.nextLine();
            if (!isImportant.equals("true") && !isImportant.equals("false")) {
                System.out.println(ConsoleColors.RED+"Not valid format. Please tap true or false again"+ConsoleColors.RESET);
                continue;
            }
            return isImportant;
        }
    }
}
