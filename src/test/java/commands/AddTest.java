package commands;

import de.maddin.multitime.commands.Add;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddTest {

    private Add addTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        addTest = new Add();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn(TEST_NAME);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_return_error_message() {

        String[] args = new String[]{"add"};

        boolean result = addTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).sendMessage("\u00A7cMust enter time to add.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_tick_parameter_should_add_time_to_current_world() {

        String[] args = new String[]{"add", valueOf(TEST_TIME)};
        when(worldMock.getTime()).thenReturn(2000L);

        boolean result = addTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getTime();
        verify(worldMock).setTime(2000L + TEST_TIME);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(
                format("Added \u00A7b%s\u00A7f ticks to \u00A7b%s\u00A7f.", TEST_TIME, TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void high_valid_tick_parameter_should_add_time_to_current_world_and_correct_time() {

        String[] args1 = new String[]{"add", "2000", TEST_NAME};
        String[] args2 = new String[]{"add", "40000"};
        String[] args3 = new String[]{"add", "-2000"};
        String[] args4 = new String[]{"add", "-40000"};
        when(worldMock.getTime()).thenReturn(10000L);
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);

        boolean result1 = addTest.run(playerMock, args1);
        boolean result2 = addTest.run(playerMock, args2);
        boolean result3 = addTest.run(playerMock, args3);
        boolean result4 = addTest.run(playerMock, args4);
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();

        verify(playerMock, times(3)).getWorld();
        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock, times(4)).getTime();
        verify(worldMock).setTime(12000L);
        verify(worldMock).setTime(2000L);
        verify(worldMock).setTime(8000L);
        verify(worldMock).setTime(18000L);
        verify(worldMock, times(4)).getName();
        verify(playerMock).sendMessage(
                format("Added \u00A7b%s\u00A7f ticks to \u00A7b%s\u00A7f.", 2000L, TEST_NAME));
        verify(playerMock).sendMessage(
                format("Added \u00A7b%s\u00A7f ticks to \u00A7b%s\u00A7f.", 40000L, TEST_NAME));
        verify(playerMock).sendMessage(
                format("Subtracted \u00A7b%s\u00A7f ticks from \u00A7b%s\u00A7f.", 2000L, TEST_NAME));
        verify(playerMock).sendMessage(
                format("Subtracted \u00A7b%s\u00A7f ticks from \u00A7b%s\u00A7f.", 40000L, TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_time_should_return_error_message() {

        String[] args1 = new String[]{"set", valueOf(123456789123456789L), TEST_NAME};
        String[] args2 = new String[]{"set", "gami"};

        boolean result1 = addTest.run(playerMock, args1);
        boolean result2 = addTest.run(playerMock, args2);
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();

        verify(playerMock).sendMessage(format("\u00A7c'%s' is not a valid time.", 123456789123456789L));
        verify(playerMock).sendMessage(format("\u00A7c'%s' is not a valid time.", "gami"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_add_to_all_worlds() {

        String[] args = new String[]{"add", valueOf(TEST_TIME), "all"};

        boolean result = addTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getTime();
        verify(worldMock, times(2)).setTime(TEST_TIME);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2)).sendMessage(
                format("Added \u00A7b%s\u00A7f ticks to \u00A7b%s\u00A7f.", TEST_TIME, TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
