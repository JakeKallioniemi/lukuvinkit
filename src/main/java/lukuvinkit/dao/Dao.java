package lukuvinkit.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {

  int create(T object) throws SQLException;

  T read(K key) throws SQLException;

  T update(T object) throws SQLException;

  void delete(K key) throws SQLException;

  List<T> list() throws SQLException;

  List<T> listByTag(String tag) throws SQLException;
}