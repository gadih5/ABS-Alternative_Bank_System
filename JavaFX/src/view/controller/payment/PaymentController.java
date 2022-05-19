package view.controller.payment;


import view.controller.customer.CustomerController;

public class PaymentController {
    private CustomerController customerController;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }
}

