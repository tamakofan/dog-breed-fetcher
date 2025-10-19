package dogapi;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws RuntimeException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("dog.ceo")
                .addPathSegments("api/breed")
                .addPathSegment(breed)
                .addPathSegment("list")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String data = response.body().string();

            JSONObject jsonObject = new JSONObject(data);
            String status = jsonObject.getString("status");

            if (status.equals("error")) {
                throw new BreedNotFoundException(breed);
            }
            else {
                JSONArray messageArray = jsonObject.getJSONArray("message");
                List<String> subbreeds = new ArrayList<>();
                for (int j = 0; j < messageArray.length(); j++) {
                subbreeds.add(messageArray.getString(j));}
                return subbreeds;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}