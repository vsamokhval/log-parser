package org.vss;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum HystrixGroupNames {
    PRODUCT_GROUP("hystrix-productGroup"),
    INVENTORY_GROUP("hystrix-inventoryGroup"),
    WSP_GROUP("hystrix-wspGroup"),
    SERVICE_MESSAGE_GROUP("hystrix-serviceMessageGroup"),
    TAX_GROUP("hystrix-taxGroup"),
    DELIVERY_SERVICE_GROUP("hystrix-deliveryServiceGroup"),
    PAYMENT_GROUP("hystrix-paymentGroup"),
    METRIX_GROUP("hystrix-MetrixGroup");

    private final String name;

    public static final List<String> GROUP_NAMES_WO_ET_GROUP =
        Collections.unmodifiableList(Arrays.asList(
            PRODUCT_GROUP.getName(),
            INVENTORY_GROUP.getName(),
            WSP_GROUP.getName(),
            TAX_GROUP.getName(),
            DELIVERY_SERVICE_GROUP.getName(),
            PAYMENT_GROUP.getName(),
            METRIX_GROUP.getName()
        ));

    HystrixGroupNames(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
