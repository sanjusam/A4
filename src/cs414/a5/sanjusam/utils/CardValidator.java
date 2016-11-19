package cs414.a5.sanjusam.utils;

public class CardValidator {
	public static boolean validate(String cardNum) {  
		//Very simple validation, check if the length is an even number
		return (cardNum.length() %2 == 0) ;
	}

}
