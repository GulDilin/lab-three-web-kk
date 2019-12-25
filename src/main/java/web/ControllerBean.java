package web;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Map;

@ManagedBean(name = "controller", eager = true)
@ApplicationScoped
public class ControllerBean {

    private String x = "0";
    private String y = "0";
    private String r = "1";
    private String result = "";

    private HiberDataBaseManager manager = null;

    public ControllerBean() {
        manager = new HiberDataBaseManager();
    }

    public void checkArea() {
        try {
            y = AreaValidator.validateY(y);
            setR(r);
            x = AreaValidator.validateX(x);
            result = AreaValidator.checkArea(x, y, r);
        } catch (NumberFormatException e) {
            result = "Incorrect value(s)!";
        }
        System.out.println("X: " + x + "\nY: " + y + "\nR: " + r + "\nResult: " + result);
        manager.addDot(Double.parseDouble(x),  Double.parseDouble(y),  Integer.parseInt(r), result);
        resetBean();
    }

    public void plotCheckArea() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String plotX = params.get("x");
        String plotY = params.get("y");
        String plotR = params.get("r");

        try {
            plotY = AreaValidator.validateY(plotY);
            plotX = AreaValidator.validateX(plotX);
            result = AreaValidator.checkArea(plotX, plotY, plotR);
        } catch (NumberFormatException e) {
            result = "Incorrect value(s)!";
        }
        System.out.println("X: " + plotX + "\nY: " + plotY + "\nR: " + plotR + "\nResult: " + result);
        if (manager != null) {
            manager.addDot(Double.parseDouble(plotX), Double.parseDouble(plotY), Integer.parseInt(plotR), result);
        }
        resetBean();
    }

    public void resetBean() {
        x = "0";
        y = "0";
        r = "1";
        result = "";
    }


    public void setX(String x) {
        this.x = x;
    }

    public String getX() {
        return x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getY() {
        return y;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getR() {
        return r;
    }

    public String getResult() {
        return result;
    }
}
