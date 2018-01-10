/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONValue;

/**
 *
 * @author walte reworked for V2 of the API (released October 2017)
 */
public class OaDoiParser
{

    private Boolean state = true;

    //private OaDoiLocation oaDoiObject = null; //list of OaDoiLocation objects
    //private List<OaDoiLocation> OaDois = null;
    private List<OaDoiLocation> OaDois = new ArrayList<OaDoiLocation>();
    private Integer objectCount = 0;

    //global variables that cover the publication irrespective of numbers of oa journal locations
    private Byte glob_isOa;
    private Byte glob_journal_is_oa;
    private String glob_journal_name;
    private String glob_journal_publisher;

    public OaDoiParser()
    {

    }

    /**
     *
     * @param doi
     * @throws java.text.ParseException
     */
    public OaDoiParser(String doi) throws java.text.ParseException
    {

        try {
            //TimeUnit.MILLISECONDS.sleep(10);

            //first check API Status object, actually no point here - no point checking status every single time...
            URL aUrl = new URL(
                    "https://api.oadoi.org/v2/" + doi + "?email=david.walters@brunel.ac.uk"); // URL to Parse

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    aUrl.openStream()));

            String inputLine;
            String jsonOutput = "";

            //read results of returned page to a string
            while ((inputLine = in.readLine()) != null) {
                jsonOutput = jsonOutput.concat(inputLine);
            }

            JSONObject jsonFirstParse = (JSONObject) JSONValue.parseWithException(
                    jsonOutput);

            //first get global variables if available:
            if (jsonFirstParse.containsKey("is_oa")) {
                Boolean result_a = (Boolean) jsonFirstParse.get("is_oa");
                setGlob_isOa(returnByte(result_a));

                if (result_a) { //we can only record IF it's OA, due to recording the IDs etc...
                    if (jsonFirstParse.containsKey("journal_is_oa")) {
                        Boolean result_b = (Boolean) jsonFirstParse.get("journal_is_oa");
                        setGlob_journal_is_oa(returnByte(result_b));

                    }
                    if (jsonFirstParse.containsKey("journal_name")) {
                        String result_c = (String) jsonFirstParse.get("journal_name");
                        setGlob_journal_name(result_c);
                        
                    }
                    if (jsonFirstParse.containsKey("publisher")) {
                        String result_d = (String) jsonFirstParse.get("publisher");
                        setGlob_journal_publisher(result_d);
                    }

                    //NOW ONTO THE ACTUAL BUSINESS OF PARSING!
                    if (jsonFirstParse.containsKey("oa_locations")) {
                        //structure is an array of objects, only expect 1 object per doi
                        JSONArray resultsArray = (JSONArray) jsonFirstParse.get(
                                "oa_locations");
                        if (resultsArray.size() > 0) { //there's data in the array to be capture
                            for (int i = 0; i < resultsArray.size(); i++) {
                                JSONObject resultSetObj = (JSONObject) resultsArray.get(
                                        i); //get
                                OaDoiLocationId id;
                                String idField = (String) resultSetObj.get("id");
                                if (( idField == null )||idField.equals("null")) {
                                    //if theirs no id, then its a publisher article... dois then.
                                    id = new OaDoiLocationId(doi,
                                        doi);
                                } else {
                                    //else it gets the corresponding system id
                                    id = new OaDoiLocationId(doi,
                                        idField);
                                }
                                String version = null;
                                String evidence = null;
                                String host_type = null;
                                Byte is_best = null;
                                String license = null;
                                Date updated = null;
                                String url = null;
                                String url_for_landing_page = null;
                                String url_for_pdf = null;

                                if (resultSetObj.containsKey("evidence")) {
                                    evidence = (String) resultSetObj.get("evidence");
                                }
                                if (resultSetObj.containsKey("host_type")) {
                                    host_type = (String) resultSetObj.get("host_type");
                                }
                                if (resultSetObj.containsKey("is_best")) {
                                    //boolean
                                    Boolean result_e = (Boolean) resultSetObj.get("is_best");
                                    is_best=returnByte(result_e);
                                }
                                if (resultSetObj.containsKey("license")) {
                                    license = (String) resultSetObj.get("license");
                                }
                                if (resultSetObj.containsKey("updated")) {
                                    //from 2017-10-21T12:34:56.074727 to Date
                                    String result_f = resultSetObj.get("updated").toString();
                                    String datePart = result_f.substring(0, 11).concat(
                                            "12:08:56.235-0700");

                                    DateFormat df1 = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                                    //String string1 = "2001-07-04T12:08:56.235-0700";
                                    updated = df1.parse(datePart); //this should be a legit date then....
                                    //
                                    //DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                                    //String string2 = "2001-07-04T12:08:56.235-07:00";
                                    //Date result2 = df2.parse(string2); 

                                }
                                if (resultSetObj.containsKey("url")) {
                                    url = (String) resultSetObj.get("url");

                                }
                                if (resultSetObj.containsKey("url_for_landing_page")) {
                                    url_for_landing_page = (String) resultSetObj.get(
                                            "url_for_landing_page");

                                }
                                if (resultSetObj.containsKey("url_for_pdf")) {
                                    url_for_pdf = (String) resultSetObj.get("url_for_pdf");

                                }
                                if (resultSetObj.containsKey("version")) {
                                    version = (String) resultSetObj.get("version");
                                }

                                OaDoiLocation newObject = new OaDoiLocation(id,
                                        version,
                                        evidence,
                                        host_type,
                                        is_best,
                                        license,
                                        updated,
                                        url,
                                        url_for_landing_page,
                                        url_for_pdf,
                                        this.getGlob_isOa(),
                                        this.getGlob_journal_is_oa(),
                                        this.getGlob_journal_name(),
                                        this.getGlob_journal_publisher()
                                );

                                this.addOaDoiObj(newObject);
                                this.setObjectCount(this.getObjectCount() + 1);

                            }

                        }
                        else {
                            //set state to false, there's no data
                            setState(false);
                        }
                    }
                } else {
                    setState(false);
                }
            } else {
                setState(false);
            }


        }
        catch (IOException | ParseException e) {
        }

    }

    public Boolean getState()
    {
        return state;
    }

    private void setState(Boolean state)
    {
        this.state = state;
    }

    public Byte getGlob_isOa()
    {
        return glob_isOa;
    }

    public void setGlob_isOa(Byte glob_isOa)
    {
        this.glob_isOa = glob_isOa;
    }

    public Byte getGlob_journal_is_oa()
    {
        return glob_journal_is_oa;
    }

    public void setGlob_journal_is_oa(Byte glob_journal_is_oa)
    {
        this.glob_journal_is_oa = glob_journal_is_oa;
    }

    public String getGlob_journal_name()
    {
        return glob_journal_name;
    }

    public void setGlob_journal_name(String glob_journal_name)
    {
        this.glob_journal_name = glob_journal_name;
    }

    public String getGlob_journal_publisher()
    {
        return glob_journal_publisher;
    }

    public void setGlob_journal_publisher(String glob_journal_publisher)
    {
        this.glob_journal_publisher = glob_journal_publisher;
    }

    public List<OaDoiLocation> getOaDois()
    {
        return OaDois;
    }

    public void setOaDois(List<OaDoiLocation> OaDois)
    {
        this.OaDois = OaDois;
    }

    public void addOaDoiObj(OaDoiLocation OaDoiObj)
    {
        this.OaDois.add(OaDoiObj);
    }

    public OaDoiLocation getOaDoiObjAtIndex(Integer index)
    {
        return this.getOaDois().get(index);
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

}
