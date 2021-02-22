# Get Database Connection

		public class JDBCUtils {
		    /**
		     * @encapsulation: get DB connection
		     */
		    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
		        InputStream is = PreparedStatementUpdateTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

		        Properties pros = new Properties();
		        pros.load(is);

		        String user = pros.getProperty("user");
		        String url = pros.getProperty("url");
		        String password = pros.getProperty("password");
		        String driver = pros.getProperty("driver");

		        Class.forName(driver);
		        Connection conn = DriverManager.getConnection(url, user, password);
		        return conn;
		    }
			}
