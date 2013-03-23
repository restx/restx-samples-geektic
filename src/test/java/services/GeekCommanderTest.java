package services;

import org.junit.Test;
import resources.SearchResource;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: xavierhanin
 * Date: 3/23/13
 * Time: 2:27 PM
 */
public class GeekCommanderTest {

    private GeekCommander commander = new GeekCommander(new SearchResource());

    @Test
    public void should_parse_command() throws Exception {
        GeekCommander.GeekCommand command = commander.parse("Xavier Hanin", "http://img.io/me.jpg",  "#geektic #likes test");
        assertThat(command).isNotNull();
        assertThat(command.getGeek()).isNotNull();
        assertThat(command.getGeek().prenom).isEqualTo("Xavier");
        assertThat(command.getGeek().nom).isEqualTo("Hanin");
        assertThat(command.getGeek().like(0)).isEqualTo("test");
    }

    @Test
    public void should_parse_command_with_multiple_likes() throws Exception {
        GeekCommander.GeekCommand command = commander.parse("Xavier Hanin", "http://img.io/me.jpg", "#geektic #likes java test");
        assertThat(command).isNotNull();
        assertThat(command.getGeek()).isNotNull();
        assertThat(command.getGeek().prenom).isEqualTo("Xavier");
        assertThat(command.getGeek().nom).isEqualTo("Hanin");
        assertThat(command.getGeek().like(0)).isEqualTo("java");
        assertThat(command.getGeek().like(1)).isEqualTo("test");
        assertThat(command.getGeek().pictureUrl).isEqualTo("http://img.io/me.jpg");
    }
}
