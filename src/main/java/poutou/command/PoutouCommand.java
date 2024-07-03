package poutou.command;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(
    name = "bin/poutou",
    description = "poutou's application for Microservice Transaction",
    subcommands = {
      LoadInitialDataCommand.class,
      GetCustomerInfoCommand.class,
    })
public class PoutouCommand implements Runnable {

  @Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Displays this help message and quits.",
      defaultValue = "true")
  private Boolean showHelp;

  @Override
  public void run() {
    if (showHelp) {
      CommandLine.usage(this, System.out);
    }
  }

  public static void main(String[] args) {
    new CommandLine(new PoutouCommand()).execute(args);
  }
}