package lukuvinkit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lukuvinkit.db.Database;
import lukuvinkit.domain.Lukuvinkki;

public class LukuvinkkiDao implements Dao<Lukuvinkki, Integer> {

  private final Database db;

  public LukuvinkkiDao(Database db) {
    this.db = db;
  }

  @Override
  public int create(Lukuvinkki lukuvinkki) throws SQLException {
    int id = -1;
    try ( Connection connection = db.getConnection()) {
      PreparedStatement stmt = connection
              .prepareStatement("INSERT INTO Lukuvinkki (otsikko, kuvaus)"
                      + " VALUES (?, ?)");
      stmt.setString(1, lukuvinkki.getOtsikko());
      stmt.setString(2, lukuvinkki.getKuvaus());
      stmt.executeUpdate();

      ResultSet generatedKeys = stmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        id = generatedKeys.getInt(1);
      }
      generatedKeys.close();
      stmt.close();

      PreparedStatement insertTags = connection
              .prepareStatement("INSERT INTO Tagi (lukuvinkki_id, nimi)"
                      + " VALUES (?, ?)");
      for (String tag : lukuvinkki.getTags()) {
        insertTags.setInt(1, id);
        insertTags.setString(2, tag);
        insertTags.addBatch();
      }
      insertTags.executeBatch();
      insertTags.close();
    }
    return id;
  }

  @Override
  public Lukuvinkki read(Integer key) throws SQLException {
    return null;
  }

  @Override
  public Lukuvinkki update(Lukuvinkki object) throws SQLException {
    return null;
  }

  @Override
  public void delete(Integer id) throws SQLException {
    Connection connection = db.getConnection();

    PreparedStatement stmt = connection
            .prepareStatement("DELETE FROM Lukuvinkki WHERE id = ?");

    stmt.setInt(1, id);
    stmt.executeUpdate();

    stmt.close();
    connection.close();
  }

  @Override
  public List<Lukuvinkki> list() throws SQLException {
    return null;
  }

  public List<String> getTags(Lukuvinkki lukuvinkki) throws SQLException {
    List<String> tags = new ArrayList<>();
    try ( Connection connection = db.getConnection()) {
      PreparedStatement stmt = connection
              .prepareStatement("SELECT name FROM Tagi WHERE lukuvinkki_id = ?");
      stmt.setInt(1, lukuvinkki.getId());
      
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        tags.add(rs.getString("nimi"));
      }
    }
    return tags;
  }
}
