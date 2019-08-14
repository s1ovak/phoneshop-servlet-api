package com.es.phoneshop.model.advancedSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {
    private String description;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minStock;
    private Integer maxStock;
}
