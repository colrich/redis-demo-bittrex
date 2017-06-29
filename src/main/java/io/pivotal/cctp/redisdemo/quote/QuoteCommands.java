
package io.pivotal.cctp.redisdemo.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import io.pivotal.cctp.bittrex.Bittrex;

@Component
public class QuoteCommands {
    
    private static final Logger log = Logger.getLogger(QuoteCommands.class);

    private static final String BITTREX_API_KEY = "REPLACE_ME";
    private static final String BITTREX_SECRET_KEY = "REPLACE_ME";

    Bittrex bittrex;

    public QuoteCommands() {
        bittrex = new Bittrex(BITTREX_API_KEY, BITTREX_SECRET_KEY, Bittrex.DEFAULT_RETRY_ATTEMPTS, Bittrex.DEFAULT_RETRY_DELAY);
    }

    public List<HashMap<String, String>> getMaps(String rawResponse) {
        return Bittrex.getMapsFromResponse(rawResponse);
    }

    public String fetchMarketSummary(String market) {
        log.error("fetching market summary for market: " + market);
        String response = bittrex.getMarketSummary(market);
        System.out.println("resp: " + response);
        Map<String, String> summary = Bittrex.getMapsFromResponse(response).get(0);
        log.error("market summary method actually ran, last price was: " + summary.get("Last"));
        return response;
    }

}