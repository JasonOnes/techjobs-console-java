package org.launchcode.techjobs.console;

import com.sun.xml.internal.ws.api.model.ExceptionType;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);//.sort(String::compareToIgnoreCase);
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    Collections.sort(results);//sorts alphabetically by default
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                    //System.out.println("Search all fields not yet implemented.");
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println(("Invalid choice, try again."));
            } else {
                validChoice = true;
                //TODO get method to catch input error if not integer
//            try {
//                if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
//                    System.out.println("Invalid choice. Try again.");
//                } else if (choiceIdx > 0 && choiceIdx < choiceKeys.length) {
//                    validChoice = true;
//                } catch(InputMismatchException) {
//                        System.out.println("Enter a NUMBER from above choices.");
//            }

            }
        }
            while (!validChoice) ;

            return choiceKeys[choiceIdx];

}

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        /*
        I'm leaving some old code commented out throughout project to remind myself of thought process e.g.
        if (!someJobs.equals(null)) { two earlier attempts to say if someJobs contains jobs then...
        if (someJobs != null) {
        */
        if (someJobs.size() > 0) {
            System.out.println("There are " + someJobs.size() + " listings for that search.");
            for (HashMap job : someJobs) {
                    System.out.println("***********");
                    for (Object key : job.keySet()) {
                        //for (Map.Entry<String, String> key : job.entrySet())
                        if (job.keySet().contains(key)) {
                            System.out.println(key + " : " + job.get(key));

                        } else {
                            System.out.println("No listing for that search term.");
                        }
                    }
                    System.out.println("***********");


            }

        }else if (someJobs.size() == 0) { System.out.println("Sorry no listing for that searchterm, " +
                "(maybe check your spelling)!");}
    }
}