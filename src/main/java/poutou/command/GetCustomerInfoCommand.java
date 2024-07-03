package poutou.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import poutou.Poutou;

@Command(name = "GetCustomerInfo", description = "Get customer information")
public class GetCustomerInfoCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "CUSTOMER_ID", description = "customer ID")
  private int customerId;

  @Override
  public Integer call() throws Exception {
    try (Poutou poutou = new Poutou()) {
      System.out.println(poutou.getCustomerInfo(customerId));
    }
    return 0;
  }
}
