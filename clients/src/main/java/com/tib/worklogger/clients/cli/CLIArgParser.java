package com.tib.worklogger.clients.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

public class CLIArgParser {
    @Parameter
    public List<String> parameters = Lists.newArrayList();

    @Parameter(names = { "--file", "-f" }, required = true, description = "The name of the file with work log items to import")
    public String fileName;

}
