package commands;

import de.maddin.commands.Set;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static main.TestUtils.TEST_NAME;
import static main.TestUtils.TEST_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetTest {

    private Set setTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        setTest = new Set();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getTime()).thenReturn(TEST_TIME);
        when(worldMock.getName()).thenReturn(TEST_NAME);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_return_error_message() {

        String[] args = new String[]{"set"};

        boolean result = setTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).sendMessage("\u00A7cMust enter time to set.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_parameter_should_set_current_world_time() {

        String[] args = new String[]{"set", valueOf(TEST_TIME)};

        boolean result = setTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).setTime(TEST_TIME);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_string_parameter_should_set_time() {

        String[] args1 = new String[]{"set", "day"};
        String[] args2 = new String[]{"set", "noon"};
        String[] args3 = new String[]{"set", "night"};
        String[] args4 = new String[]{"set", "midnight"};

        boolean result1 = setTest.run(playerMock, args1);
        boolean result2 = setTest.run(playerMock, args2);
        boolean result3 = setTest.run(playerMock, args3);
        boolean result4 = setTest.run(playerMock, args4);
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();

        verify(playerMock, times(4)).getWorld();
        verify(worldMock).setTime(1000);
        verify(worldMock).setTime(6000);
        verify(worldMock).setTime(13000);
        verify(worldMock).setTime(18000);
        verify(worldMock, times(4)).getName();
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, 1000));
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, 6000));
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, 13000));
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, 18000));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_and_world_parameter_should_set_its_world_time() {

        String[] args = new String[]{"set", valueOf(TEST_TIME), TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);

        boolean result = setTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).setTime(TEST_TIME);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_time_should_return_error_message() {

        String[] args1 = new String[]{"set", valueOf(123456789123456789L), TEST_NAME};
        String[] args2 = new String[]{"set", "gami"};

        boolean result1 = setTest.run(playerMock, args1);
        boolean result2 = setTest.run(playerMock, args2);
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();

        verify(playerMock).sendMessage(format("\u00A7c'%s' is not a valid time.", 123456789123456789L));
        verify(playerMock).sendMessage(format("\u00A7c'%s' is not a valid time.", "gami"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_should_return_error_message() {

        String[] args = new String[]{"set", valueOf(TEST_TIME), TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(null);

        boolean result = setTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(playerMock).sendMessage(format("\u00A7cWorld '%s' doesn't exist!", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_set_all_worlds() {

        String[] args = new String[]{"set", valueOf(TEST_TIME), "all"};

        boolean result = setTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).setTime(TEST_TIME);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2)).sendMessage(
                format("Set time in \u00A7b%s\u00A7f to \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}