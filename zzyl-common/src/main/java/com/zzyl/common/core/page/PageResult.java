package com.zzyl.common.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/11 下午8:41 */
@Data
@AllArgsConstructor
public class PageResult<T> {

   private Long total;

   private List<T> rows;
}
