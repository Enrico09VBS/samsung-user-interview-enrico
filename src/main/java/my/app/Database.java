package my.app;

// Importing database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.entity.GetListFilter;
import my.entity.SampleObject;

public class Database {

    /**
     *
     * @param params
     * @return list of SampleObjects
     * @throws Exception
     */
    public static List<SampleObject> GetListOfObjects(GetListFilter params) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "tes";
        String query;
        if (params.sortType != null && params.sortType.toLowerCase().equals("asc")) {
            if (params.sortBy != null && (params.sortBy.toLowerCase().equals("id") || params.sortBy.toLowerCase().equals("title"))) {
                query = "SELECT * FROM sampletable WHERE delete_time IS NULL AND title LIKE ? ORDER BY " + params.sortBy + " ASC";
            } else {
                query = "SELECT * FROM sampletable WHERE delete_time IS NULL AND title LIKE ? ORDER BY id ASC";
            }
        } else {
            query = "SELECT * FROM sampletable WHERE delete_time IS NULL AND title LIKE ? ORDER BY id DESC";
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        List<SampleObject> arrOfObject;

        String search = "%%";
        boolean usePagination = false;
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try {
                if (params.search != null) {
                    search = "%" + params.search + "%";
                }
                if (params.limit != 0) {
                    query += " LIMIT ? OFFSET ?";
                    usePagination = true;
                }
                PreparedStatement prep = con.prepareStatement(query);
                prep.setString(1, search);
                if (usePagination) {
                    prep.setInt(2, params.limit);
                    prep.setInt(3, params.page * params.limit);
                }
                ResultSet rs = prep.executeQuery();
                arrOfObject = new ArrayList<>();
                while (rs.next()) {
                    arrOfObject.add(new SampleObject(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("create_time"),
                        rs.getString("update_time")
                ));
                }
            } catch (SQLException e) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return arrOfObject;
    }

    /**
     *
     * @param id
     * @return SampleObject
     * @throws Exception
     */
    public static SampleObject GetObjectByID(int id) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres"; 
        String password = "tes";
        String query = "SELECT * FROM sampletable WHERE delete_time IS NULL AND id = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        SampleObject result;

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try {
                PreparedStatement prep = con.prepareStatement(query);
                prep.setInt(1, id);
                ResultSet rs = prep.executeQuery();
                rs.next();
                result = new SampleObject(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("create_time"),
                        rs.getString("update_time")
                );
            } catch (SQLException e) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     *
     * @param data
     * @return boolean
     * @throws Exception
     */
    public static boolean InsertObject(SampleObject data) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "tes";
        boolean useDefaultStatus = data.GetStatus() == null || data.GetStatus().equals("");
        String query = "INSERT INTO sampletable (title, description) values (?, ?);";
        if (!useDefaultStatus) {
            query = "INSERT INTO sampletable (title, description, status) values (?, ?, ?::status_type);";
        }
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try {
                PreparedStatement prep = con.prepareStatement(query);
                prep.setString(1, data.GetTitle());
                prep.setString(2, data.GetDescription());
                if (!useDefaultStatus) {
                    prep.setString(3, data.GetStatus());
                }
                prep.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param data
     * @return boolean
     * @throws Exception
     */
    public static boolean UpdateObject(SampleObject data) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "tes";
        String query = "UPDATE sampletable SET title = ?, description = ?, status = ?::status_type, update_time = NOW() WHERE id = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try {
                PreparedStatement prep = con.prepareStatement(query);
                prep.setString(1, data.GetTitle());
                prep.setString(2, data.GetDescription());
                prep.setString(3, data.GetStatus());
                prep.setInt(4, data.GetID());
                prep.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param id
     * @return boolean
     * @throws Exception
     */
    public static boolean DeleteObject(int id) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "tes";
        String query = "UPDATE sampletable SET delete_time = NOW() WHERE id = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try {
                PreparedStatement prep = con.prepareStatement(query);
                prep.setInt(1, id);
                prep.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
