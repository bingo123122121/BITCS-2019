package bit.minisys.minicc.semantic;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

class variable {
    public String name;
    public String type;
    public LinkedList arrayLimit = new LinkedList();

    public variable() {
        this.name = null;
        this.type = null;
        arrayLimit = null;
    }

    public variable(String name, String type) {
        this.name = name;
        this.type = type;
        arrayLimit = null;
    }

    public variable(String name, String type, LinkedList arrayLimit) {
        this.name = name;
        this.type = type;
        this.arrayLimit = arrayLimit;
    }
}

public class SymbolTable {
    public SymbolTable father;
    public Map<Object, Object> items = new LinkedHashMap<>();

    public void addVariable(String name, String type) {
        variable var = new variable(name, type);
        items.put(name, var);
    }

    public void addVariable(String name, String type, LinkedList limit) {
        variable var = new variable(name, type, limit);
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

    public LinkedList getArrayLimit(String name) {
        if (this.items.containsKey(name)) {
            return ((variable) this.items.get(name)).arrayLimit;
        } else if (this.father != null) {
            return this.father.getArrayLimit(name);
        } else {
            return null;
        }
    }

    public String getArrayType(String name) {
        if (this.items.containsKey(name)) {
            return ((variable) this.items.get(name)).type;
        } else if (this.father != null) {
            return this.father.getArrayType(name);
        } else {
            return null;
        }
    }
}
