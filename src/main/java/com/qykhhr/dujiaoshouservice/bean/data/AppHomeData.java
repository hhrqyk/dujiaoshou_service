package com.qykhhr.dujiaoshouservice.bean.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppHomeData {
    private String id;
    private List<String> imageList;
    private List<String> titleList;
    private String notice;
}
