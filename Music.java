import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Music {
    File f;
    URL url;
    URI uri;
    AudioClip music ;
    public Music(){
        try {
            f = new File("music/古琴白无瑕,古琴唐彬 - 天行九歌 古琴版.wav");
            uri = f.toURI();
            url = uri.toURL();
            music = Applet.newAudioClip(url);
            this.loopM();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void loopM(){
        music.loop();
    }
}
