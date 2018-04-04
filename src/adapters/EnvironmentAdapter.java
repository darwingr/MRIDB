package adapters;

public class EnvironmentAdapter {

	public static boolean setup() {
			return DBAdapter.checkEnvironment();
	}

	public EnvironmentAdapter() {
	}

}
