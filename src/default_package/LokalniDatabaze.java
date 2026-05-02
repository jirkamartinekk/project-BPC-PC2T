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

    public boolean odstranZamestnance(int ID){
        if(prvkyDatabaze.containsKey(ID)){
            prvkyDatabaze.remove(ID);
            return true;
        }else{
            return false;
        }
    }

    public Zamestnanec najdiZamestnance(int ID){
        return prvkyDatabaze.get(ID);
    }

    public int pocetPrvkuDatabaze(){
        return prvkyDatabaze.size();
    }
}