/**
 * Created by 王一疆 in 2018/7/29
 *
 * @email: lincvic@yahoo.com
 * @Description:
 */
public class Config {
    //认证地址

    private final String AUTH_HOST = "https://westus.api.cognitive.microsoft.com/sts/v1.0/issueToken";

    //CustomVoice Endpoint

    private final String URL = "https://westus.voice.speech.microsoft.com/cognitiveservices/v1?deploymentId=000000000";

    //Azure speech SUB Key

    private final String SUB_KEY = "000000000000";

    //返回的音频格式

    private final String ENCODE_TYPE = "audio-16khz-32kbitrate-mono-mp3";

    //GUID

    private final String GUID = "b336002c-9326-11e8-84af-1a00e900ee01";

    public Config() {
    }

    public String getAUTH_HOST() {
        return AUTH_HOST;
    }

    public String getURL() {
        return URL;
    }

    public String getSUB_KEY() {
        return SUB_KEY;
    }

    public String getENCODE_TYPE() {
        return ENCODE_TYPE;
    }

    public String getGUID() {
        return GUID;
    }
}
