package adapters;

public class EnvironmentAdapter {

	public static boolean setup() {
			return OracleDBAdapter.checkEnvironment();
	}

	public EnvironmentAdapter() {
	}
}
