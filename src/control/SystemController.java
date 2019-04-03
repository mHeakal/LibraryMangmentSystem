package control;

import model.dataaccess.DataAccessFacade;
import model.dataaccess.DataAccess;

public class SystemController {

	private static DataAccess dataAccess = null;

	private SystemController() {
	}

	public static DataAccess getDataAccessInstance() {
		if (dataAccess == null) {
			dataAccess = new DataAccessFacade();
		}
		return dataAccess;
	}

	public static void setDataAccess(DataAccess access) {
		dataAccess = access;
	}

}
