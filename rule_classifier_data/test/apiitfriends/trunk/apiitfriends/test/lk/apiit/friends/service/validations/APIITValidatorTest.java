package lk.apiit.friends.service.validations;

import static lk.apiit.friends.service.validations.APIITValidator.isValidCBNo;

import org.junit.Test;

public class APIITValidatorTest {

	@Test
	public void testIsValidCBNo() {
		System.out.println("CB" + String.format("%06d", 1));
		isValidCBNo("CB001897");
	}

	@Test
	public void testIsValidStaffNo() {
		isValidCBNo("1");
	}

}
