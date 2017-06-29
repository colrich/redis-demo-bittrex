package io.pivotal.cctp.redisdemo.controllers;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import io.pivotal.cctp.redisdemo.quote.QuoteCommands;

@Controller
public class MarketController {

    private static final Logger log = Logger.getLogger(QuoteCommands.class);

    private QuoteCommands commands;

    @Autowired
    FreeMarkerViewResolver resolver;

    public MarketController() {
        commands = new QuoteCommands();
    }


    @Cacheable("getMarketSummary")
    @RequestMapping(value="/summary/{market}", method=RequestMethod.GET) 
    @ResponseBody
    public String getMarketSummary(Map<String, Object> model, @PathVariable String market, HttpServletRequest request) {
        log.info("MARKETCONTROLLER: getMarketSummary called for market: " + market);
        Map<String, String> summary = commands.getMaps(commands.fetchMarketSummary(market)).get(0);
        log.info("MARKETCONTROLLER: getMarketSummary got response map, and is valid: " + (summary != null));
        if (summary != null) {
            log.info("MARKETCONTROLLER: getMarketSummary constructing model object for template");
            model.put("time", new Date());
            for (String key : summary.keySet()) {
                model.put(key, summary.get(key));
            }
            log.info("MARKETCONTROLLER: getMarketSummary model contains keys: " + model.keySet().size());
            log.info("MARKETCONTROLLER: getMarketSummary rendering template");
            String output = renderViewWithModel("summary", model, request);
            if (output != null) {
                log.info("MARKETCONTROLLER: getMarketSummary template rendering succeeded, output size: " + output.length());
                return output;
            }
            else {
                log.info("MARKETCONTROLLER: getMarketSummary template rendering failed, returning error message");
                return "getMarketController method failed to render output for market: " + market;
            }
        }
        return "getMarketController method failed to retrieve summary from Bittrex for market: " + market;
    }


    private String renderViewWithModel(String viewName, Map<String, Object> model, HttpServletRequest request) {
        try {
            View view = resolver.resolveViewName("summary", Locale.US);
            MockHttpServletResponse response = new MockHttpServletResponse();
            view.render(model, request, response);
            return response.getContentAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}