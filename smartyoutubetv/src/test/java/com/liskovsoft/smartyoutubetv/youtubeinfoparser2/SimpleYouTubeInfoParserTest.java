package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.TestHelpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SimpleYouTubeInfoParserTest {
    private InputStream mYouTubeVideoInfo;
    private InputStream mYouTubeVideoInfoCiphered;
    private final static String sVideo = "https://r1---sn-4gxb5u-qo3s.googlevideo" + "" +
            ".com/videoplayback?itag=137&clen=238313161&key=yt6&ipbits=0&initcwndbps=2827500&keepalive=yes&dur=605.433&ei=ktyIWc3fOdjrddjziZAP" +
            "&pcm2cms=yes&gir=yes&mt=1502141501&lmt=1502028605932130&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag" +
            "%2Ckeepalive%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpcm2cms%2Cpl%2Crequiressl%2Csource%2Cexpire&mv=m&signature" +
            "=DA2563BC19D0D81235FD450E4AF43FFA6CE84716.8F2CD08915FAD1A54CE0FB3CE83ABBCA7B022BFE&ms=au&id=o-AFEGaouCLpUXwcdQz" +
            "-S8ywonKslAFmkDIXB3kbbPi18i&expire=1502163187&mime=video%2Fmp4&ip=46.98.75.93&requiressl=yes&pl=16&mn=sn-4gxb5u-qo3s&mm=31&source" +
            "=youtube";
    private final static String sAudio = "https://r1---sn-4gxb5u-qo3s.googlevideo" +
            ".com/videoplayback?itag=140&clen=9617577&key=yt6&ipbits=0&initcwndbps=2827500&keepalive=yes&dur=605.506&ei=ktyIWc3fOdjrddjziZAP" +
            "&pcm2cms=yes&gir=yes&mt=1502141501&lmt=1502028596840916&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag" +
            "%2Ckeepalive%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpcm2cms%2Cpl%2Crequiressl%2Csource%2Cexpire&mv=m&signature" +
            "=801EE6B5D7D5EE28C0FFA35A5D1F1916EB3C226C.72D7E1A145D1D8837F6BFF6F964E41815DB4C44C&ms=au&id=o-AFEGaouCLpUXwcdQz" +
            "-S8ywonKslAFmkDIXB3kbbPi18i&expire=1502163187&mime=audio%2Fmp4&ip=46.98.75.93&requiressl=yes&pl=16&mn=sn-4gxb5u-qo3s&mm=31&source" +
            "=youtube";
    private final static String sVideoDeciphered = "https://r3---sn-4gxb5u-qo3s.googlevideo" + "" + "" +
            ".com/videoplayback?sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Ckeepalive%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv"
            + "%2Cpcm2cms%2Cpl%2Crequiressl%2Csource%2Cexpire&gir=yes&mm=31&mn=sn-4gxb5u-qo3s&key=yt6&clen=49431326&ei=SqWIWZXJC4SYNJrOqLgF&ms=au"
            + "&mt" + "=1502127302&pl=16&mv=m&requiressl=yes&lmt=1500318989274570&itag=137&keepalive=yes&ip=46.98.75.93&dur=277.320&pcm2cms=yes" +
            "&source" + "=youtube&id=o-AD0tcX8q3azwHnG4ymA9SxyeTUWpSh6f9gQa1zSD2vmU&mime=video%2Fmp4&expire=1502149034&ipbits=0&initcwndbps=2915000" +
            "" + "&signature" + "=8C85C198F11DA24D5964D412FA4487AA09E27A9C.893CF7AD1F333DF80C481AC9186D768667511E71";

    @Before
    public void setUp() throws Exception {
        mYouTubeVideoInfo = TestHelpers.openResource("get_video_info_deciphered");
        mYouTubeVideoInfoCiphered = TestHelpers.openResource("get_video_info_ciphered");
    }

    @Test
    public void tryToExtractSomeUrls() throws Exception {
        YouTubeInfoParser parser = new SimpleYouTubeInfoParser(mYouTubeVideoInfo);
        Uri url = parser.getUrlByTag(ITag.VIDEO_1080P_AVC);
        assertTrue(url.equals(Uri.parse(sVideo)));
        Uri url2 = parser.getUrlByTag(ITag.AUDIO_128K_AAC);
        assertTrue(url2.equals(Uri.parse(sAudio)));
    }

    @Test
    public void tryToExtractCipheredUrl() {
        YouTubeInfoParser parser = new SimpleYouTubeInfoParser(mYouTubeVideoInfoCiphered);
        Uri url = parser.getUrlByTag(ITag.VIDEO_1080P_AVC);
        assertTrue(url.equals(Uri.parse(sVideoDeciphered)));
    }

    @Test
    public void decipherTest() {
        String originSig = "98C85C188F11DA24D5964D412FA4487AE09127A9C.893CF7AD1FA33DF80C481AC9186D768667511E7E7A73";
        String newSig = "8C85C198F11DA24D5964D412FA4487AA09E27A9C.893CF7AD1F333DF80C481AC9186D768667511E71";
        assertEquals(newSig, CipherUtils.decipherSignature(originSig));
    }

}