# Select statement for one record from DB

public class EncapsulationForSelectStatement() {
	/**
	*
	* @encapsulation: select one record
	*/
	 public <T> T getForOne(Class<T> clazz, String sql, Object...args) {
			 Connection conn = null;
			 PreparedStatement ps = null;
			 ResultSet rs = null;

			 try {
					 conn = JDBCUtils.getConnection();
					 ps = conn.prepareStatement(sql);
					 for (int i = 0; i < args.length; i++) {
							 ps.setObject(i + 1, args[i]);
					 }
					 rs = ps.executeQuery();
					 ResultSetMetaData rsmd = rs.getMetaData();
					 int columnCount = rsmd.getColumnCount();

					 if(rs.next()) {
							 // reflection
							 T t = clazz.newInstance();

							 // get column name and column value
							 for(int i = 0; i < columnCount; i++) {
									 // get colValue from rs
									 Object colValue = rs.getObject(i + 1);
									 // get colLabel
									 String colName = rsmd.getColumnLabel(i + 1);
									 // reflection
									 Field field = clazz.getDeclaredField(colName);
									 field.setAccessible(true);
									 field.set(t, colValue);
							 }
							 System.out.println(t);
							 return t;
					 }
			 } catch (Exception ex) {
					 ex.printStackTrace();
			 } finally {
					 JDBCUtils.closeResource(conn, ps, rs);
			 }
			 return null;
	 }


# Select statement for multiple record from DB
/**
 *
 * @encapsulation: select multiple records
 */
public <T> List<T> getForAll(Class<T> clazz, String sql, Object...args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
				conn = JDBCUtils.getConnection();
				ps = conn.prepareStatement(sql);
				for (int i = 0; i < args.length; i++) {
						ps.setObject(i + 1, args[i]);
				}
				rs = ps.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				List<T> list = new ArrayList<>();
				while(rs.next()) {
						// reflection
						T t = clazz.newInstance();
						// get column name and column value
						for(int i = 0; i < columnCount; i++) {
								// get colValue from rs
								Object colValue = rs.getObject(i + 1);
								// get colLabel
								String colName = rsmd.getColumnLabel(i + 1);
								// reflection
								Field field = clazz.getDeclaredField(colName);
								field.setAccessible(true);
								field.set(t, colValue);
						}
						list.add(t);
				}
				System.out.println(list);
				return list;
		} catch (Exception ex) {
				ex.printStackTrace();
		} finally {
				JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
}
