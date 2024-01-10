package lk.apiit.friends.service.validations;

/**
 * APIIT Validation Rules.
 * 
 * @author Yohan Liyanage
 * @version 17-Sep-2008
 * @since 17-Sep-2008
 */
public class APIITValidator {

	/**
	 * No instantiation.
	 */
	private APIITValidator() {
		super();
	}

	/**
	 * Validates CB Number Format.
	 * 
	 * @param cbNo CB No
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidCBNo(String cbNo) {

		cbNo = cbNo.toUpperCase();
		
		// If length is 8 and first two chars are CB 
		if (cbNo.length()==8 && cbNo.substring(0, 2).equals("CB")) {
			try {
				// If rest is a number
				Integer.parseInt(cbNo.substring(2));
				return true;
			}
			catch (NumberFormatException e) {
				// In this case, false will be returned
			}
		}
		return false;
	}

	/**
	 * Validates Staff Number Format.
	 * 
	 * @param staffNo Staff Number
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidStaffNo(String staffNo) {
		
		// TODO Implement Validation : Currently is just a number
		
		try {
			Integer.parseInt(staffNo);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
