package commands;

import de.maddin.commands.Get;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static java.lang.String.format;
import static main.TestUtils.TEST_NAME;
import static main.TestUtils.TEST_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetTest {

    private Get getTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        getTest = new Get();
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
    void no_parameter_should_return_current_world_time() {

        String[] args = new String[]{"get"};

        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getTime();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock).sendMessage(
                format("The time in \u00A7b%s\u00A7f is \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_world_parameter_should_give_its_world_time() {

        String[] args = new String[]{"get", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);

        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).getTime();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock).sendMessage(
                format("The time in \u00A7b%s\u00A7f is \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_parameter_should_give_error_message() {

        String[] args = new String[]{"get", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(null);

        boolean result = getTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(playerMock).sendMessage(format("\u00A7cWorld '%s' doesn't exist!", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_give_multiple_world_times() {

        String[] args = new String[]{"get", "all"};

        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getTime();
        verify(worldMock, times(2)).getName();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock, times(2)).sendMessage(
                format("The time in \u00A7b%s\u00A7f is \u00A7b%d\u00A7f ticks.", TEST_NAME, TEST_TIME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
