package fr.shurisko.module.youtube;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BYoutube {

    private YouTube youTube;
    private String TOKEN = "AIzaSyA2XSnBp7yDJkV23eRFRvScw7YRimp4tSs";

    public BYoutube() throws IOException {
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setApplicationName("VoiceMaster_").build();
    }

    public List<String> searchVideos(String query) {
        ArrayList<String> videoArray = new ArrayList<>();
        try {
            YouTube.Search.List list = youTube.search().list(Arrays.asList("id", "snippet"));

            list.setKey("AIzaSyA2XSnBp7yDJkV23eRFRvScw7YRimp4tSs");
            list.setQ(query);
            list.setType(Collections.singletonList("video"));
            list.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            SearchListResponse searchResponse = list.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult s : searchResultList)
                    videoArray.add("https://www.youtube.com/watch?v=" + s.getId().getVideoId());
            }
        } catch (IOException e) {}
        return videoArray;
    }

    public String searchVideo(String query) {
        try {
            YouTube.Search.List list = youTube.search().list(Arrays.asList("id", "snippet"));

            list.setKey("AIzaSyA2XSnBp7yDJkV23eRFRvScw7YRimp4tSs");
            list.setQ(query);
            list.setType(Collections.singletonList("video"));
            list.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            SearchListResponse searchResponse = list.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult s : searchResultList)
                    return "https://www.youtube.com/watch?v=" + s.getId().getVideoId();
            }
        } catch (IOException e) {}
        return "";
    }
}
