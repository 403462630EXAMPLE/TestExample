package com.baidao.tracker;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedOutput;

/**
 * Created by Bruce on 12/1/14.
 */
public class ApiFactory {
    private static StatisticsApi statisticsApi;
    private static String domain;
    private static Object lock = new Object();

    public static void setDomain(String domain) {
        synchronized (lock) {
            ApiFactory.domain = domain;
            statisticsApi = null;
        }
    }
    public static StatisticsApi getStatisticsApi() {
        synchronized (lock) {
            if (statisticsApi == null) {
                if (TextUtils.isEmpty(domain)) {
                    return null;
                }
                RestAdapter.Builder builder = new RestAdapter.Builder()
                        .setEndpoint(domain);
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
                builder.setConverter(new MyGsonConverter(new Gson()));

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
                okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
                OkClient okClient = new OkClient(okHttpClient);
                builder.setClient(okClient);

                statisticsApi = builder.build().create(StatisticsApi.class);
            }
            return statisticsApi;
        }
    }

    public static final class  MyGsonConverter extends GsonConverter {
        private final Gson gson;
        private String charset;

        public MyGsonConverter(Gson gson) {
            this(gson, "UTF-8");
        }

        public MyGsonConverter(Gson gson, String charset) {
            super(gson, charset);
            this.gson = gson;
            this.charset = charset;
        }

        @Override
        public TypedOutput toBody(Object object) {
            if (object instanceof String) {
                try {
                    return new JsonTypedOutput(((String)object).getBytes(charset), charset);
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
            } else {
                return super.toBody(object);
            }
        }

        private static class JsonTypedOutput implements TypedOutput {
            private final byte[] jsonBytes;
            private final String mimeType;

            JsonTypedOutput(byte[] jsonBytes, String encode) {
                this.jsonBytes = jsonBytes;
                this.mimeType = "application/json; charset=" + encode;
            }

            @Override public String fileName() {
                return null;
            }

            @Override public String mimeType() {
                return mimeType;
            }

            @Override public long length() {
                return jsonBytes.length;
            }

            @Override public void writeTo(OutputStream out) throws IOException {
                out.write(jsonBytes);
            }
        }
    }
}
