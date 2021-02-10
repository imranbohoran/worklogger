package com.tib.worklogger.clients.cli;

import com.beust.jcommander.JCommander;

public class TextFileImporter {

    public static void main(String[] args) {
        CLIArgParser workLogImporterArgs = new CLIArgParser();
        JCommander.newBuilder()
            .addObject(workLogImporterArgs)
            .build()
            .parse(args);
        System.out.println("Importing work log items from :" + workLogImporterArgs.fileName);

        System.out.println("Still not implemented!!!");
    }
}
