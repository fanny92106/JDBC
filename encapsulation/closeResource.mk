# Close Database Resource
public class JDBCUtils {
	/**
	 * @encapsulation: close resource
	 * @overload
	 */
	public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
			try {
					if(ps != null) {
							ps.close();
					}
			} catch (SQLException throwables) {
					throwables.printStackTrace();
			}

			try {
					if(conn !=null) {
							conn.close();
					}
			} catch (SQLException throwables) {
					throwables.printStackTrace();
			}

			try {
					rs.close();
			} catch (SQLException throwables) {
					throwables.printStackTrace();
			}
		}
}
