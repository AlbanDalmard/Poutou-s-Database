package poutou;

import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;
import java.io.IOException;
import java.util.Optional;

public class Poutou implements AutoCloseable{

    private final DistributedTransactionManager manager;

    public Poutou() throws IOException {
        // Create a transaction manager object
        TransactionFactory factory = TransactionFactory.create("database.properties");
        manager = factory.getTransactionManager();
      }

      public void loadInitialData() throws TransactionException {
        DistributedTransaction transaction = null;
        try {
          transaction = manager.start();
          System.out.println("transaction id: " + transaction.getId());
          loadCustomerIfNotExists(transaction, 0, 1, "Yamada Taro", "Tokyo", 3);
          loadCustomerIfNotExists(transaction, 0, 2, "Suzuki Jiro", "Osaka", 2);
          loadCustomerIfNotExists(transaction, 0, 3, "Tanaka Saburo", "Nagoya", 1);
          loadCustomerIfNotExists(transaction, 1, 1, "Yamada Hanako", "Tokyo", 3);
          loadCustomerIfNotExists(transaction, 1, 2, "Suzuki Sachiko", "Osaka", 2);
          loadCustomerIfNotExists(transaction, 1, 3, "Tanaka Yuriko", "Nagoya", 1);

          transaction.commit();
        } catch (TransactionException e) {
          if (transaction != null) {
            // If an error occurs, abort the transaction
            transaction.abort();
          }
          throw e;
        }
      }

      private void loadCustomerIfNotExists(
        DistributedTransaction transaction,
        int numDatabase,
        int customerId,
        String name,
        String city,
        int numberTransactions)
        throws TransactionException {
            if (numDatabase == 0) {
                System.out.println("Loading customer info to customers1 table: customer_id=" + customerId + ", name=" + name + ", city=" + city + ", number_transactions=" + numberTransactions);
                Optional<Result> customer =
                    transaction.get(
                        Get.newBuilder()
                            .namespace("customer")
                            .table("customers1")
                            .partitionKey(Key.ofInt("customer_id", customerId))
                            .build());
                if (!customer.isPresent()) {
                    System.out.println("Customer not found in customers1 table");
                    transaction.put(
                        Put.newBuilder()
                            .namespace("customer")
                            .table("customers1")
                            .partitionKey(Key.ofInt("customer_id", customerId))
                            .textValue("name", name)
                            .textValue("city", city)
                            .intValue("number_transactions", numberTransactions)
                            .build());
                    System.out.println("Customer info loaded to customers1 table");
                }
            } else {
                Optional<Result> customer =
                    transaction.get(
                        Get.newBuilder()
                            .namespace("customer")
                            .table("customers2")
                            .partitionKey(Key.ofInt("customer_id", customerId))
                            .build());
                if (!customer.isPresent()) {
                    transaction.put(
                        Put.newBuilder()
                            .namespace("customer")
                            .table("customers2")
                            .partitionKey(Key.ofInt("customer_id", customerId))
                            .textValue("name", name)
                            .textValue("city", city)
                            .intValue("number_transactions", numberTransactions)
                            .build());
                }
            }
    }

    public void addCustomer(int customerId, String name, String city, int numberTransactions) throws TransactionException {
      DistributedTransaction transaction = null;
      try {
        transaction = manager.start();
        System.out.println("transaction id: " + transaction.getId());
        loadCustomerIfNotExists(transaction, 0, customerId, name, city, numberTransactions);
        transaction.commit();
        System.out.println("Customer added successfully");
      } catch (TransactionException e) {
        if (transaction != null) {
          transaction.abort();
        }
        throw e;
      }
    }

    public String getCustomerInfo(int customerId) throws TransactionException {
      DistributedTransaction transaction = null;
      try {
        // Start a transaction
        transaction = manager.start();
  
        // Retrieve the customer info for the specified customer ID from the customers table
        Optional<Result> customer =
            transaction.get(
                Get.newBuilder()
                    .namespace("customer")
                    .table("customers1")
                    .partitionKey(Key.ofInt("customer_id", customerId))
                    .build());
        
        if (!customer.isPresent()) {
          // If the customer info the specified customer ID doesn't exist, try to retrieve it from the other table
          customer =
              transaction.get(
                  Get.newBuilder()
                      .namespace("customer")
                      .table("customers2")
                      .partitionKey(Key.ofInt("customer_id", customerId))
                      .build());
        }

        if (!customer.isPresent()) {
          // If the customer info the specified customer ID doesn't exist, throw an exception
          throw new RuntimeException("Customer not found");
        }
  
        // Commit the transaction (even when the transaction is read-only, we need to commit)
        transaction.commit();
  
        // Return the customer info as a JSON format
        return String.format(
            "{\"id\": %d, \"name\": \"%s\", \"city\": %d, \"number_transactions\": %d}",
            customerId,
            customer.get().getText("name"),
            customer.get().getText("city"),
            customer.get().getInt("number_transactions"));
      } catch (Exception e) {
        if (transaction != null) {
          // If an error occurs, abort the transaction
          transaction.abort();
        }
        throw e;
      }
    }


    @Override
    public void close() {
      manager.close();
    }
}