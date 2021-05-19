package compiler;

import java.util.ArrayList;

public class Let {
    public ArrayList<String> parametersTypes = new ArrayList<String>();
    public ArrayList<String> parametersNames = new ArrayList<String>();

    public String toString() {
        String let = "Let: ";

        String names = " (parametersName:";
        for (int i = 0; i < parametersNames.size(); i++) {
            names += " [" + parametersNames.get(i) + " , index: " + (i + 1) + "] ";
        }

        let = let + names + ")";

        String types = " (parametersType:";
        for (int i = 0; i < parametersTypes.size(); i++) {
            types += " [" + parametersTypes.get(i) + " , index: " + (i + 1) + "] ";
        }

        return let + types + ")";
    }
}
