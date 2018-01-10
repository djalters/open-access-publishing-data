/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONValue;

/**
 *
 * @author walte reworked for V2 of the API (released October 2017)
 */
public class CoreParser
{

    private Boolean state = false;
    private List<Core> Cores = new ArrayList<Core>();
    private int objectCount = 0;
    private int extraApiCalls = 0;
    private String[] entry;

    private String repoentry;
    private List<CoreLocation> CoreLocations = new ArrayList<CoreLocation>();

    public CoreParser()
    {

    }

    /**
     *
     * @param doi
     * @throws java.text.ParseException
     */
    public CoreParser(String[] entry)
    {
        this.entry = entry;

    }

    public CoreParser(String repoentry)
    {
        this.repoentry = repoentry;

    }

    public void callApi()
    {

        String aUrl;
        String articleTitle = "";
        String doi = entry[2];
        String resultByDoi = "";
        String resultByTitle = "";
        String status;
        Long totalHits;
        String[] bits;
        HashMap<String, JSONObject> jsonResultsMap = new HashMap<String, JSONObject>();
        HashMap<String, String> doiOrTitlesMap = new HashMap<String, String>();

        try {
            if (entry[2] != null) {

                this.extraApiCalls = extraApiCalls + 1;
                aUrl = "https://core.ac.uk:443/api-v2/articles/search/doi%3A%22"
                        + entry[2] + "%22?page=1&pageSize=10&metadata=true&fulltext=false&citations=false&similar=false&duplicate=true&urls=true&faithfulMetadata=false&apiKey=Cbmsx6hKQzXtYd5egRFkIo7l1Tu4ALij";
                resultByDoi = this.getInputStreamResult(aUrl);
                bits = entry[1].split(
                        "[_+-.,\\-!@#$%^&‘*():;\\\\/|<>\"'’”“\n]");
                for (String a : bits) {
                    if ((a.length() > 0) && (!(a.equals(" ")))) {
                        String trim_a = a.trim(); //no whitespace for quotes
                        articleTitle = articleTitle.concat(trim_a.concat(
                                "\" AND \""));
                    }
                }
                articleTitle = "(\"".concat(articleTitle.substring(0,
                        articleTitle.length() - 7).concat("\")")).trim();

                aUrl = "https://core.ac.uk:443/api-v2/articles/search/title%3A(" + URLEncoder.encode(
                        articleTitle, "UTF-8") + ")?page=1&pageSize=10&metadata=true&fulltext=false&citations=false&similar=false&duplicate=true&urls=true&faithfulMetadata=false&apiKey=Cbmsx6hKQzXtYd5egRFkIo7l1Tu4ALij";
                resultByTitle = this.getInputStreamResult(aUrl);
            }
            else {
                bits = entry[1].split(
                        "[_+-.,\\-!@#$%^&‘*():;\\\\/|<>\"'’”“\n]");
                for (String a : bits) {
                    if ((a.length() > 0) && (!(a.equals(" ")))) {
                        String trim_a = a.trim();
                        articleTitle = articleTitle.concat(trim_a.concat(
                                "\" AND \""));
                    }
                }
                articleTitle = "(\"".concat(articleTitle.substring(0,
                        articleTitle.length() - 7).concat("\")")).trim();
                aUrl = "https://core.ac.uk:443/api-v2/articles/search/title%3A(" + URLEncoder.encode(
                        articleTitle, "UTF-8") + ")?page=1&pageSize=10&metadata=true&fulltext=false&citations=false&similar=false&duplicate=true&urls=true&faithfulMetadata=false&apiKey=Cbmsx6hKQzXtYd5egRFkIo7l1Tu4ALij";
                resultByTitle = this.getInputStreamResult(aUrl);
            }

            if (resultByDoi.length() > 0) {
                JSONObject doiFirstParse = (JSONObject) JSONValue.parseWithException(
                        resultByDoi);
                status = (String) doiFirstParse.get("status");
                totalHits = (Long) doiFirstParse.get("totalHits");
                if ((status.equals("OK")) && (totalHits > 0)) {
                    JSONArray resultsArray = (JSONArray) doiFirstParse.get(
                            "data");
                    for (int i = 0; i < resultsArray.size(); i++) {
                        JSONObject resultSetObj = (JSONObject) resultsArray.get(
                                i);
                        String id = (String) resultSetObj.get("id");
                        jsonResultsMap.put(id, resultSetObj);
                        doiOrTitlesMap.put(id, "doi");
                    }
                }
            }

            if (resultByTitle.length() > 0) {
                JSONObject titleFirstParse = (JSONObject) JSONValue.parseWithException(
                        resultByTitle);
                status = (String) titleFirstParse.get("status");
                totalHits = (Long) titleFirstParse.get("totalHits");
                if ((status.equals("OK")) && (totalHits > 0)) {

                    JSONArray resultsTitleArray = (JSONArray) titleFirstParse.get(
                            "data");
                    for (int i = 0; i < resultsTitleArray.size(); i++) {
                        JSONObject resultSetObj = (JSONObject) resultsTitleArray.get(
                                i);
                        String id = (String) resultSetObj.get("id");
                        String title = (String) resultSetObj.get("title");

                        String test1 = entry[1].replaceAll(
                                "/[^a-zA-Z0-9-&; :.<>\"'()/\\\\|+_@?=,!#$%^*’”“\n]/g",
                                "").trim().toUpperCase();
                        String test2 = title.replaceAll(
                                "/[^a-zA-Z0-9-&; :.<>\"'()/\\\\|+_@?=,!#$%^*’”“\n]/g",
                                "").trim().toUpperCase();
                        if (test1.equals(test2)) {
                            if (!(jsonResultsMap.containsKey(id))) {
                                jsonResultsMap.put(id, resultSetObj);
                                doiOrTitlesMap.put(id, "title");
                            }
                        }
                    }
                }
            }

            for (String key : jsonResultsMap.keySet()) {
                String doiOrTitles = doiOrTitlesMap.get(key);
                JSONObject storedObject = (JSONObject) jsonResultsMap.get(key);
                int coreId = Integer.parseInt(
                        storedObject.get("id").toString());
                JSONArray repositoryArray = (JSONArray) storedObject.get(
                        "repositories");
                JSONObject repoObj = (JSONObject) repositoryArray.get(0);
                int coreRepoId = Integer.parseInt(repoObj.get("id").toString());
                Integer openDoarId = null;
                if (repoObj.containsKey("openDoarId")) {
                    if (repoObj.get("openDoarId").toString() != null) {
                        openDoarId = Integer.parseInt(
                                repoObj.get("openDoarId").toString());
                    }
                }

                String repoName = null;
                if (repoObj.containsKey("name")) {
                    repoName = (String) repoObj.get("name");
                    if (repoName == null) {
                        if (repoObj.containsKey("physicalName")) {
                            repoName = (String) repoObj.get("physicalName");
                        }
                    }
                }
                else if (repoObj.containsKey("physicalName")) {
                    repoName = (String) repoObj.get("physicalName");
                }

                String fullTextUrls = null;
                if (storedObject.containsKey("fullTextUrls")) {
                    JSONArray allLocUrls = (JSONArray) storedObject.get(
                            "fullTextUrls");
                    fullTextUrls = "";
                    for (int l = 0; l < allLocUrls.size(); l++) {
                        String urlEntry = allLocUrls.get(l).toString();
                        fullTextUrls = fullTextUrls.concat(urlEntry).concat(",");
                    }
                }
                String oai = null;
                if (storedObject.containsKey("oai")) {
                    oai = (String) storedObject.get("oai");
                }

                CoreId coreid = new CoreId(coreId, entry[0], coreRepoId);
                Core coreobj = new Core(coreid, openDoarId, repoName,
                        fullTextUrls, oai, doiOrTitles);

                this.addCoreObj(coreobj);
            }
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void callLocationApi()
    {
        String aUrl;

        String result;

        String status;

        try {
            aUrl = "https://core.ac.uk:443/api-v2/repositories/get/"
                    + this.getRepoentry() + "?apiKey=Cbmsx6hKQzXtYd5egRFkIo7l1Tu4ALij";
            result = this.getInputStreamResult(aUrl);

            if (result.length() > 0) {
                JSONObject resultFirstParse = (JSONObject) JSONValue.parseWithException(
                        result);
                status = (String) resultFirstParse.get("status");
                if (status.equals("OK")) {
                    JSONObject resultSetdataObj = (JSONObject) resultFirstParse.get(
                            "data");
                    JSONObject resultSetRepoLocObj = (JSONObject) resultSetdataObj.get(
                            "repositoryLocation");

                    Long idLong = (Long) resultSetdataObj.get("id");
                    Integer id = (int) (long) idLong;
                    String name = (String) resultSetdataObj.get("name");
                    Number latObj = (Number) resultSetRepoLocObj.get("latitude");
                    Double latDbl = latObj.doubleValue();
                    Number lngObj = (Number) resultSetRepoLocObj.get("longitude");
                    Double lngDbl = lngObj.doubleValue();
                    BigDecimal lat = new BigDecimal(latDbl);
                    BigDecimal longd = new BigDecimal(lngDbl);
                    String code = (String) resultSetRepoLocObj.get("countryCode");
                    CoreLocation coreobj = new CoreLocation(id, name, lat,
                            longd, code);
                    this.addCoreLocationObj(coreobj);
                }
            }
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public String getInputStreamResult(String urlString)
    {
        URL aUrl;
        String result = "";
        try {

            int statusCode;
            aUrl = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) aUrl.openConnection();
            statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                this.setState(true);

                BufferedReader in = null;
                in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result = result.concat(inputLine);
                }
                in.close();

            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            return result;
        }

    }

    public String getRepoentry()
    {
        return repoentry;
    }

    public void setRepoentry(String repoentry)
    {
        this.repoentry = repoentry;
    }

    public int getExtraApiCalls()
    {
        return extraApiCalls;
    }

    public Boolean getState()
    {
        return state;
    }

    private void setState(Boolean state)
    {
        this.state = state;
    }

    public List<Core> getCores()
    {
        return Cores;
    }

    public void addCoreObj(Core aCore)
    {
        this.Cores.add(aCore);
    }

    public Core getCoreObjAtIndex(Integer index)
    {
        return this.getCores().get(index);
    }

    public Integer getObjectCount()
    {
        return objectCount;
    }

    public void setObjectCount(Integer objectCount)
    {
        this.objectCount = objectCount;
    }

    public Byte returnByte(Boolean aBool)
    {
        if (aBool) {
            return (byte) 1;
        }
        else {
            return (byte) 0;
        }
    }

    public List<CoreLocation> getCoreLocations()
    {
        return CoreLocations;
    }

    public void setCoreLocations(List<CoreLocation> CoreLocations)
    {
        this.CoreLocations = CoreLocations;
    }

    public CoreLocation getCoreLocationObjAtIndex(Integer index)
    {
        return this.getCoreLocations().get(index);
    }

    public void addCoreLocationObj(CoreLocation aCoreLocation)
    {
        this.CoreLocations.add(aCoreLocation);
    }

}
