import com.jcraft.jsch.JSchException;

import java.sql.*;

public class DataBaseManager {
    private Tunnel tunnel;
    private Connection connection;
    private Statement statement;
    private int lPort;
    private int rPort;
    private String host = "helios.se.ifmo.ru";
    private String rhost = "pg";
    private String user;
    private String password;
    private boolean isConnect = false;

    public DataBaseManager(String dbName, int lPort) {
        statement = null;
        user = "s264449";
        password = "cfv571";
        this.lPort = lPort;
        int i = 1;
        while (!isConnect) {
            try {
                tunnel = new Tunnel(host, "s264449", "cfv571", 2222, rhost, this.lPort, 5432);
                tunnel.connect();
                isConnect = true;

            } catch (JSchException e) {
                e.printStackTrace();
                this.lPort += 15;
                System.out.println("SSH tunneling error. Trying new local port: " + this.lPort);
                System.out.println(i);
                isConnect = false;
                i--;
                if (i == 0) {
                    System.out.println("Can't connect SSH tunnel.");
                    break;
                }
            }
        }
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + lPort + "/" + dbName, user, password);
            statement = connection.createStatement();
            System.out.println("Database connected");
            System.out.println(statement.executeQuery(BDQuerys.GET_ALL.getTextQuery()));
        } catch (ClassNotFoundException e) {
            System.out.println("Cant load Driver class");
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getAll(boolean isTable) {
        String result = "";
        String x;
        String y;
        String r;
        int x_dot;
        int y_dot;
        int r_dot;
        String res;
        try {
            if (statement != null) {
                result = "";
                if (isTable) {
                    result += "<div class=\"table-container\">\n" +
                            "<table class=\"res-table\" id=\"table\">\n" +
                            "<tr><th>X</th><th>Y</th><th>R</th><th>RESULT</th></tr>\n";
                }
                ResultSet rs = statement.executeQuery(BDQuerys.GET_ALL.getTextQuery());
                while (rs.next()) {
                    try {
                        res = rs.getString("result");
                        x = rs.getString("x");
                        y = rs.getString("y");
                        r = rs.getString("r");
                        System.out.println("x=" + Double.parseDouble(x) + " y=" + Double.parseDouble(y) + " r = " + r);
                        r_dot = Integer.parseInt(r);
                        x_dot = (int) Math.round(Double.parseDouble(x) * 120 / r_dot + 150);
                        y_dot = (int) Math.round(-Double.parseDouble(y) * 120 / r_dot + 150);

                        if (!isTable) {
                            if (res.toLowerCase().equals("true") || res.toLowerCase().equals("false")) {
                                result += dotString(String.valueOf(x_dot), String.valueOf(y_dot), r, res) + "\n";
                            }
                        } else {
                            result += trString(Double.parseDouble(x), Double.parseDouble(y), r, res) + "\n";
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
                if (isTable) {
                    result += "</table>\n" +
                            "        </div>";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public void deleteAll() {
        try {
            if (statement != null) {
                statement.execute(BDQuerys.DELETE_DOTS.getTextQuery());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addDot(double x, double y, int r, String result) {
        try {
            if (statement != null) {
                String insert = "INSERT into dots VALUES(" + x + ", " + y + ", " + r + ", \'" + result + "\')";
                System.out.println(insert);
                statement.execute(insert);
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String dotString(String x, String y, String r, String res) {
        return "<circle r=\"3\" cx=\"" + x + "\" cy=\"" + y + "\" cr=\"" + r + "\" "
                + (res.toLowerCase().equals("true") ? "stroke=\"green\" fill=\"green\"" : "stroke=\"#AD2D2D\" fill=\"#AD2D2D\"")
                + "></circle>";
    }

    public String trString(String x, String y, String r, String result) {
        return "<tr>" + tdString(x) + tdString(y) + tdString(r) + tdString(result) + "</tr>";
    }

    public String trString(double x, double y, String r, String result) {
        return "<tr>" + tdString(x) + tdString(y) + tdString(r) + tdString(result) + "</tr>";
    }

    private String tdString(Object s) {
        return String.format("<td>%s</td>", s.toString());
    }

    private String tdString(double s) {
        return String.format("<td>%f</td>", s);
    }
}
