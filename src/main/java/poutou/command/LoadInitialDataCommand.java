package poutou.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import poutou.Poutou;

@Command(name = "LoadInitialData", description = "Load initial data")
public class LoadInitialDataCommand implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    try (Poutou poutou = new Poutou()) {
      poutou.loadInitialData();
    }
    return 0;
  }
}
