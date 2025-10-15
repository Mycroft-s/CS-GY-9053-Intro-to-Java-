package orgchart;

public abstract class Employee {
    protected static int nextId = 1;
    protected final int id;
    protected final String name;
    protected final String title;      
    protected final int baseSalary;    
    protected Manager manager;

    public Employee(String name, String title, int baseSalary) {
        this.id = nextId++;
        this.name = name;
        this.title = title;
        this.baseSalary = baseSalary;
    }

    public abstract int getHeadCount();
    public abstract int getTotalComp();

    public void setManager(Manager m) { this.manager = m; }
    public Manager getManager() { return manager; }
    public String getName() { return name; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getBaseSalary() { return baseSalary; }

    //  CEO:contentReference[oaicite:3]{index=3}
    public String pathToCeo() {
        StringBuilder sb = new StringBuilder(name + " (id=" + id + ")");
        Manager cur = manager;
        while (cur != null) {
            sb.append(" - ").append(cur.name).append(" (id=").append(cur.id).append(")");
            cur = cur.manager;
        }
        return sb.toString();
    }

    // contentReference[oaicite:4]{index=4}
    public boolean validate() {
        // 1) CEO
        Employee root = this;
        while (root.getManager() != null) root = root.getManager();
        if (!(root instanceof Manager)) return false; // CEO must be Manager

        // 2) root DFS  “child.manager == parent”
        java.util.Set<Integer> seen = new java.util.HashSet<>();
        return dfsCheck((Manager) root, seen);
    }

    private static boolean dfsCheck(Manager m, java.util.Set<Integer> seen) {
        if (!seen.add(m.getId())) return false; // cycle
        for (Employee d : m.getDirects()) {
            if (d.getManager() != m) return false; 
            if (d instanceof Manager) {
                if (!dfsCheck((Manager) d, seen)) return false;
            } else {
                // IC
                if (!seen.add(d.getId())) return false;
            }
        }
        return true;
    }
}
