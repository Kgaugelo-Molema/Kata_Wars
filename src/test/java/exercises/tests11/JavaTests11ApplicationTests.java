package exercises.tests11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static exercises.tests11.PhoneBook.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JavaTests11ApplicationTests {

	static Stream<Arguments> telephones = Stream.of(
			Arguments.of("/+1-541-754-3010 156 Alphand_St. <J Steeve>", "1-541-754-3010"), // null strings should be considered blank
			Arguments.of(" 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!", "1-541-914-3010"),
			Arguments.of("<Anastasia> +48-421-674-8974 Via Quirinal Roma", "48-421-674-8974"),
			Arguments.of(" :+1-321-512-2222 <Paul Dive> Sequoia Alley PQ-67209","1-321-512-2222")
	);

	static Stream<Arguments> names = Stream.of(
			Arguments.of("/+1-541-754-3010 156 Alphand_St. <J Steeve>", "J Steeve"), // null strings should be considered blank
			Arguments.of(" 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!", "E Kustur"),
			Arguments.of("<Anastasia> +48-421-674-8974 Via Quirinal Roma", "Anastasia"),
			Arguments.of(" :+1-321-512-2222 <Paul Dive> Sequoia Alley PQ-67209","Paul Dive")

	);

	static Stream<Arguments> addresses = Stream.of(
			Arguments.of("/+1-541-754-3010 156 Alphand_St. <J Steeve>", "156 Alphand St."), // null strings should be considered blank
			Arguments.of(" 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010!", "133 Green Rd. NY-56423"),
			Arguments.of("<Anastasia> +48-421-674-8974 Via Quirinal Roma", "Via Quirinal Roma"),
			Arguments.of(" :+1-321-512-2222 <Paul Dive> Sequoia Alley PQ-67209","Sequoia Alley PQ-67209"),
			Arguments.of("+1-741-984-3090 <Peter Reedgrave> _Chicago","Chicago"),
			Arguments.of(" :+1-921-333-2222 <Anna Stevens> Haramburu_Street AA-67209", "Haramburu Street AA-67209")
	);

	public static final String stringList = "/+1-541-754-3010 156 Alphand_St. <J Steeve>\n 133, Green, Rd. <E Kustur> NY-56423 ;+1-541-914-3010\n"
			+ "+1-541-984-3012 <P Reed> /PO Box 530; Pollocksville, NC-28573\n :+1-321-512-2222 <Paul Dive> Sequoia Alley PQ-67209\n"
			+ "+1-741-984-3090 <Peter Reedgrave> _Chicago\n :+1-921-333-2222 <Anna Stevens> Haramburu_Street AA-67209\n"
			+ "+1-111-544-8973 <Peter Pan> LA\n +1-921-512-2222 <Wilfrid Stevens> Wild Street AA-67209\n"
			+ "<Peter Gone> LA ?+1-121-544-8974 \n <R Steell> Quora Street AB-47209 +1-481-512-2222\n"
			+ "<Arthur Clarke> San Antonio $+1-121-504-8974 TT-45120\n <Ray Chandler> Teliman Pk. !+1-681-512-2222! AB-47209,\n"
			+ "<Sophia Loren> +1-421-674-8974 Bern TP-46017\n <Peter O'Brien> High Street +1-908-512-2222; CC-47209\n"
			+ "<Anastasia> +48-421-674-8974 Via Quirinal Roma\n <P Salinger> Main Street, +1-098-512-2222, Denver\n"
			+ "<C Powel> *+19-421-674-8974 Chateau des Fosses Strasbourg F-68000\n <Bernard Deltheil> +1-498-512-2222; Mount Av.  Eldorado\n"
			+ "+1-099-500-8000 <Peter Crush> Labrador Bd.\n +1-931-512-4855 <William Saurin> Bison Street CQ-23071\n"
			+ "<P Salinge> Main Street, +1-098-512-2222, Denve\n" + "<P Salinge> Main Street, +1-098-512-2222, Denve\n"
			+ "<Anastasia> +8-421-674-8974 Via Quirinal Roma\n ";

	@ParameterizedTest
	@MethodSource
	void GivenInputReturnTelephoneNumber(String str, String expectedResult) {
		String value = ExtractMatchValue(str, PhoneBook.TELEPHONEPATTERN);
		assertEquals(expectedResult, value);
	}

	private static Stream<Arguments> GivenInputReturnTelephoneNumber() {
		return telephones;
	}

	@ParameterizedTest
	@MethodSource
	void GivenInputReturnName(String str, String expectedResult) {
		String value = ExtractMatchValue(str, PhoneBook.NAMEPATTERN);
		assertEquals(expectedResult, value);
	}

	private static Stream<Arguments> GivenInputReturnName() {
		return names;
	}

	@ParameterizedTest
	@MethodSource
	void GivenInputReturnAddress(String str, String expectedResult) {
		String value = ExtractAddress(str);
		assertEquals(expectedResult, value);
	}

	private static Stream<Arguments> GivenInputReturnAddress() {
		return addresses;
	}

	@ParameterizedTest
	@ValueSource(strings = {PhoneBook.NAMEPATTERN, PhoneBook.TELEPHONEPATTERN})
	void GivenRegexPatternReturnMatches(String pattern) {
		List<String> list = Arrays.asList(stringList.split("\n"));
		list.forEach(i -> {
			String value = ExtractMatchValue(i, pattern);
			System.out.println(value);
		});
		assertEquals(22, list.size());
	}

	@ParameterizedTest
	@ValueSource(strings = {"5-555-555-5555"})
	void GivenNonExistentTelephoneNumberReturnNotFound(String input) {
		String result = PhoneBook.FindContact(stringList, input);
		assertEquals("Error => Not found: 5-555-555-5555", result);
	}

	@ParameterizedTest
	@ValueSource(strings = {"1-098-512-2222"})
	void GivenTelephoneNumberWithMultipleMatchesReturnTooManyPeople(String input) {
		String result = PhoneBook.FindContact(stringList, input);
		assertEquals("Error => Too many people: 1-098-512-2222", result);
	}

	@ParameterizedTest
    @ValueSource(strings = {"8-421-674-8974"})
	void GivenTelephoneNumberReturnContact(String input) {
		String result = PhoneBook.FindContact(stringList, input);
		assertEquals("Phone => 8-421-674-8974, Name => Anastasia, Address => Via Quirinal Roma", result);
	}
}

