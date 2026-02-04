package org.example;

import org.example.lexer.EtaLexer;
import org.example.lexer.Token;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String outputDir = ".";
        boolean lexMode = false;
        String inputFile = null;

        // Parse arguments
        int i = 0;
        while (i < args.length) {
            String arg = args[i];

            if (arg.equals("--help")) {
                printHelp();
                return;
            }
            else if (arg.equals("--lex")) {
                lexMode = true;
                i++;
            }
            else if (arg.equals("-D")) {
                if (i + 1 >= args.length) {
                    System.err.println("Error: -D requires a path argument");
                    System.exit(1);
                }
                outputDir = args[i + 1];
                i += 2;
            }
            else {
                inputFile = arg;
                i++;
            }
        }

        if (args.length == 0) {
            printHelp();
            return;
        }

        if (lexMode) {
            if (inputFile == null) {
                System.err.println("Error: --lex requires an input file");
                System.exit(1);
            }

            lexFile(inputFile, outputDir);
            return;
        }

        System.err.println("Unknown option. Use --help for usage information.");
        System.exit(1);
    }

    private static void lexFile(String inputFile, String outputDir) {
        try {
            // Read input file
            FileReader reader = new FileReader(inputFile);
            EtaLexer lexer = new EtaLexer(reader);

            // Generate output filename
            String filename = new File(inputFile).getName();
            String outputFilename = filename.replace(".eta", ".lexed");
            String outputPath = outputDir + File.separator + outputFilename;

            // Create output directory if needed
            new File(outputDir).mkdirs();

            // Write tokens to output file
            PrintWriter writer = new PrintWriter(new FileWriter(outputPath));

            Token token;
            while ((token = lexer.yylex()) != null) {
                writer.println(token);
            }

            writer.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + inputFile);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printHelp() {
        System.out.println("Usage: etac [options] <source files>");
        System.out.println("Options:");
        System.out.println("  --help          Print this help message");
        System.out.println("  --lex           Generate output from lexical analysis");
        System.out.println("  -D <path>       Specify where to place generated diagnostic files");
    }
}