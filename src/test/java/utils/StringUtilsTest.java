package utils;

import de.maddin.multitime.Commands;
import de.maddin.multitime.utils.StringUtils;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StringUtilsTest {

    @Test
    void locked_world_should_return_locked_message() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).thenReturn(false);

        String result = StringUtils.getTimeLockedMessage(worldMock);

        assertThat(result).isEqualTo(" \u00A7eTime is locked in this world.");
    }

    @Test
    void unlocked_world_should_not_return_locked_message() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).thenReturn(true);

        String result = StringUtils.getTimeLockedMessage(worldMock);

        assertThat(result).isEmpty();
    }

    @Test
    void null_world_should_not_return_locked_message() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).thenReturn(null);

        String result = StringUtils.getTimeLockedMessage(worldMock);

        assertThat(result).isEmpty();
    }

    @Test
    void empty_arg_should_return_every_possible_command() {

        List<String> result = StringUtils.getAllCommandsAsStringsStartingWith("");

        assertThat(result.size()).isEqualTo(Commands.values().length);
        assertThat(result.containsAll(List.of("set", "get", "lock", "unlock", "help", "add"))).isTrue();
    }

    @Test
    void partial_arg_should_return_possible_commands() {

        List<String> result1 = StringUtils.getAllCommandsAsStringsStartingWith("s");
        List<String> result2 = StringUtils.getAllCommandsAsStringsStartingWith("uNl");

        assertThat(result1.size()).isEqualTo(1);
        assertThat(result2.size()).isEqualTo(1);
        assertThat(result1.contains("set")).isTrue();
        assertThat(result2.contains("unlock")).isTrue();
    }

    @Test
    void full_arg_should_return_its_command() {

        List<String> result = StringUtils.getAllCommandsAsStringsStartingWith("Get");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains("get")).isTrue();
    }

    @Test
    void correct_string_should_return_its_message() {

        String result1 = StringUtils.getMessage("time_get_locked");
        String result2 = StringUtils.getMessage("error_invalid_world", "test_world");

        assertThat(result1).isEqualTo(" \u00A7eTime is locked in this world.");
        assertThat(result2).isEqualTo("\u00A7cWorld \u00A74test_world \u00A7cdoesn't exist.");
    }
}
