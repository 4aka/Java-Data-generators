package framework;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataGenerators {

    Faker faker = new Faker();

    /**
     *
     * @return first name without apostrophe symbol
     */
    String fakeFirstName() {
        return faker.name().firstName().replace("'", "");
    }

    /**
     *
     * @return last name without apostrophe symbol
     */
    String fakeLastName() {
        return faker.name().lastName().replace("'", "");
    }

    /**
     *
     * @return fake email
     */
    String fakeEmail() {
        return faker.internet().emailAddress();
    }

    /**
     *
     * @param localPart
     * @return local part of email address
     */
    String fakeEmail(String localPart) {
        return faker.internet().safeEmailAddress(localPart);
    }

    /**
     *
     * @return fake full name
     */
    String fakeFullName() {
        return faker.name().fullName();
    }

    /**
     *
     * @return street address
     */
    String fakeStreetAddress() {
        return faker.address().streetAddress();
    }

    /**
     *
     * @return fake city name
     */
    String fakeCity() {
        return faker.address().city();
    }

    /**
     *
     * @return random zip code (!might not be real)
     */
    String fakeZipCode() {
        return faker.numerify("#####");
    }

    /**
     *
     * @return random boolean
     */
    boolean fakeBoolean() {
        return faker.bool().bool();
    }

    /**
     *
     * @return
     */
    String fakeSymbols(int count) {
        return RandomStringUtils.random(count, true, true);
    }

    /**
     *
     * @param lettersCount str length
     * @return random String
     */
    String fakeFixedString(int lettersCount) {
        return faker.lorem().characters(lettersCount);
    }

    /**
     *
     * @return random word
     */
    String fakeRandomWord() {
        return faker.lorem().word();
    }

    /**
     *
     * @return date of birth
     */
    String fakeDateOfBirth() {
        return faker.date().past(15000, TimeUnit.DAYS).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().format(DateTimeFormatter.ofPattern("MM-dd-yyyy'T'HH:mm:ss.SSS[xxx][xx][X]"));
    }

    /**
     *
     * @param from 0
     * @param to MAX Int
     * @return random Int
     */
    int fakeRandomIntFromTo(int from, int to) {
        return faker.number().numberBetween(from, to);
    }

    /**
     *
     * @return fake id in UUID format
     */
    String fakeUuid() {
        return faker.internet().uuid().toUpperCase();
    }

    /**
     *
     * @return Phone or Fax 10 digits
     */
    String fakePhoneFax() {
        return String.valueOf(faker.number().numberBetween(1111111111, 9_999_999_99));
    }

    /**
     *
     * @param count
     * @return
     */
    List<String> fakeEmailsList(int count) {
        if (count == null || count < 1 || count > 150) {
            KeywordUtil.logInfo("ERROR!!!! Wrong count of emails")
            return
        }

        List<String> emails = new ArrayList<>()
        count.times { emails.add(fakeEmail()) }
        return emails
    }

    /**
     *
     * @param list
     * @return
     */
    static Boolean isListSorted(LinkedList<String> list) {
        Assert.assertTrue(list.size() >= 2)
        LinkedList<String> list1 = list // Add additional list to do the sorting action with this list (not to change the original)
        Collections.sort(list1)
        return list.equals(list1)

    }

    /**
     *
     * @param list
     * @return
     */
    static Boolean isListSortedDESC(LinkedList<String> list) {
        Assert.assertTrue(list.size() >= 2)
        LinkedList<String> list1 = list // Add additional list to do the sorting action with this list (not to change the original)
        Collections.sort(list1, Collections.reverseOrder())
        return list.equals(list1)
    }

    /**
     *
     * @param list
     * @return
     */
    static <T> T getRandomElementFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()))
    }

    /**
     *
     * @param date
     * @return
     */
    static String extractDateFromISO(String date) {
        return date[0..9]
    }

    /**
     *
     * @param date1
     * @return
     */
    static Boolean isDateEqualsToCurrentDate(String date) {
        return extractDateFromISO(date).equals(extractDateFromISO(buildCurrentDate()))
    }

    /**
     *
     * @return
     */
    static String buildCurrentDateWithoutISO() {
        return extractDateFromISO(buildCurrentDate())
    }

    /**
     *
     * @return
     */
    static String buildCurrentDate() {
        return convertLocalDateTimeToString(LocalDateTime.now(), Common.ISO_FORMATTER)
    }

    /**
     *
     * @param date
     * @return
     */
    static String buildStartOfTheDate(String date) {
        DateTime dateTime = DateTime.parse(date)
        return convertDateTimeToString(dateTime.withTime(0, 0, 0, 0), Common.ISO_FORMATTER)
    }

    /**
     *
     * @param date
     * @return
     */
    static String buildEndOfTheDate(String date) {
        DateTime dateTime = DateTime.parse(date)
        return convertDateTimeToString(dateTime.withTime(23, 59, 59, 999), Common.ISO_FORMATTER)
    }

    /**
     *
     * @return
     */
    static String buildStartOfTheDate() {
        DateTime dateTime = DateTime.parse(buildCurrentDate())
        return convertDateTimeToString(dateTime.withTime(0, 0, 0, 0), Common.ISO_FORMATTER)
    }

    /**
     *
     * @return
     */
    static String buildEndOfTheDate() {
        DateTime dateTime = DateTime.parse(buildCurrentDate())
        return convertDateTimeToString(dateTime.withTime(23, 59, 59, 999), Common.ISO_FORMATTER)
    }

    /**
     *
     * @param days
     * @return
     */
    static String buildPreviousDate(int days) {
        return convertLocalDateTimeToString(LocalDateTime.now().minusDays(days).withTime(12, 0, 0, 0), Common.ISO_FORMATTER)
    }

    /**
     *
     * @param days
     * @return
     */
    static String buildFutureDate(int days) {
        return convertLocalDateTimeToString(LocalDateTime.now().plusDays(days).withTime(12, 0, 0, 0), Common.ISO_FORMATTER)
    }

    /**
     *
     * @param value
     * @return
     */
    static<T> String capitalizeAll(T value) {
        List<String> list = value.toString().replace("_", " ").split(" ")
        list.each{it.capitalize()}
        String str = ""
        for (int i; i < list.size(); i++) {
            str = str + list.get(i) + " "
        }
        return str.substring(0, str.length() - 1)
    }

    /**
     *
     * @param keys
     * @param values
     * @return
     */
    static Map<String, String> combineTwoListsToMap(List<String> keys, List<String> values) {
        def map = [:]
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys[i], values[i])
        }
        return map
    }

    static String buildStringForSqlList(List<String> list) {
        String result = ""

        for(el in list) {
            if (list.last() != el) result += "'${el}', "
            else result += "'${el}'"
        }
        return result
    }

    /**
     * Get current date & time. Don't CHANGE date\time format.
     */
    public static String getCurrentTime() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy__HH.mm.ss");
        long timeMilli = System.currentTimeMillis();
        return format.format(new Date(timeMilli));
    }

    /**
     * return current Month name - January, February ...
     */
    public String getCurrentMonthName() {
        DateFormat format = new SimpleDateFormat("MMMM", Locale.US);
        long timeMilli = System.currentTimeMillis();
        return format.format(new Date(timeMilli));
    }

    /**
     * return current month number - 01, 02 ...
     */
    public String getCurrentMonthNumber() {
        DateFormat format = new SimpleDateFormat("MM");
        long timeMilli = System.currentTimeMillis();
        return format.format(new Date(timeMilli));
    }

    /**
     * return current year - 2020
     */
    public String getCurrentYear() {
        DateFormat format = new SimpleDateFormat("yyyy");
        long timeMilli = System.currentTimeMillis();
        return format.format(new Date(timeMilli));
    }
}
