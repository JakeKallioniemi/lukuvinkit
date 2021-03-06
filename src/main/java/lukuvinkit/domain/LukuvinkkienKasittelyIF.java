package lukuvinkit.domain;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LukuvinkkienKasittelyIF {

  void saveRecommendation(Lukuvinkki l) throws SQLException;

  void deleteRecommendation(Lukuvinkki l) throws SQLException;

  void editRecommendation(Lukuvinkki l);

  ArrayList<Lukuvinkki> getAllRecommendations() throws SQLException;

  ArrayList<Lukuvinkki> getRecommendationsByTag(String tag) throws SQLException;

  ArrayList<Lukuvinkki> getRecommendationsByType(LukuvinkkiTyyppi tyyppi) throws SQLException;
}
