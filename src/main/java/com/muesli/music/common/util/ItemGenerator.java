package com.muesli.music.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGenerator {
    public static List<Map<String, Object>> makeItemListMap(List<Map<String, Object>> itemList) {
        var newItemList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : itemList) {
            var newTrackMap = new HashMap<>(stringObjectMap);
            newItemList.add(newTrackMap);
        }
        return newItemList;
    }
}
