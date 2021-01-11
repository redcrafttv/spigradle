package eu.brickpics.staffsys.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing dates.
 */
public class DateUtil {

    /**
     * Regex matching an amount of some sort of time unit e.g. 4d see <a
     * href="https://regex101.com/r/ErO2oC/1/">Regex101</a> for mor info
     *
     * This regex has to groups the amount and the unit
     */
    public static final Pattern REGEX = Pattern.compile("(\\d)([yMwdhms])");

    /**
     * Converts a String formatted like 1M2d4m to an LocalDateTime containing the date in the future with the specified string added.
     *
     * @param input the string to parse
     * @return an {@link LocalDateTime} containing the amount of converted milliseconds or {@code null} if
     * the input was not formatted correctly
     * @throws NumberFormatException when the amount group of any input is not a valid number
     */
    public static LocalDateTime parseDate(String input) {
        List<MatchResult> allResults = findAll(input);
        if (allResults.isEmpty()) {
            return null;
        }
        LocalDateTime time = LocalDateTime.now();
        for(MatchResult matchResult : allResults) {
            int amount = Integer.parseInt(matchResult.group(1));
            switch (matchResult.group(2)) {
                case "y":
                    time = time.plus(amount, ChronoUnit.YEARS);
                    break;
                case "M":
                    time = time.plus(amount, ChronoUnit.MONTHS);
                    break;
                case "w":
                    time = time.plus(amount, ChronoUnit.WEEKS);
                    break;
                case "d":
                    time = time.plus(amount, ChronoUnit.DAYS);
                    break;
                case "h":
                    time = time.plus(amount, ChronoUnit.HOURS);
                    break;
                case "m":
                    time = time.plus(amount, ChronoUnit.MINUTES);
                    break;
                case "s":
                    time = time.plus(amount, ChronoUnit.SECONDS);
                    break;
            }
        }
        return time;
    }

    private static List<MatchResult> findAll(String input) {
        List<MatchResult> results = new ArrayList<>();
        Matcher matcher = DateUtil.REGEX.matcher(input);
        for (int i = 0; matcher.find(i); ) {
            MatchResult result = matcher.toMatchResult();
            i = result.end();
            results.add(result);
        }
        return Collections.unmodifiableList(results);
    }
}