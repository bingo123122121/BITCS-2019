package bit.minisys.minicc.semantic;

import java.util.LinkedHashMap;
import java.util.Map;

class variable {
    public String name;
    public String type;

    public variable() {
        this.name = null;
        this.type = null;
    }

    public variable(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

public class SymbolTable {
    public SymbolTable father;
    public Map<Object, Object> items = new LinkedHashMap<>();

    public void addVariable(String name, String type) {
        variable var = new variable(name, type);
        items.put(name, var);
    }

    public boolean findPresent(String name) {
        return this.items.containsKey(name);
    }

    public boolean findAll(String name) {
        if (this.items.containsKey(name)) {
            return true;
        } else if (this.father != null) {
            return this.father.findAll(name);
        } else {
            return false;
        }
    }
}
