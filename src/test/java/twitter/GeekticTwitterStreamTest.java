package twitter;

import geeks.Geek;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import resources.GeeksResource;
import services.GeekCommander;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GeekticTwitterStreamTest {
    private GeeksResource geeksResource = mock(GeeksResource.class);
    private GeekCommander commander = new GeekCommander(geeksResource);
    private GeekticTwitterStream stream = new GeekticTwitterStream(commander);
    private ArgumentCaptor<Geek> geekCaptor = ArgumentCaptor.forClass(Geek.class);

    @Test
    public void should_add_geek_on_status_sent() throws Exception {
        stream.onStatus(new GeekticTwitterStream.TwitterStatus().setScreenName("johndoe").setName("John Doe").setStatus("#likes test"));
        verify(geeksResource).addGeek(geekCaptor.capture());
        Geek geek = geekCaptor.getValue();
        assertThat(geek.likes).containsExactly("test");
        assertThat(geek.prenom).isEqualTo("John");
        assertThat(geek.nom).isEqualTo("Doe");
        assertThat(geek.twitterAccount).isEqualTo("johndoe");
    }
}
