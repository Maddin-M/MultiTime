package commands;

import de.maddin.multitime.commands.CommandSet;
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
class CommandSetTest {

    private CommandSet commandSetTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandSetTest = new CommandSet();
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

        String[] args = new String[]{"set"};

        boolean result = commandSetTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).sendMessage("\u00A7cMust enter time to set.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_parameter_should_set_current_world_time() {

        String[] args1 = new String[]{"set", "1000"};
        String[] args2 = new String[]{"set", "26000"};
        String[] args3 = new String[]{"set", "-1000"};
        String[] args4 = new String[]{"set", "-26000"};

        boolean result1 = commandSetTest.run(playerMock, args1);
        boolean result2 = commandSetTest.run(playerMock, args2);
        boolean result3 = commandSetTest.run(playerMock, args3);
        boolean result4 = commandSetTest.run(playerMock, args4);
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();

        verify(playerMock, times(4)).getWorld();
        verify(worldMock, times(4)).getName();
        verify(worldMock).setTime(1000L);
        verify(worldMock).setTime(2000L);
        verify(worldMock).setTime(23000L);
        verify(worldMock).setTime(22000L);
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b1,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b2,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b23,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b22,000 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_string_parameter_should_set_time() {

        String[] args1 = new String[]{"set", "day"};
        String[] args2 = new String[]{"set", "noon"};
        String[] args3 = new String[]{"set", "night"};
        String[] args4 = new String[]{"set", "midnight"};

        boolean result1 = commandSetTest.run(playerMock, args1);
        boolean result2 = commandSetTest.run(playerMock, args2);
        boolean result3 = commandSetTest.run(playerMock, args3);
        boolean result4 = commandSetTest.run(playerMock, args4);
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();

        verify(playerMock, times(4)).getWorld();
        verify(worldMock, times(4)).getName();
        verify(worldMock).setTime(1000);
        verify(worldMock).setTime(6000);
        verify(worldMock).setTime(13000);
        verify(worldMock).setTime(18000);
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b1,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b6,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b13,000 \u00A7fticks.");
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b18,000 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_time_and_world_parameter_should_set_its_world_time() {

        String[] args = new String[]{"set", "1234", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);

        boolean result = commandSetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).setTime(1234);
        verify(worldMock).getName();
        verify(playerMock).sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b1,234 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_time_should_return_error_message() {

        String[] args1 = new String[]{"set", "123456789123456789", "test_world"};
        String[] args2 = new String[]{"set", "gami"};

        boolean result1 = commandSetTest.run(playerMock, args1);
        boolean result2 = commandSetTest.run(playerMock, args2);
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();

        verify(playerMock).sendMessage("\u00A74123456789123456789 \u00A7cis not a valid time.");
        verify(playerMock).sendMessage("\u00A74gami \u00A7cis not a valid time.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_should_return_error_message() {

        String[] args = new String[]{"set", "1234", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(null);

        boolean result = commandSetTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(playerMock).sendMessage("\u00A7cWorld \u00A74test_world \u00A7cdoesn't exist.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_set_all_worlds() {

        String[] args = new String[]{"set", "1234", "all"};

        boolean result = commandSetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).setTime(1234);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2))
                .sendMessage("Set time in \u00A7btest_world \u00A7fto \u00A7b1,234 \u00A7fticks.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
