package twitter;

import org.junit.Test;
import resources.GeeksResource;
import services.GeekCommander;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: xavierhanin
 * Date: 3/23/13
 * Time: 2:31 PM
 */
public class GeekticTwitterStreamTest {
    private GeeksResource geeksResource = new GeeksResource();
    private GeekCommander commander = new GeekCommander(geeksResource);
    private GeekticTwitterStream stream = new GeekticTwitterStream(commander);

    @Test
    public void should_add_geek_on_status_sent() throws Exception {
        int size = geeksResource.json("test").size();
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setName("John Doe").setStatus("#likes test"));
        assertThat(geeksResource.json("test")).hasSize(size + 1);
    }

    @Test
    public void should_add__several_geeks_on_status_sent() throws Exception {
        int size = geeksResource.json("test").size();
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setName("John Doe").setStatus("#likes test"));
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setName("John Black").setStatus("#likes java test"));
        assertThat(geeksResource.json("test")).hasSize(size + 2);
    }

    @Test
    public void should_update_geek_on_status_sent() throws Exception {
        int sizeTest = geeksResource.json("test").size();
        int sizeJava = geeksResource.json("java").size();
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setScreenName("johndoe").setName("John Doe").setStatus("#likes test"));
        assertThat(geeksResource.json("test")).hasSize(sizeTest + 1);
        assertThat(geeksResource.json("java")).hasSize(sizeJava);
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setScreenName("johndoe").setName("John Doe").setStatus("#likes java test"));
        assertThat(geeksResource.json("test")).hasSize(sizeTest + 1);
        assertThat(geeksResource.json("java")).hasSize(sizeJava + 1);
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setScreenName("johndoe").setName("John Doe").setStatus("#likes java"));
        assertThat(geeksResource.json("test")).hasSize(sizeTest);
        assertThat(geeksResource.json("java")).hasSize(sizeJava + 1);
    }
}
