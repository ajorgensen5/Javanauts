/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit260.pioneertrail.view;

import byui.cit260.pioneertrail.control.GameControl;
import byui.cit260.pioneertrail.model.GameModel;
import byui.cit260.pioneertrail.model.InventoryModel;
import java.util.Scanner;

/**
 *
 * @author Derek
 */
public class RepairWagonView {
    
    private int chosenAction = 0;
    private int[] availableActions = new int[3];
    
    public int displayRepairWagonView(GameControl gameControl, GameModel gameModel, InventoryModel inventoryModel) {
        
        availableActions = gameControl.repairWagonCheck(gameModel, inventoryModel);
        
        /* array indexes
        0 - 0/1 is wheel available
        1 - 0/1 is hammer available
        2 - 0-3 general status of hammer durability
        3 - errors, if found
        */
        
        if (availableActions[3] != 0)
            return -1;
            //todo: error handling
        
        System.out.println("Repairing Wagon:");
        String promptString = new String();
        
        if (availableActions[0] == 1) {
            promptString = promptString + "\n  W: You have a spare wheel. That would be the safest way to fix this.";
        }
        
        if (availableActions[1] == 1) {
            switch (availableActions[2]) {
            case 3:
                promptString = promptString + "\n  H: Your Hammer looks good as new and should do quite well.";
                break;
            case 2:
                promptString = promptString + "\n  H: Your Hammer's showing signs of use, but should still work just fine.";
                break;
            case 1:
                promptString = promptString + "\n  H: It looks like your Hammer's seen better days, but it should still have some life in it yet.";
                break;
            case 0:
                promptString = promptString + "\n  H: You hammer is almost broken, but it should at least get this wagon moving again.";
                break;
            default:
                break;
            }
        }
        
        if (promptString.length() == 0) {
            promptString = promptString + "\n  F: Looks like you're in a pinch. You don't have anything on hand to fix this properly."
                    + "\n     Fix the wagon with whatever you can find. Maybe this stone will do.";
        }
        
        promptString = promptString + "\n  E: Exit to previous menu";
        
        chosenAction = 0;
        
        boolean endOfView = false;
        int feedback = 0;

        do {

            String[] inputs = this.getInputs(promptString);
            
            if (inputs == null) {
                return -1;
            }
            else if ("E".equals(inputs[0].toUpperCase())) {
                return -1;
            }
            
            endOfView = doAction(inputs);
            feedback = gameControl.repairWagonApply(gameModel, inventoryModel, chosenAction);
            String feedbackString = new String();

            switch (feedback) {
            case 0:
                break;
            case 1:
                feedbackString = "You put the new wheel on.\nIt's all fixed up, and rolls even better than before!";
                break;
            case 2:
                feedbackString = "You use your hammer to repair the wagon.\nYou're good to go again.";
                break;
            case 3:
                feedbackString = "You use your hammer to repair the wagon.\nIt breaks in the process, but at least you can still keep moving.";
                break;
            case 4:
                feedbackString = "Using a stone you found lying around, you repair the wagon.\nIt doesn't look like it'll last too long, but at least it'll keep rolling for a bit longer...";
                break;
            default:
                break;
            }
            
            System.out.println(feedbackString);
            
        } while (endOfView == false);
        
        return feedback;
    }

    private String[] getInputs(String promptString) {

        Scanner reader = new Scanner(System.in);
        String[] inputs = new String[1];
        boolean valid = false;
        
        while (valid == false) {

            System.out.println(promptString);

            inputs[0] = reader.nextLine().trim();

            if (inputs[0].length() < 1) {
                System.out.println("You didn't specify an action");
                continue;
            }

            valid = true;
        }

        return inputs;
    }

    private boolean doAction(String[] inputs) {
        
        switch (inputs[0].toUpperCase()) {
        case "W":
            if (availableActions[0] == 1) {
                chosenAction = 1;
                return true;
            } else {
                System.out.println("Invalid input");
            }
            break;
        case "H":
            if (availableActions[1] == 1) {
                chosenAction = 2;
                return true;
            } else {
                System.out.println("Invalid input");
            }
            break;
        case "F":
            chosenAction = 4;
            return true;
        case "E":
        case "Q":
            return true;
        default:
            System.out.println("Invalid input");
        }

        return false;
    }
    
}
