package org.vss;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum HystrixGroupNames {
    PRODUCT_SERVICE_GROUP("hystrix-productServiceGroup"),
    INVENTORY_SERVICE_GROUP("hystrix-inventoryServiceGroup"),
    CTFS_GROUP("hystrix-ctfsGroup"),
    ET_GROUP("hystrix-exactTargetGroup"),
    TAXONOMY_GROUP("hystrix-taxonomyGroup"),
    PNA_GROUP("hystrix-pnaGroup"),
    DELIVERY_SERVICE_GROUP("hystrix-deliveryServiceGroup"),
    FITMENT_GROUP("hystrix-fitmentGroup"),
    PAYMENT_PLAN_GROUP("hystrix-paymentPlanGroup"),
    THREAT_METRIX_GROUP("hystrix-threatMetrixGroup");

    private final String name;

    public static final List<String> GROUP_NAMES_WO_ET_GROUP =
        Collections.unmodifiableList(Arrays.asList(
            PRODUCT_SERVICE_GROUP.getName(),
            INVENTORY_SERVICE_GROUP.getName(),
            CTFS_GROUP.getName(),
            TAXONOMY_GROUP.getName(),
            PNA_GROUP.getName(),
            DELIVERY_SERVICE_GROUP.getName(),
            FITMENT_GROUP.getName(),
            PAYMENT_PLAN_GROUP.getName(),
            THREAT_METRIX_GROUP.getName()
        ));

    HystrixGroupNames(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
