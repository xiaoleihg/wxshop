package com.huang.params;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class GoodsParams {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private String categoryId;

}
