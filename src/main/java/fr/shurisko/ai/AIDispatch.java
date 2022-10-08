package fr.shurisko.ai;

import java.util.Locale;

public class AIDispatch {

    public static boolean prepareCommand(String cmd) {
        String[] argument = cmd.substring(cmd.split(" ")[0].length()).split(" ");
        return analyseCommand(cmd.split(" ")[0], argument);
    }

    private static boolean analyseCommand(String cmd, String[] args) {
        switch (cmd.toLowerCase(Locale.ROOT)) {
            case "joue": case "play": case "lance": case "ajoute": case "add":
                return true;
            case "skip": case "suivant": case "next":
                return true;
            case "clear": case "supprime": case "stop":
                return true;
            case "kick":
                return true;
            case "mute":
                return true;
            default:
                return false;
        }
    }
}
