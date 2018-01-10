/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
 * @author walte
 */
public class SherpaRefParser
{

    private Boolean state = true;

    private List<SherpaRef> SherpaRefs = new ArrayList<SherpaRef>();

    private String[] entry;

    public SherpaRefParser()
    {

    }

    /**
     *
     * @param doi
     * @throws java.text.ParseException
     */
    public SherpaRefParser(String[] entry)
    {
        this.entry = entry;

    }

    public void callApi() throws ParseException
    {

        String aUrl;

        String result = "";

        Integer finalKey = 0;

        if ((entry[1] != null) || (entry[0] != null)) { //an ISSN or an EISSN
            if (entry[1] != null) {
                aUrl = "https://ref.sherpa.ac.uk/id/journal/" + entry[1].trim();
            }
            else {
                aUrl = "https://ref.sherpa.ac.uk/id/journal/" + entry[0].trim();
            }

            result = this.getInputStreamResult(aUrl);
            Integer key1 = 0;
            Integer key2 = 0;

            if ((entry[1] != null) && (!entry[1].isEmpty())) {

                if ((entry[0] != null) && (!entry[0].isEmpty())) {
                    key1 = this.issnToNumber(entry[1]);
                    key2 = this.issnToNumber(entry[0]);
                    if (key1 < key2) {
                        finalKey = this.issnToNumber(entry[1].concat(
                                entry[0]));
                    }
                    else {
                        finalKey = this.issnToNumber(entry[0].concat(
                                entry[1]));
                    }
                }
                else {
                    finalKey = this.issnToNumber(entry[1]);
                }
            }
            else if ((entry[0] != null) && (!entry[0].isEmpty())) {
                finalKey = this.issnToNumber(entry[0]);
            }

            if (result.length() > 0) {

                //first just getting data rows for publisher policy options.
                try { //0 is eissn 1 is issn
                    JSONObject resultFirstParse = (JSONObject) JSONValue.parseWithException(
                            result);

                    JSONArray issnArray = (JSONArray) resultFirstParse.get(
                            "issns");

                    String title = (String) resultFirstParse.get("title");

                    JSONObject publisherObj = (JSONObject) resultFirstParse.get(
                            "publisher");
                    String publisher = (String) publisherObj.get("name");

                    String SR_output_type = "publisher_policy_permitted_action"; //classify for 1 type first, can extend to panels ab cd
                    JSONObject publisherPolicyObj = (JSONObject) resultFirstParse.get(
                            "publisher_policy");

                    String information_urls = "";
                    if (publisherPolicyObj.containsKey("information_urls")) {
                        JSONArray urls_array = (JSONArray) publisherPolicyObj.get(
                                "information_urls");
                        for (int h = 0; h < urls_array.size(); h++) {
                            String urlEntry = (String) urls_array.get(h);
                            information_urls = information_urls.concat(urlEntry);
                        }
                    }
                    Date date_last_revised = null;
                    if (publisherPolicyObj.containsKey("date_last_revised")) {
                        DateFormat df1 = new SimpleDateFormat(
                                "yyyy-MMM-dd HH:mm:ss");
                        String date_last_revised_str = (String) publisherPolicyObj.get(
                                "date_last_revised");
                        date_last_revised = df1.parse(date_last_revised_str);
                    }
                    String no_deposit_within_three_months = (String) publisherPolicyObj.get(
                            "no_deposit_within_three_months");
                    JSONArray permitted_actions_array = (JSONArray) publisherPolicyObj.get(
                            "permitted_actions");
                    for (int i = 0; i < permitted_actions_array.size(); i++) {
                        JSONObject obj = (JSONObject) permitted_actions_array.get(
                                i);
                        String open_access_route = (String) obj.get(
                                "open_access_route");
                        Integer embargo_in_months = (Integer) obj.get(
                                "embargo_in_months");
                        JSONArray article_version_array = (JSONArray) obj.get(
                                "article_version");
                        JSONArray repo_type_array = (JSONArray) obj.get(
                                "repository_type");
                        for (int j = 0; j < article_version_array.size(); j++) {
                            String article_version = (String) article_version_array.get(
                                    j);
                            for (int k = 0; k < repo_type_array.size(); k++) {
                                String repo_type = (String) repo_type_array.get(
                                        k);
                                String repo_type_other_txt = null;
                                if (repo_type.equals("Other")) {
                                    repo_type_other_txt = (String) obj.get(
                                            "repository_type_other_text");
                                }
                                SherpaRefId srefid = new SherpaRefId(finalKey,
                                        open_access_route, article_version,
                                        repo_type);
                                SherpaRef sref = new SherpaRef(srefid, entry[0],
                                        entry[1], title, publisher,
                                        date_last_revised, SR_output_type,
                                        repo_type_other_txt, embargo_in_months,
                                        no_deposit_within_three_months,
                                        information_urls);
                                this.addSRefObj(sref);
                            }
                        }
                    }
                }
                catch (ParseException ex) {
                    //            ex.printStackTrace();
                    this.setState(false);

                }
                catch (java.text.ParseException ex) {
                    this.setState(false);
                    ex.printStackTrace();
                }
            }
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

    public Boolean getState()
    {
        return state;
    }

    private void setState(Boolean state)
    {
        this.state = state;
    }

    public int issnToNumber(String issn)
    {
        StringBuilder number = new StringBuilder();
        String returnStr = "0";
        Boolean NoGreaterThanZero = false;
        if (issn.length() > 0) {

            String trim_a = issn.trim();

            for (int i = 0; i < trim_a.length(); i++) {
                char c = trim_a.charAt(i);

                if (Character.isDigit(c)) {

                    int cde = Character.getNumericValue(c);
                    if (cde > 0) {
                        number = number.append(c);
                        NoGreaterThanZero = true;
                    }
                    else {
                        if (NoGreaterThanZero) {
                            number = number.append(c);
                        }
                    }
                }
            }
            returnStr = number.toString();
            if (returnStr.length() > 9) { 
                returnStr = returnStr.substring(0, 8);
            }
        }


        return Integer.parseInt(returnStr);
    }

    public List<SherpaRef> getSherpaRefs()
    {
        return SherpaRefs;
    }

    public void addSRefObj(SherpaRef SherpaRef)
    {
        this.SherpaRefs.add(SherpaRef);
    }

    public SherpaRef getObjAtIndex(Integer index)
    {
        
        return this.getSherpaRefs().get(index);

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
