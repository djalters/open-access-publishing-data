/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS_main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.hibernate.SessionFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author walte
 */
public class Main
{

    private static SessionFactory factory;

    /**
     * @param args the command line arguments
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException
    {

        String currentStringPath = Paths.get("").toAbsolutePath().toString();
        //Initialise a run object, so that references to directories and files are available
        Run run = new Run(currentStringPath);

        // This will always run first, getting the use case to run as integer numbers from the array
        int[] useCaseArgs = new int[args.length];

        for (int i : useCaseArgs) {
            try {
                useCaseArgs[i] = Integer.parseInt(args[i]);

                run.runUseCase(useCaseArgs[i]);

            }
            catch (NumberFormatException e) {
                System.err.println(
                        "Failed trying to parse a non-numeric argument, " + args[i]);
            }
            catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
