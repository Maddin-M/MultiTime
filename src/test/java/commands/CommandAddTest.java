package commands;

import de.maddin.multitime.commands.CommandAdd;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandAddTest {

    private CommandAdd commandAddTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandAddTest = new CommandAdd();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn("test_world");
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_return_error_message() {

        String[] args = new String[]{"add"};

        boolean result = commandAddTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).sendMessage("\u00A7cMust enter time to add.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_tick_parameter_should_add_time_to_current_world() {

        String[] args = new String[]{"add", "1234"};

        when(worldMock.getTime()).thenReturn(2000L);

        boolean result = commandAddTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getTime();
        verify(worldMock).setTime(2000L + 1234);
        verify(worldMock).getName();
        verify(playerMock).sendMessage("Added \u00A7b1,234 \u00A7fticks to \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void high_valid_tick_parameter_should_add_time_to_current_world_and_correct_time() {

        String[] args1 = new String[]{"add", "2000", "test_world"};
        String[] args2 = new String[]{"add", "40000"};
        String[] args3 = new String[]{"add", "-2000"};
        String[] args4 = new String[]{"add", "-40000"};
        when(worldMock.getTime()).thenReturn(10000L);
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);

        boolean result1 = commandAddTest.run(playerMock, args1);
        boolean result2 = commandAddTest.run(playerMock, args2);
        boolean result3 = commandAddTest.run(playerMock, args3);
        boolean result4 = commandAddTest.run(playerMock, args4);

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).setTime(12000L);
        verify(playerMock, times(3)).getWorld();
        verify(worldMock, times(4)).getTime();
        verify(worldMock, times(4)).getName();
        verify(worldMock).setTime(2000L);
        verify(worldMock).setTime(8000L);
        verify(worldMock).setTime(18000L);
        verify(playerMock).sendMessage("Added \u00A7b2,000 \u00A7fticks to \u00A7btest_world\u00A7f.");
        verify(playerMock).sendMessage("Added \u00A7b40,000 \u00A7fticks to \u00A7btest_world\u00A7f.");
        verify(playerMock).sendMessage("Subtracted \u00A7b2,000 \u00A7fticks from \u00A7btest_world\u00A7f.");
        verify(playerMock).sendMessage("Subtracted \u00A7b40,000 \u00A7fticks from \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_time_should_return_error_message() {

        String[] args1 = new String[]{"set", valueOf(123456789123456789L), "test_world"};
        String[] args2 = new String[]{"set", "gami"};

        boolean result1 = commandAddTest.run(playerMock, args1);
        boolean result2 = commandAddTest.run(playerMock, args2);
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();

        verify(playerMock).sendMessage("\u00A74123456789123456789 \u00A7cis not a valid time.");
        verify(playerMock).sendMessage("\u00A74gami \u00A7cis not a valid time.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_add_to_all_worlds() {

        String[] args = new String[]{"add", "1234", "all"};

        boolean result = commandAddTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getTime();
        verify(worldMock, times(2)).setTime(1234);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2))
                .sendMessage("Added \u00A7b1,234 \u00A7fticks to \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
