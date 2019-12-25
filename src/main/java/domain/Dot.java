package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="dots")
public class Dot {
    @Id
    @GeneratedValue
    private int id;

    private Double x;
    private Double y;
    private Double r;
    private String result;

    public Dot(){

    }

    public Dot(Double x, Double y, Double r, String result){
        setX(x);
        setR(r);
        setY(y);
        setResult(result);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public Double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "<circle r=\"3\" cx=\"" + x + "\" cy=\"" + y + "\" cr=\"" + r + "\" "
                + (result.toLowerCase().equals("true") ? "stroke=\"green\" fill=\"green\"" : "stroke=\"#AD2D2D\" fill=\"#AD2D2D\"")
                + "></circle>";
    }

    public String trString() {
        return "<tr>" + tdString(x) + tdString(y) + tdString(r) + tdString(result) + "</tr>";
    }

    private String tdString(Object s) {
        return String.format("<td>%s</td>", s.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;
        return id == dot.id &&
                Objects.equals(x, dot.x) &&
                Objects.equals(y, dot.y) &&
                Objects.equals(r, dot.r) &&
                Objects.equals(result, dot.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, r, result, x, y);
    }
}
