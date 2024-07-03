package poutou.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import poutou.Poutou;


@Command(name = "AddCustomer", description = "Get customer information")
public class AddCustomerCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "CUSTOMER_ID", description = "customer ID")
  private int customerId;

  @Parameters(index = "1", paramLabel = "CUSTOMER_NAME", description = "customer name")
  private String customerName;

  @Parameters(index = "2", paramLabel = "CUSTOMER_CITY", description = "customer city")
  private String customerCity;

  @Parameters(index = "3", paramLabel = "CUSTOMER_NUMBER_TRANSACTIONS", description = "customer number transactions")
  private int customerNumberTransactions;

  @Override
  public Integer call() throws Exception {
    try (Poutou poutou = new Poutou()) {
      poutou.addCustomer(customerId, customerName, customerCity, customerNumberTransactions);
      System.out.println("Customer added successfully");
      System.out.println(poutou.getCustomerInfo(customerId));
    }
    return 0;
  }
}