package io.temporal.demo.versioning.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.demo.versioning.activities.CustomerActivities;
import io.temporal.demo.versioning.model.Account;
import io.temporal.demo.versioning.model.Customer;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class CustomerWorkflowImpl implements CustomerWorkflow {
    private final CustomerActivities customerActivities =
            Workflow.newActivityStub(
                    CustomerActivities.class,
                    ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofMinutes(6))
                            .build());

    private int bonus = 100;

    @Override
    public Account execute(Customer customer) {

        Account account = customerActivities.getCustomerAccount(customer);

        Workflow.sleep(customer.getDemoWaitDuration());

        // STEP 1
//        int version = Workflow.getVersion("addedCheck", Workflow.DEFAULT_VERSION, 1);
//        if (version == 1) {
//            boolean checked = customerActivities.checkCustomerAccount(customer);
//            if(!checked) {
//                return null;
//            }
//            Workflow.sleep(customer.getDemoWaitDuration());
//        }
        // END STEP 1


        // STEP 2
//        int version2 = Workflow.getVersion("addedBonus", Workflow.DEFAULT_VERSION, 1);
//        if(version2 == 1) {
//            bonus = 200;
//        }

        account = customerActivities.updateCustomerAccount(account, bonus, "Added bonus of: " + bonus);

        // STEP 2
//        int version3 = Workflow.getVersion("addedBonus", Workflow.DEFAULT_VERSION, 1);
//        if(version3 == 1) {
//            customerActivities.sendBonusEmail(customer, "You received a bonus!");
//        }

        return account;

    }
}
