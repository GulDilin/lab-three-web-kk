import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "db", eager = true)
@ApplicationScoped
public class DBBean {

    private DataBaseManager manager = null;
    private String resultAllDot;
    private String resultAllTable;

    public DBBean() {
        manager = new DataBaseManager("studs", 8495);
    }

    public String getResultAllDot() {
        return manager.getAll(false);
    }

    public String getResultAllTable() {
        return manager.getAll(true);
    }

    public void setResultAllDot(String resultAllDot) {
        this.resultAllDot = resultAllDot;
    }

    public void setResultAllTable(String resultAllTable) {
        this.resultAllTable = resultAllTable;
    }

    public boolean addDot(String s) {
        if (s.equals("")) return false;
        try {
            String[] vals = s.split(" ");
            if (vals.length != 3) return false;
            double x = Double.parseDouble(vals[0]);
            double y = Double.parseDouble(vals[1]);
            int r = Integer.getInteger(vals[2]);
            return manager.addDot(x, y, r, "ну и срань");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void deleteAll() {
        if (manager != null)
            manager.deleteAll();
    }
}
