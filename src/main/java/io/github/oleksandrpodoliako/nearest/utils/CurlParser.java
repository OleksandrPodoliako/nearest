package io.github.oleksandrpodoliako.nearest.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurlParser {

    private static final String REQUEST_OPTION = "request";
    private static final String HEADER_OPTION = "header";
    private static final String DATA_RAW_OPTION = "data-raw";

    public String parseApiMethod(String curl) {
        String optionValue = parseToMap(curl).get(REQUEST_OPTION).get(0);
        return optionValue.substring(0, optionValue.indexOf(" "));
    }

    public String parseUrl(String curl) {
        String optionValue = parseToMap(curl).get(REQUEST_OPTION).get(0);
        int startIndex = optionValue.indexOf("'") + 1;
        int endIndex = optionValue.indexOf("?");
        if (endIndex != -1) {
            return optionValue.substring(startIndex, endIndex);
        }
        return optionValue.substring(startIndex, optionValue.lastIndexOf("'"));
    }

    public Map<String, String> parseQueryParameters(String curl) {
        String optionValue = parseToMap(curl).get(REQUEST_OPTION).get(0);
        Map<String, String> queryParameters = new HashMap<>();
        int startIndex = optionValue.indexOf("?") + 1;
        int endIndex = optionValue.lastIndexOf("'");

        for (String queryParameter : optionValue.substring(startIndex, endIndex).split("&")) {
            if (queryParameter.contains("=")) {
                queryParameters.put(queryParameter.split("=")[0], queryParameter.split("=")[1]);
            }
        }

        return queryParameters;
    }

    public Map<String, String> parseHeaders(String curl) {
        List<String> optionValues = parseToMap(curl).get(HEADER_OPTION);
        Map<String, String> headers = new HashMap<>();

        if (optionValues == null) {
            return headers;
        }

        for (String fullHeader : optionValues) {
            fullHeader = fullHeader.substring(fullHeader.indexOf("'") + 1, fullHeader.lastIndexOf("'"));
            if (fullHeader.contains(":")) {
                headers.put(fullHeader.split(":")[0], fullHeader.split(":")[1]);
            }
        }

        return headers;
    }

    public String parseBody(String curl) {
        if (parseToMap(curl).get(DATA_RAW_OPTION) == null) {
            return "";
        }

        String optionValue = parseToMap(curl).get(DATA_RAW_OPTION).get(0);
        return optionValue.substring(optionValue.indexOf("'") + 1, optionValue.lastIndexOf("'"));
    }

    private Map<String, List<String>> parseToMap(String curl) {
        Map<String, List<String>> curlMap = new HashMap<>();
        curl = normalizeCurl(curl);

        char[] chars = curl.toCharArray();

        int lastCharIndex = chars.length;
        int spaceCharIndex = chars.length - 1;
        int secondHyphenIndex = chars.length - 1;


        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                spaceCharIndex = i;
                continue;
            }

            if (chars[i] == '-' && i == secondHyphenIndex - 1) {
                String key = curl.substring(secondHyphenIndex + 1, spaceCharIndex);
                String value = curl.substring(spaceCharIndex + 1, lastCharIndex);

                if (!curlMap.containsKey(key)) {
                    List<String> optionValues = new ArrayList<>();
                    optionValues.add(value);
                    curlMap.put(key, optionValues);
                } else {
                    curlMap.get(key).add(value);
                }


                lastCharIndex = i;
                continue;
            }

            if (chars[i] == '-') {
                secondHyphenIndex = i;
            }

        }

        return curlMap;
    }

    private String normalizeCurl(String curl) {
        curl = curl.replaceAll("\n", "");
        curl = curl.replaceAll("\r", "");
        return curl.replaceAll("\r\n", "");
    }
}
