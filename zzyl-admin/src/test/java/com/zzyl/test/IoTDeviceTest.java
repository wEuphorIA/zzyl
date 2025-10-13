package com.zzyl.test;

import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsRequest;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsResponse;
import com.huaweicloud.sdk.iotda.v5.model.Page;
import com.huaweicloud.sdk.iotda.v5.model.ProductSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IoTDeviceTest {


    @Autowired
    private IoTDAClient client;
    
    /**
     * 查询公共实例下的所有产品
     * @throws Exception
     */
    @Test
    public void selectProduceList() throws Exception {

        ListProductsRequest listProductsRequest = new ListProductsRequest();
        listProductsRequest.setLimit(50);
        ListProductsResponse response = client.listProducts(listProductsRequest);
        List<ProductSummary> products = response.getProducts();
        System.out.println(products);
    }
    
}