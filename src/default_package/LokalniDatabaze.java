package default_package;

import java.util.HashMap;
import java.util.Map;

public class LokalniDatabaze {
    private Map<Integer, Zamestnanec> prvkyDatabaze;

    public LokalniDatabaze(){
        prvkyDatabaze = new HashMap<>();
    }

    public void pridejZamestnance(Zamestnanec zamestnanec){
        prvkyDatabaze.put(zamestnanec.ziskejID(), zamestnanec);
    }

    public void odstranZamestnance(Zamestnanec zamestnanec){
        prvkyDatabaze.remove(zamestnanec.ziskejID());
    }

    public Zamestnanec najdiZamestnance(Zamestnanec zamestnanec){
        return prvkyDatabaze.get(zamestnanec.ziskejID());
    }
}