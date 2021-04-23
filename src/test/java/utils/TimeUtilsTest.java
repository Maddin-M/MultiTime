package utils;

import de.maddin.multitime.Constants;
import de.maddin.multitime.utils.TimeUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeUtilsTest {

    @Test
    void valid_ticks_should_return_true() {

        boolean[] results = new boolean[4];

        results[0] = TimeUtils.isValidTickAmount("1234");
        results[1] = TimeUtils.isValidTickAmount(String.valueOf(Integer.MAX_VALUE));
        results[2] = TimeUtils.isValidTickAmount(String.valueOf(Integer.MIN_VALUE));
        results[3] = TimeUtils.isValidTickAmount("0");

        for (boolean result : results) {
            assertThat(result).isTrue();
        }
    }

    @Test
    void invalid_ticks_should_return_false() {

        boolean[] results = new boolean[7];

        results[0] = TimeUtils.isValidTickAmount("abc");
        results[1] = TimeUtils.isValidTickAmount(String.valueOf(Long.MAX_VALUE));
        results[2] = TimeUtils.isValidTickAmount(String.valueOf(Long.MIN_VALUE));
        results[3] = TimeUtils.isValidTickAmount(".");
        results[4] = TimeUtils.isValidTickAmount("-");
        results[5] = TimeUtils.isValidTickAmount("123.0");
        results[6] = TimeUtils.isValidTickAmount("123a");

        for (boolean result : results) {
            assertThat(result).isFalse();
        }
    }

    @Test
    void should_convert_to_real_ticks() {

        int result1 = TimeUtils.convertToRealTicks(0);
        assertThat(result1).isZero();

        int result2 = TimeUtils.convertToRealTicks(-0);
        assertThat(result2).isZero();

        int result3 = TimeUtils.convertToRealTicks(2000);
        assertThat(result3).isEqualTo(2000);

        int result4 = TimeUtils.convertToRealTicks(40000);
        assertThat(result4).isEqualTo(16000);

        int result5 = TimeUtils.convertToRealTicks(-2000);
        assertThat(result5).isEqualTo(22000);

        int result6 = TimeUtils.convertToRealTicks(-40000);
        assertThat(result6).isEqualTo(8000);
    }

    @Test
    void empty_arg_should_return_every_possible_time_preset() {

        List<String> result = TimeUtils.getAllTimePresetsAsStringsStartingWith("");

        assertThat(result.size()).isEqualTo(Constants.TimePresets.values().length);
        assertThat(result.containsAll(List.of("day", "noon", "night", "midnight"))).isTrue();
    }

    @Test
    void partial_arg_should_return_possible_time_presets() {

        List<String> result1 = TimeUtils.getAllTimePresetsAsStringsStartingWith("n");
        List<String> result2 = TimeUtils.getAllTimePresetsAsStringsStartingWith("dA");

        assertThat(result1.size()).isEqualTo(2);
        assertThat(result2.size()).isEqualTo(1);
        assertThat(result1.containsAll(List.of("noon", "night"))).isTrue();
        assertThat(result2.contains("day")).isTrue();
    }

    @Test
    void full_arg_should_return_its_time_preset() {

        List<String> result = TimeUtils.getAllTimePresetsAsStringsStartingWith("midNight");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains("midnight")).isTrue();
    }
}
