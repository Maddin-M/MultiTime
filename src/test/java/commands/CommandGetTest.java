package commands;

import de.maddin.multitime.commands.CommandGet;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandGetTest {

    private CommandGet commandGetTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandGetTest = new CommandGet();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn("test_world");
        when(worldMock.getTime()).thenReturn(1234L);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_return_current_world_time() {

        String[] args = new String[]{"get"};

        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getTime();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock).sendMessage("The time in \u00A7btest_world \u00A7fis \u00A7b1,234 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void locked_world_should_display_it() {

        String[] args = new String[]{"get"};
        when(worldMock.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).thenReturn(false);

        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(worldMock).getTime();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock).sendMessage("The time in \u00A7btest_world \u00A7fis \u00A7b1,234 \u00A7fticks. " +
                "\u00A7eTime is locked in this world.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_world_parameter_should_give_its_world_time() {

        String[] args = new String[]{"get", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);

        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).getTime();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock).sendMessage("The time in \u00A7btest_world \u00A7fis \u00A7b1,234 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_parameter_should_give_error_message() {

        String[] args = new String[]{"get", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(null);

        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(playerMock).sendMessage("\u00A7cWorld \u00A74test_world \u00A7cdoesn't exist.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_give_multiple_world_times() {

        String[] args = new String[]{"get", "all"};

        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getTime();
        verify(worldMock, times(2)).getName();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        verify(playerMock, times(2))
                .sendMessage("The time in \u00A7btest_world \u00A7fis \u00A7b1,234 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
