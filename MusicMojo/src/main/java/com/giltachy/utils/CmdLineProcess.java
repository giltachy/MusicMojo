package com.giltachy.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Main example demonstrating Apache Commons CLI.  Apache Commons CLI and more
 * details on it are available at http://commons.apache.org/cli/.
 * 
 * @author Dustin
 */
public class CmdLineProcess
{
   private static Options options = new Options();

   /**
    * Apply Apache Commons CLI PosixParser to command-line arguments.
    * 
    * @param commandLineArguments Command-line arguments to be processed with
    *    Posix-style parser.
    */
   public static void usePosixParser(final String[] commandLineArguments)
   {
      final CommandLineParser cmdLinePosixParser = new PosixParser();
      final Options posixOptions = constructPosixOptions();
      CommandLine commandLine;
      try
      {
         commandLine = cmdLinePosixParser.parse(posixOptions, commandLineArguments);
         if ( commandLine.hasOption("display") )
         {
            System.out.println("You want a display!");
         }
      }
      catch (ParseException parseException)  // checked exception
      {
         System.err.println(
              "Encountered exception while parsing using PosixParser:\n"
            + parseException.getMessage() );
      }
   }

   /**
    * Apply Apache Commons CLI GnuParser to command-line arguments.
    * 
    * @param commandLineArguments Command-line arguments to be processed with
    *    Gnu-style parser.
    */
   public static void useGnuParser(final String[] commandLineArguments)
   {
      final CommandLineParser cmdLineGnuParser = new GnuParser();

      final Options gnuOptions = constructGnuOptions();
      CommandLine commandLine;
      try
      {
         commandLine = cmdLineGnuParser.parse(gnuOptions, commandLineArguments);
         if ( commandLine.hasOption("p") )
         {
            System.out.println("You want to print (p chosen)!");
         }
         if ( commandLine.hasOption("print") )
         {
            System.out.println("You want to print (print chosen)!");
         }
         if ( commandLine.hasOption('g') )
         {
            System.out.println("You want a GUI!");
         }
         if ( commandLine.hasOption("n") )
         {
            System.out.println(
               "You selected the number " + commandLine.getOptionValue("n"));
         }
      }
      catch (ParseException parseException)  // checked exception
      {
         System.err.println(
              "Encountered exception while parsing using GnuParser:\n"
            + parseException.getMessage() );
      }
   }

   /**
    * Construct and provide Posix-compatible Options.
    * 
    * @return Options expected from command-line of Posix form.
    */
   public static Options constructPosixOptions()
   {
      final Options posixOptions = new Options();
      posixOptions.addOption("display", false, "Display the state.");
      return posixOptions;
   }

   /**
    * Construct and provide GNU-compatible Options.
    * 
    * @return Options expected from command-line of GNU form.
    */
   public static Options constructGnuOptions()
   {
      final Options gnuOptions = new Options();
      gnuOptions.addOption("m", "mode", true, "Mode of processing the files")
                .addOption("", "gui", false, "HMI option")
                .addOption("n", true, "Number of copies");
      return gnuOptions;
   }

   /**
    * Display command-line arguments without processing them in any further way.
    * 
    * @param commandLineArguments Command-line arguments to be displayed.
    */
   public static void displayProvidedCommandLineArguments(
      final String[] commandLineArguments,
      final OutputStream out)
   {
      final StringBuffer buffer = new StringBuffer();
      for ( final String argument : commandLineArguments )
      {
         buffer.append(argument).append(" ");
      }
      try
      {
         out.write((buffer.toString() + "\n").getBytes());
      }
      catch (IOException ioEx)
      {
         System.err.println(
            "WARNING: Exception encountered trying to write to OutputStream:\n"
            + ioEx.getMessage() );
         System.out.println(buffer.toString());
      }
   }

   /**
    * Display example application header.
    * 
    * @out OutputStream to which header should be written.
    */
   public static void displayHeader(final OutputStream out)
   {
      final String header =
           "[Apache Commons CLI Example from Dustin's Software Development "
         + "Cogitations and Speculations Blog]\n";
      try
      {
         out.write(header.getBytes());
      }
      catch (IOException ioEx)
      {
         System.out.println(header);
      }
   }

   /**
    * Write the provided number of blank lines to the provided OutputStream.
    * 
    * @param numberBlankLines Number of blank lines to write.
    * @param out OutputStream to which to write the blank lines.
    */
   public static void displayBlankLines(
      final int numberBlankLines,
      final OutputStream out)
   {
      try
      {
         for (int i=0; i<numberBlankLines; ++i)
         {
            out.write("\n".getBytes());
         }
      }
      catch (IOException ioEx)
      {
         for (int i=0; i<numberBlankLines; ++i)
         {
            System.out.println();
         }
      }
   }

   /**
    * Print usage information to provided OutputStream.
    * 
    * @param applicationName Name of application to list in usage.
    * @param options Command-line options to be part of usage.
    * @param out OutputStream to which to write the usage information.
    */
   public static void printUsage(
      final String applicationName,
      final Options options,
      final OutputStream out)
   {
      final PrintWriter writer = new PrintWriter(out);
      final HelpFormatter usageFormatter = new HelpFormatter();
      usageFormatter.printUsage(writer, 80, applicationName, options);
      writer.flush();
   }

   /**
    * Write "help" to the provided OutputStream.
    */
   public static void printHelp(
      final Options options,
      final int printedRowWidth,
      final String header,
      final String footer,
      final int spacesBeforeOption,
      final int spacesBeforeOptionDescription,
      final boolean displayUsage,
      final OutputStream out)
   {
      final String commandLineSyntax = "java -cp ApacheCommonsCLI.jar";
      final PrintWriter writer = new PrintWriter(out);
      final HelpFormatter helpFormatter = new HelpFormatter();
      helpFormatter.printHelp(
         writer,
         printedRowWidth,
         commandLineSyntax,
         header,
         options,
         spacesBeforeOption,
         spacesBeforeOptionDescription,
         footer,
         displayUsage);
      writer.flush();
   }

   /**
    * Main executable method used to demonstrate Apache Commons CLI.
    * 
    * @param commandLineArguments Commmand-line arguments.
    */
   public static void main(final String[] commandLineArguments)
   {
      final String applicationName = "MainCliExample";
      displayBlankLines(1, System.out);
      displayHeader(System.out);
      displayBlankLines(2, System.out);
      if (commandLineArguments.length < 1)
      {
         System.out.println("-- USAGE --");
         printUsage(applicationName + " (Posix)", constructPosixOptions(), System.out);
         displayBlankLines(1, System.out);
         printUsage(applicationName + " (Gnu)", constructGnuOptions(), System.out);

         displayBlankLines(4, System.out);

         System.out.println("-- HELP --");
         printHelp(
            constructPosixOptions(), 80, "POSIX HELP", "End of POSIX Help",
               3, 5, true, System.out);
         displayBlankLines(1, System.out);
         printHelp(
            constructGnuOptions(), 80, "GNU HELP", "End of GNU Help",
               5, 3, true, System.out);
      }
      displayProvidedCommandLineArguments(commandLineArguments, System.out);
      usePosixParser(commandLineArguments);
      //useGnuParser(commandLineArguments);
   }
}