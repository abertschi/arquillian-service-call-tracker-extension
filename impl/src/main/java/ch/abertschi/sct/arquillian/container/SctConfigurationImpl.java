package ch.abertschi.sct.arquillian.container;

import java.net.URL;

import ch.abertschi.sct.api.SctConfiguration;

/**
 * @author Andrin Bertschi
 */
public class SctConfigurationImpl implements SctConfiguration
{

    private boolean isCallRecording;

    private boolean isResponseLoading;

    private URL callRecordingUrl;

    private URL responseLoadingUrl;

    public SctConfigurationImpl()
    {
        this.isCallRecording = false;
        this.isResponseLoading = false;
    }

    @Override
    public boolean isCallRecording()
    {
        return isCallRecording;
    }

    @Override
    public boolean isResponseLoading()
    {
        return isResponseLoading;
    }

    @Override
    public URL getCallRecordingUrl()
    {
        return callRecordingUrl;
    }

    @Override
    public URL getResponseLoadingUrl()
    {
        return responseLoadingUrl;
    }

    public void setCallRecording(boolean isCallRecording)
    {
        this.isCallRecording = isCallRecording;
    }

    public void setResponseLoading(boolean isResponseLoading)
    {
        this.isResponseLoading = isResponseLoading;
    }

    public void setCallRecordingUrl(URL callRecordingUrl)
    {
        this.callRecordingUrl = callRecordingUrl;
    }

    public void setResponseLoadingUrl(URL responseLoadingUrl)
    {
        this.responseLoadingUrl = responseLoadingUrl;
    }

    @Override
    public String toString()
    {
        return "SctConfigurationImpl [isCallRecording=" + isCallRecording + ", isResponseLoading="
                + isResponseLoading + ", callRecordingUrl=" + callRecordingUrl
                + ", responseLoadingUrl=" + responseLoadingUrl + "]";
    }
}
