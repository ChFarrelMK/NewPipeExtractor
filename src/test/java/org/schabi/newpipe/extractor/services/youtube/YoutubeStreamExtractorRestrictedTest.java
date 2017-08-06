package org.schabi.newpipe.extractor.services.youtube;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.VideoStream;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.schabi.newpipe.extractor.ServiceList.Youtube;

/**
 * Test for {@link YoutubeStreamUrlIdHandler}
 */
public class YoutubeStreamExtractorRestrictedTest {
    public static final String HTTPS = "https://";
    private StreamExtractor extractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = Youtube.getService()
                .getStreamExtractor("https://www.youtube.com/watch?v=i6JTvzrpBy0");
    }

    @Test
    public void testGetInvalidTimeStamp() throws ParsingException {
        assertTrue(Integer.toString(extractor.getTimeStamp()),
                extractor.getTimeStamp() <= 0);
    }

    @Test
    public void testGetValidTimeStamp() throws IOException, ExtractionException {
        StreamExtractor extractor= Youtube.getService()
                .getStreamExtractor("https://youtu.be/FmG385_uUys?t=174");
        assertTrue(Integer.toString(extractor.getTimeStamp()),
                extractor.getTimeStamp() == 174);
    }

    @Test
    public void testGetAgeLimit() throws ParsingException {
        assertTrue(extractor.getAgeLimit() == 18);
    }

    @Test
    public void testGetTitle() throws ParsingException {
        assertTrue(!extractor.getTitle().isEmpty());
    }

    @Test
    public void testGetDescription() throws ParsingException {
        assertTrue(extractor.getDescription() != null);
    }

    @Test
    public void testGetUploader() throws ParsingException {
        assertTrue(!extractor.getUploader().isEmpty());
    }

    @Test
    public void testGetLength() throws ParsingException {
        assertTrue(extractor.getLength() > 0);
    }

    @Test
    public void testGetViews() throws ParsingException {
        assertTrue(extractor.getLength() > 0);
    }

    @Test
    public void testGetUploadDate() throws ParsingException {
        assertTrue(extractor.getUploadDate().length() > 0);
    }

    @Test
    public void testGetThumbnailUrl() throws ParsingException {
        assertTrue(extractor.getThumbnailUrl(),
                extractor.getThumbnailUrl().contains(HTTPS));
    }

    @Test
    public void testGetUploaderThumbnailUrl() throws ParsingException {
        assertTrue(extractor.getUploaderThumbnailUrl(),
                extractor.getUploaderThumbnailUrl().contains(HTTPS));
    }

    @Test
    public void testGetAudioStreams() throws IOException, ExtractionException {
        // audiostream not always necessary
        assertTrue(!extractor.getAudioStreams().isEmpty());
    }

    @Test
    public void testGetVideoStreams() throws IOException, ExtractionException {
        for(VideoStream s : extractor.getVideoStreams()) {
            assertTrue(s.url,
                    s.url.contains(HTTPS));
            assertTrue(s.resolution.length() > 0);
            assertTrue(Integer.toString(s.format),
                    0 <= s.format && s.format <= 4);
        }
    }
}