package KillerSokoban;

import java.io.*;
import java.util.ArrayList;

class BuildMap {

    private static final String UTF8_BOM = "\uFEFF";
    private Field[][] map;
    private ArrayList<Player> players;
    private BufferedReader br;
    private int playerIndex = 1;


    /**
     * Lényegében egy sima mezőkböl álló pályát inicializál. Ha a paraméterben kap egy fájlnevet, akkor felkészül a fájlból való olvasásra, egyébként konzolból olvas.
     * @param filename
     */
    BuildMap(String filename) {
        map = new Field[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (i == 0 || j == 0 || i == 19 || j == 19) {
                    map[i][j] = new WallField();
                } else {
                    map[i][j] = new Field();
                }
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (i > 0)
                    map[i][j].setNeighbour(Direction.Up, map[i - 1][j]);
                if (i < 19)
                    map[i][j].setNeighbour(Direction.Down, map[i + 1][j]);
                if (j > 0)
                    map[i][j].setNeighbour(Direction.Left, map[i][j - 1]);
                if (j < 19)
                    map[i][j].setNeighbour(Direction.Right, map[i][j + 1]);
            }
        }
        players = new ArrayList<>();

        if (filename != null) {
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

    }

    /**
     * visszaadja a mapet
     * @return az elkészült map
     */
    Field[][] getMap() {
        return map;
    }

    /**
     * visszaadja a játékosokat
     * @return a playerek
     */
    ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Ez a fuggvény építi meg a pályát.
     * A megadott fájl alapján elkészíti a pályát. A fájlnak a pályaépítő nyelvvel kell írva lennie.
     * @throws IOException
     */
    void mukodj() throws IOException {
        String choice;
        //főmenü
        do {
            choice = br.readLine();

            if (choice.startsWith(UTF8_BOM)) {
                choice = choice.substring(1);
            }

            switch (choice) {
                //a fajlban levo palyat tolti be
                case "loadGameFromFile": {
                    String filename = br.readLine();
                    BuildMap mapFromFile = new BuildMap(filename);
                    mapFromFile.mukodj();
                    map = mapFromFile.getMap();
                    break;
                }

                case "buildGame": {

                    //játéképítő rész
                    do {
                        choice = br.readLine();
                        switch (choice) {
                            //fieldeket pakolos resz
                            case "makeFields": {
                                do {
                                    choice = br.readLine();
                                    ArrayList<String> parameters;
                                    try {
                                        parameters = getParameters(choice);

                                        int x = 0;
                                        int y = 0;
                                        Field pre_field = null;
                                        if (parameters.size() > 1) {
                                            x = Integer.parseInt(parameters.get(1)) - 1;
                                            y = 20 - Integer.parseInt(parameters.get(2));
                                            pre_field = map[y][x];
                                        }

                                        switch (parameters.get(0)) {
                                            case "F": {
                                                map[y][x] = new Field();
                                                if (pre_field != null) {
                                                    map[y][x].setNeighbours(pre_field.getNeighbours());
                                                }
                                                break;
                                            }
                                            case "H": {
                                                map[y][x] = new HoleField();
                                                if (pre_field != null) {
                                                    map[y][x].setNeighbours(pre_field.getNeighbours());
                                                }
                                                break;
                                            }
                                            case "SF": {
                                                int sf_x = x;
                                                int sf_y = y;
                                                boolean hozzaadva = false;

                                                while (!hozzaadva) {
                                                    choice = br.readLine();
                                                    try {
                                                        parameters = getParameters(choice);
                                                        x = Integer.parseInt(parameters.get(1)) - 1;
                                                        y = 20 - Integer.parseInt(parameters.get(2));
                                                        hozzaadva = true;
                                                        Field tmp_pre_field = map[y][x];
                                                        map[y][x] = new SwitchHole();
                                                        map[y][x].setNeighbours(tmp_pre_field.getNeighbours());
                                                    } catch (WrongFormatException | NumberFormatException e) {
                                                        hozzaadva = false;
                                                        System.out.println(e.getMessage());
                                                    }
                                                }
                                                map[sf_y][sf_x] = new SwitchField((SwitchHole) map[y][x]);
                                                if (pre_field != null) {
                                                    map[sf_y][sf_x].setNeighbours(pre_field.getNeighbours());
                                                }
                                                break;
                                            }
                                            case "WF": {
                                                map[y][x] = new WallField();
                                                if (pre_field != null) {
                                                    map[y][x].setNeighbours(pre_field.getNeighbours());
                                                }
                                                break;
                                            }
                                            case "FF": {
                                                map[y][x] = new FinishField();
                                                if (pre_field != null) {
                                                    map[y][x].setNeighbours(pre_field.getNeighbours());
                                                }
                                                break;
                                            }
                                            case "back": {
                                                choice = "back_to_builder";
                                                break;
                                            }
                                            default: {
                                                break;
                                            }

                                        }
                                    } catch (WrongFormatException | NumberFormatException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                while (!choice.equals("back_to_builder")); //field epito vege
                                break;
                            }

                            //gameobject pakolos resz
                            case "addGameObjects": {
                                do {
                                    choice = br.readLine();
                                    ArrayList<String> parameters;
                                    try {
                                        parameters = getParameters(choice);
                                        int x = 0;
                                        int y = 0;
                                        if (parameters.size() > 1) {
                                            x = Integer.parseInt(parameters.get(1)) - 1;
                                            y = 20 - Integer.parseInt(parameters.get(2));
                                        }

                                        switch (parameters.get(0)) {
                                            case "player": {
                                                map[y][x].setCurrentGameObj(new Player(playerIndex++));
                                                map[y][x].getCurrentGameObject().setCurrentField(map[y][x]);
                                                players.add((Player) map[y][x].getCurrentGameObject());
                                                break;
                                            }
                                            case "box": {
                                                map[y][x].setCurrentGameObj(new Box());
                                                map[y][x].getCurrentGameObject().setCurrentField(map[y][x]);
                                                break;
                                            }
                                            case "back": {
                                                choice = "back_to_builder";
                                                break;
                                            }
                                            default: {
                                                break;
                                            }
                                        }

                                    } catch (WrongFormatException | NumberFormatException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                while (!choice.equals("back_to_builder")); //field epito vege
                                break;
                            }

                            //gameobject pakolos resz
                            case "addLubricant":
                                do {
                                    choice = br.readLine();
                                    ArrayList<String> parameters;
                                    try {
                                        parameters = getParameters(choice);
                                        int x = 0;
                                        int y = 0;
                                        if (parameters.size() > 1) {
                                            x = Integer.parseInt(parameters.get(1));
                                            y = Integer.parseInt(parameters.get(2));
                                        }

                                        switch (parameters.get(0)) {
                                            case "H": {
                                                map[y][x].setLubricant(new Honey());
                                                break;
                                            }
                                            case "O": {
                                                map[y][x].setLubricant(new Oil());
                                                break;
                                            }
                                            case "back": {
                                                choice = "back_to_builder";
                                                break;
                                            }
                                            default: {
                                                break;
                                            }

                                        }
                                    } catch (WrongFormatException | NumberFormatException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                while (!choice.equals("back_to_builder")); //field epito vege
                                break;

                            case "back": {
                                choice = "back_to_main";
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    while (!choice.equals("back_to_main"));
                    break;
                }
                default:
                    break;
            }
        }
        while (!choice.equals("exit"));
    }

    /**
     * a parancsokból egy paramétertömböt készít
     * @param cmd a parancs
     * @return a paramétertömb
     * @throws WrongFormatException ha rossz a parancs
     */
    private ArrayList<String> getParameters(String cmd) throws WrongFormatException {

        ArrayList<String> parameters = new ArrayList<>();


        if (cmd.equals("back")) {
            parameters.add("back");
            return parameters;
        }
        if (cmd.equals("exit")) {
            parameters.add("exit");
            return parameters;
        }
        if (cmd.equals("help")) {
            parameters.add("help");
            return parameters;
        }


        int firstParenthesis = cmd.indexOf('(');
        int comma = cmd.indexOf(',');
        int lastParenthesis = cmd.indexOf(')');

        if (firstParenthesis < 0)
            throw (new WrongFormatException("Hianyzo '(' zarojel."));
        if (comma < 0)
            throw (new WrongFormatException("Hianyzo ',' vesszo."));
        if (lastParenthesis < 0)
            throw (new WrongFormatException("Hianyzo ')' zarojel."));

        String fieldType = cmd.substring(0, firstParenthesis);
        if (fieldType.isEmpty())
            throw (new WrongFormatException("Ures a mezotipus!"));
        String x = cmd.substring(firstParenthesis + 1, comma);
        if (x.isEmpty())
            throw (new WrongFormatException("Ures az x koordinata!"));
        String y = cmd.substring(comma + 1, lastParenthesis);
        if (y.isEmpty())
            throw (new WrongFormatException("Ures az y koordianat!"));

        //Ha minden jol ment
        parameters.add(fieldType); //field type
        parameters.add(x); //x coord
        parameters.add(y); //y
        return parameters;
    }

    /**
     * Egy kivétel arra, ha a megadott input/parancs rossz formátumú.
     */
    private class WrongFormatException extends Exception {
        WrongFormatException(String error) {
            super("A megadott input nem megfelelo formatumu! HIBA: " + error);
        }
    }
}
