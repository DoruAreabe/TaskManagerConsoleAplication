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
            System.out.println("Please select the option:");
            System.out.println("add | remove | list | exit");
            String temp = scan.nextLine();
            switch (temp.toLowerCase().trim()) {
                case "add":
                    data=add(data);
                    break;
                case "remove":
                    data = remove(data);
                    break;
                case "list":
                    for (String element : data) {
                        System.out.println(data.indexOf(element)+" : "+element);
                    }
                    break;
                case "exit":
                    exit(data);
                    return;
                default:
                    System.out.println("Not valid input, please tap again: ");
                    continue;
            }
        }

    }

    private static List<String> add(List<String> data) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please, type task description:");
        String title = scan.nextLine();
        System.out.println("Please type task due date in format YYYY-MM-DD");
        String date = scan.nextLine();
        System.out.println("Is this task important? true/false");
        String isImportant = "";
        while (true) {
            String temp = scan.nextLine();
            if (!temp.equals("true") && !temp.equals("false")) {
                System.out.println("Not valid format. Please tap true or false again");
                continue;
            }
            isImportant = temp;
            break;
        }
        String finalConcat =title+"  "+date+"  "+isImportant;
        data.add(finalConcat);
        return data;
    }

    private static void exit(List<String> data) {
        System.out.println("See you next time, bye!");
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
                System.out.println("Input data not a number, please retype index again: ");
            }
            int index = scan.nextInt();
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
                System.out.println("Failed fail creation");
            }
        }
        List<String> dataList = new ArrayList<>();
        try {
            for (String listElenement : Files.readAllLines(path)) {
                dataList.add(listElenement);
            }
        } catch (IOException e) {
            System.out.println("You can't read data file");
        }
        return dataList;
    }
}
