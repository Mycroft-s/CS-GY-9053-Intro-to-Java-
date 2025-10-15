package orgchart;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    private final ArrayList<Employee> directs;

    public Manager(String name, String title, int baseSalary, ArrayList<Employee> reports) {
        super(name, title, baseSalary);
        this.directs = new ArrayList<>(reports);          // copy on construct
        for (Employee e : reports) e.setManager(this);    // set back-pointer
    }

    @Override
    public int getHeadCount() {
        int cnt = 1;
        for (Employee e : directs) cnt += e.getHeadCount();
        return cnt;
    }

    @Override
    public int getTotalComp() {
        int sum = baseSalary;
        for (Employee e : directs) sum += e.getTotalComp();
        return sum;
    }

    /** NEW: return a COPY (required by the update) */
    public List<Employee> getDirects() {
        return new ArrayList<>(directs);
    }

    /** NEW: add a direct report (keeps pointers consistent) */
    public void addDirect(Employee e) {
        if (e == null) return;
        directs.add(e);
        e.setManager(this);
    }

    /** NEW: remove a direct report (manager pointer cleared) */
    public boolean removeDirect(Employee e) {
        if (e == null) return false;
        boolean ok = directs.remove(e);
        if (ok && e.getManager() == this) e.setManager(null);
        return ok;
    }
}
